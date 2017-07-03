package com.jusfoun.hookah.pay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dengxu on 2017/7/3/0003.
 */

@RestController
@RequestMapping("/pay")
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "访问此处，你需要支付10000000.00元";
    }
}
