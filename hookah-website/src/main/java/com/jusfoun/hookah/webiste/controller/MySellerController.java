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
}
