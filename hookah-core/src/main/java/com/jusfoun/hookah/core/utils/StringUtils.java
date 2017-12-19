package com.jusfoun.hookah.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

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

    /**
     * 若干字符串校验空
     * @param strings
     * @return
     */
    public static boolean stringsIsEmpty(String ...strings ) {
        for (String s : strings){
            if(!isNotBlank(s))
                return true;
        }
        return false;
    }

    /**
     * 生成随机32位uuid
     * @return
     */
    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
}
