package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;

/**
 * Created by ctp on 2017/9/4.
 */
public class ChannelDataVo implements Serializable {

    private String goodsId; //推送的商品id
    private int opera;// 0 撤回； 1 推送

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getOpera() {
        return opera;
    }

    public void setOpera(int opera) {
        this.opera = opera;
    }
}
