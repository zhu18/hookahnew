package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.rpc.api.*;
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
    GoodsService goodsService;

    @Resource
    GoodsMongoService goodsMongoService;

    @Resource
    UserMongoService userMongoService;

    @Resource
    MqSenderService mqSenderService;


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
        List<Goods> list = (List) goodsService.selectList();

        return list;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public Object insert(Model model) {
        Goods t2 = new Goods();
        t2.setGoodsName("wwwwww");
        goodsService.insert(t2);
        return "success";
    }

    @RequestMapping(value = "/insertbatch", method = RequestMethod.GET)
    @ResponseBody
    public Object insertBatch(Model model) {
        List<Goods> list = new ArrayList<Goods>();
        Goods t1 = new Goods();
        t1.setGoodsName("t111111111111");
        list.add(t1);
        Goods t2 = new Goods();
        t2.setGoodsName("t2222222222");
        list.add(t2);
        goodsService.insertBatch(list);
        return "success";
    }

    @RequestMapping(value = "/mselect", method = RequestMethod.GET)
    @ResponseBody
    public Object mselect(Model model) {
        List<Goods> list = (List) goodsMongoService.selectList();

        return list;
    }

    @RequestMapping(value = "/mselectone", method = RequestMethod.GET)
    @ResponseBody
    public Goods mselectone(Model model) {
        List<Condition> filters = new ArrayList(1);
        filters.add(Condition.eq("id","ee"));
        Goods goods = (Goods) goodsMongoService.selectOne(filters);

        return goods;
    }

    @RequestMapping(value = "/minsert", method = RequestMethod.GET)
    @ResponseBody
    public Object minsert(Model model) {
        Goods t2 = new Goods();
        t2.setGoodsName("xxxxxxxx");
        t2.setGoodsId(UUID.randomUUID().toString());
        goodsMongoService.insert(t2);
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
       /* User t2 = new User();
        t2.setEmail("abc@ddd.com");
        t2.setId(UUID.randomUUID().toString());
        userMongoService.insert(t2);*/
        return "success";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @ResponseBody
    public Object sendMsg(String msg,Model model) {
        mqSenderService.send(msg);
        return "sucess";
    }
}