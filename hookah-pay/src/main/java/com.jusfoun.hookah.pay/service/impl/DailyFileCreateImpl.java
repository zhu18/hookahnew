package com.jusfoun.hookah.pay.service.impl;

import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.FileUtils;
import com.jusfoun.hookah.rpc.api.PayAccountRecordService;
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
    PayAccountRecordService payAccountRecordService;

    /**
     * 日终交易金额文件
     */
    @RequestMapping
    public void createDailyTradeMoneyFile(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String ywrq = formatter.format(new Date());
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
}
