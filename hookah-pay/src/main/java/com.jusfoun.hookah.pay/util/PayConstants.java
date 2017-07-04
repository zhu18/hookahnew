package com.jusfoun.hookah.pay.util;

/**
 * @Author dengxu
 * @create 2017-07-04 11:46
 */
public class PayConstants {

    /**
     银行代码
     0000   未签约清算中心的银行
     JSYH   建行
     GSYH   工行
     NYYH   农行第一通道
     NY02   农行第二通道
     KJTP   快捷支付
     */
    public enum BankCode {

        NoSign("0000"),
        JSYH("JSYH"),
        GSYH("GSYH"),
        NYYH("NYYH"),
        NY02("NY02"),
        KJTP("KJTP");

        public String code;

        BankCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 0    普通投资者
     * 9    市场受益者
     */
    public enum UserType {

        Personal(0),
        Enterprise(9);

        public Integer code;

        UserType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 农行签名流水号前缀BD
     */
    public static final String QDABC_PREFIX = "BD";

}
