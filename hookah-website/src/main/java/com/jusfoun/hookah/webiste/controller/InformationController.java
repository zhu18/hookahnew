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
    public String index(){
        return "information/index";
    }

    @RequestMapping(value = "/information/center_dynamic", method = RequestMethod.GET)
    public String centerDynamic(){
        return "information/center_dynamic";
    }

    @RequestMapping(value = "/information/industry_consulting", method = RequestMethod.GET)
    public String industryConsulting(){
        return "information/industry_consulting";
    }

    @RequestMapping(value = "/information/center_announcement", method = RequestMethod.GET)
    public String centerAnnouncement(){
        return "information/center_announcement";
    }

    @RequestMapping(value = "/information/details", method = RequestMethod.GET)
    public String details(){ return "information/details"; }
}
