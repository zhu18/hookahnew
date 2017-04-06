package com.jusfoun.hookah.core.domain.es;

import java.io.Serializable;

/**
 * Created by wangjl on 2017-4-6.
 */
public class Suggest implements Serializable {
    private String input;
    private String goodsId;
    private String goodsName;

    public Suggest(String input, String goodsId, String goodsName) {
        this.input = input;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
