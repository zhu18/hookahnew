package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.constants.HookahConstants;

import java.io.Serializable;

/**
 * Created by ctp on 2017/7/18.
 * 公共查询条件
 */
public class BaseCritVo implements Serializable{
    private Integer pageNumber = HookahConstants.PAGE_NUM;//页码
    private Integer pageSize = HookahConstants.PAGE_SIZE;//每页条数
    private String orderField;//排序字段
    private String order;//desc,asc
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String keywords;//关键字

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
