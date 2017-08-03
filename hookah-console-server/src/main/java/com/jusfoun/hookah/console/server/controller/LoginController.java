package com.jusfoun.hookah.console.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huang lei
 * @date 2017/4/12 下午5:14
 * @desc
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String login(String backurl,HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.isEmpty(backurl)) {
            return "redirect:" + backurl;
        } else {
            return "redirect:/";
        }

    }

}
