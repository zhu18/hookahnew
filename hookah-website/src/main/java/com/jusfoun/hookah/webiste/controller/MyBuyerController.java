package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:08
 * @desc 买家中心
 */
@Controller
public class MyBuyerController {
    
    @RequestMapping(value = "/usercenter/buyer/orderManagement", method = RequestMethod.GET)
    public String orderManagement() { return "/usercenter/buyer/orderManagement"; }

    @RequestMapping(value = "/usercenter/invoice", method = RequestMethod.GET)
    public String invoice() { return "/usercenter/buyer/invoice"; }

    @RequestMapping(value = "/usercenter/invoiceList", method = RequestMethod.GET)
    public String invoiceList() { return "/usercenter/buyer/invoiceList"; }

    @RequestMapping(value = "/usercenter/info", method = RequestMethod.GET)
    public String info() { return "/usercenter/buyer/info"; }

    @RequestMapping(value = "usercenter/buyer/cart", method = RequestMethod.GET)
    public String cart() { return "usercenter/buyer/cart"; }

    @RequestMapping(value = "/usercenter/buyer/evaluation", method = RequestMethod.GET)
    public String evaluation(){
        return "usercenter/buyer/evaluation";
    }

    @RequestMapping(value = "/usercenter/myAttention", method = RequestMethod.GET)
    public String myAttention(){
        return "usercenter/buyer/myAttention";
    }










}
