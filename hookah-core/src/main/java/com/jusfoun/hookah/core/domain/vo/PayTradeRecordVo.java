package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.PayTradeRecord;

/**
 * Created by computer on 2017/7/19.
 */
public class PayTradeRecordVo extends PayTradeRecord {

    private String userName;

    private String accountParty;

    public String getAccountParty() {
        return accountParty;
    }

    public void setAccountParty(String accountParty) {
        this.accountParty = accountParty;
    }

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}
}
