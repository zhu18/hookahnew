package com.jusfoun.hookah.core.domain.vo;

import com.jusfoun.hookah.core.domain.UserCoupon;
import com.jusfoun.hookah.core.generic.GenericModel;

/**
 * Created by hhh on 2017/11/8.
 * 用户优惠券扩展类 用于后台展示用户优惠券信息
 */
public class UserCouponVo extends UserCoupon {

    private String userName;

    private String userSn;

    private Long unused;

    private Long used;

    private Long expired;

    private String mobile;

    private Byte userType;

    private Long faceValue;

    private String couponName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSn() {
        return userSn;
    }

    public void setUserSn(String userSn) {
        this.userSn = userSn;
    }

    public Long getUnused() {
        return unused;
    }

    public void setUnused(Long unused) {
        this.unused = unused;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getExpired() {
        return expired;
    }

    public void setExpired(Long expired) {
        this.expired = expired;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Long getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Long faceValue) {
        this.faceValue = faceValue;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
}
