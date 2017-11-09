package com.jusfoun.hookah.crowd.config.mq;

/**
 * @author:jsshao
 * @date: 2017-4-12
 */

import com.jusfoun.hookah.core.constants.RabbitmqExchange;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.constants.RabbitmqRoutekey;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 新增业务时，两个步骤
 * 1、增加 queue bean ，参照queueXXXX方法
 * 2增加 queue 和 exchange的binding,参照 bindingExchangeXXXX方法(topic类似于模糊查询，对不同的routekey中特定关键词进行消费)
 * @author jsshao
 */
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqExchangeConfig {

    /*************   开始 Exchange 交换机 定义  *******************/
    /**
     * 主题型 交换机
     * 默认:durable = true, autoDelete = false
     *
     * @param rabbitAdmin
     * @return
     */
    @Bean
    TopicExchange contractTopicExchangeDurable(RabbitAdmin rabbitAdmin) {
        TopicExchange contractTopicExchange = new TopicExchange(RabbitmqExchange.CONTRACT_TOPIC);
        rabbitAdmin.declareExchange(contractTopicExchange);
        return contractTopicExchange;
    }

    /**
     * 直连型交换机
     */
    @Bean
    DirectExchange contractDirectExchange(RabbitAdmin rabbitAdmin) {
        DirectExchange contractDirectExchange = new DirectExchange(RabbitmqExchange.CONTRACT_DIRECT);
        rabbitAdmin.declareExchange(contractDirectExchange);
        return contractDirectExchange;
    }

    /*************   开始 Queue队列  定义  *******************/

    @Bean
    Queue queueMessage(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqQueue.CONTRACE_MESSAGE, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueCheck(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqQueue.CONTRACT_GOODSCHECK, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueGoodsId(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqQueue.CONTRACE_GOODS_ID, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueNewMessage(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqQueue.CONTRACE_NEW_MESSAGE, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueWaitSettleOrder(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqQueue.WAIT_SETTLE_ORDERS, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Queue queueChannel(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(RabbitmqQueue.CONTRACE_CENTER_CHANNEL, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /*************   开始  将 queue 绑定到 指定交换机   *******************/


    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueMessage).to(exchange).with(RabbitmqQueue.CONTRACE_MESSAGE);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeCommonMessage(Queue queueMessage, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueMessage).to(exchange).with(RabbitmqRoutekey.CONTRACE_RK_MESSAGE_COMMON);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeSystemMessage(Queue queueMessage, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueMessage).to(exchange).with(RabbitmqRoutekey.CONTRACE_RK_MESSAGE_SYSTEM);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeCheck(Queue queueCheck, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueCheck).to(exchange).with(RabbitmqQueue.CONTRACT_GOODSCHECK);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeGoodsId(Queue queueGoodsId, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueGoodsId).to(exchange).with(RabbitmqQueue.CONTRACE_GOODS_ID);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeCheck(Queue queueCheck, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueCheck).to(exchange).with(RabbitmqQueue.CONTRACT_GOODSCHECK);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeGoodsId(Queue queueGoodsId, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueGoodsId).to(exchange).with(RabbitmqQueue.CONTRACE_GOODS_ID);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeNewMessage(Queue queueNewMessage, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueNewMessage).to(exchange).with(RabbitmqQueue.CONTRACE_NEW_MESSAGE);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeWaitSettleOrder(Queue queueWaitSettleOrder, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueWaitSettleOrder).to(exchange).with(RabbitmqQueue.WAIT_SETTLE_ORDERS);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeChannel(Queue queueChannel, DirectExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueChannel).to(exchange).with(RabbitmqQueue.CONTRACE_CENTER_CHANNEL);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    Binding bindingExchangeChannel(Queue queueChannel, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueChannel).to(exchange).with(RabbitmqQueue.CONTRACE_CENTER_CHANNEL);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }


}