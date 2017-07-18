package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.MqSenderService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    UserService userService;

    @Resource
    MqSenderService mqSenderService;

    /**
     * 发送注册短信
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sms/send", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData sendSms(@RequestParam String mobile, @RequestParam Integer type, HttpServletRequest request) {
        String code = StrUtil.random(4);
//        Map<String,String> param = new HashMap<>(1);
//        param.put("code",code);
//        param.put("mobile",mobile.substring(mobile.length()-4));

        try {
//            String vars = JsonUtils.toJson(param);
//            String templateId = HookahConstants.SmsType.values()[type].toString();
//            SMSUtilNew.send(mobile,vars,templateId);

            MessageCode messageCode = new MessageCode();
            messageCode.setCode(Integer.valueOf(HookahConstants.SmsTypeNew.values()[type].toString()));
            messageCode.setMobileNo(mobile);
            messageCode.setMobileVerfCode(code);
            //添加短信记录
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);

            //缓存短信
            redisOperate.set(HookahConstants.REDIS_SMS_CACHE_PREFIX+":"+ mobile, code, HookahConstants.SMS_DURATION_MINITE * 60);
            logger.info(redisOperate.get(mobile));
            return ReturnData.success("短信验证码已经发送");
        }catch (Exception e){
            logger.error("发送短信出错");
            return ReturnData.error("发送短信出错");
        }
    }

    /**
     * 发送注册短信
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/sms/sendToUser", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData sendSmsByUserId(@RequestParam String userId,@RequestParam Integer type, HttpServletRequest request) {
        String code = StrUtil.random(4);
        StringBuffer content = new StringBuffer();
        //content.append("验证码为：").append(code).append(",有效时间").append(HookahConstants.SMS_DURATION_MINITE).append("分钟。");


        try {
            User user = userService.selectById(userId);
            String mobile = user.getMobile();

//            Map<String,String> param = new HashMap<>(1);
//            param.put("code",code);
//            param.put("mobile",mobile.substring(mobile.length()-4));

//            String vars = JsonUtils.toJson(param);
//            String templateId = HookahConstants.SmsType.values()[type].toString();


            MessageCode messageCode = new MessageCode();
            messageCode.setCode(Integer.valueOf(HookahConstants.SmsTypeNew.values()[type].toString()));
            messageCode.setMobileNo(mobile);
            messageCode.setMobileVerfCode(code);
            //添加短信记录
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);
//            SMSUtilNew.send(mobile,vars,templateId);

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
            return ReturnData.error("短信验证码错误");
        } else {
            if (!cacheSms.equalsIgnoreCase(validSms)) {
                return ReturnData.fail("短信验证码错误");
            }
        }
        return ReturnData.success();
    }
}
