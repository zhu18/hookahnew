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

    @RequestMapping(value = "/1/information", method = RequestMethod.GET)
    public String index(){
        return "1/information/index";
    }

    @RequestMapping(value = "/1/information/center_dynamic", method = RequestMethod.GET)
    public String centerDynamic(){
        return "1/information/center_dynamic";
    }

    @RequestMapping(value = "/1/information/industry_consulting", method = RequestMethod.GET)
    public String industryConsulting(){
        return "1/information/industry_consulting";
    }

    @RequestMapping(value = "/1/information/center_announcement", method = RequestMethod.GET)
    public String centerAnnouncement(){
        return "1/information/center_announcement";
    }

    @RequestMapping(value = "/1/information/details", method = RequestMethod.GET)
    public String details(){ return "1/information/details"; }

    @RequestMapping(value = "/introduction", method = RequestMethod.GET)
    public String index2(){
        return "/introduction/index";
    }
}
