package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bingbing wu
 * @date 2017/3/13 下午9:33
 * @desc
 */
@Controller
public class goodsController {

    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public String list(){
        return "/goods/list";
    }

    @RequestMapping(value = "/goods/details", method = RequestMethod.GET)
    public String details(){
        return "/goods/details";
    }
}
