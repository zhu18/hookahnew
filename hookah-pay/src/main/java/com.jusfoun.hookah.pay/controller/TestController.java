package com.jusfoun.hookah.pay.controller;

import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.rpc.api.PayAccountRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by dengxu on 2017/7/3/0003.
 */
@RestController
@RequestMapping("/pay")
public class TestController {

    @Resource
    PayAccountRecordService payAccountRecordService;

    @RequestMapping("/test")
    public String test(){
        return "访问此处，你需要支付10000000.00元";
    }

    @RequestMapping("/moneyIn")
    public void moneyIn(){
        MoneyInOutBo moneyInOutBo = new MoneyInOutBo();
        moneyInOutBo.setMoney(1000L);
        moneyInOutBo.setPayAccountID(2l);
        moneyInOutBo.setOperatorType(1);
        moneyInOutBo.setUserId("1234567");
        payAccountRecordService.entryAndExitPayments(moneyInOutBo);
    }
}
