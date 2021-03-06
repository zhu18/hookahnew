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
@RequestMapping("/usercenter")
public class MyBuyerController {

    @RequestMapping(value = "/buyer/orderManagement", method = RequestMethod.GET)
    public String orderManagement() {
        return "/usercenter/buyer/orderManagement";
    }

    @RequestMapping(value = "/buyer/orderStay", method = RequestMethod.GET)
    public String orderStay() {
        return "/usercenter/buyer/orderStay";
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public String invoice() {
        return "/usercenter/buyer/invoice";
    }

    @RequestMapping(value = "/invoiceList", method = RequestMethod.GET)
    public String invoiceList() {
        return "/usercenter/buyer/invoiceList";
    }

    @RequestMapping(value = "/invoiceInfo", method = RequestMethod.GET)
    public String invoiceInfo() {
        return "/usercenter/buyer/invoiceInfo";
    }

    @RequestMapping(value = "/buyer/evaluation", method = RequestMethod.GET)
    public String evaluation() {
        return "usercenter/buyer/evaluation";
    }

    @RequestMapping(value = "/myAttention", method = RequestMethod.GET)
    public String myAttention() {
        return "usercenter/buyer/myAttention";
    }

//    @RequestMapping(value = "/viewDetails", method = RequestMethod.GET)
//    public String viewDetails() {  return "usercenter/buyer/viewDetails"; }

//    @RequestMapping(value = "/sunAlone", method = RequestMethod.GET)
//    public String sunAlone() {  return "usercenter/buyer/sunAlone"; }
}
