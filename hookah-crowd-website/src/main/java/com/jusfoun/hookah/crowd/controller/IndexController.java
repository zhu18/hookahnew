package com.jusfoun.hookah.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by huanglei on 2016/11/8.
 */
@Controller
//@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
//        userService.setPVCountByDate();
        return "index";
    }
    @RequestMapping(value = "/height.html", method = RequestMethod.GET)
    public String iframeHeight() {
        return "/height";
    }

}