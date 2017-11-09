package com.jusfoun.hookah.crowd.config.mq;

import com.jusfoun.hookah.core.constants.RabbitmqExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqSender.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 发送到 指定routekey的指定queue
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqDirect(String routeKey,Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOGGER.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitmqExchange.CONTRACT_DIRECT, routeKey , obj, correlationData);
    }

    /**
     * 所有发送到Topic Exchange的消息被转发到所有关心RouteKey中指定Topic的Queue上
     * @param routeKey
     * @param obj
     */
    public void sendRabbitmqTopic(String routeKey,Object obj) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOGGER.info("send: " + correlationData.getId());
        this.rabbitTemplate.convertAndSend(RabbitmqExchange.CONTRACT_TOPIC, routeKey , obj, correlationData);
    }


    /** 回调方法 */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        LOGGER.info("confirm: " + correlationData.getId());
    }
}
