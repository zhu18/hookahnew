package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsCheckMapper;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/7/0007.
 */
@Service
public class GoodsCheckServiceImpl extends GenericServiceImpl<GoodsCheck, String> implements GoodsCheckService {

    @Resource
    GoodsCheckMapper goodsCheckMapper;

    @Resource
    public void setDao(GoodsCheckMapper goodsCheckMapper) {
        super.setDao(goodsCheckMapper);
    }

    @Resource
    GoodsService goodsService;

    @Resource
    MqSenderService mqSenderService;

    @Override
    @Transactional
    public void insertRecord(GoodsCheck goodsCheck) throws HookahException {
        if (goodsCheck == null)
            throw new HookahException("空数据！");

        goodsCheck = super.insert(goodsCheck);

        if(goodsCheck == null)
            throw new HookahException("操作失败");

        Goods goods = new Goods();
        goods.setGoodsId(goodsCheck.getGoodsId());
        MessageCode messageCode = new MessageCode();
        messageCode.setBusinessId(goodsCheck.getId());
        if(goodsCheck.getCheckStatus() == 1){
            goods.setCheckStatus(Byte.parseByte(HookahConstants.CheckStatus.audit_success.getCode()));
            goodsService.updateByIdSelective(goods);
            messageCode.setCode(HookahConstants.MESSAGE_501);
            try {
                //添加商品到ES
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_GOODS_ID, goodsCheck.getGoodsId());
                //发送消息，下发短信/站内信/邮件
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);
            }catch (Exception e){
                logger.error("审核处理消息队列发送失败：" + e.getMessage());
            }
        }else if(goodsCheck.getCheckStatus() == 2){
            goods.setCheckStatus(Byte.parseByte(HookahConstants.CheckStatus.audit_fail.getCode()));
            goodsService.updateByIdSelective(goods);
            //发送消息，下发短信/站内信/邮件
            messageCode.setCode(HookahConstants.MESSAGE_502);
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);
        }
    }

    @Override
    public GoodsCheck selectOneByGoodsId(String goodsId) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("goodsId", goodsId));
        return super.selectOne(filters);
    }
}
