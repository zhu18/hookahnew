package com.jusfoun.hookah.core.domain.zb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbProgramFiles extends GenericModel {
  @Id
  private Long id;
  private Long programId;
  private String fileName;
  private String filePath;
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date addTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProgramId() {return programId;}

  public void setProgramId(Long programId) {this.programId = programId;}

  public String getFileName() {return fileName;}

  public void setFileName(String fileName) {this.fileName = fileName;}

  public String getFilePath() {return filePath;}

  public void setFilePath(String filePath) {this.filePath = filePath;}

  public Date getAddTime() {return addTime;}

  public void setAddTime(Date addTime) {this.addTime = addTime;}
}
