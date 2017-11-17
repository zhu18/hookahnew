package com.jusfoun.hookah.integral.contants;

public enum JfEnum {


    JF_1(1, "新用户注册"),
    JF_2(2, "邀请新用户注册认证"),
    JF_3(3, "账号身份认证"),
    JF_4(4, "抽奖"),
    JF_5(5, "抽奖"),
    JF_6(6, "VIP月度会员卡"),
    JF_7(7, "优惠券"),
    JF_8(8, "产品免费试用");

    private int code;

    private String msg;

    private JfEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(int code) {
        for (JfEnum c : JfEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
