package com.jusfoun.hookah.webiste.util;

import com.jusfoun.hookah.core.Md5Utils;

/**
 * Created by admin on 2017/9/25.
 */
public class SecretUtil {
    private static final  String SECRET_KEY="2017bdgstore@#$";
    public  static String getSecret(String userId){
        return  "userId:"+userId+"&token:"+Md5Utils.encoderByMd5(userId+SECRET_KEY);
    }
}
