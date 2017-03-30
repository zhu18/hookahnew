package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgSmsValidate;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by huanglei on 2016/11/8.
 */
@Controller
@RequestMapping("/test")
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
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("goodsId",new Object[]{1149,1152}));
        List<Goods> list = (List) goodsService.selectList(filters);

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
    public ReturnData mselect(Model model) {
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("goodsName","商品名称"));
        List<Goods> list = (List) mgGoodsService.selectList(filters);

        return ReturnData.success(list);
    }

    @RequestMapping(value = "/mselectid", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData mselectid(Model model) {
        String id = "d1c634f6-ae96-4af7-a1b5-f3c037d8a751";
        MgGoods goods =  mgGoodsService.selectById(id);

        return ReturnData.success(goods);
    }

    @RequestMapping(value = "/mselectone", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData mselectone(Model model) {
        List<Condition> filters = new ArrayList(1);
        filters.add(Condition.eq("goodsId","d1c634f6-ae96-4af7-a1b5-f3c037d8a751"));
        MgGoods goods = (MgGoods) mgGoodsService.selectOne(filters);

        return ReturnData.success(goods);
    }

    @RequestMapping(value = "/minsert", method = RequestMethod.GET)
    @ResponseBody
    public Object minsert(Model model) {
        MgGoods t2 = new MgGoods();
        t2.setGoodsId(UUID.randomUUID().toString());
        mgGoodsService.insert(t2);
        return "success";
    }

    @RequestMapping(value = "/msms", method = RequestMethod.GET)
    @ResponseBody
    public Object msms(String content,Model model) {
        MgSmsValidate sms = new MgSmsValidate();
        sms.setSendTime(new Date());
        sms.setPhoneNum("18611661363");
        sms.setSmsContent(content);
        sms.setExpireTime(new Date());
        mgSmsValidateService.insert(sms);
        return "success";
    }

    @RequestMapping(value = "/mail", method = RequestMethod.GET)
    @ResponseBody
    public Object mail(String content,Model model) {
        String toEmail = "sjs@jusfoun.com";
        String subject = "测试";
        String text = "<b>测试</b>";
        mailService.send(toEmail,subject,text);
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