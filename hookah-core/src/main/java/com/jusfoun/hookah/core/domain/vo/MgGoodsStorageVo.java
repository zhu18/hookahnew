package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoodsStorage;

import java.util.List;

/**
 * Created by wangjl on 2017-10-28.
 */
public class MgGoodsStorageVo extends MgGoodsStorage {
    private List<Goods> goodsList;//商品数据

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
