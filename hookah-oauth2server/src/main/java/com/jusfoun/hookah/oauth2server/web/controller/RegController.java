package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/23 上午10:15
 * @desc 注册
 */
@Controller
public class RegController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/reg",method = RequestMethod.GET)
    public String reg(Model model){
        return "register";
    }

    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    @ResponseBody
    public Object pReg(User user){
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        int i = userService.insert(user);
        System.out.println(user.getUserId());
        return user;
    }
}
