package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.ExpressInfo;
import com.jusfoun.hookah.core.domain.Invoice;
import com.jusfoun.hookah.core.domain.UserInvoiceAddress;
import com.jusfoun.hookah.core.domain.UserInvoiceTitle;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-17.
 */
public class InvoiceDTOVo implements Serializable {

    private String invoiceId;// 发票ID
    private String titleId; // 抬头ID
    private String id; // 收票人ID
    private String orderIds; // 订单IDs
    private String userId; // 用户ID

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}