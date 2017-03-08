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
}
