package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.UserInvoiceTitle;

import java.io.Serializable;

/**
 * 用于展示发票详情页
 */
public class UserInvoiceTitleVo extends UserInvoiceTitle implements Serializable {

    private String userName;

    private String realName;

    private Byte userType;

    private String auditOpinion;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
