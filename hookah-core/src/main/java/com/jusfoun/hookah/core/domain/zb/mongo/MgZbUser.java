package com.jusfoun.hookah.core.domain.zb.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 个人服务商认证
 */
@Document
public class MgZbUser extends GenericModel {

    @Id
    private String userId;

    private String personDesc;                          //个人介绍

    private Integer status;                             //认证状态

    private List<String> specialSkills;                 //擅长领域

    private List<EducationsExp> educationsExpList;      //

    private List<WorksExp> worksExpList;                //

    private List<ProjectsExp> projectsExpList;          //

    public static class EducationsExp implements Serializable {

        private String schoolName;  //学校名称
        private String major;       //专业名称
        private Date startTime;     //就学开始时间
        private Date endTime;       //结束时间
        private String edu;         //学历
        private Integer orExam;     //是否统招
        private List<String> certPath;//证明材料

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getEdu() {
            return edu;
        }

        public void setEdu(String edu) {
            this.edu = edu;
        }

        public Integer getOrExam() {
            return orExam;
        }

        public void setOrExam(Integer orExam) {
            this.orExam = orExam;
        }

        public List<String> getCertPath() {
            return certPath;
        }

        public void setCertPath(List<String> certPath) {
            this.certPath = certPath;
        }
    }
    public static class WorksExp implements Serializable {

        private String companyName;         //公司名称
        private String departName;          //部门名称
        private Date startTime;             //开始花间
        private Date endTime;               //结束时间
        private String position;            //职位
        private List<String> certPath;      //证明材料

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public List<String> getCertPath() {
            return certPath;
        }

        public void setCertPath(List<String> certPath) {
            this.certPath = certPath;
        }
    }
    public static class ProjectsExp implements Serializable {

        private String projectName;     //项目名称
        private String projectDuty;     //项目职责
        private Date startTime;         //开始时间
        private Date endTime;           //结束时间
        private String projectDesc;     //项目描述

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectDuty() {
            return projectDuty;
        }

        public void setProjectDuty(String projectDuty) {
            this.projectDuty = projectDuty;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getProjectDesc() {
            return projectDesc;
        }

        public void setProjectDesc(String projectDesc) {
            this.projectDesc = projectDesc;
        }
    }

}
