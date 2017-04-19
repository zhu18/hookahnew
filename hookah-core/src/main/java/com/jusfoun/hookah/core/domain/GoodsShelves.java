package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class GoodsShelves extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.shelves_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String shelvesId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.shelves_name
     *
     * @mbggenerated
     */
    private String shelvesName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.domain_id
     *
     * @mbggenerated
     */
    private String domainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.goods_number
     *
     * @mbggenerated
     */
    private Integer goodsNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.now_number
     *
     * @mbggenerated
     */
    private Integer nowNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.shelves_status
     *
     * @mbggenerated
     */
    private Byte shelvesStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.shelves_type_id
     *
     * @mbggenerated
     */
    private String shelvesType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.is_sys_shelves
     *
     * @mbggenerated
     */
    private Byte isSysShelves;
    private String shelvesDesc;
    private String shelvesTag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.end_time
     *
     * @mbggenerated
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.start_time
     *
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.add_time
     *
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.last_update_time
     *
     * @mbggenerated
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.add_user
     *
     * @mbggenerated
     */
    private String addUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_shelves.version
     *
     * @mbggenerated
     */
    private Integer version;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.shelves_id
     *
     * @return the value of goods_shelves.shelves_id
     *
     * @mbggenerated
     */
    public String getShelvesId() {
        return shelvesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.shelves_id
     *
     * @param shelvesId the value for goods_shelves.shelves_id
     *
     * @mbggenerated
     */
    public void setShelvesId(String shelvesId) {
        this.shelvesId = shelvesId == null ? null : shelvesId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.shelves_name
     *
     * @return the value of goods_shelves.shelves_name
     *
     * @mbggenerated
     */
    public String getShelvesName() {
        return shelvesName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.shelves_name
     *
     * @param shelvesName the value for goods_shelves.shelves_name
     *
     * @mbggenerated
     */
    public void setShelvesName(String shelvesName) {
        this.shelvesName = shelvesName == null ? null : shelvesName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.domain_id
     *
     * @return the value of goods_shelves.domain_id
     *
     * @mbggenerated
     */
    public String getDomainId() {
        return domainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.domain_id
     *
     * @param domainId the value for goods_shelves.domain_id
     *
     * @mbggenerated
     */
    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.goods_number
     *
     * @return the value of goods_shelves.goods_number
     *
     * @mbggenerated
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.goods_number
     *
     * @param goodsNumber the value for goods_shelves.goods_number
     *
     * @mbggenerated
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.now_number
     *
     * @return the value of goods_shelves.now_number
     *
     * @mbggenerated
     */
    public Integer getNowNumber() {
        return nowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.now_number
     *
     * @param nowNumber the value for goods_shelves.now_number
     *
     * @mbggenerated
     */
    public void setNowNumber(Integer nowNumber) {
        this.nowNumber = nowNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.shelves_status
     *
     * @return the value of goods_shelves.shelves_status
     *
     * @mbggenerated
     */
    public Byte getShelvesStatus() {
        return shelvesStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.shelves_status
     *
     * @param shelvesStatus the value for goods_shelves.shelves_status
     *
     * @mbggenerated
     */
    public void setShelvesStatus(Byte shelvesStatus) {
        this.shelvesStatus = shelvesStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.shelves_type_id
     *
     * @return the value of goods_shelves.shelves_type_id
     *
     * @mbggenerated
     */
    public String getShelvesType() {
        return shelvesType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.shelves_type_id
     *
     * @param shelvesType the value for goods_shelves.shelves_type_id
     *
     * @mbggenerated
     */
    public void setShelvesType(String shelvesType) {
        this.shelvesType = shelvesType == null ? null : shelvesType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.is_sys_shelves
     *
     * @return the value of goods_shelves.is_sys_shelves
     *
     * @mbggenerated
     */
    public Byte getIsSysShelves() {
        return isSysShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.is_sys_shelves
     *
     * @param isSysShelves the value for goods_shelves.is_sys_shelves
     *
     * @mbggenerated
     */
    public void setIsSysShelves(Byte isSysShelves) {
        this.isSysShelves = isSysShelves;
    }

    public String getShelvesDesc() {
        return shelvesDesc;
    }

    public void setShelvesDesc(String shelvesDesc) {
        this.shelvesDesc = shelvesDesc;
    }

    public String getShelvesTag() {
        return shelvesTag;
    }

    public void setShelvesTag(String shelvesTag) {
        this.shelvesTag = shelvesTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.end_time
     *
     * @return the value of goods_shelves.end_time
     *
     * @mbggenerated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.end_time
     *
     * @param endTime the value for goods_shelves.end_time
     *
     * @mbggenerated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.start_time
     *
     * @return the value of goods_shelves.start_time
     *
     * @mbggenerated
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.start_time
     *
     * @param startTime the value for goods_shelves.start_time
     *
     * @mbggenerated
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.add_time
     *
     * @return the value of goods_shelves.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.add_time
     *
     * @param addTime the value for goods_shelves.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.last_update_time
     *
     * @return the value of goods_shelves.last_update_time
     *
     * @mbggenerated
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.last_update_time
     *
     * @param lastUpdateTime the value for goods_shelves.last_update_time
     *
     * @mbggenerated
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.user_id
     *
     * @return the value of goods_shelves.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.user_id
     *
     * @param userId the value for goods_shelves.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.add_user
     *
     * @return the value of goods_shelves.add_user
     *
     * @mbggenerated
     */
    public String getAddUser() {
        return addUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.add_user
     *
     * @param addUser the value for goods_shelves.add_user
     *
     * @mbggenerated
     */
    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_shelves.version
     *
     * @return the value of goods_shelves.version
     *
     * @mbggenerated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_shelves.version
     *
     * @param version the value for goods_shelves.version
     *
     * @mbggenerated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}