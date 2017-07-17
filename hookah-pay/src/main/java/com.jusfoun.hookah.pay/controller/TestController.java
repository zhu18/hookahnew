package com.jusfoun.hookah.pay.controller;

import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.bo.SettleBo;
import com.jusfoun.hookah.rpc.api.PayAccountRecordService;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/moneyIn/{type}")
    public void moneyIn(@PathVariable Integer type){
        MoneyInOutBo moneyInOutBo = new MoneyInOutBo();
        moneyInOutBo.setMoney(1000L);
        moneyInOutBo.setPayAccountID(2l);
        moneyInOutBo.setOperatorType(type);
        moneyInOutBo.setUserId("1234567");
        if(type == 1){
            payAccountRecordService.entryPayments(moneyInOutBo);
        }else if(type == 2){
            payAccountRecordService.ExitPayments(moneyInOutBo);
        }
    }

    @RequestMapping("/moneyIn2/{type}")
    public void moneyIn2(@PathVariable String type){
        MoneyInOutBo moneyInOutBo = new MoneyInOutBo();
        moneyInOutBo.setMoney(1000L);
        moneyInOutBo.setPayAccountID(2l);
        moneyInOutBo.setOperatorType(Integer.parseInt(type));
        moneyInOutBo.setUserId("1234567");
        if(type.equals(1)){
            payAccountRecordService.entryPayments(moneyInOutBo);
        }else if(type.equals(2)){
            payAccountRecordService.ExitPayments(moneyInOutBo);
        }
    }

    @RequestMapping("/settle")
    public void moneyIn3(String orderSn, String shopName,
                         String startTime, String endTime, Integer settleStatus
    ){
        SettleBo bo = new SettleBo();
        bo.setOrderSn(orderSn);
        bo.setShopName(shopName);
        bo.setStartDate(startTime);
        bo.setEndDate(endTime);
        bo.setSettleStatus(settleStatus);


    }
}
