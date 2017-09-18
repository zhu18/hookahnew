package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import java.util.Date;

public class Zb_requirement_check {
  @Id
  private Long id;
  private Long requirementId;
  private String checkContent;
  private String checkUser;
  private Long checkStatus;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date checkTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRequirementId() {
    return requirementId;
  }

  public void setRequirementId(Long requirementId) {
    this.requirementId = requirementId;
  }

  public String getCheckContent() {
    return checkContent;
  }

  public void setCheckContent(String checkContent) {
    this.checkContent = checkContent;
  }

  public String getCheckUser() {
    return checkUser;
  }

  public void setCheckUser(String checkUser) {
    this.checkUser = checkUser;
  }

  public Long getCheckStatus() {
    return checkStatus;
  }

  public void setCheckStatus(Long checkStatus) {
    this.checkStatus = checkStatus;
  }

  public Date getCheckTime() {
    return checkTime;
  }

  public void setCheckTime(Date checkTime) {
    this.checkTime = checkTime;
  }
}
