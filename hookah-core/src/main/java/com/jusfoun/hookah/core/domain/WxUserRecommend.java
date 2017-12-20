package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.Id;
import java.util.Date;

public class WxUserRecommend extends GenericModel {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.id
     *
     * @mbggenerated
     */
    @Id
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.recommenderId
     *
     * @mbggenerated
     */
    private String recommenderid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.inviteeId
     *
     * @mbggenerated
     */
    private String inviteeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.isdeal
     *
     * @mbggenerated
     */
    private Byte isdeal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.isauthenticate
     *
     * @mbggenerated
     */
    private Byte isauthenticate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.reward_money
     *
     * @mbggenerated
     */
    private Long rewardMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.add_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wx_user_recommend.update_time
     *
     * @mbggenerated
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer rewardJf;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.id
     *
     * @return the value of wx_user_recommend.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.id
     *
     * @param id the value for wx_user_recommend.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.recommenderId
     *
     * @return the value of wx_user_recommend.recommenderId
     *
     * @mbggenerated
     */
    public String getRecommenderid() {
        return recommenderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.recommenderId
     *
     * @param recommenderid the value for wx_user_recommend.recommenderId
     *
     * @mbggenerated
     */
    public void setRecommenderid(String recommenderid) {
        this.recommenderid = recommenderid == null ? null : recommenderid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.inviteeId
     *
     * @return the value of wx_user_recommend.inviteeId
     *
     * @mbggenerated
     */
    public String getInviteeid() {
        return inviteeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.inviteeId
     *
     * @param inviteeid the value for wx_user_recommend.inviteeId
     *
     * @mbggenerated
     */
    public void setInviteeid(String inviteeid) {
        this.inviteeid = inviteeid == null ? null : inviteeid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.isdeal
     *
     * @return the value of wx_user_recommend.isdeal
     *
     * @mbggenerated
     */
    public Byte getIsdeal() {
        return isdeal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.isdeal
     *
     * @param isdeal the value for wx_user_recommend.isdeal
     *
     * @mbggenerated
     */
    public void setIsdeal(Byte isdeal) {
        this.isdeal = isdeal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.isauthenticate
     *
     * @return the value of wx_user_recommend.isauthenticate
     *
     * @mbggenerated
     */
    public Byte getIsauthenticate() {
        return isauthenticate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.isauthenticate
     *
     * @param isauthenticate the value for wx_user_recommend.isauthenticate
     *
     * @mbggenerated
     */
    public void setIsauthenticate(Byte isauthenticate) {
        this.isauthenticate = isauthenticate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.reward_money
     *
     * @return the value of wx_user_recommend.reward_money
     *
     * @mbggenerated
     */
    public Long getRewardMoney() {
        return rewardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.reward_money
     *
     * @param rewardMoney the value for wx_user_recommend.reward_money
     *
     * @mbggenerated
     */
    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.add_time
     *
     * @return the value of wx_user_recommend.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.add_time
     *
     * @param addTime the value for wx_user_recommend.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wx_user_recommend.update_time
     *
     * @return the value of wx_user_recommend.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wx_user_recommend.update_time
     *
     * @param updateTime the value for wx_user_recommend.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRewardJf() {
        return rewardJf;
    }

    public void setRewardJf(Integer rewardJf) {
        this.rewardJf = rewardJf;
    }
}