package com.jusfoun.hookah.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/4.
 */
public class ShowVO implements Serializable {

    private String userId;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    //地域id
    private Long regionId;

    private Long pid;

    private Long count;

    private String name;

    //支付状态
    private Integer payStatus;

    //订单金额
    private Long orderAmount;

    //登录名称
    private String loginName;

    public String getUserId() { return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public Date getAddTime() {return addTime; }

    public void setAddTime(Date addTime) {this.addTime = addTime;}

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
