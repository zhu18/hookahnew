package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * @author huang lei
 * @date 2017/4/12 上午9:39
 * @desc
 */
@Controller
public class ModifyController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/modify/loginPassword", method = RequestMethod.GET)
    public String loginPassword(Model model) {
        model.addAttribute("title", "修改登录密码");
        return "modify/loginPassword";
    }

    @RequestMapping(value = "/modify/payPassword", method = RequestMethod.GET)
    public String payPassword(Model model) {
        model.addAttribute("title", "修改支付密码");
        return "modify/payPassword";
    }

    @RequestMapping(value = "/modify/setPayPassword", method = RequestMethod.GET)
    public String setPayPassword(Model model) {
        model.addAttribute("title", "设置支付密码");
        return "modify/setPayPassword";
    }

    @RequestMapping(value = "/modify/success", method = RequestMethod.GET)
    public String success(Model model) {
        model.addAttribute("title", "修改成功");
        return "modify/success";
    }

    @RequestMapping(value = "/modify/mobile", method = RequestMethod.GET)
    public String mobile(Model model) {
        model.addAttribute("title", "修改手机号");
        return "modify/mobile";
    }

    @RequestMapping(value = "/modify/mobile", method = RequestMethod.POST)
    public String pMobile(User userForm,Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        if (StringUtils.isNotBlank(userForm.getMobile())) {
            user.setMobile(userForm.getMobile());
            try {
                userService.updateById(user);
                return "redirect:/modify/success";
            } catch (Exception e) {
                model.addAttribute("error","修改错误");
                return "modify/mobile";
            }
        }
        model.addAttribute("error","修改错误");
        return "modify/mobile";
    }

    @RequestMapping(value = "/modify/mail", method = RequestMethod.GET)
    public String mail(Model model) {
        model.addAttribute("title", "修改邮箱");
        return "modify/mail";
    }
}

