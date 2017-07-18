package com.jusfoun.hookah.core.constants;

/**
 * Created by wangjl on 2017-3-22.
 */
public class HookahConstants {
    /** 网站地址  */
    public static final String SITE_URL = "http://localhost:9000";
    /** 第几页 */
    public static final int PAGE_NUM = 1;
    /** 每页记录数 */
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_SIZE_20 = 20;

    /** 短信缓存前缀  */
    public static final String REDIS_SMS_CACHE_PREFIX = "smscache";
    /** 短信过期时间，单位随 分 */
    public static final Integer SMS_DURATION_MINITE = 10;

    public static final Integer SMS_USER_REGISTER = 6524;  //注册
    public static final Integer SMS_FIND_USER_PWD = 6525;   //找回登录密码
    public static final Integer SMS_CHANGE_USER_PWD = 6527;   //修改登录密码
    public static final Integer SMS_CHANGE_MOBILE = 6526;   //修改手机号
    public static final Integer SMS_CHANGE_PAY_PWD = 6528;   //修改支付密码

    //站内信:2 邮件:1 短信:0
    public static final Byte SEND_TYPE_SYSTEM = 2;
    public static final Byte SEND_TYPE_EMAIL = 1;
    public static final Byte SEND_TYPE_SMS = 0;

    //消息事件
    public static final String property_eventType = "EVENT_TYPE";
    //消息常量
    public static final String PROPERTY_MESSAGE_CONSTANTS = "MESSAGE_CONSTANTS";

    //第三方短信下发成功失败标志
    public static final String SMS_SUCCESS = "success";
    public static final String SMS_FAIL = "fail";

    //消息发送：短信下发成功/失败
    public static final byte LOCAL_SMS_SUCCESS = 1;
    public static final byte LOCAL_SMS_FAIL = 0;

    public static final String TIME_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";

    // message exception
    public static final String MESSAGE_EXCEPTION_NOUSER = "未查询到用户!";

    public enum SmsTypeNew {
        SMS_USER_REGISTER("101"),  //注册
        SMS_FIND_USER_PWD("102"),   //找回登录密码
        SMS_CHANGE_USER_PWD("104"),   //修改登录密码
        SMS_CHANGE_MOBILE("103"),   //修改手机号
        SMS_CHANGE_PAY_PWD("105"),   //修改交易密码
        SMS_SET_PAY_PWD("106");   //设置交易密码

        public String code;

        SmsTypeNew(String  code) {
            this.code = code;
        }
        @Override
        public String toString(){
            return this.code;
        }
    }

    public enum SmsType {
        SMS_USER_REGISTER("6524"),  //注册
        SMS_FIND_USER_PWD("6525"),   //找回登录密码
        SMS_CHANGE_USER_PWD("6527"),   //修改登录密码
        SMS_CHANGE_MOBILE("6526"),   //修改手机号
        SMS_CHANGE_PAY_PWD("6528"),   //修改支付密码
        SMS_SET_PAY_PWD("6587");   //设置支付密码

        public String code;

        SmsType(String  code) {
            this.code = code;
        }
        @Override
        public String toString(){
            return this.code;
        }
    }

    /** es操作(增删改查)默认字段 */
    // 聚合字段
    public static final String GOODS_ORDER_FIELD = "lastUpdateTime";
    public static final String GOODS_ORDER_SORT = "desc";
    public static final String GOODS_AGG_CATEGORY = "catAgg";
    public static final String GOODS_AGG_CATEGORY_FIELD = "catId";
    public static final String GOODS_AGG_ATTR = "attrAgg";
    public static final String GOODS_AGG_ATTR_FIELD = "attrId";
    public static final String GOODS_AGG_ATTR_TYPE = "attrTypeAgg";
    public static final String GOODS_AGG_ATTR_TYPE_FIELD = "attrTypeId";
    public static final String GOODS_AGG_AREA_COUNTRY = "countryAgg";
    public static final String GOODS_AGG_AREA_COUNTRY_FIELD = "areaCountry";
    public static final String GOODS_AGG_AREA_PROVINCE = "provinceAgg";
    public static final String GOODS_AGG_AREA_PROVINCE_FIELD = "areaProvince";
    public static final String GOODS_AGG_AREA_CITY = "cityAgg";
    public static final String GOODS_AGG_AREA_CITY_FIELD = "areaCity";

    /** 缓存 */
    public static final String CACHE_GOODS_CATEGORY = "categoryInfo";//商品分类信息
    public static final String CACHE_GOODS_ATTR = "goodsAttrInfo";//商品属性
    public static final String CACHE_GOODS_AREA = "regionInfo";//地域信息

    /** 商品状态 */
    public static final Byte GOODS_STATUS_DELETE = 0;
    public static final Byte GOODS_STATUS_UNDELETE = 1;
    public static final Byte GOODS_STATUS_ONSALE = 1;
    public static final Byte GOODS_STATUS_OFFSALE = 0;
    public static final Byte GOODS_STATUS_FORCE_OFFSALE = 2;//强制下架
    public static final Byte GOODS_CHECK_STATUS_WAIT = 0;
    public static final Byte GOODS_CHECK_STATUS_YES = 1;
    public static final Byte GOODS_CHECK_STATUS_NOT = 2;

    public static final Byte GOODS_ON_LINE = 0;//线上支付
    public static final Byte GOODS_OFF_LINE = 1;//线下支付

    public static final Byte GOODS_TYPE_0 = 0;//离线数据
    public static final Byte GOODS_TYPE_1 = 1;//API
    public static final Byte GOODS_TYPE_2 = 2;//数据模型
    public static final Byte GOODS_TYPE_3 = 4;//分析工具--独立软件
    public static final Byte GOODS_TYPE_5 = 5;//分析工具--SaaS
    public static final Byte GOODS_TYPE_6 = 6;//应用场景--独立软件
    public static final Byte GOODS_TYPE_7 = 7;//应用场景--SaaS

    /** 此字段用于比较当前时间与onsaleStartDate关系，选出非预约上架的商品 */
    public static final String ONSALE_START_DATE_FILEDNAME = "onsaleStartDate";
    public static final String ONSALE_START_DATE_FILED = "onsaleStartDateField";
    /** 符号 */
    public static final String SPACE_SIGN = "&nbsp;&nbsp;";
    public static final Integer ES_SIZE_BIGGER = 99999;

    /** 消息编号 */
    public static final Integer MESSAGE_201 = 201;//单位认证通过
    public static final Integer MESSAGE_202 = 202;//单位认证不通过
    public static final Integer MESSAGE_301 = 301;//银行卡签约绑定申请时，验证预留手机号码
    public static final Integer MESSAGE_302 = 302;//银行卡签约绑定成功后，通知用户
    public static final Integer MESSAGE_303 = 303;//银行卡签约绑定失败后，通知用户
    public static final Integer MESSAGE_401 = 401;//供应商审核通过
    public static final Integer MESSAGE_402 = 402;//供应商审核未通过
    public static final Integer MESSAGE_501 = 501;//商品上架审核通过
    public static final Integer MESSAGE_502 = 502;//商品上架审核不通过
    public static final Integer MESSAGE_503 = 503;//商品强制下架
    public static final Integer MESSAGE_601 = 601;//充值成功
    public static final Integer MESSAGE_602 = 602;//充值失败
    public static final Integer MESSAGE_603 = 603;//支付成功
    public static final Integer MESSAGE_604 = 604;//支付失败

    // 消息类型
    public static final byte MESSAGE_TYPE_SMS = 0;//短信
    public static final byte MESSAGE_TYPE_MAIL = 1;//邮件
    public static final byte MESSAGE_TYPE_STATION = 2;//站内信

    public enum AnalyzeOpt {
        ANALYZED("analyzed"),
        NOT_ANALYZED("not_analyzed");
        public String val;

        AnalyzeOpt(String val) {
            this.val = val;
        }
    }

    public enum Analyzer {
        IK_MAX_WORD("ik_max_word"),
        IK_SMART("ik_smart"),
        PINYIN("pinyin"),
        WHITESPACE("whitespace"),
        LC_INDEX("lc_index"),
        LC_SEARCH("lc_search"),
        NONE(null);

        public String val;

        Analyzer(String val) {
            this.val = val;
        }
    }

    public enum TermVector {
        OFFSETS("with_positions_offsets"),
        NONE(null);

        public String val;

        TermVector(String val) {
            this.val = val;
        }
    }

    public enum Type {
        TEXT("text"),
        KEYWORD("keyword"),
        DATE("date"),
        LONG("long"),
        DOUBLE("double"),
        BOOLEAN("boolean"),
        FLOAT("float"),
        INTEGER("integer"),
        SHORT("short"),
        BYTE("byte"),
        IP("ip"),
        COMPLETION("completion");

        public String val;

        Type(String val) {
            this.val = val;
        }
    }

    /**
     * 审核状态
     * 0    审核中
     * 1    审核通过
     * 2    审核失败
     */
    public enum CheckStatus {

        audit("0"),
        audit_success("1"),
        audit_fail("2"),
        audit_forceOff("3");

        public String code;

        CheckStatus(String code){
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 上架状态
     * 0    下架
     * 1    上架
     * 2    强制下架
     */
    public enum SaleStatus {

        sale("1"),
        off("0"),
        forceOff("2");

        public String code;

        SaleStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 0系统,1未认证,2个人,3个人待审核,4企业,5企业待审核,6个人审核失败,7企业审核失败8供应商,9供应商待审核,10供应商审核失败
     */
    public enum UserType {

        SYSTEM(0),
        NO_AUTH(1),
        PERSON_CHECK_OK(2),
        PERSON_CHECK_NO(3),
        PERSON_CHECK_FAIL(6),
        ORGANIZATION_CHECK_OK(4),
        ORGANIZATION_CHECK_NO(5),
        ORGANIZATION_CHECK_FAIL(7),
        SUPPLIER_CHECK_OK(8),
        SUPPLIER_CHECK_NO(9),
        SUPPLIER_CHECK_FAIL(10);

        public Integer code;

        UserType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 流水状态
     * 0    处理中
     * 1    成功
     * 2    失败
     */
    public enum CashStatus {

        handing((byte)0),
        success((byte)1),
        fail((byte)2);

        public byte code;

        CashStatus(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }

    /**
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
    public enum CashType {

        Deduct((byte)1),
        IntoAccount((byte)2),
        OnlineRecharge((byte)3),
        OnlineCash((byte)4),
        ManualRecharge((byte)5),
        ManualDebit((byte)6),
        OfflineRecharge((byte)7),
        CashREverse((byte)8);

        public byte code;

        CashType(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }

    /** 订单结算状态 */
    public static final Byte NO_SETTLE_STATUS = 0;
    public static final Byte HAS_SETTLE_STATUS = 1;

    /**
     * payTradeRecode
     交易平台类型
     1：在线充值（入金），
     2：在线提现（出金），
     5：手工充值,
     6：手工扣款，
     7：线下充值，
     8：提现冲账
     9: 结算扣款  从冻结部分和总金额部分 同时减去欲结算的金额
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
     * withdraw
     * 提现审核状态
         0待审核
         1审核成功
         2审核失败
         3处理成功
         4处理失败
     */
    public enum WithdrawStatus {

        waitCheck((byte)0),
        CheckSuccess((byte)1),
        checkFail((byte)2);
//        handleSuccess((byte)3),
//        handleFail((byte)4);

        public byte code;

        WithdrawStatus(byte code) {
            this.code = code;
        }

        public byte getCode() {
            return code;
        }
    }
}
