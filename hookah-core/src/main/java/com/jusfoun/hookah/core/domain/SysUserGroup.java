package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

public class SysUserGroup extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_group.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_group.sys_user_id
     *
     * @mbggenerated
     */
    private String sysUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_group.sys_group_id
     *
     * @mbggenerated
     */
    private String sysGroupId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_group.id
     *
     * @return the value of sys_user_group.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_group.id
     *
     * @param id the value for sys_user_group.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_group.sys_user_id
     *
     * @return the value of sys_user_group.sys_user_id
     *
     * @mbggenerated
     */
    public String getSysUserId() {
        return sysUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_group.sys_user_id
     *
     * @param sysUserId the value for sys_user_group.sys_user_id
     *
     * @mbggenerated
     */
    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId == null ? null : sysUserId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_group.sys_group_id
     *
     * @return the value of sys_user_group.sys_group_id
     *
     * @mbggenerated
     */
    public String getSysGroupId() {
        return sysGroupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_group.sys_group_id
     *
     * @param sysGroupId the value for sys_user_group.sys_group_id
     *
     * @mbggenerated
     */
    public void setSysGroupId(String sysGroupId) {
        this.sysGroupId = sysGroupId == null ? null : sysGroupId.trim();
    }
}