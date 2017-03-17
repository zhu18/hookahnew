package com.jusfoun.hookah.core.utils;

import java.util.UUID;

/**
 * Created by wangjl on 2017-3-16.
 */
public class GeneratorUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
