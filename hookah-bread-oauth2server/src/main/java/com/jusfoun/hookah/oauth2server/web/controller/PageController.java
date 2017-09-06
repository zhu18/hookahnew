package com.jusfoun.hookah.oauth2server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jsshao1986
 * @date 2017/3/7 下午6:08
 * @desc 页面直接访问
 */
@Controller
public class PageController {

    @RequestMapping(value = "/page/{html}", method = RequestMethod.GET)
    public String error404(@PathVariable String html) {
        String path = html.replaceAll("=","/");
        return path;
    }

}
