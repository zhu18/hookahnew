package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 下午3:22
 * @desc
 */
@Controller
public class InformationController {

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String index2(){
        return "/information/index";
    }

    @RequestMapping(value = "/information/details", method = RequestMethod.GET)
    public String detail(){
        return "/information/details";
    }

}
