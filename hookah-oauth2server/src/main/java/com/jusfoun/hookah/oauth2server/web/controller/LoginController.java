package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.core.utils.NetUtils;
import com.jusfoun.hookah.oauth2server.config.MyProps;
import com.jusfoun.hookah.oauth2server.security.UsernameAndPasswordToken;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MyProps myProps;

    @Resource
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(User user, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

        String username = user.getUserName();
        UsernameAndPasswordToken token = new UsernameAndPasswordToken();
        if(FormatCheckUtil.checkMobile(username)){
             token.setMobile(username);
        }else if(FormatCheckUtil.checkEmail(username)){
            token.setEmail(username);
        }else{
            token.setUsername(username);
        }
        token.setPassword(user.getPassword().toCharArray());
        Subject currentUser = SecurityUtils.getSubject();

        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        Map<String,String> host = myProps.getHost();
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            User updateUser = new User();
            updateUser.setLastLoginIp(NetUtils.getIpAddr(request));
            updateUser.setLastLoginTime(new Date());
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("userName", username));
            userService.updateByConditionSelective(updateUser,filters);
            //TODO...登录日志
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return "redirect:"+host.get("website")+"/login";
        } else {
            token.clear();
            return "redirect:"+host.get("website")+"/login";
        }
    }

}
