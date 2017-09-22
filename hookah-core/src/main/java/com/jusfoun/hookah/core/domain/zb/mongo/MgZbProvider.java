package com.jusfoun.hookah.core.domain.zb.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document
public class MgZbProvider extends GenericModel {

    @Id
    private String userId;

    private Integer authType;                           // 1个人2企业

    private String personDesc;                          //服务商介绍

    private Integer status;                             //认证状态 0.未认证 1.审核中 2.已认证 3.认证失败

    private List<String> specialSkills;                 //擅长领域

    /**
     * 个人服务商认证
     */
    private List<EducationsExp> educationsExpList;      //教育经历

    private List<WorksExp> worksExpList;                //工作经历

    private List<ProjectsExp> projectsExpList;          //项目经历

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

    /**
     * 企业服务商认证
     */
    private List<AppCase> appCaseList;              //应用案例

    private List<SoftWarePower> swpList;            //软件著作权

    private List<InventionPatent> inPatentsList;    //发明专利

    public static class AppCase implements Serializable {
        private String caseName;    //应用案例名称
        private Date startTime;     //开始时间
        private Date endTime;       //结束时间
        private String caseDesc;    //解决方案

        private List<String> certPath;//证明材料

        public String getCaseName() {
            return caseName;
        }

        public void setCaseName(String caseName) {
            this.caseName = caseName;
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

        public String getCaseDesc() {
            return caseDesc;
        }

        public void setCaseDesc(String caseDesc) {
            this.caseDesc = caseDesc;
        }

        public List<String> getCertPath() {
            return certPath;
        }
        public void setCertPath(List<String> certPath) {
            this.certPath = certPath;
        }

    }

    public static class SoftWarePower implements Serializable {
        private String softWareName;    //  软件名称
        private Date publicTime;        //  首次发表日期
        private String registerNum;     //  登记号
        private String purpose;         //  软件用途

        private List<String> certPath;  //  证明材料

        public String getSoftWareName() {
            return softWareName;
        }

        public void setSoftWareName(String softWareName) {
            this.softWareName = softWareName;
        }

        public Date getPublicTime() {
            return publicTime;
        }

        public void setPublicTime(Date publicTime) {
            this.publicTime = publicTime;
        }

        public String getRegisterNum() {
            return registerNum;
        }

        public void setRegisterNum(String registerNum) {
            this.registerNum = registerNum;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public List<String> getCertPath() {
            return certPath;
        }
        public void setCertPath(List<String> certPath) {
            this.certPath = certPath;
        }

    }

    public static class InventionPatent implements Serializable {
        private String patentName;      //  专利名称
        private String PatentNum;       //  专利号
        private Date applyTime;         //  申请日期
        private String PatentDesc;     //   专利概述

        private List<String> certPath;  //  证明材料

        public String getPatentName() {
            return patentName;
        }

        public void setPatentName(String patentName) {
            this.patentName = patentName;
        }

        public String getPatentNum() {
            return PatentNum;
        }

        public void setPatentNum(String patentNum) {
            PatentNum = patentNum;
        }

        public Date getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(Date applyTime) {
            this.applyTime = applyTime;
        }

        public String getPatentDesc() {
            return PatentDesc;
        }

        public void setPatentDesc(String patentDesc) {
            PatentDesc = patentDesc;
        }

        public List<String> getCertPath() {
            return certPath;
        }
        public void setCertPath(List<String> certPath) {
            this.certPath = certPath;
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getPersonDesc() {
        return personDesc;
    }

    public void setPersonDesc(String personDesc) {
        this.personDesc = personDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getSpecialSkills() {
        return specialSkills;
    }

    public void setSpecialSkills(List<String> specialSkills) {
        this.specialSkills = specialSkills;
    }

    public List<EducationsExp> getEducationsExpList() {
        return educationsExpList;
    }

    public void setEducationsExpList(List<EducationsExp> educationsExpList) {
        this.educationsExpList = educationsExpList;
    }

    public List<WorksExp> getWorksExpList() {
        return worksExpList;
    }

    public void setWorksExpList(List<WorksExp> worksExpList) {
        this.worksExpList = worksExpList;
    }

    public List<ProjectsExp> getProjectsExpList() {
        return projectsExpList;
    }

    public void setProjectsExpList(List<ProjectsExp> projectsExpList) {
        this.projectsExpList = projectsExpList;
    }

    public List<AppCase> getAppCaseList() {
        return appCaseList;
    }

    public void setAppCaseList(List<AppCase> appCaseList) {
        this.appCaseList = appCaseList;
    }

    public List<SoftWarePower> getSwpList() {
        return swpList;
    }

    public void setSwpList(List<SoftWarePower> swpList) {
        this.swpList = swpList;
    }

    public List<InventionPatent> getInPatentsList() {
        return inPatentsList;
    }

    public void setInPatentsList(List<InventionPatent> inPatentsList) {
        this.inPatentsList = inPatentsList;
    }
}
