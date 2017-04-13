package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.GoodsAttrType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-4-10.
 */
public class EsTypesVo implements Serializable {
    private List<EsTreeVo<Category>> categoryList;
    private List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList;

    public List<EsTreeVo<Category>> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<EsTreeVo<Category>> categoryList) {
        this.categoryList = categoryList;
    }

    public List<EsTreeVo<GoodsAttrType>> getGoodsAttrTypeList() {
        return goodsAttrTypeList;
    }

    public void setGoodsAttrTypeList(List<EsTreeVo<GoodsAttrType>> goodsAttrTypeList) {
        this.goodsAttrTypeList = goodsAttrTypeList;
    }
}
