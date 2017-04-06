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

}
