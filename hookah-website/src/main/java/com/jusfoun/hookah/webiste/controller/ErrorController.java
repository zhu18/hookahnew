package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:08
 * @desc 买家中心
 */
@Controller
public class ErrorController {

    @RequestMapping(value = "/error/404", method = RequestMethod.GET)
    public String error404() {
        return "/error/404";
    }

    @RequestMapping(value = "/error/500", method = RequestMethod.GET)
    public String error500() {
        return "/error/500";
    }


}
