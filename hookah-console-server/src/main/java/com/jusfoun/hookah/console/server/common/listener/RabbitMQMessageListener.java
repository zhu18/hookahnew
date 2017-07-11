package com.jusfoun.hookah.console.server.common.listener;

import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.GoodsMapper;
import com.jusfoun.hookah.core.dao.MessageSendInfoMapper;
import com.jusfoun.hookah.core.dao.MessageTemplateMapper;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.MessageTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangjl on 2017-7-11.
 * 消息发送
 */
@Component
public class RabbitMQMessageListener {
    @Autowired
    GoodsMapper mapper;
    @Autowired
    MessageSendInfoMapper messageSendInfoMapper;
    @Autowired
    MessageTemplateMapper messageTemplateMapper;

    //消息发送
    @RabbitListener(queues = RabbitmqQueue.CONTRACE_NEW_MESSAGE)
    public void sendMessage(MessageCode messageCode) {
        //通过事件编码，获取相关模板
        List<MessageTemplate> templates = messageTemplateList(messageCode.getCode() + "");
        //选择消息发送类型
    }

    /**
     * 通过事件编码，获取相关模板
     * @param eventTypeCode
     * @return
     */
    List<MessageTemplate> messageTemplateList(String eventTypeCode) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setEventType(eventTypeCode);
        messageTemplate.setIsVaild((byte)1);
        return messageTemplateMapper.select(messageTemplate);
    }
}
