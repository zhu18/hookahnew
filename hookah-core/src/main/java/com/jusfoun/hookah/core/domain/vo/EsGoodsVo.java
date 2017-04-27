package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.es.EsGoods;
import com.jusfoun.hookah.core.domain.es.EsRange;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-31.
 */
public class EsGoodsVo implements Serializable {
    private EsGoods esGoods;
    private Integer PageNumber = HookahConstants.PAGE_NUM;
    private Integer PageSize = HookahConstants.PAGE_SIZE;
    private String orderFiled;
    private String order;
    EsRange range;
//    private String orderFiled = HookahConstants.GOODS_ORDER_FIELD;
//    private String order = HookahConstants.GOODS_ORDER_SORT;

    public EsGoods getEsGoods() {
        return esGoods;
    }

    public void setEsGoods(EsGoods esGoods) {
        this.esGoods = esGoods;
    }


    public Integer getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        PageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public String getOrderFiled() {
        return orderFiled;
    }

    public void setOrderFiled(String orderFiled) {
        this.orderFiled = orderFiled;
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
