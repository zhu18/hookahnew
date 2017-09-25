package com.jusfoun.hookah.core.domain.zb.vo;

import java.util.Date;

public class ZbTradeRecord {

    private String title;

    private Integer type;

    private Long rewardMoney;

    private Date pressTime;

    private Integer status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Long rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Date getPressTime() {
        return pressTime;
    }

    public void setPressTime(Date pressTime) {
        this.pressTime = pressTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
