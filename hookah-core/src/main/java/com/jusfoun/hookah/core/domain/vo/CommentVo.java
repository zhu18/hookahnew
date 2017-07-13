package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Comment;

/**
 * Created by ctp on 2017/6/6.
 */
public class CommentVo extends Comment {
    private String username;

    private String goodsName;

    //敏感词字符串
    private String sensitiveWords;

    //是否包含敏感词
    private Boolean isContains;

    //订单编号
    private String orderSn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSensitiveWords() {
        return sensitiveWords;
    }

    public void setSensitiveWords(String sensitiveWords) {
        this.sensitiveWords = sensitiveWords;
    }

    public Boolean getIsContains() {
        return isContains;
    }

    public void setIsContains(Boolean isContains) {
        this.isContains = isContains;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}
