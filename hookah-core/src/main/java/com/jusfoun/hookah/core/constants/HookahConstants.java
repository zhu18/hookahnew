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
        audit_fail("2");

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
     * 0系统,1未认证,2个人,3个人待审核,4企业,5企业待审核,6个人审核失败,7企业审核失败
     */
    public enum UserType {

        SYSTEM(0),
        NO_AUTH(1),
        PERSON_CHECK_OK(2),
        PERSON_CHECK_NO(3),
        PERSON_CHECK_FAIL(6),
        ORGANIZATION_CHECK_OK(4),
        ORGANIZATION_CHECK_NO(5),
        ORGANIZATION_CHECK_FAIL(7);


        public Integer code;

        UserType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

}
