package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 统计
 * Created by zhaoshuai on 2017/11/30.
 */
@Document
public class MgTongJi extends GenericModel {

    @Id
    private String id;

    //统计cookieId
    private String tongJiId;

    //统计来源
    private String utmSource;

    //统计关键字
    private String utmTerm;

    private String addTime;

    //统计当前地址
    private String tongJiUrl;

    //用户id
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTongJiId() {
        return tongJiId;
    }

    public void setTongJiId(String tongJiId) {
        this.tongJiId = tongJiId;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getTongJiUrl() {
        return tongJiUrl;
    }

    public void setTongJiUrl(String tongJiUrl) {
        this.tongJiUrl = tongJiUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
