package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by lt on 2017/10/29.
 */
@Controller
public class ActivityController {
    @Resource
    CategoryService categoryService;

    /**
     * api一分购活动页
     */
    @RequestMapping(value = "/activity/superCost", method = RequestMethod.GET)
    public String activity(Model model) {
        model.addAttribute("categoryInfo", categoryService.getCatTree());
        return "activity/index";
    }
    /**
     * 优惠券活动页
     */
    @RequestMapping(value = "/activity/coupon", method = RequestMethod.GET)
    public String coupon(Model model) {

        return "activity/coupon_activity";
    }
}
