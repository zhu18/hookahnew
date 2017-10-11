package com.jusfoun.hookah.core.domain.zb.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document
public class MgZbRequireStatus extends GenericModel {

    @Id
    private String requirementNum;

    // 发布需求
    private Date addTime;

    // 平台审核
    private Date checkTime;
    private String checkContext;

    // 资金托管
    private Date trusteeTime;

    // 平台发布
    private Date pressTime;

    // 服务商工作
    private Date workingTime;

    // 验收付款
    private Date payTime;

    // 评价
    private Date commentTime;

    // 报名
    private Date applyTime;

    // 资格筛选
    private Date selectTime;

    // 提交成果
    private Date submitTime;

    // 平台预验
    private Date platevalTime;

    // 需求方验收
    private Date requiredAcceptTime;

    public String getRequirementNum() {
        return requirementNum;
    }

    public void setRequirementNum(String requirementNum) {
        this.requirementNum = requirementNum;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckContext() {
        return checkContext;
    }

    public void setCheckContext(String checkContext) {
        this.checkContext = checkContext;
    }

    public Date getTrusteeTime() {
        return trusteeTime;
    }

    public void setTrusteeTime(Date trusteeTime) {
        this.trusteeTime = trusteeTime;
    }

    public Date getPressTime() {
        return pressTime;
    }

    public void setPressTime(Date pressTime) {
        this.pressTime = pressTime;
    }

    public Date getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Date workingTime) {
        this.workingTime = workingTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(Date selectTime) {
        this.selectTime = selectTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getPlatevalTime() {
        return platevalTime;
    }

    public void setPlatevalTime(Date platevalTime) {
        this.platevalTime = platevalTime;
    }

    public Date getRequiredAcceptTime() {
        return requiredAcceptTime;
    }

    public void setRequiredAcceptTime(Date requiredAcceptTime) {
        this.requiredAcceptTime = requiredAcceptTime;
    }
}
