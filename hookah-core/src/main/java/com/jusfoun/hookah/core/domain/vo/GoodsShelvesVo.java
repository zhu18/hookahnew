package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.GoodsShelves;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctp on 2017/4/12.
 */
public class GoodsShelvesVo extends GoodsShelves {

    private List<Goods> goods = new ArrayList();

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
}
