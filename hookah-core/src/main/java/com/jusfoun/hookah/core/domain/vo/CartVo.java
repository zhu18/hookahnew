package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Cart;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;

public class CartVo extends Cart {
    private Goods goods;

    private MgGoods.FormatBean format;

    private Long priceChange;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public MgGoods.FormatBean getFormat() {
        return format;
    }

    public void setFormat(MgGoods.FormatBean format) {
        this.format = format;
    }

    public void setPriceChange(Long priceChange) {
        this.priceChange = priceChange;
    }

    public Long getPriceChange() {
        return this.getGoodsPrice()-format.getPrice();
    }
}