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

    /** 短信过期时间，单位秒 */
    public static final Integer SMS_DURATION_SECONDS = 30;

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
        IP("ip");

        public String val;

        Type(String val) {
            this.val = val;
        }
    }
}
