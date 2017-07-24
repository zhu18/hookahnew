package com.jusfoun.hookah.console.server.common.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.MgOrderInfoService;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RabbitMQWaitSettleListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQWaitSettleListener.class);

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @Resource
    WaitSettleRecordService waitSettleRecordService;

    @RabbitListener(queues = RabbitmqQueue.WAIT_SETTLE_ORDERS)
    public void handleWaitSettleOrders(String orderSn) {

        // 支付成功 查询订单 获取订单中商品插入到待清算记录
        List<Condition> mgfilters = new ArrayList<>();
        mgfilters.add(Condition.eq("orderSn", orderSn));
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectOne(mgfilters);
        if(orderInfoVo != null){
            List<MgOrderGoods> mgOrderGoodsList = orderInfoVo.getMgOrderGoodsList();
            List<WaitSettleRecord> waitSettleRecords = new ArrayList<>();
            if(mgOrderGoodsList != null && mgOrderGoodsList.size() > 0){
                for(MgOrderGoods mgOrderGoods : mgOrderGoodsList){
                    WaitSettleRecord waitSettleRecord = new WaitSettleRecord();
                    waitSettleRecord.setOrderSn(mgOrderGoods.getOrderSn());
                    waitSettleRecord.setGoodsId(mgOrderGoods.getGoodsId());
                    waitSettleRecord.setOrderId(orderInfoVo.getOrderId());
                    waitSettleRecord.setOrderAmount(mgOrderGoods.getGoodsPrice());
                    waitSettleRecord.setOrderTime(orderInfoVo.getAddTime());
                    waitSettleRecord.setHasSettleAmount(0L);
                    waitSettleRecord.setNoSettleAmount(mgOrderGoods.getGoodsPrice());
                    waitSettleRecord.setAddTime(new Date());
                    waitSettleRecord.setSettleStatus((byte)0);
                    waitSettleRecord.setShopName(mgOrderGoods.getAddUser());
                    waitSettleRecord.setGoodsName(mgOrderGoods.getGoodsName());
                    waitSettleRecords.add(waitSettleRecord);
                }

                int n = waitSettleRecordService.insertBatch(waitSettleRecords);
                System.out.println(n);
            }
        }
    }
}
