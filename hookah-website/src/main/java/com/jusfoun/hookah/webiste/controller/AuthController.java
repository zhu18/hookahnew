package com.jusfoun.hookah.webiste.controller;

import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * All rights Reserved, Designed By
 * Copyright:  Copyright(C) 2016-2020
 * Company:    ronwo.com LTD.
 *
 * @version V1.0
 *          Createdate: 2016/11/12 下午11:53
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          2016/11/12     huanglei         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 * @author: huanglei
 */
@Controller
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Model pLogin(Model model) {
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        return "register";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:http://localhost:9900/logout";
    }
}
