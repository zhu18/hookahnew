package com.jusfoun.hookah.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huang lei
 * @date 2017/3/23 下午1:41
 * @desc
 */
public class EmailFormatCheckUtil {

    public static boolean checkEmail(String str){
        String regExp = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
