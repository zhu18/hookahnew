package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lt on 2017/5/8.
 */
public class Cooperation extends GenericModel{

    public static final Integer COOPERATION_STATE_ON = 1;
    public static final Integer COOPERATION_STATE_OFF= 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cooperationId;

    private String url;

    private String pictureUrl;

    private String cooName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    private Integer state;

    private String cooAddress;

    private String cooPhone;

    private Integer cooOrder;

    public String getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(String cooperationId) {
        this.cooperationId = cooperationId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCooName() {
        return cooName;
    }

    public void setCooName(String cooName) {
        this.cooName = cooName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCooAddress() {
        return cooAddress;
    }

    public void setCooAddress(String cooAddress) {
        this.cooAddress = cooAddress;
    }

    public String getCooPhone() {
        return cooPhone;
    }

    public void setCooPhone(String cooPhone) {
        this.cooPhone = cooPhone;
    }

    public Integer getCooOrder() {
        return cooOrder;
    }

    public void setCooOrder(Integer cooOrder) {
        this.cooOrder = cooOrder;
    }
}
