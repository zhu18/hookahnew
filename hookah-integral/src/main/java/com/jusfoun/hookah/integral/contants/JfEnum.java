package com.jusfoun.hookah.integral.contants;

public enum JfEnum {


    JF_1(1, 40, "新用户注册"),
    JF_2(2, 20, "邀请新用户注册认证"),
    JF_3(3, 60, "账号身份认证"),
    JF_4(4, 0, "抽奖"),
    JF_5(5, -50, "抽奖"),
    JF_6(6, -1000, "VIP月度会员卡"),
    JF_7(7, -50, "优惠券"),
    JF_8(8, -50, "产品免费试用");

    private int code;

    private int score;

    private String msg;

    private JfEnum(int code, int score, String msg) {
        this.code = code;
        this.code = score;
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

    public static int getScoreByCode(int code) {
        for (JfEnum c : JfEnum.values()) {
            if (c.getCode() == code) {
                return c.score;
            }
        }
        return 0;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
