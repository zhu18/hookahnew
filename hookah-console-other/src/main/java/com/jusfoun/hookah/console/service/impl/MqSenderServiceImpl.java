package com.jusfoun.hookah.console.service.impl;

import com.jusfoun.hookah.console.config.mq.RabbitMqSender;
import com.jusfoun.hookah.rpc.api.other.MqSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class MqSenderServiceImpl implements MqSenderService {
    @Autowired
    RabbitMqSender sender;

    @Override
    public void send(String msg) {
        sender.send(msg);
    }
}
