package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class SysNews extends GenericModel {

    public static String Innovation = "innovation"; //双创中心
    public static String Exposition = "exposition"; //博览中心
    public static String Information = "Information"; //咨询中心

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.news_id
     *
     * @mbggenerated
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private String newsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.news_title
     *
     * @mbggenerated
     */
    private String newsTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.news_group
     *
     * @mbggenerated
     */
    private String newsGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.is_hot
     *
     * @mbggenerated
     */
    private String isHot;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.hot_num
     *
     * @mbggenerated
     */
    private Integer hotNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.sys_user
     *
     * @mbggenerated
     */
    private String sysUser;

    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.syt_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sytTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.picture_url
     *
     * @mbggenerated
     */
    private String pictureUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.news_son_group
     *
     * @mbggenerated
     */
    private String newsSonGroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_news.content
     *
     * @mbggenerated
     */
    private String content;


    private String contentValidity;
    public String getContentValidity() {
        return contentValidity;
    }

    public void setContentValidity(String contentValidity) {
        this.contentValidity = contentValidity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.news_id
     *
     * @return the value of sys_news.news_id
     * @mbggenerated
     */
    public String getNewsId() {
        return newsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.news_id
     *
     * @param newsId the value for sys_news.news_id
     * @mbggenerated
     */
    public void setNewsId(String newsId) {
        this.newsId = newsId == null ? null : newsId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.news_title
     *
     * @return the value of sys_news.news_title
     * @mbggenerated
     */
    public String getNewsTitle() {
        return newsTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.news_title
     *
     * @param newsTitle the value for sys_news.news_title
     * @mbggenerated
     */
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle == null ? null : newsTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.news_group
     *
     * @return the value of sys_news.news_group
     * @mbggenerated
     */
    public String getNewsGroup() {
        return newsGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.news_group
     *
     * @param newsGroup the value for sys_news.news_group
     * @mbggenerated
     */
    public void setNewsGroup(String newsGroup) {
        this.newsGroup = newsGroup == null ? null : newsGroup.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.is_hot
     *
     * @return the value of sys_news.is_hot
     * @mbggenerated
     */
    public String getIsHot() {
        return isHot;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.is_hot
     *
     * @param isHot the value for sys_news.is_hot
     * @mbggenerated
     */
    public void setIsHot(String isHot) {
        this.isHot = isHot == null ? null : isHot.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.hot_num
     *
     * @return the value of sys_news.hot_num
     * @mbggenerated
     */
    public Integer getHotNum() {
        return hotNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.hot_num
     *
     * @param hotNum the value for sys_news.hot_num
     * @mbggenerated
     */
    public void setHotNum(Integer hotNum) {
        this.hotNum = hotNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.sys_user
     *
     * @return the value of sys_news.sys_user
     * @mbggenerated
     */
    public String getSysUser() {
        return sysUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.sys_user
     *
     * @param sysUser the value for sys_news.sys_user
     * @mbggenerated
     */
    public void setSysUser(String sysUser) {
        this.sysUser = sysUser == null ? null : sysUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.syt_time
     *
     * @return the value of sys_news.syt_time
     * @mbggenerated
     */
    public Date getSytTime() {
        return sytTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.syt_time
     *
     * @param sytTime the value for sys_news.syt_time
     * @mbggenerated
     */
    public void setSytTime(Date sytTime) {
        this.sytTime = sytTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.update_time
     *
     * @return the value of sys_news.update_time
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.update_time
     *
     * @param updateTime the value for sys_news.update_time
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.picture_url
     *
     * @return the value of sys_news.picture_url
     * @mbggenerated
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.picture_url
     *
     * @param pictureUrl the value for sys_news.picture_url
     * @mbggenerated
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.news_son_group
     *
     * @return the value of sys_news.news_son_group
     * @mbggenerated
     */
    public String getNewsSonGroup() {
        return newsSonGroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.news_son_group
     *
     * @param newsSonGroup the value for sys_news.news_son_group
     * @mbggenerated
     */
    public void setNewsSonGroup(String newsSonGroup) {
        this.newsSonGroup = newsSonGroup == null ? null : newsSonGroup.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_news.content
     *
     * @return the value of sys_news.content
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_news.content
     *
     * @param content the value for sys_news.content
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}