package com.jusfoun.hookah.core.constants;

/**
 * Created by wangjl on 2017-3-22.
 */
public class HookahConstants {
    /** 第几页 */
    public static final int PAGE_NUM = 1;
    /** 每页记录数 */
    public static final int PAGE_SIZE = 10;
    /** es分词 */
    public static final String ES_ANALYZER_IK_MAX = "ik_max_word";
    public static final String ES_ANALYZER_IK_SMART = "ik_smart_word";

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
        PINYIN("pinyin");

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
}
