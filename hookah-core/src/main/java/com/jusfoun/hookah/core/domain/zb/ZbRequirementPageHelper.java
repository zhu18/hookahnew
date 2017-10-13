package com.jusfoun.hookah.core.domain.zb;

import com.jusfoun.hookah.core.generic.GenericModel;

/**
 * Created by admin on 2017/9/19.
 */
public class ZbRequirementPageHelper extends GenericModel {

    //第几页
    private Integer pageNumber;
    //每页显示条数
    private Integer pageSize;
    //从第几条开始
    private Integer startIndex;

    //按照时间段查询的时间段类型
    //day :一天，week：一周内，month：一个月内，gtmonth：一个月以上，""或者null：全部
    private String timeType;

    //发布时间的查询条件，该值通过timeType推算出来
    private String pressTime;
    //需求类型
    private Short type;
    //需求状态
    private  Short status;
    //以哪个字段排序
    private String order;
    //正序还是倒叙
    private String sort;

    private String requireTitle;//需求标题(ctp to 20171010 add)

    private String requireSn;//需求编号(ctp to 20171011 add)

    private Short applyStatus;//报名状态(ctp to 20171011 add)  '状态；0 ：已报名；1：已被选中 2:未中标 3：工作中 4：评审中 5：验收中 6：待付款 7：待评价 8：交易取消 9：交易完成'

    public Short getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getRequireSn() {
        return requireSn;
    }

    public void setRequireSn(String requireSn) {
        this.requireSn = requireSn;
    }

    public String getRequireTitle() {
        return requireTitle;
    }

    public void setRequireTitle(String requireTitle) {
        this.requireTitle = requireTitle;
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

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getPressTime() {
        return pressTime;
    }

    public void setPressTime(String pressTime) {
        this.pressTime = pressTime;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
