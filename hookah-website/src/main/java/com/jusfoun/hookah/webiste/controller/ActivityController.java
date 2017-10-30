package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lt on 2017/10/29.
 */
@Controller
public class ActivityController {

    /**
     * api一分购活动页
     */
    @RequestMapping(value = "/activity/superCost", method = RequestMethod.GET)
    public String activity(Model model) {
        return "activity/index";
    }
}
