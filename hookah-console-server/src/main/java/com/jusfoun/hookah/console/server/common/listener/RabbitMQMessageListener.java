package com.jusfoun.hookah.console.server.common.listener;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.*;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.exception.EmailException;
import com.jusfoun.hookah.core.exception.SmsException;
import com.jusfoun.hookah.core.exception.StationException;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.SMSUtilNew;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    PayAccountRecordMapper payAccountRecordMapper;

    //消息发送
    @RabbitListener(queues = RabbitmqQueue.CONTRACE_NEW_MESSAGE)
    public void sendMessage(MessageCode messageCode) {
        //通过事件编码，获取相关模板
        List<MessageTemplate> templates = messageTemplateList(messageCode.getCode() + "");
        //查询业务数据
        Map<String, String> map = new HashMap<>();
        User user = null;
        try {
            user = this.getBusinessData(messageCode.getBusinessId(), messageCode, map);
        } catch (Exception e) {
            logger.error("短信/邮件/站内信消息获取map数据失败！" + JSON.toJSONString(messageCode) + ":" + e);
            e.printStackTrace();
        }
        //选择消息发送类型
        if(templates != null) {
            for(MessageTemplate t : templates) {
                MessageSendInfo messageSendInfo = new MessageSendInfo();
                //短信发送
                try {
                    if(HookahConstants.MESSAGE_TYPE_SMS == t.getTemplateType()) {
                        if (user != null && StringUtils.isNotBlank(user.getUserId()) ) {
                            this.sendSMS(t, user.getMobile(), map, messageSendInfo);
                        } else if((user == null || !StringUtils.isNotBlank(user.getUserId()))
                                && StringUtils.isNotBlank(messageCode.getMobileNo())) {
                            if(StringUtils.isNotBlank(messageCode.getUserId()))
                                user.setUserId(messageCode.getUserId());
                            this.sendSMS(t, messageCode.getMobileNo(), map, messageSendInfo);
                        }else {
                            messageSendInfo.setSendType(HookahConstants.MESSAGE_TYPE_SMS);
                            if (user != null && StringUtils.isNotBlank(user.getUserId())) {
                                messageSendInfo.setReceiveUser(user.getUserId());
                            }
                            if ((user != null && StringUtils.isNotBlank(user.getMobile())) || StringUtils.isNotBlank(messageCode.getMobileNo())) {
                                messageSendInfo.setReceiveAddr(StringUtils.isNotBlank(messageCode.getMobileNo()) ?
                                        messageCode.getMobileNo() : user.getMobile());
                            }
                            throw new SmsException("短信发送失败:未查询到用户或者电话号码为空！");
                        }
                    }else if(HookahConstants.MESSAGE_TYPE_MAIL == t.getTemplateType()) { // 邮件发送
                        if (user != null && StringUtils.isNotBlank(user.getUserId())
                                && StringUtils.isNotBlank(user.getEmail())) {
                            this.sendMail(t, user.getEmail(), map, messageSendInfo);
                        }else if(user != null && StringUtils.isNotBlank(user.getUserId()) && !StringUtils.isNotBlank(user.getEmail()) ) {
                            messageSendInfo.setSendType(HookahConstants.MESSAGE_TYPE_MAIL);
                            messageSendInfo.setReceiveUser(user.getUserId());
                            throw new EmailException("邮件发送失败:未获取到邮箱地址！");
                        }else {
                            messageSendInfo.setSendType(HookahConstants.MESSAGE_TYPE_MAIL);
                            throw new EmailException("邮件发送失败:" + HookahConstants.MESSAGE_EXCEPTION_NOUSER);
                        }
                    }else { // 站内信发送
                        if (user != null && StringUtils.isNotBlank(user.getUserId())) {
                            this.sendStation(t, map, messageSendInfo);
                        }else {
                            messageSendInfo.setSendType(HookahConstants.MESSAGE_TYPE_STATION);
                            throw new StationException("站内信发送失败:" + HookahConstants.MESSAGE_EXCEPTION_NOUSER);
                        }
                    }
                }catch (SmsException | EmailException | StationException e) {
                    messageSendInfo.setErrorInfo(e.toString());
                    messageSendInfo.setIsSuccess(HookahConstants.LOCAL_SMS_FAIL);
                    e.printStackTrace();
                    continue;
                }
                if(user != null && StringUtils.isNotBlank(user.getUserId())) {
                    messageSendInfo.setReceiveUser(user.getUserId());
                }
                messageSendInfo.setTemplateId(t.getId());
                messageSendInfo.setSendHeader(t.getTemplateHeader());
                messageSendInfo.setSendUser("sys");
                messageSendInfo.setEventType(messageCode.getCode() + "");
                messageSendInfo.setBusinessId(messageCode.getBusinessId());
                messageSendInfoMapper.insertSelective(messageSendInfo);
            }
        }else {
            logger.info("短信/邮件/站内信消息：" + JSON.toJSONString(messageCode) + "未发送消息！");
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
    public void sendSMS(MessageTemplate template, String mobileNo, Map<String, String> map, MessageSendInfo sendInfo) throws SmsException {
        //获取模板内容
        String content = template.getTemplateContent();
        content = this.getContent(content, map);
        String retVal = SMSUtilNew.send(mobileNo, JsonUtils.toJson(map), template.getSmsTypeCode());
        if (HookahConstants.SMS_SUCCESS.equals(retVal)) {
            sendInfo.setIsSuccess(HookahConstants.LOCAL_SMS_SUCCESS);
        }else {
            sendInfo.setIsSuccess(HookahConstants.LOCAL_SMS_FAIL);
        }
        sendInfo.setSendContent(content);
        sendInfo.setSendHeader(template.getTemplateHeader());
        sendInfo.setReceiveAddr(mobileNo);
        sendInfo.setSendType(HookahConstants.MESSAGE_TYPE_SMS);
    }

    //邮件发送
    public void sendMail(MessageTemplate template, String mail, Map<String, String> map, MessageSendInfo sendInfo) throws EmailException{
        //获取模板内容
        String content = template.getTemplateContent();
        content = this.getContent(content, map);
        String retVal = mailService.send(mail, template.getTemplateHeader(), content);
        if (HookahConstants.SMS_SUCCESS.equals(retVal)) {
            sendInfo.setIsSuccess(HookahConstants.LOCAL_SMS_SUCCESS);
        }else {
            sendInfo.setIsSuccess(HookahConstants.LOCAL_SMS_FAIL);
        }
        sendInfo.setSendContent(content);
        sendInfo.setReceiveAddr(mail);
        sendInfo.setSendHeader(template.getTemplateHeader());
        sendInfo.setSendType(HookahConstants.MESSAGE_TYPE_MAIL);
    }

    //站内信发送
    public void sendStation(MessageTemplate template, Map<String, String> map, MessageSendInfo sendInfo) {
        //获取模板内容
        String content = template.getTemplateContent();
        content = this.getContent(content, map);
        sendInfo.setSendContent(content);
        sendInfo.setSendHeader(template.getTemplateHeader());
        sendInfo.setSendType(HookahConstants.MESSAGE_TYPE_STATION);
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
    public User getBusinessData(String businessId, MessageCode messageCode, Map<String, String> map) throws Exception {
        User user = new User();
        switch (messageCode.getCode()) {
            case 106:
            case 102:
            case 103:
            case 104:
            case 105:
                user.setMobile(messageCode.getMobileNo());
                List<User> users = userMapper.select(user);
                if(users != null && users.size() > 0)
                    user = users.get(0);
            case 101:
                map.put("code", messageCode.getMobileVerfCode());
                if(messageCode.getMobileNo().length() > 4)
                    map.put("mobile", messageCode.getMobileNo().substring(messageCode.getMobileNo().length() - 4));
                else
                    map.put("mobile", messageCode.getMobileNo());
                break;
            case 201:
            case 202:
                Organization organization = organizationMapper.selectByPrimaryKey(messageCode.getBusinessId());
                user = userMapper.getOrgUser(messageCode.getBusinessId());
                map.put("userName", user.getUserName());
                map.put("orgName", organization.getOrgName());
                break;
            case 401:
            case 402:
                user = userMapper.selectByPrimaryKey(messageCode.getBusinessId());
                map.put("userName", user.getUserId());
                break;
            case 501:
            case 502:
            case 503:
                GoodsCheck goodsCheck = goodsCheckMapper.selectByPrimaryKey(businessId);
                Goods goods = goodsMapper.selectByPrimaryKey(goodsCheck.getGoodsId());
                map.put("goodsSn", goodsCheck.getGoodsSn());
                map.put("goodsName", goodsCheck.getGoodsName());
                map.put("goodsId", goodsCheck.getGoodsId());
                map.put("time", DateUtils.toDateText(goodsCheck.getCheckTime(), HookahConstants.TIME_FORMAT));
                user = userMapper.selectByPrimaryKey(goods.getAddUser());
                map.put("userName", user.getUserName());
                break;
            case 601:
            case 602:
                PayAccountRecord payAccountRecord = payAccountRecordMapper.selectByPrimaryKey(messageCode.getBusinessId());
                user = userMapper.selectByPrimaryKey(payAccountRecord.getUserId());
                map.put("userName", user.getUserName());
                map.put("time", DateUtils.toDateText(payAccountRecord.getAddTime(), HookahConstants.TIME_FORMAT));
                map.put("money", String.valueOf(longToMoney(payAccountRecord.getMoney())));
                break;
        }
        return user;
    }

    /**
     * 区号+座机号码+分机号码
     * @param fixedPhone
     * @return
     */
    public boolean isFixedPhone(String fixedPhone){
        String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }

    /**
     * long转钱
     * @param num
     * @return
     */
    public BigDecimal longToMoney(long num) {
        BigDecimal totalFee = new BigDecimal(num);
        BigDecimal d100 = new BigDecimal(100);
        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
        return fee;
    }
}
