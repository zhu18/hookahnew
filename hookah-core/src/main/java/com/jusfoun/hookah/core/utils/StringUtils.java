package com.jusfoun.hookah.core.utils;

import java.io.UnsupportedEncodingException;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author huang lei
 * @date 2017/4/18 下午3:14
 * @desc
 */
public class StringUtils {

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 字节数组转换为字符串
     *
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return EMPTY;
        }
    }

    /**
     * 字符串转换为字节数组
     *
     * @param str
     * @return
     */
    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes(CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return str != null && !"".equals(str.trim());
    }
}
