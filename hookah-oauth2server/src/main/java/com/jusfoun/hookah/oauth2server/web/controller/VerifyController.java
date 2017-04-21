package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.domain.User;
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
public class VerifyController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String search(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String,String> userMap = (HashMap<String,String>)session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        model.addAttribute("userObj",user);
        model.addAttribute("title","验证身份");
        return "modify/verify";
    }
}
