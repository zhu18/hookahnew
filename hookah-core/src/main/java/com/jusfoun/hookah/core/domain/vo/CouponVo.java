package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.Coupon;

/**
 * Created by hhh on 2017/11/8.
 */
public class CouponVo extends Coupon {

    private String expiryStartTime;

    private String expiryEndTime;

    private Byte couponTag;

    public Byte getCouponTag() {
        return couponTag;
    }

    public void setCouponTag(Byte couponTag) {
        this.couponTag = couponTag;
    }

    public String getExpiryStartTime() {
        return expiryStartTime;
    }

    public void setExpiryStartTime(String expiryStartTime) {
        this.expiryStartTime = expiryStartTime;
    }

    public String getExpiryEndTime() {
        return expiryEndTime;
    }

    public void setExpiryEndTime(String expiryEndTime) {
        this.expiryEndTime = expiryEndTime;
    }
}
