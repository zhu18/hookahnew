package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Controller
public class ShowController {

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show() {
        return "show/demo";
    }
}
