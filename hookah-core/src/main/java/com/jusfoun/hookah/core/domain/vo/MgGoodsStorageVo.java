package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.mongo.MgGoodsStorage;

import java.util.List;

/**
 * Created by wangjl on 2017-10-28.
 */
public class MgGoodsStorageVo extends MgGoodsStorage {
    private List<GoodsVo> goodsList;//商品数据

    public List<GoodsVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsVo> goodsList) {
        this.goodsList = goodsList;
    }
}
