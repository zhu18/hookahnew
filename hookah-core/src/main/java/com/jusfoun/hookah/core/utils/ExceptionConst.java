package com.jusfoun.hookah.core.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class ExceptionConst {

    public static final String Failed = "0";
    public static final String Success = "1";
    public static final String Error = "9";
    public static final String empty = "2";
    public static final String InvalidParameters = "1001";
    public static final String AssertFailed = "1002";

    private static final Map<String, String> CODE = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put(Failed, "调用失败");
            put(Success, "调用成功");
            put(empty, "暂无数据");
            put(Error, "系统内部错误");
            put(InvalidParameters, "无效参数");
            put(AssertFailed, "参数验证失败");
        }
    };

    public static String get(String code) {
        return contains(code) ? CODE.get(code) : "未知错误";
    }

    public static boolean contains(String code) {
        return CODE.containsKey(code);
    }

}
