package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class Permission extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String permissionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_name
     *
     * @mbggenerated
     */
    private String permissionName;


    private String permissionParentId;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.is_deleted
     *
     * @mbggenerated
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.version
     *
     * @mbggenerated
     */
    private Integer version;

    private String permissionExplain;

    @Transient
    private List<Permission> children;


    public String getPermissionParentId() {
        return permissionParentId;
    }

    public void setPermissionParentId(String permissionParentId) {
        this.permissionParentId = permissionParentId;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public String getPermissionExplain() {
        return permissionExplain;
    }

    public void setPermissionExplain(String permissionExplain) {
        this.permissionExplain = permissionExplain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_id
     *
     * @return the value of permission.permission_id
     * @mbggenerated
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_id
     *
     * @param permissionId the value for permission.permission_id
     * @mbggenerated
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_name
     *
     * @return the value of permission.permission_name
     * @mbggenerated
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_name
     *
     * @param permissionName the value for permission.permission_name
     * @mbggenerated
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.is_deleted
     *
     * @return the value of permission.is_deleted
     * @mbggenerated
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.is_deleted
     *
     * @param isDeleted the value for permission.is_deleted
     * @mbggenerated
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.add_time
     *
     * @return the value of permission.add_time
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.add_time
     *
     * @param addTime the value for permission.add_time
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.version
     *
     * @return the value of permission.version
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.version
     *
     * @param version the value for permission.version
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}