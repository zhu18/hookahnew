package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.GoodsAttrType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangjl on 2017-4-12.
 */
public class EsGoodsAttrVo extends GoodsAttrType implements Serializable {
    private Long cnt;
    private List<EsGoodsAttrVo> children;

    public Long getCnt() {
        return cnt;
    }

    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    public List<EsGoodsAttrVo> getChildren() {
        return children;
    }

    public void setChildren(List<EsGoodsAttrVo> children) {
        this.children = children;
    }
}
