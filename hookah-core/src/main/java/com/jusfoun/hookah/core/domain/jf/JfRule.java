package com.jusfoun.hookah.core.domain.jf;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

public class JfRule extends GenericModel {
    private Integer id;

    private Byte sn;

    private Byte action;

    private String actionDesc;

    private Integer score;

    private Integer upperLimit;

    private Integer lowerLimit;

    private Byte upperTimeLimit;

    private Byte lowerTimeLimit;

    private String operator;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getSn() {
        return sn;
    }

    public void setSn(Byte sn) {
        this.sn = sn;
    }

    public Byte getAction() {
        return action;
    }

    public void setAction(Byte action) {
        this.action = action;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc == null ? null : actionDesc.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Byte getUpperTimeLimit() {
        return upperTimeLimit;
    }

    public void setUpperTimeLimit(Byte upperTimeLimit) {
        this.upperTimeLimit = upperTimeLimit;
    }

    public Byte getLowerTimeLimit() {
        return lowerTimeLimit;
    }

    public void setLowerTimeLimit(Byte lowerTimeLimit) {
        this.lowerTimeLimit = lowerTimeLimit;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}