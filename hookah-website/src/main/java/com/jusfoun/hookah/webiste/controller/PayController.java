package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.rpc.api.PayCoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author bingbing wu
 * @date 2017/3/10 下午21:21
 * @desc
 */
@Controller
@RequestMapping("/1/pay")
public class PayController {

    @Resource
    private PayCoreService payCoreService;


    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "/1/pay/createOrder";
    }

    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    public String cash() {
        return "/1/pay/cash";
    }

    @RequestMapping(value = "/pay2", method = RequestMethod.GET)
    public Object pay (Integer orderId){
        String reqHtml = null;
        try {
            System.out.print("ddddddddddddddddddddddddddddddddddddddddddddddddddddd");

            //.findPayCoreByOrderSn(null);
            reqHtml = payCoreService.doPay(orderId,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(reqHtml)){
            return "redirect:/404.html";
        }else
            return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
    }
}
