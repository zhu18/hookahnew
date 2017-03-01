package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.other.UserService;
import com.jusfoun.hookah.core.domain.Test;
import com.jusfoun.hookah.rpc.api.TestService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by huanglei on 2016/11/8.
 */
@Controller
public class IndexController {


    @Autowired
    ApplicationContext context;

    @Resource
    UserService userService;

    @Resource
    TestService testService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("happy", "Hello,world");
        model.addAttribute("x", "index");
        String s = userService.sayhello();
        System.out.println(s);
        return "index";
    }

    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String sayhello(Model model) {
        String s = userService.sayhello();
        System.out.println(s);
        return "index";
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Object select(Model model) {
        Test test = (Test) testService.selectById("ee");

        return test;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public Object insert(Model model) {
        Test t2 = new Test();
        t2.setName("wwwwww");
        testService.insert(t2);
        return "success";
    }

}