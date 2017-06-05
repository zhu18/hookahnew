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
    public String login(String redirect_uri,HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.isEmpty(redirect_uri)) {
            return "redirect:" + redirect_uri;
        } else {
            return "redirect:/";
        }

    }

}
