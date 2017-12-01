package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

public class UserInvoiceAddress extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.invoice_name
     *
     * @mbggenerated
     */
    private String invoiceName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.mobile
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.post_code
     *
     * @mbggenerated
     */
    private String postCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.region
     *
     * @mbggenerated
     */
    private String region;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.address
     *
     * @mbggenerated
     */
    private String address;

    @Transient
    private String receiveAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.fixed_line
     *
     * @mbggenerated
     */
    private String fixedLine;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.add_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.default_status
     *
     * @mbggenerated
     */
    private Byte defaultStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_invoice_address.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.id
     *
     * @return the value of user_invoice_address.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.id
     *
     * @param id the value for user_invoice_address.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.invoice_name
     *
     * @return the value of user_invoice_address.invoice_name
     *
     * @mbggenerated
     */
    public String getInvoiceName() {
        return invoiceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.invoice_name
     *
     * @param invoiceName the value for user_invoice_address.invoice_name
     *
     * @mbggenerated
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName == null ? null : invoiceName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.user_id
     *
     * @return the value of user_invoice_address.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.user_id
     *
     * @param userId the value for user_invoice_address.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.mobile
     *
     * @return the value of user_invoice_address.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.mobile
     *
     * @param mobile the value for user_invoice_address.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.post_code
     *
     * @return the value of user_invoice_address.post_code
     *
     * @mbggenerated
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.post_code
     *
     * @param postCode the value for user_invoice_address.post_code
     *
     * @mbggenerated
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.region
     *
     * @return the value of user_invoice_address.region
     *
     * @mbggenerated
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.region
     *
     * @param region the value for user_invoice_address.region
     *
     * @mbggenerated
     */
    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.address
     *
     * @return the value of user_invoice_address.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.address
     *
     * @param address the value for user_invoice_address.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.fixed_line
     *
     * @return the value of user_invoice_address.fixed_line
     *
     * @mbggenerated
     */
    public String getFixedLine() {
        return fixedLine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.fixed_line
     *
     * @param fixedLine the value for user_invoice_address.fixed_line
     *
     * @mbggenerated
     */
    public void setFixedLine(String fixedLine) {
        this.fixedLine = fixedLine == null ? null : fixedLine.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.email
     *
     * @return the value of user_invoice_address.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.email
     *
     * @param email the value for user_invoice_address.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.add_time
     *
     * @return the value of user_invoice_address.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.add_time
     *
     * @param addTime the value for user_invoice_address.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.default_status
     *
     * @return the value of user_invoice_address.default_status
     *
     * @mbggenerated
     */
    public Byte getDefaultStatus() {
        return defaultStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.default_status
     *
     * @param defaultStatus the value for user_invoice_address.default_status
     *
     * @mbggenerated
     */
    public void setDefaultStatus(Byte defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_invoice_address.version
     *
     * @return the value of user_invoice_address.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_invoice_address.version
     *
     * @param version the value for user_invoice_address.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}