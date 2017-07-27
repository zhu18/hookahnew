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
     * 9    交易中心
     */
    public enum UserType {

        Personal((byte)0),
        Enterprise((byte)9);

        public Byte code;

        UserType(Byte code) {
            this.code = code;
        }

        public Byte getCode() {
            return code;
        }
    }

    /**
     * 1    签到
     * 2    签退
     */
    public enum Sign {

        SIGN_IN("1"),
        SIGN_OUT("2");

        public String code;

        Sign(String code) {
            this.code = code;
        }

        public String getCode() {
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
     * 下载文件存储地址
     */
    public static final String FILE_PATH = "D:\\\\download";

    /**
     * transfer 类型 2出金 1入金 和 payTradeRecord 同步 3入金4出金
     */
    public enum TransferType {

        MONEY_IN((byte)3),
        MONEY_OUT((byte)4);

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
     交易平台类型
     1：在线充值（入金），
     2：在线提现（出金），
     5：手工充值,
     6：手工扣款，
     7：线下充值，
     8：提现冲账
     清算中心类型
     3001：销售（货款）收入
     3007：交易交收手续费-收入
     4001：销售（货款）支出
     6003：冻结划入-收益账户
     6004：释放划出-收益账户
     */
    public enum TradeType {

        OnlineRecharge(1),
        OnlineCash(2),
        ManualRecharge(5),
        ManualDebit(6),
        OfflineRecharge(7),
        CashREverse(8),
        SettleCut(9),
        SalesIn(3001),
        SalesOut(4001),
        ChargeIn(3007),
        FreezaIn(6003),
        releaseDraw(6004);

        public Integer code;

        TradeType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }


    public enum UploadedNoticeFileType {

        fundFile("1"),
        tradeFile("2");

        public String code;

        UploadedNoticeFileType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
