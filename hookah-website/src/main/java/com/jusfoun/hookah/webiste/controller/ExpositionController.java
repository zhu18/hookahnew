package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 下午3:21
 * @desc
 */
@Controller
public class ExpositionController {

    @RequestMapping(value = "/exposition", method = RequestMethod.GET)
    public String index(){
        return "exposition/index";
    }
}
