package com.jusfoun.hookah.core.domain.jf;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

public class JfRecord extends GenericModel {

    private Long id;

    private String userId;

    private Integer sourceId;

    private Integer score;

    private String note;

    private Short expire;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String operator;

    private String addDate;

    private Short action;

    private String actionDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Short getExpire() {
        return expire;
    }

    public void setExpire(Short expire) {
        this.expire = expire;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public Short getAction() {
        return action;
    }

    public void setAction(Short action) {
        this.action = action;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public JfRecord() {
    }

    public JfRecord(String userId, Integer sourceId, Integer score, String note, Short expire,
                    Date addTime, String operator, String addDate, Short action, String actionDesc) {
        this.userId = userId;
        this.sourceId = sourceId;
        this.score = score;
        this.note = note;
        this.expire = expire;
        this.addTime = addTime;
        this.operator = operator;
        this.addDate = addDate;
        this.action = action;
        this.actionDesc = actionDesc;
    }
}