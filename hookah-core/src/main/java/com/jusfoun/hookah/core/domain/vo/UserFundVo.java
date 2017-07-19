package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ctp on 2017/7/18.
 */
public class UserFundVo implements Serializable {

    private String userId;
    private String userName;
    private String orgName;
    private String phone;
    private Byte userType;
    //pay_bank
    private String bankName;//银行名称
    private String bankCode;//银行代码
    private String cardOwner;//银行卡持卡人
    private String cardCode;//银行卡编号
    private Integer bindFlag;//绑定状态(0：正常，1：解除绑定，默认：0)
    //pay_account
    private Long balance;//余额
    private Long useBalance;//可用余额
    private Byte isBind;//是否绑定
    private Date addTime;//绑定时间

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Byte getIsBind() {
        return isBind;
    }

    public void setIsBind(Byte isBind) {
        this.isBind = isBind;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(Integer bindFlag) {
        this.bindFlag = bindFlag;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getUseBalance() {
        return useBalance;
    }

    public void setUseBalance(Long useBalance) {
        this.useBalance = useBalance;
    }
}
