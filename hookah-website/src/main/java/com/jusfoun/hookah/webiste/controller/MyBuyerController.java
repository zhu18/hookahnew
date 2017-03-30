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

    @RequestMapping(value = "/mybuyer/order", method = RequestMethod.GET)
    public String index() {
        return "/mybuyer/order";
    }

    @RequestMapping(value = "/mybuyer/invoice", method = RequestMethod.GET)
    public String invoice() {
        return "/mybuyer/invoice/index";
    }

    @RequestMapping(value = "/mybuyer/invoice/info", method = RequestMethod.GET)
    public String invoiceinfo() {
        return "/mybuyer/invoice/info";
    }

    @RequestMapping(value = "/mybuyer/rate", method = RequestMethod.GET)
    public String rate() {
        return "/mybuyer/rate";
    }

    @RequestMapping(value = "/mybuyer/attention_item", method = RequestMethod.GET)
    public String attentionitem() {
        return "/mybuyer/attention_item";
    }

    @RequestMapping(value = "/mybuyer/attention_shop", method = RequestMethod.GET)
    public String attentionshop() {
        return "/mybuyer/attention_shop";
    }

}
