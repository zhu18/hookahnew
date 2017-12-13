package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class HomeImage extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.img_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String imgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.img_name
     *
     * @mbggenerated
     */
    private String imgName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.img_desc
     *
     * @mbggenerated
     */
    private String imgDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.img_link
     *
     * @mbggenerated
     */
    private String imgLink;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.img_sort
     *
     * @mbggenerated
     */
    private Byte imgSort;

    private Byte imgType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.user_id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column home_image.update_time
     *
     * @mbggenerated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String imgUrl;

    private Byte isNewTab;

    public Byte getIsNewTab() {
        return isNewTab;
    }

    public void setIsNewTab(Byte isNewTab) {
        this.isNewTab = isNewTab;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.img_id
     *
     * @return the value of home_image.img_id
     *
     * @mbggenerated
     */
    public String getImgId() {
        return imgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.img_id
     *
     * @param imgId the value for home_image.img_id
     *
     * @mbggenerated
     */
    public void setImgId(String imgId) {
        this.imgId = imgId == null ? null : imgId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.img_name
     *
     * @return the value of home_image.img_name
     *
     * @mbggenerated
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.img_name
     *
     * @param imgName the value for home_image.img_name
     *
     * @mbggenerated
     */
    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.img_desc
     *
     * @return the value of home_image.img_desc
     *
     * @mbggenerated
     */
    public String getImgDesc() {
        return imgDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.img_desc
     *
     * @param imgDesc the value for home_image.img_desc
     *
     * @mbggenerated
     */
    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc == null ? null : imgDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.img_link
     *
     * @return the value of home_image.img_link
     *
     * @mbggenerated
     */
    public String getImgLink() {
        return imgLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.img_link
     *
     * @param imgLink the value for home_image.img_link
     *
     * @mbggenerated
     */
    public void setImgLink(String imgLink) {
        this.imgLink = imgLink == null ? null : imgLink.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.img_sort
     *
     * @return the value of home_image.img_sort
     *
     * @mbggenerated
     */
    public Byte getImgSort() {
        return imgSort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.img_sort
     *
     * @param imgSort the value for home_image.img_sort
     *
     * @mbggenerated
     */
    public void setImgSort(Byte imgSort) {
        this.imgSort = imgSort;
    }

    public Byte getImgType() {
        return imgType;
    }

    public void setImgType(Byte imgType) {
        this.imgType = imgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.user_id
     *
     * @return the value of home_image.user_id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.user_id
     *
     * @param userId the value for home_image.user_id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column home_image.update_time
     *
     * @return the value of home_image.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column home_image.update_time
     *
     * @param updateTime the value for home_image.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}