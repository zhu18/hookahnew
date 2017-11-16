package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

/**
 * Created by lt on 2017/11/7.
 */
public interface CouponService extends GenericService<Coupon,Long> {

    ReturnData addCoupon(Coupon coupon, String goodsList, String userId, String categoriesList) throws Exception;

    ReturnData modify(Coupon coupon, String goodsList, String userId, String categoriesList) throws Exception;

    Pagination getCouponReceivedDetail(String userId, Long couponId, Byte userCouponStatus, String orderSn,
                                       String currentPage, String pageSize, Byte couponTag) throws Exception;

    Pagination getCouponByUserId(String userId, Long couponId, Byte userCouponStatus, String orderSn, String currentPage,
                                 String pageSize, Byte couponTag) throws Exception;

    void logicDelete(Long couponId) throws Exception;

    Pagination getUserCouponList(User user, String currentPage, String pageSize) throws Exception;

    ReturnData getUserCouponById(String userId) throws Exception;
}
