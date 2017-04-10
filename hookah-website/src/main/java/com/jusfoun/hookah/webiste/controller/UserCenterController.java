package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:05
 * @desc
 */
@Controller
@RequestMapping(value = "/usercenter")
public class UserCenterController {

    @Resource
    SysNewsService sysNewsService;
    @Resource
    CategoryService categoryService;
    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;
    @Resource
    GoodsService goodsService;
    @Resource
    MgGoodsService mgGoodsService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String userInfo() {
        return "usercenter/userInfo/userInfo";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public String userProfile() {
        return "usercenter/userInfo/userProfile";
    }

    @RequestMapping(value = "/infoCenter", method = RequestMethod.GET)
    public String infoCenterA() {
        return "usercenter/userInfo/infoCenter";
    }

    @RequestMapping(value = "/infoDetail", method = RequestMethod.GET)
    public String infoDetail() {
        return "usercenter/userInfo/infoDetail";
    }

    @RequestMapping(value = "/fundmanage", method = RequestMethod.GET)
    public String fundmanage() {
        return "usercenter/userInfo/fundmanage";
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String recharge() {
        return "/usercenter/userInfo/recharge";
    }

    @RequestMapping(value = "/withdrawals", method = RequestMethod.GET)
    public String withdrawals() {
        return "/usercenter/userInfo/withdrawals";
    }

    @RequestMapping(value = "/safeSet", method = RequestMethod.GET)
    public String safeSet() {
        return "usercenter/userInfo/safeSet";
    }


    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "/usercenter/userInfo/createOrder";
    }


}
