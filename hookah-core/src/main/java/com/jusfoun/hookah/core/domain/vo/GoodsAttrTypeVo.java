package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.GoodsAttrType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctp on 2017/4/19.
 */
public class GoodsAttrTypeVo extends GoodsAttrType {

    private List<GoodsAttrType> children = new ArrayList();

    public List<GoodsAttrType> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsAttrType> children) {
        this.children = children;
    }
}
