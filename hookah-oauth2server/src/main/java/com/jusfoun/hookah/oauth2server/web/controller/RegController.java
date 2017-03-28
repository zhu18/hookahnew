package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgSmsValidate;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.SMSUtil;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.MgSmsValidateService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/23 上午10:15
 * @desc 注册
 */
@Controller
public class RegController {

    @Resource
    UserService userService;

    @Resource
    private MgSmsValidateService mgSmsValidateService;

    @RequestMapping(value = "/reg",method = RequestMethod.GET)
    public String reg(Model model){
        return "register";
    }

    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    @ResponseBody
    public Object pReg(User user){
        User user1 = userService.insert(user);
        return user;
    }

    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public ReturnData postLogin(String phoneNum, RedirectAttributes redirectAttributes) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("phoneNum",phoneNum));
        MgSmsValidate sms = mgSmsValidateService.selectOne(filters);
        if(sms==null){//如果近期没有发送过
            String code = StrUtil.random(4);
            StringBuffer content =  new StringBuffer();
            content.append("验证码为：").append(code).append(",有效时间").append(HookahConstants.SMS_DURATION_SECONDS).append("秒。");
            SMSUtil.sendSMS(phoneNum,content.toString());

            Calendar cal = Calendar. getInstance ();
            cal.set(Calendar.MINUTE , Calendar.SECOND+HookahConstants.SMS_DURATION_SECONDS ) ;  //计算过期时间
            sms = new MgSmsValidate();
            sms.setPhoneNum(phoneNum);
            sms.setSendTime(new Date());
            sms.setSmsContent(content.toString());
            sms.setValidCode(code);
            sms.setExpireTime(cal.getTime());
            mgSmsValidateService.insert(sms);
            return ReturnData.success("短信验证码已经发送");
        }else{    //刚刚发送过验证码，避免重复发送
            ReturnData.error("请不要频繁发送短信验证码");
        }
        return  ReturnData.fail();
    }
}
