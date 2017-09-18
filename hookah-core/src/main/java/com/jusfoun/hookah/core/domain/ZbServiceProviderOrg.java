package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbServiceProviderOrg extends GenericModel {
  @Id
  private Long id;
  private String userId;
  private String orgName;
  private String orgDesc;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;
  private Long status;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

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

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getOrgDesc() {
    return orgDesc;
  }

  public void setOrgDesc(String orgDesc) {
    this.orgDesc = orgDesc;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
