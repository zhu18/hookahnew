package com.jusfoun.hookah.coupon.service.impl;

import com.jusfoun.hookah.core.dao.CouponMapper;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.mongo.MgCoupon;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.CouponHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CouponService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgCouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    MgCouponService mgCouponService;

    @Resource
    GoodsService goodsService;

    @Override
    public ReturnData addCoupon(Coupon coupon, String goodsList, String categoriesList) throws Exception{
        String userId = super.getCurrentUser().getUserId();
        List<Condition>  filter = new ArrayList<>();
        List<MgCoupon.CouponGoods> couponGoods = new ArrayList<>();
        Date date = new Date();
        MgCoupon mgCoupon = new MgCoupon();
        coupon.setAddUser(userId);
        coupon.setAddTime(date);
        coupon.setCouponSn(CouponHelper.createCouponSn("YHQ",coupon.getCouponType().toString()));
        coupon.setCouponName(coupon.getCouponName().replaceAll(" ",""));
        coupon.setCouponStatus((short)1);
        switch (coupon.getApplyGoods()) {
            case 1: //指定商品
                String[] goodsSnList = goodsList.split(",");
                for (String goodsSn:goodsSnList){
                    filter.add(Condition.eq("goodsSn",goodsSn));
                    goodsService.selectOne(filter);
                }
                mgCoupon.setCouponGoods(couponGoods);
                break;
            case 2: //指定分类
                break;
        }
        BeanUtils.copyProperties(coupon,mgCoupon);
        mgCouponService.insert(mgCoupon);
        couponMapper.insert(coupon);
        return ReturnData.success("");
    }

    @Override
    public ReturnData modify(Coupon coupon, String goodsList, String categoriesList) throws Exception {
        String userId = super.getCurrentUser().getUserId();
        coupon.setUpdateUser(userId);
        couponMapper.updateByPrimaryKeySelective(coupon);
        return null;
    }

    @Override
    public ReturnData getDetailById(Integer couponId) throws Exception {
        Map<String,Object> map = new HashMap();
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);

        return null;
    }


}
