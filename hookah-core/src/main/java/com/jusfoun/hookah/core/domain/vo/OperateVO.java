package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.constants.HookahConstants;

import java.util.Date;

/**
 * Created by chenhf on 2017/7/12.
 */
public class OperateVO {
    //日志类型
    private String logType;
    //操作平台
    private String platform;
    //操作起始时间
    private String optStartTime;
    //操作结束时间
    private String optEndTime;
    //操作类型
    private String optType;
    //操作用户
    private String  userName;
    //第几页
    private Integer pageNumber = HookahConstants.PAGE_NUM;
    //每页条数
    private Integer pageSize = HookahConstants.PAGE_SIZE;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOptStartTime() {
        return optStartTime;
    }

    public void setOptStartTime(String optStartTime) {
        this.optStartTime = optStartTime;
    }

    public String getOptEndTime() {
        return optEndTime;
    }

    public void setOptEndTime(String optEndTime) {
        this.optEndTime = optEndTime;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
