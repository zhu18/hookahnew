package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.mongo.MgCaptcha;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgCaptchaService;
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
public class MgCaptchaServiceImpl extends GenericMongoServiceImpl<MgCaptcha, String> implements MgCaptchaService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public MgCaptcha insert(MgCaptcha cpt) {  //临时借用这个类
        //缓存短信
        cpt.setCreateTime(new Date());
        Calendar cal = Calendar. getInstance ();
        cal.set(Calendar.MINUTE , Calendar.MINUTE+ HookahConstants.CAPTCHA_DURATION_MINITE ) ;  //计算过期时间
        cpt.setExpireTime(cal.getTime());
        mongoTemplate.insert(cpt);
        return cpt;
    }
}
