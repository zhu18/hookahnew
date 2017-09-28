package com.jusfoun.hookah.oauth2server.web.controller;

import com.google.code.kaptcha.Constants;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.UserValidVo;
import com.jusfoun.hookah.core.exception.UserRegExpiredSmsException;
import com.jusfoun.hookah.core.exception.UserRegInvalidCaptchaException;
import com.jusfoun.hookah.core.exception.UserRegInvalidSmsException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.oauth2server.config.MyProps;
import com.jusfoun.hookah.rpc.api.LoginLogService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2017/9/25.
 */
@Controller
public class BindWeChatController {
    private static final Logger logger = LoggerFactory.getLogger(BindWeChatController.class);

    @Autowired
    MyProps myProps;

    @Resource
    RedisOperate redisOperate;

    @Resource
    UserService userService;

    @Resource
    PayAccountService payAccountService;

    @Resource
    LoginLogService loginLogService;

    @RequestMapping(value = "/reg/bindWeChat", method = RequestMethod.POST)
    public String bindWeChat(UserValidVo user, RedirectAttributes redirectAttributes, HttpServletRequest request){
        boolean isExists = true;
        List<Condition> filters = new ArrayList();
        try {
            //1、校验图片验证码
            String captcha = user.getCaptcha();
            HttpSession session = request.getSession();
            String value = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

            if (!value.equalsIgnoreCase(captcha)) {
                throw new UserRegInvalidCaptchaException("图片验证码验证未通过,验证码错误");
            }
            //2、校验手机验证码
            String cacheSms = redisOperate.get(HookahConstants.REDIS_SMS_CACHE_PREFIX + ":" + user.getMobile());  //从 redis 获取缓存
            if (cacheSms == null) { //验证码已过期
                throw new UserRegExpiredSmsException("短信验证码验证未通过,短信验证码已过期");
            } else {
                if (!cacheSms.equalsIgnoreCase(user.getValidSms())) {
                    throw new UserRegInvalidSmsException("短信验证码验证未通过,短信验证码错误");
                }
            }
            redisOperate.del(user.getMobile());  //删除缓存
        } catch (UserRegInvalidCaptchaException e){
            redirectAttributes.addFlashAttribute("message", "图片验证码验证未通过,验证码错误");
            return "";  //重定向到绑定微信页面
        } catch (UserRegExpiredSmsException e){
            redirectAttributes.addFlashAttribute("message", "短信验证码验证未通过,短信验证码已过期");
            return "";  //重定向到绑定微信页面
        } catch (UserRegInvalidSmsException e){
            redirectAttributes.addFlashAttribute("message", "短信验证码验证未通过,短信验证码错误");
            return "";  //重定向到绑定微信页面
        }
        //校验通过，如果该手机号已经注册过则绑定微信信息，若未注册则进行注册
        filters.add(Condition.eq("mobile", user.getMobile()));
        isExists = userService.exists(filters);
        if (isExists) {
            //绑定微信信息
            //TODO...绑定微信信息
        }else {
            //注册并绑定微信信息
            Map<String, String> site = myProps.getSite();
            user.setAddTime(new Date());
            user.setRegTime(new Date());
            user.setIsEnable((byte) 1);
            user.setHeadImg(site.get("user-default-img"));

            String now = DateUtils.toDateText(new Date(), "yyMMdd");
            String num = String.format("%06d",Integer.parseInt(redisOperate.incr("userSn")));
            String userSn = "QD"+ now + num;
            user.setUserSn(userSn);
            user.setUserName("BDGStore"+user.getMobile());
            user.setPassword("000000");

            User regUser = userService.insert((User) user);
            //TODO...绑定微信信息
            payAccountService.insertPayAccountByUserIdAndName(regUser.getUserId(),regUser.getUserName());
            logger.info("用户[" + user.getUserName() + "]注册成功(这里可以进行一些注册通过后的一些系统参数初始化操作)");
        }
        //绑定完成 登录
        return "forward:/login?userName="+user.getUserName()+"&password="+user.getPassword();
    }

}
