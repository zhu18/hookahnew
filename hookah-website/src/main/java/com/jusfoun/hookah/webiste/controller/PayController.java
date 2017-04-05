package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author bingbing wu
 * @date 2017/3/10 下午21:21
 * @desc
 */
@Controller
@RequestMapping("/1/pay")
public class PayController {

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "/1/pay/createOrder";
    }

    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    public String cash() {
        return "/1/pay/cash";
    }


}
