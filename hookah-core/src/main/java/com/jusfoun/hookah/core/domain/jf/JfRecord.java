package com.jusfoun.hookah.core.domain.jf;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class JfRecord extends GenericModel {

    @Id
    private Long id;

    private String userId;

    private Byte sourceId;

    private Byte action;

    private Integer score;

    private String note;

    private Byte expire;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date addTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String operator;

    private String addDate;

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

    public Byte getSourceId() {
        return sourceId;
    }

    public void setSourceId(Byte sourceId) {
        this.sourceId = sourceId;
    }

    public Byte getAction() {
        return action;
    }

    public void setAction(Byte action) {
        this.action = action;
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

    public Byte getExpire() {
        return expire;
    }

    public void setExpire(Byte expire) {
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

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc == null ? null : actionDesc.trim();
    }

    public JfRecord() {
    }

    public JfRecord(String userId, Byte sourceId, Byte action, Integer score,
                    String note, Byte expire, Date addTime,
                    String operator, String addDate, String actionDesc) {
        this.userId = userId;
        this.sourceId = sourceId;
        this.action = action;
        this.score = score;
        this.note = note;
        this.expire = expire;
        this.addTime = addTime;
        this.operator = operator;
        this.addDate = addDate;
        this.actionDesc = actionDesc;
    }
}