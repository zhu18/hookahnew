package com.jusfoun.hookah.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jusfoun.hookah.core.domain.Coupon;

import java.util.Date;

/**
 * Created by hhh on 2017/11/8.
 */
public class CouponVo extends Coupon {

    public static final String NEW_RECEIVED = "新到";

    public static final String SOON_EXPIRE = "即将过期";

    private String expiryStartTime;

    private String expiryEndTime;

    private Byte couponTag;

    private String tagName;

    private String orderSn;

    private Integer unReceivedCount;

    private Integer unUsedCount;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivedTime;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date usedTime;

    private Byte userCouponStatus;

    private Byte receivedMode;

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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public Byte getUserCouponStatus() {
        return userCouponStatus;
    }

    public void setUserCouponStatus(Byte userCouponStatus) {
        this.userCouponStatus = userCouponStatus;
    }

    public Byte getReceivedMode() {
        return receivedMode;
    }

    public void setReceivedMode(Byte receivedMode) {
        this.receivedMode = receivedMode;
    }

    public Integer getUnReceivedCount() {
        return unReceivedCount;
    }

    public void setUnReceivedCount(Integer unReceivedCount) {
        this.unReceivedCount = unReceivedCount;
    }

    public Integer getUnUsedCount() {
        return unUsedCount;
    }

    public void setUnUsedCount(Integer unUsedCount) {
        this.unUsedCount = unUsedCount;
    }
}
