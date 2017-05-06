package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.SMSUtil;
import com.jusfoun.hookah.core.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huang lei
 * @date 2017/3/23 上午10:15
 * @desc 注册
 */
@Controller
public class SmsController {
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @Resource
    RedisOperate redisOperate;


    /**
     * 发送注册短信
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sms/send", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData sendSms(String mobile, HttpServletRequest request) {
        String code = StrUtil.random(4);
        StringBuffer content = new StringBuffer();
        content.append("验证码为：").append(code).append(",有效时间").append(HookahConstants.SMS_DURATION_MINITE).append("分钟。");

        logger.info("发送短信，接收方：{}，内容为:{},验证码为:{}",mobile,content,code);
        try {
            SMSUtil.sendSMS(mobile, content.toString());

            //缓存短信
            redisOperate.set(HookahConstants.REDIS_SMS_CACHE_PREFIX+":"+mobile, code, HookahConstants.SMS_DURATION_MINITE * 60);
            logger.info(redisOperate.get(mobile));
            return ReturnData.success("短信验证码已经发送");
        }catch (Exception e){
            logger.error("发送短信出错");
            return ReturnData.error("发送短信出错");
        }
    }


    /**
     * 校验手机验证码
     *
     * @param validSms
     * @return
     */
    @RequestMapping(value = "/sms/check", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkValidSms(String mobile, String validSms, HttpServletRequest request) {
        logger.info("mobile--validSms: {},{}", mobile, validSms);
        String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX+":"+mobile);  //从 redis 获取缓存

        if (cacheSms == null) { //验证码已过期
            return ReturnData.error("短信验证码验证未通过,短信验证码已过期");
        } else {
            if (!cacheSms.equalsIgnoreCase(validSms)) {
                return ReturnData.fail("短信验证码验证未通过,短信验证码错误");
            }
        }
        return ReturnData.success();
    }
}
