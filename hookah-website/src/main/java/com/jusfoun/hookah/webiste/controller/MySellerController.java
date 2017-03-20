package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:00
 * @desc 卖家中心
 */
@Controller
public class MySellerController {

    @RequestMapping(value = "/myseller", method = RequestMethod.GET)
    public String index(){
        return "/myseller/index";
    }

    @RequestMapping(value = "/myseller/publish", method = RequestMethod.GET)
    public String publish(){
        return "/myseller/publish";
    }

    @RequestMapping(value = "/myseller/trade", method = RequestMethod.GET)
    public String trade(){
        return "/myseller/trade";
    }

    @RequestMapping(value = "/myseller/tradeing", method = RequestMethod.GET)
    public String tradeing(){ return "/myseller/tradeing"; }

    @RequestMapping(value = "/myseller/rate", method = RequestMethod.GET)
    public String mysellrate(){
        return "/myseller/rate";
    }

    @RequestMapping(value = "/myseller/custom", method = RequestMethod.GET)
    public String custom(){
        return "/myseller/custom";
    }

    @RequestMapping(value = "/myseller/customer", method = RequestMethod.GET)
    public String customer(){
        return "/myseller/customer";
    }

    @RequestMapping(value = "/myseller/illegal", method = RequestMethod.GET)
    public String illegal(){

        return "/myseller/illegal";
    }
}
