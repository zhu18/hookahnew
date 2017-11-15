package com.jusfoun.hookah.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by lt on 2017/11/8.
 */
public class CouponHelper {

    public static String createCouponSn(String prefix, Byte type){

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(String.format("%02d",type));
        sb.append(DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()));
        sb.append(Thread.currentThread().getId());
        return sb.toString();
    }
}
