package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import com.jusfoun.hookah.core.utils.DateUtils;

public class MgGoodsAttr extends GenericModel {
    private String attrId;
    private String attrName;
    private String domainId;
    private String addTime = DateUtils.toDefaultNowTime();
    private String lastUpdateTime = DateUtils.toDefaultNowTime();
    private String userId;

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}