package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/6 上午10:52
 * @desc
 */
@Controller
public class ExchangeController {
    @Resource
    CategoryService categoryService;

    @RequestMapping(value = "/1/exchange", method = RequestMethod.GET)
    public String index1(Model model){
        model.addAttribute("categoryInfo", categoryService.getCatTree());
        return "1/exchange/index";
    }
    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String index(Model model){
        return "exchange/index";
    }
    @RequestMapping(value = "/exchange/list", method = RequestMethod.GET)
    public String list(Model model){
        return "exchange/list";
    }

    @RequestMapping(value = "/exchange/details", method = RequestMethod.GET)
    public String details(Model model){
        return "exchange/details";
    }
}
