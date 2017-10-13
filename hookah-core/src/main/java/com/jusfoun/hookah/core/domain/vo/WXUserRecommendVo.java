package com.jusfoun.hookah.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

/**
 * Created by ndf on 2017/9/25.
 * 扩展实体类，用于展示 后台邀请好友查询
 */
public class WXUserRecommendVo extends GenericModel {

    private String userId;

    private String userName;

    private String mobile;

    private Integer userType;

    private Integer inviteeNum;

    private Long rewardMoney;

    private Integer isdealNum;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;

    private Byte isdeal;

    private Byte isauthenticate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getInviteeNum() {
        return inviteeNum;
    }

    public void setInviteeNum(Integer inviteeNum) {
        this.inviteeNum = inviteeNum;
    }

    public Long getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Integer getIsdealNum() {
        return isdealNum;
    }

    public void setIsdealNum(Integer isdealNum) {
        this.isdealNum = isdealNum;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Byte getIsdeal() {
        return isdeal;
    }

    public void setIsdeal(Byte isdeal) {
        this.isdeal = isdeal;
    }

    public Byte getIsauthenticate() {
        return isauthenticate;
    }

    public void setIsauthenticate(Byte isauthenticate) {
        this.isauthenticate = isauthenticate;
    }
}
