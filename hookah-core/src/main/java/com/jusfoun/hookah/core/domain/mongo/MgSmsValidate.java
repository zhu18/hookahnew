package com.jusfoun.hookah.core.domain.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.generic.GenericModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

// FIXME generate failure  field _$FormatList191

/**
 * 短信校验码 缓存
 */
@Document
public class MgSmsValidate extends GenericModel {
    private String userId;

    private String phoneNum;

    private String smsContent;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Indexed(expireAfterSeconds = HookahConstants.SMS_DURATION_SECONDS)
    private Date expireTime;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
