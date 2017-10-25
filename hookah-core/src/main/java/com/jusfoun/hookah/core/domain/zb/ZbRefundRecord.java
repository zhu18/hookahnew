package com.jusfoun.hookah.core.domain.zb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

public class ZbRefundRecord extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.requirement_id
     *
     * @mbggenerated
     */
    private Long requirementId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.refund_amount
     *
     * @mbggenerated
     */
    private Long refundAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.refund_sn
     *
     * @mbggenerated
     */
    private String refundSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.bank_card_num
     *
     * @mbggenerated
     */
    private String bankCardNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.desc
     *
     * @mbggenerated
     */
    private String desc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.add_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_refund_record.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.id
     *
     * @return the value of zb_refund_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.id
     *
     * @param id the value for zb_refund_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.user_id
     *
     * @return the value of zb_refund_record.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.user_id
     *
     * @param userId the value for zb_refund_record.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.requirement_id
     *
     * @return the value of zb_refund_record.requirement_id
     *
     * @mbggenerated
     */
    public Long getRequirementId() {
        return requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.requirement_id
     *
     * @param requirementId the value for zb_refund_record.requirement_id
     *
     * @mbggenerated
     */
    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.refund_amount
     *
     * @return the value of zb_refund_record.refund_amount
     *
     * @mbggenerated
     */
    public Long getRefundAmount() {
        return refundAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.refund_amount
     *
     * @param refundAmount the value for zb_refund_record.refund_amount
     *
     * @mbggenerated
     */
    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.refund_sn
     *
     * @return the value of zb_refund_record.refund_sn
     *
     * @mbggenerated
     */
    public String getRefundSn() {
        return refundSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.refund_sn
     *
     * @param refundSn the value for zb_refund_record.refund_sn
     *
     * @mbggenerated
     */
    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn == null ? null : refundSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.bank_card_num
     *
     * @return the value of zb_refund_record.bank_card_num
     *
     * @mbggenerated
     */
    public String getBankCardNum() {
        return bankCardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.bank_card_num
     *
     * @param bankCardNum the value for zb_refund_record.bank_card_num
     *
     * @mbggenerated
     */
    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum == null ? null : bankCardNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.type
     *
     * @return the value of zb_refund_record.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.type
     *
     * @param type the value for zb_refund_record.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.status
     *
     * @return the value of zb_refund_record.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.status
     *
     * @param status the value for zb_refund_record.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.desc
     *
     * @return the value of zb_refund_record.desc
     *
     * @mbggenerated
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.desc
     *
     * @param desc the value for zb_refund_record.desc
     *
     * @mbggenerated
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.add_time
     *
     * @return the value of zb_refund_record.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.add_time
     *
     * @param addTime the value for zb_refund_record.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_refund_record.update_time
     *
     * @return the value of zb_refund_record.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_refund_record.update_time
     *
     * @param updateTime the value for zb_refund_record.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    private String payTime;

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}