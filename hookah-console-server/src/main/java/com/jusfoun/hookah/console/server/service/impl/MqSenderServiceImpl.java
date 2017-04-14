package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.config.mq.RabbitMqSender;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.SysMessage;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public void sendTopic(String msg) {
        SysMessage message = new SysMessage();
        try {
            Subject subject = SecurityUtils.getSubject();

            Map userMap = null;
            if (subject != null && subject.isAuthenticated()) {
                userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            }
            message.setSenderId((String) userMap.get("userId"));
        }catch (Exception e){
            e.printStackTrace();
        }
        message.setCreateTime(new Date());
        message.setMsgText(msg);
        message.setType((short)1);

        sender.sendRabbitmqTopic(RabbitmqQueue.CONTRACE_MESSAGE,message);
    }

    @Override
    public void sendDirect(String msg) {
        SysMessage message = new SysMessage();
        try {
            Subject subject = SecurityUtils.getSubject();

            Map userMap = null;
            if (subject != null && subject.isAuthenticated()) {
                userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            }
            message.setSenderId((String) userMap.get("userId"));
        }catch (Exception e){
            e.printStackTrace();
        }
        message.setCreateTime(new Date());
        message.setMsgText(msg);
        message.setType((short)1);

        sender.sendRabbitmqDirect(RabbitmqQueue.CONTRACE_MESSAGE,message);
    }
}
