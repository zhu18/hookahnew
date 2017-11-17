package com.jusfoun.hookah.core.domain.jf;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.util.Date;

public class JfOverdueDetails extends GenericModel {
    private Long id;

    private String settleDate;

    private Integer thisMonthGetTotal;

    private Integer thisMonthSurTotal;

    private String lastExpire;

    private Integer thisMonthExpTotal;

    private String obtainDate;

    private Date addTime;

    private String operator;

    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public Integer getThisMonthGetTotal() {
        return thisMonthGetTotal;
    }

    public void setThisMonthGetTotal(Integer thisMonthGetTotal) {
        this.thisMonthGetTotal = thisMonthGetTotal;
    }

    public Integer getThisMonthSurTotal() {
        return thisMonthSurTotal;
    }

    public void setThisMonthSurTotal(Integer thisMonthSurTotal) {
        this.thisMonthSurTotal = thisMonthSurTotal;
    }

    public String getLastExpire() {
        return lastExpire;
    }

    public void setLastExpire(String lastExpire) {
        this.lastExpire = lastExpire == null ? null : lastExpire.trim();
    }

    public Integer getThisMonthExpTotal() {
        return thisMonthExpTotal;
    }

    public void setThisMonthExpTotal(Integer thisMonthExpTotal) {
        this.thisMonthExpTotal = thisMonthExpTotal;
    }

    public String getObtainDate() {
        return obtainDate;
    }

    public void setObtainDate(String obtainDate) {
        this.obtainDate = obtainDate == null ? null : obtainDate.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}