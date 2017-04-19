package com.jusfoun.hookah.webiste.common.listener;

/**
 * @author:jsshao
 * @date: 2017-4-18
 */

import com.google.gson.Gson;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.SysMessage;
import com.jusfoun.hookah.webiste.common.handler.SystemWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = RabbitmqQueue.CONTRACE_MESSAGE)
public class RabbitMQMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQMessageListener.class);


    @RabbitHandler
    public void process(SysMessage message) {
        System.out.println("Received contract<" + new Gson().toJson(message) + ">---------------------------------------");
        try {
            //入库操作

            //推送
            SystemWebSocketHandler.sendMessage(message.getSenderId(), message.getMsgText());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}