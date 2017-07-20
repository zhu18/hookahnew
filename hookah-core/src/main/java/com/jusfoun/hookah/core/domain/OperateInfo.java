package com.jusfoun.hookah.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.constants.OperateConstants;
import com.jusfoun.hookah.core.generic.GenericModel;

import java.util.Date;

/**
 * Created by admin on 2017/7/10.
 */
public class OperateInfo extends GenericModel {
    //操作记录id
    private Long Id;
    //操作平台
    private String platform;
    //操作用户
    private String userId;
    //用户名
    private String userName;
    //日志类型
    private String logType;
    //操作ip
    private String IP;
    //操作时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;
    //请求url
    private String url;
    //操作类型
    private String optType;
    //操作内容
    private String optContent;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        String result = OperateConstants.Front_OPT.getNameByCode(logType);
        if (logType.equalsIgnoreCase(result))
            result = OperateConstants.Back_OPT.getNameByCode(logType);
        this.logType = result;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = OperateConstants.OPT.getNameByCode(optType);
    }

    public String getOptContent() {
        return optContent;
    }

    public void setOptContent(String optContent) {
        this.optContent = optContent;
    }
}
