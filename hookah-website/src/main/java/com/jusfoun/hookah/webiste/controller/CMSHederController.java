package com.jusfoun.hookah.webiste.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 提现
 */

@Controller
@RequestMapping("/cms")
public class CMSHederController extends BaseController{
    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String header() {
        return "/cms/header";
    }
}
