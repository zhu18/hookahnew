package com.jusfoun.hookah.pay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dengxu
 */
public class PayUtil {


    /**
     * 生成请求流水号，当天唯一
     * @return
     */
    public static String createChannelSerial(String channelType){

        StringBuilder sb = new StringBuilder();
        sb.append(channelType);
        sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        sb.append(String.format("%05d", (int)(Math.random()*1000)));
        return sb.toString();
    }

    public static String moneyConver(Long money){
        return String.format("%.2f", (double)money).toString();
    }


    public static void main(String[] args){
        System.out.println(createChannelSerial(ChannelType.QDABC));


        System.out.println(moneyConver(100l));



    }
}
