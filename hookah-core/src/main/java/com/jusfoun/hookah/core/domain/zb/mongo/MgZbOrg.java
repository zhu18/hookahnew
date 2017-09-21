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
public class MgZbOrg extends GenericModel {

    @Id
    private String userId;

    private String companyDesc;                     //企业介绍

    private Integer status;                         //认证状态

    private List<String> specialSkills;             //擅长领域

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

}
