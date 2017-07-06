package com.jusfoun.hookah.pay.util;

/**
 * @Author dengxu
 * @create 2017-07-04 11:46
 */
public class PayConstants {

    /**
     * 测试环境 市场编码
     */
    public final static String FID_JYS = "8902";

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

    /**
     * 青岛清算中心测试IP
     */
    public static final String QD_TEST_IP = "222.173.102.106";

    /**
     * 青岛清算中心测试PORT
     */
    public static final String QD_TEST_PORT = "8005";

    /**
     * 币种
     */
    public static final String QD_BZ = "RMB";

    /**
     * transfer 类型 2出金 1入金
     */
    public enum TransferType {

        MONEY_IN((byte)1),
        MONEY_OUT((byte)2);

        public byte code;

        TransferType(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }

    /**
     * transfer 状态 0处理中 1成功 2失败
     */
    public enum TransferStatus {

        handing((byte)0),
        success((byte)1),
        fail((byte)2);

        public byte code;

        TransferStatus(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }


    /**
     * pay_bank_card 状态 0正常 1解除绑定
     */
    public enum BankCardStatus {

        binded((byte)0),
        unbind((byte)1);

        public int code;

        BankCardStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }


    /**
     * payTradeRecode
     账务类型：
     1-商品支付扣款,
     2-商品销售入账,
     3-在线充值,
     4-在线提现,
     5-手工充值,
     6-手工扣款,
     7-线下充值,
     8-提现冲账
     */
    public enum TradeType {

        Deduct((byte)1),
        IntoAccount((byte)2),
        OnlineRecharge((byte)3),
        OnlineCash((byte)4),
        ManualRecharge((byte)5),
        ManualDebit((byte)6),
        OfflineRecharge((byte)7),
        CashREverse((byte)8);

        public byte code;

        TradeType(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }


}
