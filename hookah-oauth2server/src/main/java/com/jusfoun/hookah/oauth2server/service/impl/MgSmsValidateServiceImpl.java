package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.mongo.MgSmsValidate;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.core.utils.SMSUtil;
import com.jusfoun.hookah.rpc.api.MgSmsValidateService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class MgSmsValidateServiceImpl extends GenericMongoServiceImpl<MgSmsValidate, String> implements MgSmsValidateService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public MgSmsValidate insert(MgSmsValidate sms) {
        //发送短信
        logger.info("发送短信，接收方：{}，内容为:{}",sms.getPhoneNum(),sms.getSmsContent());
        SMSUtil.sendSMS(sms.getPhoneNum(),sms.getSmsContent());

        //缓存短信
        sms.setSendTime(new Date());
        Calendar cal = Calendar. getInstance ();
        cal.set(Calendar.MINUTE , Calendar.SECOND+ HookahConstants.SMS_DURATION_SECONDS ) ;  //计算过期时间
        sms.setExpireTime(cal.getTime());
        mongoTemplate.insert(sms);
        return sms;
    }
}
