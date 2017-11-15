package com.jusfoun.hookah.integral.contants;

import com.jusfoun.hookah.core.constants.CodeEnum;

public class JfEnum {


    private final int code;

    private final String msg;

    public JfEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static JfEnum getByCode(int code) {
        for (JfEnum _enum : values()) {
            if (_enum.getCode().equals(code)) {
                return _enum;
            }
        }
        return null;
    }
}
