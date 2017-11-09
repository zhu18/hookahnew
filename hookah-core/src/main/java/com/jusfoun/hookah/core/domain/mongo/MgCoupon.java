package com.jusfoun.hookah.core.domain.mongo;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lt on 2017/11/8.
 */
public class MgCoupon extends Coupon{

    private List<CouponGoods> couponGoods;

    private List<CouponCategories> couponCategories;

    public static class CouponGoods implements Serializable {
        private String goodsName;

        private String goodsSn;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }
    }

    public static class CouponCategories{

        private String catId;

        private String catName;

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }
    }


    public List<CouponGoods> getCouponGoods() {
        return couponGoods;
    }

    public void setCouponGoods(List<CouponGoods> couponGoods) {
        this.couponGoods = couponGoods;
    }

    public List<CouponCategories> getCouponCategories() {
        return couponCategories;
    }

    public void setCouponCategories(List<CouponCategories> couponCategories) {
        this.couponCategories = couponCategories;
    }
}
