package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.webiste.util.ABCKBaoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/7/6.
 */

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/jump",method = RequestMethod.GET)
    public String jumpPage(HttpServletRequest request){
        ABCKBaoUtil kBao=new ABCKBaoUtil();
        kBao.setParam(request);
        return "show/testCall";
    }
}
