package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.vo.UserCouponVo;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.HashMap;
import java.util.List;

public interface CouponMapper extends GenericDao<Coupon> {

    List<UserCouponVo> getUserCouponCount(HashMap<String, Object> paramMap);

    int insertAndGetId(Coupon coupon);
}