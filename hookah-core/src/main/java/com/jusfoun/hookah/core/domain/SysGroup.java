package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;
import java.util.Date;

public class SysGroup extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_group.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_group.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_group.description
     *
     * @mbggenerated
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_group.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_group.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_group.default_group
     *
     * @mbggenerated
     */
    private Byte defaultGroup;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_group.id
     *
     * @return the value of sys_group.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_group.id
     *
     * @param id the value for sys_group.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_group.name
     *
     * @return the value of sys_group.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_group.name
     *
     * @param name the value for sys_group.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_group.description
     *
     * @return the value of sys_group.description
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_group.description
     *
     * @param description the value for sys_group.description
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_group.create_time
     *
     * @return the value of sys_group.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_group.create_time
     *
     * @param createTime the value for sys_group.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_group.update_time
     *
     * @return the value of sys_group.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_group.update_time
     *
     * @param updateTime the value for sys_group.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_group.default_group
     *
     * @return the value of sys_group.default_group
     *
     * @mbggenerated
     */
    public Byte getDefaultGroup() {
        return defaultGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_group.default_group
     *
     * @param defaultGroup the value for sys_group.default_group
     *
     * @mbggenerated
     */
    public void setDefaultGroup(Byte defaultGroup) {
        this.defaultGroup = defaultGroup;
    }
}