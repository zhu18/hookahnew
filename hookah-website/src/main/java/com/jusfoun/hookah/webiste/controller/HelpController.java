package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/4/10 下午1:47
 * @desc
 */
@Controller
@RequestMapping("/help")
public class HelpController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/help/help_index";
    }
}
