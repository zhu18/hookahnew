package com.jusfoun.hookah.core.domain.bo;

public class MoneyInOutBo {

    public String userId;

    public Long payAccountID;

    public Long Money;

    public Integer operatorType;

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

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }
}
