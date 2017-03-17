package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import com.jusfoun.hookah.core.utils.DateUtils;

import java.util.List;

/**
 * Created by wangjl on 2017-3-16.
 * 属性分类及属性值表
 */
public class MgGoodsAttrType extends GenericModel {
    private String typeId; //属性分类id
    private List<MgGoodsAttr> attrIds; //属性值id
    private String addTime = DateUtils.toDefaultNowTime();
    private String lastUpdateTime = DateUtils.toDefaultNowTime();
    private String userId;
    private String domainId;
    private String typeName;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<MgGoodsAttr> getAttrIds() {
        return attrIds;
    }

    public void setAttrIds(List<MgGoodsAttr> attrIds) {
        this.attrIds = attrIds;
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

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
