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

    @RequestMapping(value = "/buyer/allOrderList", method = RequestMethod.GET)
    public String allOrderList() {
        return "/usercenter/buyer/allOrderList";
    }
    @RequestMapping(value = "/buyer/cancelOrderList", method = RequestMethod.GET)
    public String cancelOrderList() {
        return "/usercenter/buyer/cancelOrderList";
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
    //    我的商品
    @RequestMapping(value = "/myGoods", method = RequestMethod.GET)
    public String myGoods() {
        return "usercenter/buyer/myGoods";
    }
    //    我的优惠券页面
    @RequestMapping(value = "/myCoupon", method = RequestMethod.GET)
    public String myCoupon() {
        return "usercenter/buyer/myCoupon";
    }

    //积分
    @RequestMapping(value = "/buyer/points", method = RequestMethod.GET)
    public String points() {
        return "usercenter/buyer/points";
    }

    //发票
    @RequestMapping(value = "/myInvoice", method = RequestMethod.GET)
    public String myInvoice() {
        return "usercenter/buyer/myInvoice";
    }
    //发票详情
    @RequestMapping(value = "/invoiceDetails", method = RequestMethod.GET)
    public String invoiceDetails() {
        return "usercenter/buyer/invoiceDetails";
    }
    //增票资质确认书
    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public String confirmation() {
        return "usercenter/buyer/confirmation";
    }

//    @RequestMapping(value = "/viewDetails", method = RequestMethod.GET)
//    public String viewDetails() {  return "usercenter/buyer/viewDetails"; }

//    @RequestMapping(value = "/sunAlone", method = RequestMethod.GET)
//    public String sunAlone() {  return "usercenter/buyer/sunAlone"; }
}
