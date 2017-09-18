package com.jusfoun.hookah.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "index";
    }

    @RequestMapping(value = "/height.html", method = RequestMethod.GET)
    public String iframeHeight() {
        return "/height";
    }

}