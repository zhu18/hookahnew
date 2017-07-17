package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.MessageSendInfo;
import com.jusfoun.hookah.core.domain.es.EsRange;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ctp on 2017/7/11.
 * message消息发送查询条件
 */
public class MessageCritVo extends MessageSendInfo implements Serializable {

    private Integer pageNumber = HookahConstants.PAGE_NUM;
    private Integer pageSize = HookahConstants.PAGE_SIZE;
    private String orderField;
    private String order;
    private String startTime;
    private String endTime;
    private String keywords;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
