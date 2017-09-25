package com.jusfoun.hookah.core.domain.zb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class ZbTrusteeRecord extends GenericModel {

    @Id
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.requirement_id
     *
     * @mbggenerated
     */
    private Long requirementId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.reward_money
     *
     * @mbggenerated
     */
    private Long rewardMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.trustee_percent
     *
     * @mbggenerated
     */
    private Integer trusteePercent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.actual_money
     *
     * @mbggenerated
     */
    private Long actualMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.trustee_num
     *
     * @mbggenerated
     */
    private Integer trusteeNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.serial_no
     *
     * @mbggenerated
     */
    private Integer serialNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_trustee_record.status
     *
     * @mbggenerated
     */
    private Short status;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.id
     *
     * @return the value of zb_trustee_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.id
     *
     * @param id the value for zb_trustee_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.user_id
     *
     * @return the value of zb_trustee_record.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.user_id
     *
     * @param userId the value for zb_trustee_record.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.requirement_id
     *
     * @return the value of zb_trustee_record.requirement_id
     *
     * @mbggenerated
     */
    public Long getRequirementId() {
        return requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.requirement_id
     *
     * @param requirementId the value for zb_trustee_record.requirement_id
     *
     * @mbggenerated
     */
    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.reward_money
     *
     * @return the value of zb_trustee_record.reward_money
     *
     * @mbggenerated
     */
    public Long getRewardMoney() {
        return rewardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.reward_money
     *
     * @param rewardMoney the value for zb_trustee_record.reward_money
     *
     * @mbggenerated
     */
    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.trustee_percent
     *
     * @return the value of zb_trustee_record.trustee_percent
     *
     * @mbggenerated
     */
    public Integer getTrusteePercent() {
        return trusteePercent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.trustee_percent
     *
     * @param trusteePercent the value for zb_trustee_record.trustee_percent
     *
     * @mbggenerated
     */
    public void setTrusteePercent(Integer trusteePercent) {
        this.trusteePercent = trusteePercent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.actual_money
     *
     * @return the value of zb_trustee_record.actual_money
     *
     * @mbggenerated
     */
    public Long getActualMoney() {
        return actualMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.actual_money
     *
     * @param actualMoney the value for zb_trustee_record.actual_money
     *
     * @mbggenerated
     */
    public void setActualMoney(Long actualMoney) {
        this.actualMoney = actualMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.trustee_num
     *
     * @return the value of zb_trustee_record.trustee_num
     *
     * @mbggenerated
     */
    public Integer getTrusteeNum() {
        return trusteeNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.trustee_num
     *
     * @param trusteeNum the value for zb_trustee_record.trustee_num
     *
     * @mbggenerated
     */
    public void setTrusteeNum(Integer trusteeNum) {
        this.trusteeNum = trusteeNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.serial_no
     *
     * @return the value of zb_trustee_record.serial_no
     *
     * @mbggenerated
     */
    public Integer getSerialNo() {
        return serialNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.serial_no
     *
     * @param serialNo the value for zb_trustee_record.serial_no
     *
     * @mbggenerated
     */
    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.status
     *
     * @return the value of zb_trustee_record.status
     *
     * @mbggenerated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.status
     *
     * @param status the value for zb_trustee_record.status
     *
     * @mbggenerated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_trustee_record.add_time
     *
     * @return the value of zb_trustee_record.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_trustee_record.add_time
     *
     * @param addTime the value for zb_trustee_record.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}