package com.jusfoun.hookah.console.server.common.listener;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.*;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.SMSUtilNew;
import com.jusfoun.hookah.rpc.api.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjl on 2017-7-11.
 * 消息发送
 */
@Component
public class RabbitMQMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQEsGoodsListener.class);
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsCheckMapper goodsCheckMapper;
    @Autowired
    MessageSendInfoMapper messageSendInfoMapper;
    @Autowired
    MessageTemplateMapper messageTemplateMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MailService mailService;

    //消息发送
    @RabbitListener(queues = RabbitmqQueue.CONTRACE_NEW_MESSAGE)
    public void sendMessage(MessageCode messageCode) {
        //通过事件编码，获取相关模板
        List<MessageTemplate> templates = messageTemplateList(messageCode.getCode() + "");
        //查询业务数据
        Map<String, String> map = new HashMap<>();
        User user = this.getBusinessData(messageCode.getBusinessId(), messageCode.getCode(), map);
        //选择消息发送类型
        if(templates != null) {
            for(MessageTemplate t : templates) {
                MessageSendInfo messageSendInfo;
                //短信发送(需要调整)
                if(HookahConstants.MESSAGE_TYPE_SMS == t.getTemplateType()) {
                    messageSendInfo = this.sendSMS(t, user.getMobile(), map);
                }else if(HookahConstants.MESSAGE_TYPE_MAIL == t.getTemplateType()) { // 邮件发送
                    messageSendInfo = this.sendMail(t, user.getEmail(), map);
                }else { // 站内信发送
                    messageSendInfo = this.sendStation(t, map);
                }
                messageSendInfo.setReceiveUser(user.getUserId());
                messageSendInfo.setTemplateId(t.getId());
                messageSendInfo.setSendUser("sys");
                messageSendInfo.setReceiveUser(user.getUserId());
                messageSendInfo.setEventType(messageCode.getCode() + "");
                messageSendInfo.setBusinessId(messageCode.getBusinessId());
                messageSendInfoMapper.insertSelective(messageSendInfo);
            }
        }else {
            logger.info("短信/邮件/站内信消息：" + messageCode.getCode() + "未查到对应模板,"
                    + messageCode.getBusinessId() + "未发送消息！");
        }
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

    // 短信发送
    public MessageSendInfo sendSMS(MessageTemplate template, String mobileNo, Map<String, String> map) {
        MessageSendInfo sendInfo = new MessageSendInfo();
        //获取模板内容
        String content = template.getTemplateContent();
        content = this.getContent(content, map);
        String retVal = SMSUtilNew.send(mobileNo, JsonUtils.toJson(map), template.getSmsTypeCode());
        if (HookahConstants.SMS_SUCCESS.equals(retVal)) {
            sendInfo.setIsSuccess(HookahConstants.LOCAL_SMS_SUCCESS);
        }else {
            sendInfo.setIsDelete(HookahConstants.LOCAL_SMS_FAIL);
        }
        sendInfo.setSendContent(content);
        sendInfo.setReceiveAddr(mobileNo);
        sendInfo.setSendType(HookahConstants.MESSAGE_TYPE_SMS);
        return sendInfo;
    }

    //邮件发送
    public MessageSendInfo sendMail(MessageTemplate template, String mail, Map<String, String> map) {
        MessageSendInfo sendInfo = new MessageSendInfo();
        //获取模板内容
        String content = template.getTemplateContent();
        content = this.getContent(content, map);
        mailService.send(mail, template.getTemplateHeader(), content);
        sendInfo.setSendContent(content);
        sendInfo.setReceiveAddr(mail);
        sendInfo.setSendHeader(template.getTemplateHeader());
        sendInfo.setSendType(HookahConstants.MESSAGE_TYPE_MAIL);
        return sendInfo;

    }

    //站内信发送
    public MessageSendInfo sendStation(MessageTemplate template, Map<String, String> map) {
        MessageSendInfo sendInfo = new MessageSendInfo();
        //获取模板内容
        String content = template.getTemplateContent();
        content = this.getContent(content, map);
        sendInfo.setSendContent(content);
        sendInfo.setSendHeader(template.getTemplateHeader());
        sendInfo.setSendType(HookahConstants.MESSAGE_TYPE_STATION);
        return sendInfo;
    }

    // 模板内容替换
    public String getContent(String content, Map<String, String> map) {
        String regex = "\\[[a-zA-Z\\d]+]";
        Pattern pattern = Pattern.compile(regex); // 内部非线程安全，须保证单线程访问
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            String key = matcher.group(); // 正则匹配到的关键词
            String mapK = key.replaceAll("[\\[|\\]]", ""); // 替换掉关键词两边的[]
            if("".equals(mapK))continue; // 防止空白的[]
            String val = map.get(mapK); // 获取对应的val值
            content = content.replaceAll(key.replace("[", "\\["), val);
        }
        return content;
    }

    // 获取业务数据
    public User getBusinessData(String businessId, Integer code, Map<String, String> map) {
        User user = new User();
        switch (code) {
            case 501:
            case 502:
            case 503:
                GoodsCheck goodsCheck = goodsCheckMapper.selectByPrimaryKey(businessId);
                Goods goods = goodsMapper.selectByPrimaryKey(goodsCheck.getGoodsId());
                map.put("goodsSn", goodsCheck.getGoodsSn());
                map.put("goodsName", goodsCheck.getGoodsName());
                map.put("time", DateUtils.toDateText(goodsCheck.getCheckTime(), "yyyy年MM月dd日HH时mm分ss秒"));
                user = userMapper.selectByPrimaryKey(goods.getAddUser());
                map.put("userName", user.getUserName());
                break;
        }
        return user;
    }
}
