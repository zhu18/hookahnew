package com.jusfoun.hookah.webiste.common.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.GoodsCheck;
import com.jusfoun.hookah.rpc.api.GoodsCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * dx
 */

@Component
@RabbitListener(queues = RabbitmqQueue.CONTRACT_GOODSCHECK)
public class RabbitMQGookCheckListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQGookCheckListener.class);

    @Autowired
    GoodsCheckService goodsCheckService;

    @RabbitHandler
    public void process(String goodsId) {
        System.out.println("Received contract<" + goodsId + ">---------------------------------------");
        try {
            //入库操作
            GoodsCheck goodsCheck = new GoodsCheck();
            goodsCheck.setGoodsId(goodsId);
            goodsCheckService.insert(goodsCheck);
            //推送
//            SystemWebSocketHandler.sendMessage(message.getSenderId(), message.getMsgText());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}