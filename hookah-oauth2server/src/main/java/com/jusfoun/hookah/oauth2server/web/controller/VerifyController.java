package com.jusfoun.hookah.oauth2server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author huang lei
 * @date 2017/4/12 上午9:39
 * @desc
 */
@Controller
public class VerifyController {
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("title","验证身份");
        return "modify/verify";
    }
}
