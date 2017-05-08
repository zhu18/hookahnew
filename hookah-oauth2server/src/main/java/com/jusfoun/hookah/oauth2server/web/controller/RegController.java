package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.Constants;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.exception.*;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.oauth2server.config.MyProps;
import com.jusfoun.hookah.rpc.api.MgSmsValidateService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.beanutils.BeanUtils;
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
    RedisOperate redisOperate;

    @Resource
    UserService userService;

    @Resource
    private MgSmsValidateService mgSmsValidateService;

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String reg(Model model) {
        return "register";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData pReg(UserValidVo user, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        boolean isExists = true;
        List<Condition> filters = new ArrayList();
        //1、校验图片验证码 ,=======>可以跳过这步，我觉得不校验问题也不大
        try {
            String captcha = user.getCaptcha();
            HttpSession session = request.getSession();
            String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

            if (!value.equalsIgnoreCase(captcha)) {
                throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
            }
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            //2、校验短信验证码
            //获取库里缓存的验证码

            String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX+":"+user.getMobile());  //从 redis 获取缓存
            if (cacheSms == null) { //验证码已过期
                throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
            } else {
                if (!cacheSms.equalsIgnoreCase(user.getValidSms())) {
                    throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                }
            }
            redisOperate.del(user.getMobile());  //删除缓存

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
//            filters.clear();
//            filters.add(Condition.eq("email", user.getEmail()));
//            isExists = userService.exists(filters);
//            if (isExists) {
//                throw new UserRegExistEmailException("该邮箱已经被注册");
//            }
            //其他校验规则
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }

        Map<String, String> host = myProps.getHost();
        Map<String, String> site = myProps.getSite();
        user.setAddTime(new Date());
        user.setRegTime(new Date());
        user.setIsEnable((byte) 1);
        user.setHeadImg(site.get("user-default-img"));
        User regUser = userService.insert((User) user);
        //redirectAttributes.addAttribute(regUser);

        //TODO...登录日志
        logger.info("用户[" + user.getUserName() + "]注册成功(这里可以进行一些注册通过后的一些系统参数初始化操作)");
//            return "redirect:"+host.get("website")+"/login";
        return ReturnData.success("注册成功");
    }


    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/reg/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkEmail(String email) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("email", email));
        boolean isExists = userService.exists(filters);
        if (isExists) {
            return ReturnData.fail("该邮箱已经被注册");
        }
        return ReturnData.success();
    }

    /**
     * 校验邮箱
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/reg/checkMobile", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkMobile(String mobile) {
        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("mobile", mobile));
        boolean isExists = userService.exists(filters);
        if (isExists) {
            return ReturnData.fail("该手机已经被注册");
        }
        return ReturnData.success();
    }

    /**
     * 校验邮箱
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/reg/checkUsername", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData checkUserName(String username) {
        List<Condition> filters = new ArrayList(1);
        filters.add(Condition.eq("userName", username));
        boolean isExists = userService.exists(filters);
        if (isExists) {
            return ReturnData.fail("该用户名已经被注册");
        }
        return ReturnData.success();
    }

    @RequestMapping(value = "/findPwd")
    public String findPwd(Integer step, UserValidVo userVo,HttpServletRequest request, Model model) {
        if (step == null) step = 1;
        List<Condition> filters = new ArrayList<>(2);
        switch (step) {
            case 1: //get or post
                return "loginName";
            case 2: //post
                try {
                    String captcha = userVo.getCaptcha();
                    HttpSession session = request.getSession();
                    String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

                    if (!value.equalsIgnoreCase(captcha)) {
                        throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
                    }
                    session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
                    filters.add(Condition.eq("userName",userVo.getUserName()));
                    User user = userService.selectOne(filters);
                    if(user!=null){
                        model.addAttribute("user",user);
                        return "checkMobile";
                    }
                    else{
                        throw new Exception("该用户不存在！");
                    }
                } catch (Exception e) {
                    model.addAttribute("message",e.getMessage());
                    return "loginName";
                }
            case 3:  //post
                //校验短信验证码
                //获取库里缓存的验证码
                try {
                    String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX + ":" + userVo.getMobile());  //从 redis 获取缓存
                    if (cacheSms == null) { //验证码已过期
                        throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
                    } else {
                        if (!cacheSms.equalsIgnoreCase(userVo.getValidSms())) {
                            throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                        }
                    }
                    redisOperate.del(userVo.getMobile());  //删除缓存
                    return "resetPwd";
                } catch (Exception e) {
                    model.addAttribute("message",e.getMessage());
                    return "checkMobile";
                }
            case 4: //post
                try {
                    //校验密码一致
                    String password = userVo.getPassword().trim();
                    String passwordRepeat = userVo.getPasswordRepeat().trim();
                    if (StringUtils.isBlank(password) || StringUtils.isBlank(passwordRepeat)) {
                        throw new UserRegEmptyPwdException("密码或者确认密码不能为空");
                    }
                    if (!password.equals(passwordRepeat)) {
                        throw new UserRegConfirmPwdException("密码与确认密码不一致");
                    }
                    if (password.length() < 6) {
                        throw new UserRegSimplePwdException("密码过于简单");
                    }
                    User user = new User();
                    BeanUtils.copyProperties(user, userVo);

                    filters.add(Condition.eq("mobile", userVo.getMobile()));
                    userService.updateByCondition(user, filters);
                    return "complete";
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    return "resetPwd";
                }
            default:
                return "findPwd";
        }
    }

}
