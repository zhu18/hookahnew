package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.es.EsRange;

import java.io.Serializable;

/**
 * Created by ctp on 2017/5/11.
 * goods商品查询条件
 */
public class GoodsCritVo implements Serializable {
    private String id;//货架id

    private Integer pageNumber = HookahConstants.PAGE_NUM;
    private Integer pageSize = HookahConstants.PAGE_SIZE;
    private String orderField;
    private String order;
    EsRange range;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        pageSize = pageSize;
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

    public EsRange getRange() {
        return range;
    }

    public void setRange(EsRange range) {
        this.range = range;
    }
}
