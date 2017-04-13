package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.generic.GenericModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author huang lei
 * @date 2017/4/13 下午1:41
 * @desc
 */
public class Advert extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String advertId;

    private String url;

    private String href;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    private String creatorId;

    private String creatorName;

    private String advertType;

    private String advertGroup;


    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getAdvertType() {
        return advertType;
    }

    public void setAdvertType(String advertType) {
        this.advertType = advertType;
    }

    public String getAdvertGroup() {
        return advertGroup;
    }

    public void setAdvertGroup(String advertGroup) {
        this.advertGroup = advertGroup;
    }
}
