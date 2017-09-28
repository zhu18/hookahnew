package com.jusfoun.hookah.core.domain.zb;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.util.Date;

public class ZbRequirementCheck extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_check.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_check.requirement_id
     *
     * @mbggenerated
     */
    private Long requirementId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_check.check_content
     *
     * @mbggenerated
     */
    private String checkContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_check.check_user
     *
     * @mbggenerated
     */
    private String checkUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_check.check_status
     *
     * @mbggenerated
     */
    private Short checkStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_requirement_check.check_time
     *
     * @mbggenerated
     */
    private Date checkTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_check.id
     *
     * @return the value of zb_requirement_check.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_check.id
     *
     * @param id the value for zb_requirement_check.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_check.requirement_id
     *
     * @return the value of zb_requirement_check.requirement_id
     *
     * @mbggenerated
     */
    public Long getRequirementId() {
        return requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_check.requirement_id
     *
     * @param requirementId the value for zb_requirement_check.requirement_id
     *
     * @mbggenerated
     */
    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_check.check_content
     *
     * @return the value of zb_requirement_check.check_content
     *
     * @mbggenerated
     */
    public String getCheckContent() {
        return checkContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_check.check_content
     *
     * @param checkContent the value for zb_requirement_check.check_content
     *
     * @mbggenerated
     */
    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent == null ? null : checkContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_check.check_user
     *
     * @return the value of zb_requirement_check.check_user
     *
     * @mbggenerated
     */
    public String getCheckUser() {
        return checkUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_check.check_user
     *
     * @param checkUser the value for zb_requirement_check.check_user
     *
     * @mbggenerated
     */
    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser == null ? null : checkUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_check.check_status
     *
     * @return the value of zb_requirement_check.check_status
     *
     * @mbggenerated
     */
    public Short getCheckStatus() {
        return checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_check.check_status
     *
     * @param checkStatus the value for zb_requirement_check.check_status
     *
     * @mbggenerated
     */
    public void setCheckStatus(Short checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_requirement_check.check_time
     *
     * @return the value of zb_requirement_check.check_time
     *
     * @mbggenerated
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_requirement_check.check_time
     *
     * @param checkTime the value for zb_requirement_check.check_time
     *
     * @mbggenerated
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}