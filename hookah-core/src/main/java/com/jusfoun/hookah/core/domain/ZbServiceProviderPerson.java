package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbServiceProviderPerson extends GenericModel {
  @Id
  private Long id;
  private String userId;
  private String realName;
  private String cardNum;
  private String mobile;
  private String personDesc;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
  private Long status;

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
    this.userId = userId;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPersonDesc() {
    return personDesc;
  }

  public void setPersonDesc(String personDesc) {
    this.personDesc = personDesc;
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

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }
}
