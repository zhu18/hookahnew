package com.jusfoun.hookah.webiste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huang lei
 * @date 2017/3/6 上午10:52
 * @desc
 */
@Controller
public class ExchangeController {

    @RequestMapping(value = "/exchange", method = RequestMethod.GET)
    public String index(){ return "exchange/index"; }

}
