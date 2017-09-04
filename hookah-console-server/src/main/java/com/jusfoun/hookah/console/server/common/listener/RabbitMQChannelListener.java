package com.jusfoun.hookah.console.server.common.listener;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.console.server.util.PropertiesManager;
import com.jusfoun.hookah.core.Md5Utils;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.ChannelTransData;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.ChannelDataVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DESUtils;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.SHAUtils;
import com.jusfoun.hookah.rpc.api.ChannelService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgOrderInfoService;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

@Component
public class RabbitMQChannelListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQChannelListener.class);

    @Resource
    GoodsService goodsService;

    @RabbitListener(queues = RabbitmqQueue.CONTRACE_CENTER_CHANNEL)
    public void operaPushGoods(ChannelDataVo channelDataVo) {

        logger.info("开始执行渠道推送/撤回操作：" + (channelDataVo == null ? "未取到推送数据！": JSON.toJSONString(channelDataVo)));
        if (Objects.nonNull(channelDataVo)) {
            //推送
            try {
                channel(channelDataVo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("推送失败：" + e.getMessage());
            }
        }
        logger.info("结束执行渠道推送/撤回操作：" + (channelDataVo == null ? "未取到推送数据！":JSON.toJSONString(channelDataVo)));

    }

    private void channel(ChannelDataVo channelDataVo) throws Exception{
        int opera = channelDataVo.getOpera();
        String goodsId = channelDataVo.getGoodsId();
        GoodsVo goodVos = goodsService.findGoodsByIdChannel(goodsId);
        if(Objects.nonNull(goodVos)){
           Byte isPush =  goodVos.getIsPush();
           //判断商品是否属于推送商品
           if(HookahConstants.GOODS_IS_PUSH_YES.equals(isPush)){
               ChannelTransData channelTransData = encryptionData(goodVos,opera);
               Map<String, String> params = new HashedMap();
               params.put("transData",channelTransData.getTransData());
               params.put("checkCode",channelTransData.getCheckCode());
               params.put("timestamp",String.valueOf(channelTransData.getTimestamp()));
               //推送商品
               if(HookahConstants.CHANNEL_PUSH_OPER_PUSH == opera){
//                   String params = JSON.toJSONString(encryptionData(goods,opera));
                   HttpClientUtil.PostMethod(PropertiesManager.getInstance().getProperty("center.system.url"),params);
               //撤销商品
               } else if(HookahConstants.CHANNEL_PUSH_OPER_CANCEL == opera){
//                   String params = JSON.toJSONString(encryptionData(goods,opera));
                   HttpClientUtil.PostMethod(PropertiesManager.getInstance().getProperty("center.system.url"),params);
               }
           } else {
               logger.info("该商品不可推送。");
           }
        } else {
            logger.info("推送商品不存在。");
        }
    }

    //加密数据
    private ChannelTransData encryptionData(Object obj,int opera){
        ChannelTransData data = new ChannelTransData();
        ChannelTransData.RelationData relationData = new ChannelTransData.RelationData();
        relationData.setOpera(opera);
        relationData.setData(obj);
        data.setTimestamp(new Date().getTime());
        String skey = PropertiesManager.getInstance().getProperty("platformCode") + HookahConstants.CHANNEL_KEY;
        skey += data.getTimestamp().toString().substring(16 - skey.length());
        data.setTransData(DESUtils.enPass(JSON.toJSONString(relationData), skey));
        data.setCheckCode(Md5Utils.encoderByMd5(SHAUtils.encryptSHA(JSON.toJSONString(relationData))));
        return data;
    }

}
