package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.CouponMapper;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lt on 2017/11/7.
 */
@Service
public class CouponServiceImpl extends GenericServiceImpl<Coupon, Long> implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    public void setDao(CouponMapper couponMapper) {
        super.setDao(couponMapper);
    }

    @Override
    public ReturnData addCoupon(Coupon coupon) {
        try {
            String userId = super.getCurrentUser().getUserId();
            Date date = new Date();
            coupon.setAddUser(userId);
            coupon.setAddTime(date);
            coupon.setCouponSn("");
            coupon.setCouponName(coupon.getCouponName().replaceAll(" ",""));
            coupon.setCouponStatus((short)1);
            switch (coupon.getApplyGoods()) {
                case 1:
                    break;
                case 2:
                    break;
            }
            couponMapper.insertSelective(coupon);
            return ReturnData.success("");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
