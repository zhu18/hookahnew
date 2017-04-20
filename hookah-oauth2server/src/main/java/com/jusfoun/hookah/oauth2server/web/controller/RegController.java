package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.Constants;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgSmsValidate;
import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.exception.*;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.oauth2server.config.MyProps;
import com.jusfoun.hookah.rpc.api.MgSmsValidateService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/3/23 上午10:15
 * @desc 注册
 */
@Controller
public class RegController {
    private static final Logger logger = LoggerFactory.getLogger(RegController.class);

    @Autowired
    MyProps myProps;

    @Resource
    UserService userService;

    @Resource
    private MgSmsValidateService mgSmsValidateService;

    @RequestMapping(value = "/reg",method = RequestMethod.GET)
    public String reg(Model model){
        return "register";
    }

    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public String pReg(UserValidVo user, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        boolean isExists = true,valid=true;
        List<Condition> filters = new ArrayList();
        //1、校验图片验证码 ,=======>可以跳过这步，我觉得不校验问题也不大
        try {
            String captcha = user.getCaptcha();
            HttpSession session = request.getSession();
            String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

            if (!value.equals(captcha)) {
                throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
            }
            //2、校验短信验证码
            //获取库里缓存的验证码

            filters.add(Condition.eq("phoneNum", user.getMobile()));
            MgSmsValidate sms = mgSmsValidateService.selectOne(filters);
            if (sms == null) { //验证码错误或者已过期
                throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
            } else {
                if (!sms.getSmsContent().equals(user.getValidSms())) {
                    throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                }
            }

            //3、校验密码一致
            String password = user.getPassword().trim();
            String passwordRepeat = user.getPasswordRepeat().trim();
            if (StringUtils.isBlank(password) || StringUtils.isBlank(passwordRepeat)) {
                throw new UserRegEmptyPwdException("密码或者确认密码不能为空");
            }
            if (!password.equals(passwordRepeat)) {
                throw new UserRegConfirmPwdException("密码与确认密码不一致");
            }
            if (password.length() < 6) {
                throw new UserRegSimplePwdException("密码过于简单");
            }

            //4，校验重复
            //4.1 用户名
            filters.clear();
            filters.add(Condition.eq("userName", user.getUserName()));
            isExists = userService.exists(filters);
            if (isExists) {
                throw new UserRegExistUsernameException("该邮箱已经被注册");
            }
            //4.2 手机
            filters.clear();
            filters.add(Condition.eq("mobile", user.getMobile()));
            isExists = userService.exists(filters);
            if (isExists) {
                throw new UserRegExistMobileException("该手机已经被注册");
            }
            //4.1 邮箱
            filters.clear();
            filters.add(Condition.eq("email", user.getEmail()));
            isExists = userService.exists(filters);
            if (isExists) {
                throw new UserRegExistEmailException("该邮箱已经被注册");
            }
            //其他校验规则
        } catch (Exception e) {
            valid = false;
            logger.info(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        Map<String,String> host = myProps.getHost();
        Map<String,String> site = myProps.getSite();
        if(valid){
            user.setAddTime(new Date());
            user.setRegTime(new Date());
            user.setIsEnable((byte)1);
            user.setHeadImg(site.get("user-default-img"));
            User regUser = userService.insert((User)user);
            //redirectAttributes.addAttribute(regUser);

            //TODO...登录日志
            logger.info("用户[" + user.getUserName() + "]注册成功(这里可以进行一些注册通过后的一些系统参数初始化操作)");
            return "redirect:"+host.get("website")+"/login";
        }else {
            return "redirect:"+host.get("website")+"/reg";
        }
    }

    /**
     * 发送注册短信
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/reg/sendSms", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData sendSms(String phoneNum) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("phoneNum",phoneNum));
        MgSmsValidate sms = mgSmsValidateService.selectOne(filters);
        if(sms==null){//如果近期没有发送过
            String code = StrUtil.random(4);
            StringBuffer content =  new StringBuffer();
            content.append("验证码为：").append(code).append(",有效时间").append(HookahConstants.SMS_DURATION_MINITE).append("分钟。");

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

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    @RequestMapping(value = "/reg/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkEmail(String email) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("email",email));
        boolean isExists = userService.exists(filters);
        if(isExists){
            return ReturnData.fail("该邮箱已经被注册");
        }
        return  ReturnData.success();
    }

    /**
     * 校验邮箱
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/reg/checkMobile", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkMobile(String mobile) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("mobile",mobile));
        boolean isExists = userService.exists(filters);
        if(isExists){
            return ReturnData.fail("该手机已经被注册");
        }
        return  ReturnData.success();
    }

    /**
     * 校验邮箱
     * @param username
     * @return
     */
    @RequestMapping(value = "/reg/checkUsername", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkUserName(String username) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("userName",username));
        boolean isExists = userService.exists(filters);
        if(isExists){
            return ReturnData.fail("该用户名已经被注册");
        }
        return  ReturnData.success();
    }
}
