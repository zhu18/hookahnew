package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 下午3:18
 * @desc
 */
@Controller
public class InnovationController {

    @RequestMapping(value = "/innovation", method = RequestMethod.GET)
    public String index(){
        return "innovation/index";
    }

    @RequestMapping(value = "/innovation/equity", method = RequestMethod.GET)
    public String equity(){
        return "innovation/equity";
    }

}
