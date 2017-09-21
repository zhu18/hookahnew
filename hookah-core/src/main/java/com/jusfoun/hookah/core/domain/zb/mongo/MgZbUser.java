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

    private String personDesc;

    private Integer status;

    private List<String> specialSkills;

    private List<EducationsExp> educationsExpList;

    private List<WorksExp> worksExpList;

    private List<ProjectsExp> projectsExpList;

    public static class EducationsExp implements Serializable {

        private String schoolName;
        private String major;
        private Date startTime;
        private Date endTime;
        private String edu;
        private Integer orExam;
        private List<String> certPath;

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

        private String companyName;
        private String departName;
        private Date startTime;
        private Date endTime;
        private String position;
        private List<String> certPath;

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
        private String projectName;
        private String projectDuty;
        private Date startTime;
        private Date endTime;
        private String projectDesc;

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
