package com.jusfoun.hookah.coupon.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.CouponMapper;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCoupon;
import com.jusfoun.hookah.core.domain.mongo.MgCoupon;
import com.jusfoun.hookah.core.domain.vo.UserCouponVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.CouponHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.coupon.utils.PropertiesManager;
import com.jusfoun.hookah.rpc.api.*;
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
    UserCouponService userCouponService;

    @Resource
    UserService userService;

    @Override
    public ReturnData addCoupon(Coupon coupon, String goodsList, String userId, String categoriesList) throws Exception{
        List<Condition>  filter = new ArrayList<>();
        List<MgCoupon.CouponGoods> couponGoods = new ArrayList<>();
        Date date = new Date();
        MgCoupon mgCoupon = new MgCoupon();
        User user = userService.selectById(userId);
        coupon.setAddUser(user.getUserId());
        coupon.setUserName(user.getUserName());
        coupon.setAddTime(date);
        coupon.setCouponSn(CouponHelper.createCouponSn(PropertiesManager.getInstance().getProperty("couponCode"), coupon.getCouponType()));
        coupon.setCouponName(coupon.getCouponName().trim());
        coupon.setCouponStatus((byte)1);
        coupon.setIsDeleted((byte)0);
        switch (coupon.getApplyGoods()) {
            case 1: //指定商品
                String[] goodsSnList = goodsList.split(",");
                for (String goodsSn:goodsSnList){
                    filter.add(Condition.eq("goodsSn",goodsSn));
//                    goodsService.selectOne(filter);
                }
                mgCoupon.setCouponGoods(couponGoods);
                break;
            case 2: //指定分类
                break;
        }
        couponMapper.insertAndGetId(coupon);
        coupon.setId(coupon.getId());
        BeanUtils.copyProperties(coupon,mgCoupon);
        mgCouponService.insert(mgCoupon);
        return ReturnData.success("");
    }

    @Override
    public ReturnData modify(Coupon coupon, String goodsList, String userId, String categoriesList) throws Exception {
        Date date = new Date();
        MgCoupon mgCoupon = new MgCoupon();
        coupon.setUpdateUser(userId);
        coupon.setUpdateTime(date);
        if (goodsList!=null){

        }
        if (categoriesList!=null){

        }
        BeanUtils.copyProperties(coupon,mgCoupon);
        couponMapper.updateByPrimaryKeySelective(coupon);
        mgCouponService.updateByIdSelective(mgCoupon);
        return null;
    }

    @Override
    public Pagination getCouponReceivedDetail(String userId, Long couponId, Byte userCouponStatus, String orderSn,
                                              String currentPage, String pageSize) throws Exception {
        List<Condition> filter = new ArrayList<>();
        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.desc("receivedTime"));
        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }
        if (StringUtils.isNotBlank(orderSn)){
            filter.add(Condition.eq("orderSn",orderSn));
        }
        if (userCouponStatus != null){
            filter.add(Condition.eq("userCouponStatus",userCouponStatus));
        }
        if (StringUtils.isNotBlank(userId)){
            filter.add(Condition.eq("userId",userId));
        }
        if (couponId!=null){
            filter.add(Condition.eq("couponId",couponId));
        }
        filter.add(Condition.eq("isDeleted",(byte)0));
        Pagination pagination = userCouponService.getListInPage(pageNumberNew,pageSizeNew,filter,orderBys);
        return pagination;
    }

    @Override
    public void logicDelete(Long couponId) throws Exception {
        List<Condition> filter = new ArrayList<>();
        Date date = new Date();
        Coupon coupon = new Coupon();
        MgCoupon mgCoupon = new MgCoupon();
        UserCoupon userCoupon = new UserCoupon();
        coupon.setId(couponId);
        coupon.setIsDeleted((byte)1);
        coupon.setUpdateTime(date);
        userCoupon.setIsDeleted((byte)1);
        BeanUtils.copyProperties(coupon,mgCoupon);
        filter.add(Condition.eq("couponId",couponId));
        couponMapper.updateByPrimaryKeySelective(coupon);
        mgCouponService.updateByIdSelective(mgCoupon);
        userCouponService.updateByConditionSelective(userCoupon,filter);
    }

    @Override
    public Pagination getUserCouponList(User user, String currentPage, String pageSize) throws Exception {
        Pagination pagination = new Pagination<>();
        HashMap<String, Object> condition = new HashMap();
        List<Condition> filter = new ArrayList<>();
        List<OrderBy> orderBys = new ArrayList<>();
        orderBys.add(OrderBy.desc("receivedTime"));
        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }
        if (user.getUserName()!=null){
            condition.put("userName",user.getUserName());
        }
        if (user.getUserSn()!=null){
            condition.put("userSn",user.getUserSn());
        }
        condition.put("isDeleted",(byte)0);
        PageHelper.startPage(pageNumberNew, pageSizeNew);
        List<UserCouponVo> list = couponMapper.getUserCouponCount(condition);
        PageInfo<UserCouponVo> page = new PageInfo<UserCouponVo>(list);
        pagination.setTotalItems(page.getTotal());
        pagination.setPageSize(pageSizeNew);
        pagination.setCurrentPage(pageNumberNew);
        pagination.setList(list);
        return pagination;
    }

    @Override
    public ReturnData getUserCouponById(String userId) throws Exception {
        HashMap<String, Object> condition = new HashMap();
        condition.put("isDeleted",(byte)0);
        condition.put("userId",userId);
        List<UserCouponVo> list = couponMapper.getUserCouponCount(condition);
        return ReturnData.success(list.get(0));
    }


}
