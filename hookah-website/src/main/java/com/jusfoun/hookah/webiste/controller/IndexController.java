package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.other.*;
import com.jusfoun.hookah.core.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Resource
    TestMongoService testMongoService;

    @Resource
    UserMongoService userMongoService;


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
        List<Test> list = (List) testService.selectList();

        return list;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public Object insert(Model model) {
        Test t2 = new Test();
        t2.setName("wwwwww");
        t2.setId("ee");
        testService.insert(t2);
        return "success";
    }

    @RequestMapping(value = "/mselect", method = RequestMethod.GET)
    @ResponseBody
    public Object mselect(Model model) {
        List<Test> list = (List) testMongoService.selectList();

        return list;
    }

    @RequestMapping(value = "/mselectone", method = RequestMethod.GET)
    @ResponseBody
    public Test mselectone(Model model) {
        List<Condition> filters = new ArrayList(1);
        filters.add(Condition.eq("id","ee"));
        Test test = (Test) testMongoService.selectOne(filters);

        return test;
    }

    @RequestMapping(value = "/minsert", method = RequestMethod.GET)
    @ResponseBody
    public Object minsert(Model model) {
        Test t2 = new Test();
        t2.setName("xxxxxxxx");
        t2.setId(UUID.randomUUID().toString());
        testMongoService.insert(t2);
        return "success";
    }

    @RequestMapping(value = "/uselect", method = RequestMethod.GET)
    @ResponseBody
    public Object mselectUser(Model model) {
        List<User> list = (List) userMongoService.selectList();

        return list;
    }


    @RequestMapping(value = "/uinsert", method = RequestMethod.GET)
    @ResponseBody
    public Object minsertUser(Model model) {
        User t2 = new User();
        t2.setEmail("abc@ddd.com");
        t2.setId(UUID.randomUUID().toString());
        userMongoService.insert(t2);
        return "success";
    }
}