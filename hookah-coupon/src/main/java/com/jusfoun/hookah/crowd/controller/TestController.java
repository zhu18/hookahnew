package com.jusfoun.hookah.crowd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/")
    public String Test1(){
        return "coupon";
    }

}
