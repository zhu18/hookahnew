package com.jusfoun.hookah.core.domain.vo;

import java.io.Serializable;

/**
 * Created by ctp on 2017/6/15.
 */
public class HomeVo implements Serializable {

    private Long userCount;//会员总数
    private Long pendCheckUserCount;//待审核会员
    private Long realUserCount;//实名会员
    private Long addedGoodsCount;//上架商品
    private Long offGoodsCount;//下架商品
    private Long goodsOneCategoryCount;//商品一级分类

    private Integer orderNotPay;//未支付订单
    private Integer orderPaid;//已支付订单
    private Integer orderIsDeleted;//已经删除的未支付订单
    private Integer orderUserCount;//购买人数

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getPendCheckUserCount() {
        return pendCheckUserCount;
    }

    public void setPendCheckUserCount(Long pendCheckUserCount) {
        this.pendCheckUserCount = pendCheckUserCount;
    }

    public Long getRealUserCount() {
        return realUserCount;
    }

    public void setRealUserCount(Long realUserCount) {
        this.realUserCount = realUserCount;
    }

    public Long getAddedGoodsCount() {
        return addedGoodsCount;
    }

    public void setAddedGoodsCount(Long addedGoodsCount) {
        this.addedGoodsCount = addedGoodsCount;
    }

    public Long getOffGoodsCount() {
        return offGoodsCount;
    }

    public void setOffGoodsCount(Long offGoodsCount) {
        this.offGoodsCount = offGoodsCount;
    }

    public Long getGoodsOneCategoryCount() {
        return goodsOneCategoryCount;
    }

    public void setGoodsOneCategoryCount(Long goodsOneCategoryCount) {
        this.goodsOneCategoryCount = goodsOneCategoryCount;
    }

    public Integer getOrderNotPay() {
        return orderNotPay;
    }

    public void setOrderNotPay(Integer orderNotPay) {
        this.orderNotPay = orderNotPay;
    }

    public Integer getOrderPaid() {
        return orderPaid;
    }

    public void setOrderPaid(Integer orderPaid) {
        this.orderPaid = orderPaid;
    }

    public Integer getOrderIsDeleted() {
        return orderIsDeleted;
    }

    public void setOrderIsDeleted(Integer oederIsDeleted) {
        this.orderIsDeleted = oederIsDeleted;
    }

    public Integer getOrderUserCount() {
        return orderUserCount;
    }

    public void setOrderUserCount(Integer orderUserCount) {
        this.orderUserCount = orderUserCount;
    }
}
