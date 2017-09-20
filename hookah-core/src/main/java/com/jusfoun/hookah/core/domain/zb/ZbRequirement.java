package com.jusfoun.hookah.core.domain.zb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

public class ZbRequirement extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.id
     *
     * @mbggenerated
     */
    @Id
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.require_sn
     *
     * @mbggenerated
     */
    private String requireSn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.contact_name
     *
     * @mbggenerated
     */
    private String contactName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.contact_phone
     *
     * @mbggenerated
     */
    private String contactPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.reward_money
     *
     * @mbggenerated
     */
    private Long rewardMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.trustee_percent
     *
     * @mbggenerated
     */
    private Integer trusteePercent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.delivery_deadline
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryDeadline;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.apply_deadline
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDeadline;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.check_remark
     *
     * @mbggenerated
     */
    private String checkRemark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.status
     *
     * @mbggenerated
     */
    private Short status;
    private Short type;
    private String tag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.add_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.add_operator
     *
     * @mbggenerated
     */
    private String addOperator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement.update_operator
     *
     * @mbggenerated
     */
    private String updateOperator;

    @Transient
    private String remainTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.id
     *
     * @return the value of zb_requirement.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.id
     *
     * @param id the value for zb_requirement.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.require_sn
     *
     * @return the value of zb_requirement.require_sn
     *
     * @mbggenerated
     */
    public String getRequireSn() {
        return requireSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.require_sn
     *
     * @param requireSn the value for zb_requirement.require_sn
     *
     * @mbggenerated
     */
    public void setRequireSn(String requireSn) {
        this.requireSn = requireSn == null ? null : requireSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.user_id
     *
     * @return the value of zb_requirement.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.user_id
     *
     * @param userId the value for zb_requirement.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.title
     *
     * @return the value of zb_requirement.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.title
     *
     * @param title the value for zb_requirement.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.contact_name
     *
     * @return the value of zb_requirement.contact_name
     *
     * @mbggenerated
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.contact_name
     *
     * @param contactName the value for zb_requirement.contact_name
     *
     * @mbggenerated
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.contact_phone
     *
     * @return the value of zb_requirement.contact_phone
     *
     * @mbggenerated
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.contact_phone
     *
     * @param contactPhone the value for zb_requirement.contact_phone
     *
     * @mbggenerated
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.description
     *
     * @return the value of zb_requirement.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.description
     *
     * @param description the value for zb_requirement.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.reward_money
     *
     * @return the value of zb_requirement.reward_money
     *
     * @mbggenerated
     */
    public Long getRewardMoney() {
        return rewardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.reward_money
     *
     * @param rewardMoney the value for zb_requirement.reward_money
     *
     * @mbggenerated
     */
    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.trustee_percent
     *
     * @return the value of zb_requirement.trustee_percent
     *
     * @mbggenerated
     */
    public Integer getTrusteePercent() {
        return trusteePercent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.trustee_percent
     *
     * @param trusteePercent the value for zb_requirement.trustee_percent
     *
     * @mbggenerated
     */
    public void setTrusteePercent(Integer trusteePercent) {
        this.trusteePercent = trusteePercent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.delivery_deadline
     *
     * @return the value of zb_requirement.delivery_deadline
     *
     * @mbggenerated
     */
    public Date getDeliveryDeadline() {
        return deliveryDeadline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.delivery_deadline
     *
     * @param deliveryDeadline the value for zb_requirement.delivery_deadline
     *
     * @mbggenerated
     */
    public void setDeliveryDeadline(Date deliveryDeadline) {
        this.deliveryDeadline = deliveryDeadline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.apply_deadline
     *
     * @return the value of zb_requirement.apply_deadline
     *
     * @mbggenerated
     */
    public Date getApplyDeadline() {
        return applyDeadline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.apply_deadline
     *
     * @param applyDeadline the value for zb_requirement.apply_deadline
     *
     * @mbggenerated
     */
    public void setApplyDeadline(Date applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.check_remark
     *
     * @return the value of zb_requirement.check_remark
     *
     * @mbggenerated
     */
    public String getCheckRemark() {
        return checkRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.check_remark
     *
     * @param checkRemark the value for zb_requirement.check_remark
     *
     * @mbggenerated
     */
    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark == null ? null : checkRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.status
     *
     * @return the value of zb_requirement.status
     *
     * @mbggenerated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.status
     *
     * @param status the value for zb_requirement.status
     *
     * @mbggenerated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.add_time
     *
     * @return the value of zb_requirement.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.add_time
     *
     * @param addTime the value for zb_requirement.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.update_time
     *
     * @return the value of zb_requirement.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.update_time
     *
     * @param updateTime the value for zb_requirement.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.add_operator
     *
     * @return the value of zb_requirement.add_operator
     *
     * @mbggenerated
     */
    public String getAddOperator() {
        return addOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.add_operator
     *
     * @param addOperator the value for zb_requirement.add_operator
     *
     * @mbggenerated
     */
    public void setAddOperator(String addOperator) {
        this.addOperator = addOperator == null ? null : addOperator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement.update_operator
     *
     * @return the value of zb_requirement.update_operator
     *
     * @mbggenerated
     */
    public String getUpdateOperator() {
        return updateOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement.update_operator
     *
     * @param updateOperator the value for zb_requirement.update_operator
     *
     * @mbggenerated
     */
    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator == null ? null : updateOperator.trim();
    }


    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}