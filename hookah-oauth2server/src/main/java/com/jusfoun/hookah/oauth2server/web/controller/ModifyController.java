package com.jusfoun.hookah.oauth2server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author huang lei
 * @date 2017/4/12 上午9:39
 * @desc
 */
@Controller
public class ModifyController {
    @RequestMapping(value = "/modify/loginPassword", method = RequestMethod.GET)
    public String loginPassword(Model model) {
        model.addAttribute("title","修改登录密码");
        return "modify/loginPassword";
    }
    @RequestMapping(value = "/modify/payPassword", method = RequestMethod.GET)
    public String payPassword(Model model) {
        model.addAttribute("title","修改支付密码");
        return "modify/payPassword";
    }
    @RequestMapping(value = "/modify/setPayPassword", method = RequestMethod.GET)
    public String setPayPassword(Model model) {
        model.addAttribute("title","设置支付密码");
        return "modify/setPayPassword";
    }

    @RequestMapping(value = "/modify/success", method = RequestMethod.GET)
    public String success(Model model) {
        model.addAttribute("title","修改成功");
        return "modify/success";
    }

    @RequestMapping(value = "/modify/mobile", method = RequestMethod.GET)
    public String mobile(Model model) {
        model.addAttribute("title","修改手机号");
        return "modify/mobile";
    }

    @RequestMapping(value = "/modify/mail", method = RequestMethod.GET)
    public String mail(Model model) {
        model.addAttribute("title","修改邮箱");
        return "modify/mail";
    }
}

