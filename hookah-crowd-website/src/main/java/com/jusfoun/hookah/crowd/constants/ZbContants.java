package com.jusfoun.hookah.crowd.constants;

public class ZbContants {

    /**
     * 需求周期状态
     0: 草稿
     1：待审核,
     2：待托管赏金，
     3：待发布，
     4：报名中，
     5：待验收，
     6：已验收待付款，
     7：待评价，
     8：交易成功，
     9：交易失败，
     10：交易失败待退款
     */
    public enum Zb_Require_Status {

        DRAFT(0),
        WAIT_CHECK(1),
        WAIT_TG(2),
        WAIT_FB(3),
        SINGING(4),
        WAIT_YS(5),
        WAIT_FK(6),
        WAIT_PJ(7),
        ZB_SUCCESS(8),
        ZB_FAIL(9),
        WAIT_TK(10);

        public Integer code;

        Zb_Require_Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }



}
