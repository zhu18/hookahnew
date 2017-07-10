package com.jusfoun.hookah.core.domain.bo;

public class MoneyInOutBo {

    public String userId;

    public Long payAccountID;

    public Long Money;          //  默认已经乘以100了

    public byte operatorType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPayAccountID() {
        return payAccountID;
    }

    public void setPayAccountID(Long payAccountID) {
        this.payAccountID = payAccountID;
    }

    public Long getMoney() {
        return Money;
    }

    public void setMoney(Long money) {
        Money = money;
    }

    public byte getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(byte operatorType) {
        this.operatorType = operatorType;
    }
}
