package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.config.mq.RabbitMqSender;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jsshao
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class MqSenderServiceImpl implements MqSenderService {

    @Autowired
    RabbitMqSender sender;

    @Override
    public void sendTopic(String routeKey, Object obj) {
        sender.sendRabbitmqTopic(routeKey, obj);
    }

    @Override
    public void sendDirect(String routeKey, Object obj) {
        sender.sendRabbitmqDirect(routeKey, obj);
    }
}
