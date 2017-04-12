package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-4-10.
 */
public class EsTypesVo implements Serializable {
    private List<EsCategoryVo> categoryList;
    private List<EsGoodsAttrVo> goodsAttrTypeList;

    public List<EsCategoryVo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EsCategoryVo> categoryList) {
        this.categoryList = categoryList;
    }

    public List<EsGoodsAttrVo> getGoodsAttrTypeList() {
        return goodsAttrTypeList;
    }

    public void setGoodsAttrTypeList(List<EsGoodsAttrVo> goodsAttrTypeList) {
        this.goodsAttrTypeList = goodsAttrTypeList;
    }
}
