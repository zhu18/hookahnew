package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.GoodsLabel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-10-28.
 */
public class GoodsLabelsPagVo implements Serializable {
    private Pagination pagination;
    private List<GoodsLabel> goodsLabelList;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<GoodsLabel> getGoodsLabelList() {
        return goodsLabelList;
    }

    public void setGoodsLabelList(List<GoodsLabel> goodsLabelList) {
        this.goodsLabelList = goodsLabelList;
    }
}
