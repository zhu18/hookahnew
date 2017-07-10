package com.jusfoun.hookah.pay.service.impl;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.FileUtils;
import com.jusfoun.hookah.pay.util.DateUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.PayAccountRecordService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/7/7.
 */
@Service
public class DailyFileCreateImpl{

    @Resource
    PayAccountService payAccountService;

    /**
     * 日终交易金额文件
     */
    @RequestMapping
    public void createDailyTradeMoneyFile(){
        String ywrq = DateUtil.getCurrentDate("YYYYMMDD");
        String path = "C:/Users/hhh/Desktop/zhengshu/";
        String name = "8902_9999_"+ywrq+"_01.TXT";
        String pathAndName = path+name;
        StringBuffer contents = new StringBuffer();
        contents.append("0000000001\r\n");

        for (int i=0;i<5;i++){
            contents.append(ywrq).append("|").append("交易市场|").append(i).append("\r\n");
        }
        String content = contents.toString();
        FileUtils.writeFile(pathAndName,content);
    }

    /**
     * 日终交易余额文件
     */
    public void createDailyTradeBalanceFile(){
        String ywrq = DateUtil.getCurrentDate("YYYYMMDD");
        String path = "C:/Users/hhh/Desktop/zhengshu/";
        String name = "8902_9999_"+ywrq+"_02.TXT";
        String pathAndName = path+name;
        StringBuffer contents = new StringBuffer();
        contents.append("0000000001\r\n");

        List<PayAccount> payAccounts = payAccountService.selectList();
        for (PayAccount payAccount:payAccounts){
            contents.append(ywrq).append("|").append(PayConstants.FID_JYS).append("|").append(PayConstants.BankCode.NY02)
                    .append("|").append(PayConstants.QD_BZ).append("|").append(payAccount.getUserId()).append("|")
                    .append(payAccount.getAccountType()).append("|").append(payAccount.getBalance()).append("|")
                    .append(payAccount.getEndingBalance()).append("\r\n");
        }
        String content = contents.toString();
        FileUtils.writeFile(pathAndName,content);
    }
}
