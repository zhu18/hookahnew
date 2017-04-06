package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ki
 * @date 2017/4/6 上午9:22
 * @desc
 */
@Controller
public class IntroductionController {

    @RequestMapping(value = "/introduction", method = RequestMethod.GET)
    public String index(){
        return "/introduction/index";
    }

    @RequestMapping(value = "/introduction/glory", method = RequestMethod.GET)
    public String glory(){
        return "/introduction/glory";
    }

    @RequestMapping(value = "/introduction/leader", method = RequestMethod.GET)
    public String leader(){
        return "/introduction/leader";
    }

    @RequestMapping(value = "/introduction/media", method = RequestMethod.GET)
    public String media(){
        return "/introduction/media";
    }

    @RequestMapping(value = "/introduction/strategy", method = RequestMethod.GET)
    public String strategy(){
        return "/introduction/strategy";
    }
}
