package com.jusfoun.hookah.core;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangjl on 2017-8-10.
 */
public class Md5Utils {
    public static String encoderByMd5(String str) {
        //确定计算方法
        MessageDigest md5= null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            return newstr;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println(encoderByMd5("{\"userId\":\"kkk\", \"pwd\":\"kkk2\"}"));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
}
