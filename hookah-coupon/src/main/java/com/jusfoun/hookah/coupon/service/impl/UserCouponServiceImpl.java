package com.jusfoun.hookah.coupon.service.impl;

import com.jusfoun.hookah.core.dao.UserCouponMapper;
import com.jusfoun.hookah.core.domain.UserCoupon;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserCouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lt on 2017/11/7.
 */
@Service
public class UserCouponServiceImpl extends GenericServiceImpl<UserCoupon, Long> implements UserCouponService {

    @Resource
    private UserCouponMapper userCouponMapper;

    @Resource
    public void setDao(UserCouponMapper userCouponMapper) {
        super.setDao(userCouponMapper);
    }

}
