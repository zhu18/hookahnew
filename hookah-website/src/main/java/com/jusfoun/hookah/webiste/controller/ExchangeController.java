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

    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("categoryInfo", categoryService.getCatTree());
        return "exchange/index";
    }
}
