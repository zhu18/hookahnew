package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:08
 * @desc 买家中心
 */
@Controller
@RequestMapping(value = "/1/error/")
public class ErrorController {

    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String error404() {
        return "/1/error/404";
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String error500() {
        return "/1/error/500";
    }

    @RequestMapping(value = "oauth2Failure",method = RequestMethod.GET)
    public String oauth2Failure(HttpServletRequest request){
        return "/1/error/oauth2Failure";
    }

}
