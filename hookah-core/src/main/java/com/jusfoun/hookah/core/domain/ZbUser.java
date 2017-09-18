package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

public class ZbUser extends GenericModel {
  private Long userId;
  private String userDesc;
  private Long userStatus;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserDesc() {
    return userDesc;
  }

  public void setUserDesc(String userDesc) {
    this.userDesc = userDesc;
  }

  public Long getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(Long userStatus) {
    this.userStatus = userStatus;
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
