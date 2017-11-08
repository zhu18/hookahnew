package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by lt on 2017/11/7.
 */
public interface CouponService extends GenericService<Coupon,Long> {
    ReturnData addCoupon(Coupon coupon);
}
