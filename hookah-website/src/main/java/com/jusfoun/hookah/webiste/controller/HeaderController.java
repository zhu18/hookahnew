package com.jusfoun.hookah.webiste.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lt on 2018/1/11.
 */
@RestController
@RequestMapping("/header")
public class HeaderController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(HeaderController.class);

    @RequestMapping(value = "/getHeader", method = RequestMethod.GET)
    public String cart(Model model) {
        try {
            Subject subject = SecurityUtils.getSubject();
            StringBuffer html = new StringBuffer("");
            if(subject.isAuthenticated()) {
                //已登录
                html.append("<ul class=\"text-align-right topbar-user grid-right margin-left-30\" style=\"margin-top:28px;position: relative\">");
                html.append("<li class=\"display-inline-block margin-left-10\">");
                html.append("<a href=\"http://auth.bdgstore.cn/logout\">退出</a></li></ul>");
                return html.toString();
            }else {
                html.append("<ul class=\"text-align-right topbar-user grid-right margin-left-30\" style=\"margin-top:28px;position: relative\">");
                html.append("<li class=\"display-inline-block\">");
                html.append("<a href=\"http://auth.bdgstore.cn/oauth/authorize?client_id=website&amp;response_type=code&amp;redirect_uri=\" style=\"padding:7px 14px;\">登录</a>");
                html.append("</li>");
                html.append("<li class=\"display-inline-block margin-left-10\">");
                html.append("<a class=\"reg\" href=\"http://auth.bdgstore.cn/reg\">免费注册</a>");
                html.append("</li>");
                html.append("<p class=\"show-ad show\">注册送200元大礼<i class=\"fa fa-close\"></i></p></ul>");
                return html.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return "redirect:/error/500";
        }
    }
}
