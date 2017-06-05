package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by huanglei on 2016/11/8.
 */
@Controller
//@RequestMapping("/")
public class IndexController {


    @Autowired
    ApplicationContext context;

    @Resource
    UserService userService;

    @Resource
    GoodsService goodsService;

    @Resource
    MgGoodsService mgGoodsService;

    @Resource
    UserMongoService userMongoService;

    @Resource
    MqSenderService mqSenderService;

    @Resource
    MgSmsValidateService mgSmsValidateService;

    @Resource
    MailService mailService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("title", "青岛大数据交易中心");
        return "index";
    }

}