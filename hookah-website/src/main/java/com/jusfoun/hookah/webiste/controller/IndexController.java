package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

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
    MailService mailService;

    @Resource
    CategoryService categoryService;

    @Resource
    GoodsStorageService goodsStorageService;

    @Resource
    HomeImageService homeImageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        userService.setPVCountByDate();
        try {
            model.addAttribute("categoryInfo", categoryService.getCatTree());
            model.addAttribute("goodsStorageInfo", goodsStorageService.getGoodsStorageList());
            model.addAttribute("imagesInfo", homeImageService.getImageInfoList());
//            model.addAttribute("goodsShelvesVoInfo",goodsShelvesService.getShevlesGoodsVoList(new HashMap<String,Object>()));
            return "exchange/index";
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorInfo", e);
            return "/error/500";
        }
    }
    @RequestMapping(value = "/height.html", method = RequestMethod.GET)
    public String iframeHeight() {
        return "/height";
    }

    @RequestMapping(value = "/MP_verify_1pbvO5erZ0HrZkcn.txt", method = RequestMethod.GET)
    public String vvv() {
        return "/MP_verify_1pbvO5erZ0HrZkcn.txt";
    }

}