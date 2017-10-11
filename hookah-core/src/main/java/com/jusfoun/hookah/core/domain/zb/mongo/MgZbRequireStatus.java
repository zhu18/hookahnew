package com.jusfoun.hookah.core.domain.zb.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MgZbRequireStatus extends GenericModel {

    @Id
    private String requirementNum;

    // 发布需求
    private String addTime;

    // 平台审核
    private String checkTime;
    private String checkContext;

    // 资金托管
    private String trusteeTime;

    // 平台发布
    private String pressTime;

    // 服务商工作
    private String workingTime;

    // 验收付款
    private String payTime;

    // 评价
    private String commentTime;

    // 报名
    private String applyTime;

    // 资格筛选
    private String selectTime;

    // 提交成果
    private String submitTime;

    // 平台预验
    private String platevalTime;

    // 需求方验收
    private String requiredAcceptTime;

    public String getRequirementNum() {
        return requirementNum;
    }

    public void setRequirementNum(String requirementNum) {
        this.requirementNum = requirementNum;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckContext() {
        return checkContext;
    }

    public void setCheckContext(String checkContext) {
        this.checkContext = checkContext;
    }

    public String getTrusteeTime() {
        return trusteeTime;
    }

    public void setTrusteeTime(String trusteeTime) {
        this.trusteeTime = trusteeTime;
    }

    public String getPressTime() {
        return pressTime;
    }

    public void setPressTime(String pressTime) {
        this.pressTime = pressTime;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getPlatevalTime() {
        return platevalTime;
    }

    public void setPlatevalTime(String platevalTime) {
        this.platevalTime = platevalTime;
    }

    public String getRequiredAcceptTime() {
        return requiredAcceptTime;
    }

    public void setRequiredAcceptTime(String requiredAcceptTime) {
        this.requiredAcceptTime = requiredAcceptTime;
    }

    @Override
    public String toString() {
        return "MgZbRequireStatus{" +
                "requirementNum='" + requirementNum + '\'' +
                ", addTime='" + addTime + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", checkContext='" + checkContext + '\'' +
                ", trusteeTime='" + trusteeTime + '\'' +
                ", pressTime='" + pressTime + '\'' +
                ", workingTime='" + workingTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", applyTime='" + applyTime + '\'' +
                ", selectTime='" + selectTime + '\'' +
                ", submitTime='" + submitTime + '\'' +
                ", platevalTime='" + platevalTime + '\'' +
                ", requiredAcceptTime='" + requiredAcceptTime + '\'' +
                '}';
    }
}
