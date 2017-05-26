package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class UserCheck extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.user_type
     *
     * @mbggenerated
     */
    private String userType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.check_status
     *
     * @mbggenerated
     */
    private Byte checkStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.check_user
     *
     * @mbggenerated
     */
    private String checkUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.check_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_check.check_content
     *
     * @mbggenerated
     */
    private String checkContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.id
     *
     * @return the value of user_check.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.id
     *
     * @param id the value for user_check.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.user_id
     *
     * @return the value of user_check.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.user_id
     *
     * @param userId the value for user_check.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.user_type
     *
     * @return the value of user_check.user_type
     *
     * @mbggenerated
     */
    public String getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.user_type
     *
     * @param userType the value for user_check.user_type
     *
     * @mbggenerated
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.user_name
     *
     * @return the value of user_check.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.user_name
     *
     * @param userName the value for user_check.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.check_status
     *
     * @return the value of user_check.check_status
     *
     * @mbggenerated
     */
    public Byte getCheckStatus() {
        return checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.check_status
     *
     * @param checkStatus the value for user_check.check_status
     *
     * @mbggenerated
     */
    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.check_user
     *
     * @return the value of user_check.check_user
     *
     * @mbggenerated
     */
    public String getCheckUser() {
        return checkUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.check_user
     *
     * @param checkUser the value for user_check.check_user
     *
     * @mbggenerated
     */
    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser == null ? null : checkUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.check_time
     *
     * @return the value of user_check.check_time
     *
     * @mbggenerated
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.check_time
     *
     * @param checkTime the value for user_check.check_time
     *
     * @mbggenerated
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_check.check_content
     *
     * @return the value of user_check.check_content
     *
     * @mbggenerated
     */
    public String getCheckContent() {
        return checkContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_check.check_content
     *
     * @param checkContent the value for user_check.check_content
     *
     * @mbggenerated
     */
    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent == null ? null : checkContent.trim();
    }
}