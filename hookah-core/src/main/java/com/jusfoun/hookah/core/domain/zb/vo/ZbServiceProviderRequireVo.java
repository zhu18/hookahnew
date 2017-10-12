package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbComment;

/**
 * 服务商 - 查看需求
 * Created by ctp on 2017/10/11.
 */
public class ZbServiceProviderRequireVo {

    private Short reqStatus;//当前需求状态

    private ZbRequirementSPVo zbRequirementSPVo;//需求

    private ZbRequirementApplyVo zbRequirementApplyVo;//报名信息

    private ZbProgramVo zbProgramVo;//方案信息

    private ZbComment zbComment;//评价

    public Short getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(Short reqStatus) {
        this.reqStatus = reqStatus;
    }

    public ZbComment getZbComment() {
        return zbComment;
    }

    public void setZbComment(ZbComment zbComment) {
        this.zbComment = zbComment;
    }

    public ZbRequirementSPVo getZbRequirementSPVo() {
        return zbRequirementSPVo;
    }

    public void setZbRequirementSPVo(ZbRequirementSPVo zbRequirementSPVo) {
        this.zbRequirementSPVo = zbRequirementSPVo;
    }

    public ZbRequirementApplyVo getZbRequirementApplyVo() {
        return zbRequirementApplyVo;
    }

    public void setZbRequirementApplyVo(ZbRequirementApplyVo zbRequirementApplyVo) {
        this.zbRequirementApplyVo = zbRequirementApplyVo;
    }

    public ZbProgramVo getZbProgramVo() {
        return zbProgramVo;
    }

    public void setZbProgramVo(ZbProgramVo zbProgramVo) {
        this.zbProgramVo = zbProgramVo;
    }
}
