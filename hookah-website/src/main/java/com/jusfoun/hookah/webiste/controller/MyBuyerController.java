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

    @RequestMapping(value = "/1/mybuyer/order", method = RequestMethod.GET)
    public String index() {
        return "redirect:/order/list";
    }

    @RequestMapping(value = "/1/mybuyer/invoice", method = RequestMethod.GET)
    public String invoice() {
        return "/1/mybuyer/invoice/index";
    }

    @RequestMapping(value = "/1/mybuyer/invoice/info", method = RequestMethod.GET)
    public String invoiceinfo() {
        return "/1/mybuyer/invoice/info";
    }

    @RequestMapping(value = "/1/mybuyer/rate", method = RequestMethod.GET)
    public String rate() {
        return "/1/mybuyer/rate";
    }

    @RequestMapping(value = "/1/mybuyer/attention_item", method = RequestMethod.GET)
    public String attentionitem() {
        return "/1/mybuyer/attention_item";
    }

    @RequestMapping(value = "/1/mybuyer/attention_shop", method = RequestMethod.GET)
    public String attentionshop() {
        return "/1/mybuyer/attention_shop";
    }

}
