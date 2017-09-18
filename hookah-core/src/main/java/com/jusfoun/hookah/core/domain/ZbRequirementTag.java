package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbRequirementTag extends GenericModel {
  @Id
  private Long id;
  private Long requirementId;
  private Long tagId;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;

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

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}
