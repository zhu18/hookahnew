package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.WithdrawRecord;

public class WithdrawVo extends WithdrawRecord {

    private String userName;

    private String orgName;

    private Long useBalance;

    private Long balance;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getUseBalance() {
        return useBalance;
    }

    public void setUseBalance(Long useBalance) {
        this.useBalance = useBalance;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
