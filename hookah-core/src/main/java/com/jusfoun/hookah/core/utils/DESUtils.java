package com.jusfoun.hookah.core.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by wangjl on 2017-8-10.
 */
public class DESUtils {
    //获取秘钥
    public static SecretKey readKey(String skey){
        try {
            byte[] bs = skey.getBytes("UTF8");
            //创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(bs);
            //转换DESKeySpec为SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // 加密密码
    public static String enPass(String password, String skey){
        try {
            SecretKey key = readKey(skey);

            Cipher cip = Cipher.getInstance("DES");
            cip.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipBytes = cip.doFinal(password.getBytes());
            String sblob = new BASE64Encoder().encode(cipBytes);

            return sblob;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //解密密码
    public static String dePass(String password, String skey){
        try {
            byte[] keyBuffer = new BASE64Decoder().decodeBuffer(password);
            SecretKey key = readKey(skey);
            Cipher cip = Cipher.getInstance("DES");
            cip.init(Cipher.DECRYPT_MODE, key);
            String stmp =  new String(cip.doFinal(keyBuffer),"UTF8");
            return stmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        System.out.println("加密：" + enPass("{\"userId\":\"kkk\", \"pwd\":\"kkk2\"}", "12345678"));
//        System.out.println("解密：" + dePass("491DqLgu8xM1FIeYNTdtvjjWPqPgrrrGmy9ZU7IRjdY=", "12345678"));
//    }
}
