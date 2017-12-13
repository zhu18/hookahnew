package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:08
 * @desc 买家中心
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String error404() {
        return "/error/404";
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String error500() {
        return "/error/500";
    }

    @RequestMapping(value = "confirmOrderError", method = RequestMethod.GET)
    public String errorConfirmOrderError() {
        return "/error/confirmOrderError";
    }

    @RequestMapping(value = "oauth2Failure",method = RequestMethod.GET)
    public String oauth2Failure(HttpServletRequest request){
        return "/error/oauth2Failure";
    }

    @RequestMapping(value = "limitedGoodsError",method = RequestMethod.GET)
    public String limitedGoodsError(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        model.addAttribute("message", session.getAttribute("limitedGoodsErrorMessage"));
        return "/error/limitedGoodsError";
    }


}
