package com.jusfoun.hookah.crowd.constants;

public class ZbContants {

    /**
     * 需求周期状态
     0草稿
     1待审核
     2审核未通过
     3待托管资金
     4待发布
     5报名中
     6平台筛选
     7待二次托管资金
     8工作中
     9待平台验收
     10待需方验收
     11已验收代付款
     12二次工作中
     13待评价
     14交易取消
     15交易成功
     16待退款
     17删除
     18取消
     19到期
     20违约失败
     */
    public enum Zb_Require_Status {

        DRAFT(0),
        WAIT_CHECK(1),
        CHECK_NOT(2),
        WAIT_TG(3),
        WAIT_FB(4),
        SINGING(5),
        SELECTING(6),
        WAIT_TWO_TG(7),
        WORKINGING(8),
        WAIT_PLAT_YS(9),
        WAIT_buyer_YS(10),
        WAIT_FK(11),
        TWO_WORKING(12),
        WAIT_PJ(13),
        ZB_SUCCESS(14),
        ZB_FAIL(15),
        WAIT_TK(16),
        DELETE(17),
        CANCEL(18),
        DATE_EXPIRE(19),
        BREA_FAILE(20)
        ;

        public Integer code;

        Zb_Require_Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }



}
