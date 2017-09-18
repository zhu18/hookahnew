package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import java.util.Date;

public class Zb_program {
  @Id
  private Long id;
  private Long applyId;
  private String title;
  private String content;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getApplyId() {return applyId;}

  public void setApplyId(Long applyId) {this.applyId = applyId;}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getAddTime() {
    return addTime;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }
}
