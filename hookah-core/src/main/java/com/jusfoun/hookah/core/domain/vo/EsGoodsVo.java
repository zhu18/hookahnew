package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.es.EsGoods;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-3-31.
 */
public class EsGoodsVo implements Serializable {
    private EsGoods esGoods;
    private Integer PageNum = 1;
    private Integer PageSize = 10;
    private String orderFiled;
    private String order;

    public EsGoods getEsGoods() {
        return esGoods;
    }

    public void setEsGoods(EsGoods esGoods) {
        this.esGoods = esGoods;
    }

    public int getPageNum() {
        return PageNum;
    }

    public void setPageNum(Integer pageNum) {
        PageNum = pageNum;
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
}
