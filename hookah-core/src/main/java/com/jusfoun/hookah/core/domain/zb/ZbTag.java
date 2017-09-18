package com.jusfoun.hookah.core.domain.zb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbTag extends GenericModel {
  @Id
  private Long id;
  private String tagContent;
  private Long type;
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

  public String getTagContent() {
    return tagContent;
  }

  public void setTagContent(String tagContent) {
    this.tagContent = tagContent;
  }

  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
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
