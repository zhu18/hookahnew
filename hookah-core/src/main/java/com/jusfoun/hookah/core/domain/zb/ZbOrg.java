package com.jusfoun.hookah.core.domain.zb;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.util.Date;

public class ZbOrg extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.org_name
     *
     * @mbggenerated
     */
    private String orgName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.status
     *
     * @mbggenerated
     */
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column zb_org.org_desc
     *
     * @mbggenerated
     */
    private String orgDesc;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.id
     *
     * @return the value of zb_org.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.id
     *
     * @param id the value for zb_org.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.user_id
     *
     * @return the value of zb_org.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.user_id
     *
     * @param userId the value for zb_org.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.org_name
     *
     * @return the value of zb_org.org_name
     *
     * @mbggenerated
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.org_name
     *
     * @param orgName the value for zb_org.org_name
     *
     * @mbggenerated
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.add_time
     *
     * @return the value of zb_org.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.add_time
     *
     * @param addTime the value for zb_org.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.status
     *
     * @return the value of zb_org.status
     *
     * @mbggenerated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.status
     *
     * @param status the value for zb_org.status
     *
     * @mbggenerated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.update_time
     *
     * @return the value of zb_org.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.update_time
     *
     * @param updateTime the value for zb_org.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column zb_org.org_desc
     *
     * @return the value of zb_org.org_desc
     *
     * @mbggenerated
     */
    public String getOrgDesc() {
        return orgDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column zb_org.org_desc
     *
     * @param orgDesc the value for zb_org.org_desc
     *
     * @mbggenerated
     */
    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc == null ? null : orgDesc.trim();
    }
}