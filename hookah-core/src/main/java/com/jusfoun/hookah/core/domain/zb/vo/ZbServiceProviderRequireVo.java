package com.jusfoun.hookah.core.domain.zb.vo;

import com.jusfoun.hookah.core.domain.zb.ZbComment;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.domain.zb.mongo.MgZbRequireStatus;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.List;

/**
 * 服务商 - 查看需求
 * Created by ctp on 2017/10/11.
 */
public class ZbServiceProviderRequireVo extends GenericModel {

    private Integer userType;//当前用户类型

    private Short reqStatus;//当前需求状态

    private ZbRequirementSPVo zbRequirementSPVo;//需求

    private ZbRequirementApplyVo zbRequirementApplyVo;//报名信息

    private ZbProgramVo zbProgramVo;//方案信息

    private ZbCommentVo zbCommentVo;//评价

    private MgZbRequireStatus mgZbRequireStatus = new MgZbRequireStatus();//进度条状态时间

    private List<ZbRequirement>  analogyTask;//类似任务

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<ZbRequirement> getAnalogyTask() {
        return analogyTask;
    }

    public void setAnalogyTask(List<ZbRequirement> analogyTask) {
        this.analogyTask = analogyTask;
    }

    public MgZbRequireStatus getMgZbRequireStatus() {
        return mgZbRequireStatus;
    }

    public void setMgZbRequireStatus(MgZbRequireStatus mgZbRequireStatus) {
        this.mgZbRequireStatus = mgZbRequireStatus;
    }

    public Short getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(Short reqStatus) {
        this.reqStatus = reqStatus;
    }

    public ZbCommentVo getZbCommentVo() {
        return zbCommentVo;
    }

    public void setZbCommentVo(ZbCommentVo zbCommentVo) {
        this.zbCommentVo = zbCommentVo;
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
