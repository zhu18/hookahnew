package com.jusfoun.hookah.core.domain;

import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class Category extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.cat_id
     *
     * @mbggenerated
     */
    @Id
    private String catId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.cat_name
     *
     * @mbggenerated
     */
    private String catName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.domain_id
     *
     * @mbggenerated
     */
    private String domainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.cat_desc
     *
     * @mbggenerated
     */
    private String catDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.parent_id
     *
     * @mbggenerated
     */
    private String parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.type
     *
     * @mbggenerated
     */
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.is_show
     *
     * @mbggenerated
     */
    private Byte isShow;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.level
     *
     * @mbggenerated
     */
    private Byte level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.last_update_time
     *
     * @mbggenerated
     */
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.add_user
     *
     * @mbggenerated
     */
    private String addUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.cat_sign
     *
     * @mbggenerated
     */
    private Byte catSign;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column category.version
     *
     * @mbggenerated
     */
    private Integer version;

    private String code; // 2017-05-24 15:30 编码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.cat_id
     *
     * @return the value of category.cat_id
     *
     * @mbggenerated
     */
    public String getCatId() {
        return catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.cat_id
     *
     * @param catId the value for category.cat_id
     *
     * @mbggenerated
     */
    public void setCatId(String catId) {
        this.catId = catId == null ? null : catId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.cat_name
     *
     * @return the value of category.cat_name
     *
     * @mbggenerated
     */
    public String getCatName() {
        return catName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.cat_name
     *
     * @param catName the value for category.cat_name
     *
     * @mbggenerated
     */
    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.domain_id
     *
     * @return the value of category.domain_id
     *
     * @mbggenerated
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.domain_id
     *
     * @param domainId the value for category.domain_id
     *
     * @mbggenerated
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.cat_desc
     *
     * @return the value of category.cat_desc
     *
     * @mbggenerated
     */
    public String getCatDesc() {
        return catDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.cat_desc
     *
     * @param catDesc the value for category.cat_desc
     *
     * @mbggenerated
     */
    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc == null ? null : catDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.parent_id
     *
     * @return the value of category.parent_id
     *
     * @mbggenerated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.parent_id
     *
     * @param parentId the value for category.parent_id
     *
     * @mbggenerated
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.type
     *
     * @return the value of category.type
     *
     * @mbggenerated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.type
     *
     * @param type the value for category.type
     *
     * @mbggenerated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.is_show
     *
     * @return the value of category.is_show
     *
     * @mbggenerated
     */
    public Byte getIsShow() {
        return isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.is_show
     *
     * @param isShow the value for category.is_show
     *
     * @mbggenerated
     */
    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.level
     *
     * @return the value of category.level
     *
     * @mbggenerated
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.level
     *
     * @param level the value for category.level
     *
     * @mbggenerated
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.add_time
     *
     * @return the value of category.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.add_time
     *
     * @param addTime the value for category.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.last_update_time
     *
     * @return the value of category.last_update_time
     *
     * @mbggenerated
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.last_update_time
     *
     * @param lastUpdateTime the value for category.last_update_time
     *
     * @mbggenerated
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.user_id
     *
     * @return the value of category.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.user_id
     *
     * @param userId the value for category.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.add_user
     *
     * @return the value of category.add_user
     *
     * @mbggenerated
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.add_user
     *
     * @param addUser the value for category.add_user
     *
     * @mbggenerated
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.cat_sign
     *
     * @return the value of category.cat_sign
     *
     * @mbggenerated
     */
    public Byte getCatSign() {
        return catSign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.cat_sign
     *
     * @param catSign the value for category.cat_sign
     *
     * @mbggenerated
     */
    public void setCatSign(Byte catSign) {
        this.catSign = catSign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column category.version
     *
     * @return the value of category.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column category.version
     *
     * @param version the value for category.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}