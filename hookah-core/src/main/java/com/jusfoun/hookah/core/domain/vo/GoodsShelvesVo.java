package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsShelves;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctp on 2017/4/12.
 */
public class GoodsShelvesVo extends GoodsShelves {

    private String[] shelvesTagList;

    private List<GoodsVo> goods = new ArrayList();

    public String[] getShelvesTagList() {
        return shelvesTagList;
    }

    public void setShelvesTagList(String[] shelvesTagList) {
        this.shelvesTagList = shelvesTagList;
    }

    public List<GoodsVo> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsVo> goods) {
        this.goods = goods;
    }
}
