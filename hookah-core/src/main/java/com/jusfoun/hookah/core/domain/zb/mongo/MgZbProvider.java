package com.jusfoun.hookah.core.domain.zb.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String upname;//姓名-企业名称

    private String ucity;//所在城市

    private Integer ucreditValue;//信誉值

    private List<Evalevel> evalevelList; //用户评价

    private String phoneNum;//手机号码

    private String lawPersonName;//企业法人代表

    private String registerTime;//注册时间

    private String registerAddr;//注册地点

    private String scopeOfBuss;//经营范围

    private String creditCode;//统一社会信用代码

    private Integer authType;                           // 1个人2企业

    private String providerDesc;                        //服务商介绍

    private Integer status = 1;                         //认证状态 0.未认证 1.审核中 2.已认证 3.认证失败

    private List<String> specialSkills;                 //擅长领域

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String checkContent;                        //审核意见

    private String checkUser;                           //审核人

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private String checkTime;                           //审核时间

    public static class CertPaths implements Serializable {
        private String certName;//材料名称
        private String certPath;//证明材料

        public String getCertName() {
            return certName;
        }

        public void setCertName(String certName) {
            this.certName = certName;
        }

        public String getCertPath() {
            return certPath;
        }

        public void setCertPath(String certPath) {
            this.certPath = certPath;
        }
    }

    /**
     * 个人服务商认证
     */
    private List<EducationsExp> educationsExpList;      //教育经历

    private List<WorksExp> worksExpList;                //工作经历

    private List<ProjectsExp> projectsExpList;          //项目经历

    public static class EducationsExp implements Serializable {

        private String sn;
        private String schoolName;  //学校名称
        private String major;       //专业名称
        private String startTime;     //就学开始时间
        private String endTime;       //结束时间
        private String edu;         //学历
        private Integer orExam;     //是否统招
        private List<CertPaths> certPathsList;//证明材料

        public List<CertPaths> getCertPathsList() {
            return certPathsList;
        }

        public void setCertPathsList(List<CertPaths> certPathsList) {
            this.certPathsList = certPathsList;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
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


    }

    public static class WorksExp implements Serializable {

        private String sn;
        private String companyName;         //公司名称
        private String departName;          //部门名称
        private String startTime;             //开始花间
        private String endTime;               //结束时间
        private String position;            //职位
        private List<CertPaths> certPathsList;//证明材料

        public List<CertPaths> getCertPathsList() {
            return certPathsList;
        }

        public void setCertPathsList(List<CertPaths> certPathsList) {
            this.certPathsList = certPathsList;
        }


        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

    }

    public static class ProjectsExp implements Serializable {

        private String sn;
        private String projectName;     //项目名称
        private String projectDuty;     //项目职责
        private String startTime;         //开始时间
        private String endTime;           //结束时间
        private String projectDesc;     //项目描述

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
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

        private String sn;
        private String caseName;    //应用案例名称
        private String startTime;     //开始时间
        private String endTime;       //结束时间
        private String caseView;    //案例概述
        private String caseDesc;    //解决方案
        private List<CertPaths> certPathsList;//证明材料

        public List<CertPaths> getCertPathsList() {
            return certPathsList;
        }

        public void setCertPathsList(List<CertPaths> certPathsList) {
            this.certPathsList = certPathsList;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getCaseName() {
            return caseName;
        }

        public void setCaseName(String caseName) {
            this.caseName = caseName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCaseDesc() {
            return caseDesc;
        }

        public void setCaseDesc(String caseDesc) {
            this.caseDesc = caseDesc;
        }

        public String getCaseView() {
            return caseView;
        }

        public void setCaseView(String caseView) {
            this.caseView = caseView;
        }
    }

    public static class SoftWarePower implements Serializable {

        private String sn;
        private String softWareName;    //  软件名称
        private String publicTime;      //  首次发表日期
        private String registerNum;     //  登记号
        private String purpose;         //  软件用途
        private List<CertPaths> certPathsList;//证明材料

        public List<CertPaths> getCertPathsList() {
            return certPathsList;
        }

        public void setCertPathsList(List<CertPaths> certPathsList) {
            this.certPathsList = certPathsList;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getSoftWareName() {
            return softWareName;
        }

        public void setSoftWareName(String softWareName) {
            this.softWareName = softWareName;
        }

        public String getPublicTime() {
            return publicTime;
        }

        public void setPublicTime(String publicTime) {
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
    }

    public static class InventionPatent implements Serializable {

        private String sn;
        private String patentName;      //  专利名称
        private String patentNum;       //  专利号
        private String applyTime;         //  申请日期
        private String patentDesc;     //   专利概述
        private List<CertPaths> certPathsList;//证明材料

        public List<CertPaths> getCertPathsList() {
            return certPathsList;
        }

        public void setCertPathsList(List<CertPaths> certPathsList) {
            this.certPathsList = certPathsList;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getPatentName() {
            return patentName;
        }

        public void setPatentName(String patentName) {
            this.patentName = patentName;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getPatentNum() {
            return patentNum;
        }

        public void setPatentNum(String patentNum) {
            this.patentNum = patentNum;
        }

        public String getPatentDesc() {
            return patentDesc;
        }

        public void setPatentDesc(String patentDesc) {
            this.patentDesc = patentDesc;
        }
    }

    /**
     * 评论级别
     */
    public static class Evalevel implements Serializable{

        private Integer starsNum;

        private Integer evalNum;

        public Integer getStarsNum() {
            return starsNum;
        }

        public void setStarsNum(Integer starsNum) {
            this.starsNum = starsNum;
        }

        public Integer getEvalNum() {
            return evalNum;
        }

        public void setEvalNum(Integer evalNum) {
            this.evalNum = evalNum;
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

    public String getProviderDesc() {
        return providerDesc;
    }

    public void setProviderDesc(String providerDesc) {
        this.providerDesc = providerDesc;
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

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public String getUcity() {
        return ucity;
    }

    public void setUcity(String ucity) {
        this.ucity = ucity;
    }

    public Integer getUcreditValue() {
        return ucreditValue;
    }

    public void setUcreditValue(Integer ucreditValue) {
        this.ucreditValue = ucreditValue;
    }

    public List<Evalevel> getEvalevelList() {
        return evalevelList;
    }

    public void setEvalevelList(List<Evalevel> evalevelList) {
        this.evalevelList = evalevelList;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLawPersonName() {
        return lawPersonName;
    }

    public void setLawPersonName(String lawPersonName) {
        this.lawPersonName = lawPersonName;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getScopeOfBuss() {
        return scopeOfBuss;
    }

    public void setScopeOfBuss(String scopeOfBuss) {
        this.scopeOfBuss = scopeOfBuss;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
}
