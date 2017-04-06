package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.SysNewsService;
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
public class UserCenterController {

    @Resource
    SysNewsService sysNewsService;

    @RequestMapping(value = "/1/usercenter", method = RequestMethod.GET)
    public String index1(){
        return "1/usercenter/index";
    }

    @RequestMapping(value = "/1/usercenter/fundmanage", method = RequestMethod.GET)
    public String fundmanage() { return "1/usercenter/fundmanage"; }

    @RequestMapping(value = "/1/usercenter/safeset", method = RequestMethod.GET)
    public String safeset() { return "1/usercenter/safeset"; }

    @RequestMapping(value = "/1/usercenter/recharge", method = RequestMethod.GET)
    public String recharge() { return "1/usercenter/recharge"; }

    @RequestMapping(value = "/1/usercenter/withdrawals", method = RequestMethod.GET)
    public String withdrawals() { return "1/usercenter/withdrawals"; }



    @RequestMapping(value = "/1/usercenter/publishArticle", method = RequestMethod.GET)
    public String publishArticle() { return "1/usercenter/publishArticle"; }



    @RequestMapping(value = "/usercenter/userInfo", method = RequestMethod.GET)
    public String userInfo() { return "usercenter/userInfo"; }

    @RequestMapping(value = "/usercenter/goodsManage", method = RequestMethod.GET)
    public String index(){
        return "usercenter/myseller/goodsManage";
    }

}
