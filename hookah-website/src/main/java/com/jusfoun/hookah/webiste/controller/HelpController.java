package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.HelpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/10 下午1:47
 * @desc
 */
@Controller
@RequestMapping("/help")
public class HelpController {

    @Resource
    HelpService helpService;

    @RequestMapping(value = "/{id}.html", method = RequestMethod.GET)
    public String index(@PathVariable("id") String id) {
        return "/help/help_index";
    }
}
