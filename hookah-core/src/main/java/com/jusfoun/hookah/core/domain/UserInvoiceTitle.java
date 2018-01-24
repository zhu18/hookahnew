package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class UserInvoiceTitle extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.title_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String titleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.user_invoice_type
     *
     * @mbggenerated
     */
    private Byte userInvoiceType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.title_name
     *
     * @mbggenerated
     */
    private String titleName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.taxpayer_identify_no
     *
     * @mbggenerated
     */
    private String taxpayerIdentifyNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.reg_address
     *
     * @mbggenerated
     */
    private String regAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.reg_tel
     *
     * @mbggenerated
     */
    private String regTel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.reg_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.open_bank
     *
     * @mbggenerated
     */
    private String openBank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.bank_account
     *
     * @mbggenerated
     */
    private String bankAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.default_status
     *
     * @mbggenerated
     */
    private Byte defaultStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.invoice_status
     *
     * @mbggenerated
     */
    private Byte invoiceStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.add_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.delete_status
     *
     * @mbggenerated
     */
    private Byte deleteStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_title.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.title_id
     *
     * @return the value of user_invoice_title.title_id
     *
     * @mbggenerated
     */
    public String getTitleId() {
        return titleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.title_id
     *
     * @param titleId the value for user_invoice_title.title_id
     *
     * @mbggenerated
     */
    public void setTitleId(String titleId) {
        this.titleId = titleId == null ? null : titleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.user_invoice_type
     *
     * @return the value of user_invoice_title.user_invoice_type
     *
     * @mbggenerated
     */
    public Byte getUserInvoiceType() {
        return userInvoiceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.user_invoice_type
     *
     * @param userInvoiceType the value for user_invoice_title.user_invoice_type
     *
     * @mbggenerated
     */
    public void setUserInvoiceType(Byte userInvoiceType) {
        this.userInvoiceType = userInvoiceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.user_id
     *
     * @return the value of user_invoice_title.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.user_id
     *
     * @param userId the value for user_invoice_title.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.title_name
     *
     * @return the value of user_invoice_title.title_name
     *
     * @mbggenerated
     */
    public String getTitleName() {
        return titleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.title_name
     *
     * @param titleName the value for user_invoice_title.title_name
     *
     * @mbggenerated
     */
    public void setTitleName(String titleName) {
        this.titleName = titleName == null ? null : titleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.taxpayer_identify_no
     *
     * @return the value of user_invoice_title.taxpayer_identify_no
     *
     * @mbggenerated
     */
    public String getTaxpayerIdentifyNo() {
        return taxpayerIdentifyNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.taxpayer_identify_no
     *
     * @param taxpayerIdentifyNo the value for user_invoice_title.taxpayer_identify_no
     *
     * @mbggenerated
     */
    public void setTaxpayerIdentifyNo(String taxpayerIdentifyNo) {
        this.taxpayerIdentifyNo = taxpayerIdentifyNo == null ? null : taxpayerIdentifyNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.reg_address
     *
     * @return the value of user_invoice_title.reg_address
     *
     * @mbggenerated
     */
    public String getRegAddress() {
        return regAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.reg_address
     *
     * @param regAddress the value for user_invoice_title.reg_address
     *
     * @mbggenerated
     */
    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress == null ? null : regAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.reg_tel
     *
     * @return the value of user_invoice_title.reg_tel
     *
     * @mbggenerated
     */
    public String getRegTel() {
        return regTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.reg_tel
     *
     * @param regTel the value for user_invoice_title.reg_tel
     *
     * @mbggenerated
     */
    public void setRegTel(String regTel) {
        this.regTel = regTel == null ? null : regTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.reg_time
     *
     * @return the value of user_invoice_title.reg_time
     *
     * @mbggenerated
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.reg_time
     *
     * @param regTime the value for user_invoice_title.reg_time
     *
     * @mbggenerated
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.open_bank
     *
     * @return the value of user_invoice_title.open_bank
     *
     * @mbggenerated
     */
    public String getOpenBank() {
        return openBank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.open_bank
     *
     * @param openBank the value for user_invoice_title.open_bank
     *
     * @mbggenerated
     */
    public void setOpenBank(String openBank) {
        this.openBank = openBank == null ? null : openBank.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.bank_account
     *
     * @return the value of user_invoice_title.bank_account
     *
     * @mbggenerated
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.bank_account
     *
     * @param bankAccount the value for user_invoice_title.bank_account
     *
     * @mbggenerated
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.default_status
     *
     * @return the value of user_invoice_title.default_status
     *
     * @mbggenerated
     */
    public Byte getDefaultStatus() {
        return defaultStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.default_status
     *
     * @param defaultStatus the value for user_invoice_title.default_status
     *
     * @mbggenerated
     */
    public void setDefaultStatus(Byte defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.invoice_status
     *
     * @return the value of user_invoice_title.invoice_status
     *
     * @mbggenerated
     */
    public Byte getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.invoice_status
     *
     * @param invoiceStatus the value for user_invoice_title.invoice_status
     *
     * @mbggenerated
     */
    public void setInvoiceStatus(Byte invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.add_time
     *
     * @return the value of user_invoice_title.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.add_time
     *
     * @param addTime the value for user_invoice_title.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.delete_status
     *
     * @return the value of user_invoice_title.delete_status
     *
     * @mbggenerated
     */
    public Byte getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.delete_status
     *
     * @param deleteStatus the value for user_invoice_title.delete_status
     *
     * @mbggenerated
     */
    public void setDeleteStatus(Byte deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_title.version
     *
     * @return the value of user_invoice_title.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_title.version
     *
     * @param version the value for user_invoice_title.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}