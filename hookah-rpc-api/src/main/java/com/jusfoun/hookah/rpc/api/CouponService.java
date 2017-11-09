package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

/**
 * Created by lt on 2017/11/7.
 */
public interface CouponService extends GenericService<Coupon,Long> {
    ReturnData addCoupon(Coupon coupon, String goodsList, String categoriesList) throws Exception;

    ReturnData modify(Coupon coupon, String goodsList, String categoriesList) throws Exception;

    ReturnData getDetailById(Integer couponId) throws Exception;
}
