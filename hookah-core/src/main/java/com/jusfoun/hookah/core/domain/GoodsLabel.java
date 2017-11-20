package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

public class GoodsLabel extends GenericModel {
    @Id
    private String labId;
    private String labName;
    private String labType;
    private String labPy;
    private String labAllPy;
    private String userId;
    private Byte deleteStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getLabType() {
        return labType;
    }

    public void setLabType(String labType) {
        this.labType = labType;
    }

    public String getLabPy() {
        return labPy;
    }

    public void setLabPy(String labPy) {
        this.labPy = labPy;
    }

    public String getLabAllPy() {
        return labAllPy;
    }

    public void setLabAllPy(String labAllPy) {
        this.labAllPy = labAllPy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Byte deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}