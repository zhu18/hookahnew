package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.UserInvoiceTitle;

import java.io.Serializable;

/**
 * 用于展示发票详情页
 */
public class UserInvoiceTitleVo extends UserInvoiceTitle implements Serializable {

    private byte invoiceStatus;

    private String auditOpinion;

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
