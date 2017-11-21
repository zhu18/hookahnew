package com.jusfoun.hookah.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class JfUserVo implements Serializable{

    private String userId;
    private String userSn;
    private String userName;
    private String mobile;
    private Integer userType;
    private String userLevel;
    private String userTypeShow;
    private Integer useJf;
    private Integer exchangeJf;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSn() {
        return userSn;
    }

    public void setUserSn(String userSn) {
        this.userSn = userSn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserTypeShow() {

        String userTypeShow = null;

        if(this.userType != null){
            if(userType == 2){
                userTypeShow = "个人用户";
            }else if(userType == 4){
                userTypeShow = "企业用户";
            }else {
                userTypeShow = "";
            }
        }

        return userTypeShow;
    }

    public void setUserTypeShow(String userTypeShow) {
        this.userTypeShow = userTypeShow;
    }

    public Integer getUseJf() {
        return useJf;
    }

    public void setUseJf(Integer useJf) {
        this.useJf = useJf;
    }

    public Integer getExchangeJf() {
        return exchangeJf;
    }

    public void setExchangeJf(Integer exchangeJf) {
        this.exchangeJf = exchangeJf;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
