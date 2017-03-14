package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:05
 * @desc
 */
@Controller
public class UserCenterController {

    @RequestMapping(value = "/usercenter", method = RequestMethod.GET)
    public String index(){
        return "usercenter/index";
    }

    @RequestMapping(value = "/usercenter/fundmanage", method = RequestMethod.GET)
    public String fundmanage() { return "usercenter/fundmanage"; }

    @RequestMapping(value = "/usercenter/safeset", method = RequestMethod.GET)
    public String safeset() { return "usercenter/safeset"; }

    @RequestMapping(value = "/usercenter/recharge", method = RequestMethod.GET)
    public String recharge() { return "usercenter/recharge"; }

    @RequestMapping(value = "/usercenter/withdrawals", method = RequestMethod.GET)
    public String withdrawals() { return "usercenter/withdrawals"; }

    @RequestMapping(value = "/usercenter/articleManagement", method = RequestMethod.GET)
    public String articleManagement() { return "usercenter/articleManagement"; }

    @RequestMapping(value = "/usercenter/publishArticle", method = RequestMethod.GET)
    public String publishArticle() { return "usercenter/publishArticle"; }
}
