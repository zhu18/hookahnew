package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Coupon;

/**
 * Created by hhh on 2017/11/8.
 */
public class CouponVo extends Coupon {

    private Byte couponTag;

    public Byte getCouponTag() {
        return couponTag;
    }

    public void setCouponTag(Byte couponTag) {
        this.couponTag = couponTag;
    }
}
