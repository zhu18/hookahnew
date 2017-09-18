package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import java.util.Date;

public class Zb_requirement {
  @Id
  private Long id;
  private String requireSn;
  private String userId;
  private String title;
  private String contactName;
  private String contactPhone;
  private String description;
  private Long rewardMoney;
  private Long trusteePercent;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date deliveryDeadline;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date applyDeadline;
  private String checkRemark;
  private Long status;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;
  private String addOperator;
  private String updateOperator;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRequireSn() {
    return requireSn;
  }

  public void setRequireSn(String requireSn) {
    this.requireSn = requireSn;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getRewardMoney() {
    return rewardMoney;
  }

  public void setRewardMoney(Long rewardMoney) {
    this.rewardMoney = rewardMoney;
  }

  public Long getTrusteePercent() {
    return trusteePercent;
  }

  public void setTrusteePercent(Long trusteePercent) {
    this.trusteePercent = trusteePercent;
  }

  public Date getDeliveryDeadline() {
    return deliveryDeadline;
  }

  public void setDeliveryDeadline(Date deliveryDeadline) {
    this.deliveryDeadline = deliveryDeadline;
  }

  public Date getApplyDeadline() {
    return applyDeadline;
  }

  public void setApplyDeadline(Date applyDeadline) {
    this.applyDeadline = applyDeadline;
  }

  public String getCheckRemark() {
    return checkRemark;
  }

  public void setCheckRemark(String checkRemark) {
    this.checkRemark = checkRemark;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
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

  public String getAddOperator() {
    return addOperator;
  }

  public void setAddOperator(String addOperator) {
    this.addOperator = addOperator;
  }

  public String getUpdateOperator() {
    return updateOperator;
  }

  public void setUpdateOperator(String updateOperator) {
    this.updateOperator = updateOperator;
  }
}
