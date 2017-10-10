package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbRequirementApply;

/**
 * Created by ctp on 2017/9/28.
 */
public class ZbRequirementApplyVo extends ZbRequirementApply {

    private String currUserPhone;//报名者手机号(当前用户)

    private Integer applyNumber;//报名数量

    public String getCurrUserPhone() {
        return currUserPhone;
    }

    public void setCurrUserPhone(String currUserPhone) {
        this.currUserPhone = currUserPhone;
    }

    public Integer getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(Integer applyNumber) {
        this.applyNumber = applyNumber;
    }
}
