package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.GoodsLabel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-10-24.
 */
public class GoodsLabelVo implements Serializable {
    private String type;
    private List<GoodsLabel> goodsLabels;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<GoodsLabel> getGoodsLabels() {
        return goodsLabels;
    }

    public void setGoodsLabels(List<GoodsLabel> goodsLabels) {
        this.goodsLabels = goodsLabels;
    }
}
