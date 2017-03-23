package com.jusfoun.hookah.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author huang lei
 * @date 2017/3/23 下午1:38
 * @desc
 */
public class PhoneFormatCheckUtil {
    /**
     * 验证手机号
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(86){0,1}1\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
