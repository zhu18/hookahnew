package com.jusfoun.hookah.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Created by lt on 2017/11/8.
 */
public class CouponHelper {

    public static String createCouponSn(String prefix, Byte type){

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        sb.append(prefix);
        sb.append(String.format("%02d",type));
        sb.append(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
        sb.append(num);
        return sb.toString();
    }
}
