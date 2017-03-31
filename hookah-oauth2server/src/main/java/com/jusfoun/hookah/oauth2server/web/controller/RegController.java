package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgSmsValidate;
import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.rpc.api.MgSmsValidateService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public ReturnData pReg(UserValidVo user){
        //boolean valid = true;
        //1、校验图片验证码 ,=======>可以跳过这步，我觉得不校验问题也不大
        String captcha = user.getCaptcha();

        //2、校验短信验证码
            //获取库里缓存的验证码
        /*
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("phoneNum",user.getMobile()));
        filters.add(Condition.eq("validCode",user.getValidSms()));
        MgSmsValidate sms = mgSmsValidateService.selectOne(filters);
        if(sms==null){ //验证码错误或者已过期
            return ReturnData.error("短信验证码错误或者已过期");
        }
        */

        //3、校验密码一致
        String password = user.getPassword().trim();
        String passwordRepeat = user.getPasswordRepeat().trim();
        if(StringUtils.isBlank(password) || StringUtils.isBlank(passwordRepeat) ){
            return  ReturnData.error("密码或者确认密码不能为空");
        }
        if(!password.equals(passwordRepeat)){
            return  ReturnData.error("密码与确认密码不一致");
        }

        if(password.length()<6){
            return  ReturnData.error("密码过短");
        }

        //其他校验规则

        User user1 = userService.insert((User)user);
        return ReturnData.success(user1);
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

            sms = new MgSmsValidate();
            sms.setPhoneNum(phoneNum);
            sms.setSmsContent(content.toString());
            sms.setValidCode(code);
            mgSmsValidateService.insert(sms);
            return ReturnData.success("短信验证码已经发送");
        }else{    //刚刚发送过验证码，避免重复发送
            ReturnData.error("请不要频繁发送短信验证码");
        }
        return  ReturnData.fail();
    }
}
