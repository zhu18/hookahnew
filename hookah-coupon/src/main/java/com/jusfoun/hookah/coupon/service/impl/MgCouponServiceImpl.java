package com.jusfoun.hookah.coupon.service.impl;

import com.jusfoun.hookah.core.domain.mongo.MgCoupon;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.MgCouponService;
import org.springframework.stereotype.Service;

/**
 * Created by lt on 2017/11/8.
 */
@Service
public class MgCouponServiceImpl extends GenericMongoServiceImpl<MgCoupon, Integer> implements MgCouponService {
}
