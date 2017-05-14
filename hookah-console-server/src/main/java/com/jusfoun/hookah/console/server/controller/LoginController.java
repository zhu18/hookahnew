package com.jusfoun.hookah.console.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huang lei
 * @date 2017/4/12 下午5:14mv
 * @desc
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer url = request.getRequestURL();
        String redirectURI = request.getParameter("backurl");

        return "redirect:" + redirectURI;

    }

}
