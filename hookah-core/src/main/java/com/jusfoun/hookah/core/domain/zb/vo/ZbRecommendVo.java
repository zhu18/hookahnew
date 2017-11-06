package com.jusfoun.hookah.core.domain.zb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.domain.zb.ZbRecommend;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

public class ZbRecommendVo{

    private Integer requirementId;

    private Integer orderNum;

    private String title;

    private Long rewardMoney;

    private String description;

    private String requireSn;

    private String userName;

    private List<ZbRecommend> zbRecommendList;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date deliveryDeadline;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDeadline;

    //报名剩余时间
    @Transient
    private String applyLastTime;

    //报名数量
    @Transient
    private Integer count;

    private String dayTime;

    //需求方名称
    private String requiremetName;

    public Integer getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Integer requirementId) {
        this.requirementId = requirementId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeliveryDeadline() {
        return deliveryDeadline;
    }

    public void setDeliveryDeadline(Date deliveryDeadline) {
        this.deliveryDeadline = deliveryDeadline;
    }

    public String getApplyLastTime() {
        return applyLastTime;
    }

    public void setApplyLastTime(String applyLastTime) {
        this.applyLastTime = applyLastTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getApplyDeadline() {
        return applyDeadline;
    }

    public void setApplyDeadline(Date applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    public String getRequireSn() {
        return requireSn;
    }

    public void setRequireSn(String requireSn) {
        this.requireSn = requireSn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ZbRecommend> getZbRecommendList() {
        return zbRecommendList;
    }

    public void setZbRecommendList(List<ZbRecommend> zbRecommendList) {
        this.zbRecommendList = zbRecommendList;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getRequiremetName() {
        return requiremetName;
    }

    public void setRequiremetName(String requiremetName) {
        this.requiremetName = requiremetName;
    }
}
