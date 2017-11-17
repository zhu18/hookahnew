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
import com.jusfoun.hookah.core.domain.vo.CouponVo;
import com.jusfoun.hookah.core.domain.vo.UserCouponVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.coupon.utils.PropertiesManager;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        coupon.setFaceValue(coupon.getFaceValue()*100);
        coupon.setAddTime(date);
        coupon.setCouponSn(createCouponSn(PropertiesManager.getInstance().getProperty("couponCode"), coupon.getCouponType()));
        coupon.setCouponName(coupon.getCouponName().trim());
        coupon.setCouponStatus((byte)1);
        coupon.setIsDeleted((byte)0);
        coupon.setReceivedCount(0);
        coupon.setUsedCount(0);
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
                                              String currentPage, String pageSize, Byte couponTag) throws Exception {
        List<Condition> filter = new ArrayList<>();
        List<OrderBy> orderBys = new ArrayList<>();
        Pagination pagination = new Pagination();
        if (couponTag!=null){
            if (couponTag==0){
                orderBys.add(OrderBy.desc("receivedTime"));
            }else if (couponTag==1){
                orderBys.add(OrderBy.asc("expiryEndDate"));
            }
        }else {
            orderBys.add(OrderBy.desc("receivedTime"));
        }
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
        filter.add(Condition.eq("isDeleted",(byte)0));
        if (StringUtils.isNotBlank(userId)){
            filter.add(Condition.eq("userId",userId));
            pagination = userCouponService.getListInPage(pageNumberNew,pageSizeNew,filter,orderBys);
            List<UserCoupon> list = pagination.getList();
            List<UserCouponVo> page = new ArrayList<>();
            if (list!=null && list.size()>0){
                for (UserCoupon userCoupon:list){
                    Coupon coupon = couponMapper.selectByPrimaryKey(userCoupon.getCouponId());
                    UserCouponVo userCouponVo = new UserCouponVo();
                    BeanUtils.copyProperties(userCoupon,userCouponVo);
                    userCouponVo.setCouponName(coupon.getCouponName());
                    userCouponVo.setFaceValue(coupon.getFaceValue());
                    page.add(userCouponVo);
                }
            }
            pagination.setList(page);
        }
        if (couponId!=null){
            filter.add(Condition.eq("couponId",couponId));
            pagination = userCouponService.getListInPage(pageNumberNew,pageSizeNew,filter,orderBys);
            List<UserCoupon> list = pagination.getList();
            List<UserCouponVo> page = new ArrayList<>();
            if (list!=null && list.size()>0){
                for (UserCoupon userCoupon:list){
                    User user = userService.selectById(userCoupon.getUserId());
                    UserCouponVo userCouponVo = new UserCouponVo();
                    BeanUtils.copyProperties(userCoupon,userCouponVo);
                    userCouponVo.setUserName(user.getUserName());
                    page.add(userCouponVo);
                }
            }
            pagination.setList(page);
        }
        return pagination;
    }

    @Override
    public Pagination getCouponByUserId(String userId, Long couponId, Byte userCouponStatus, String orderSn, String currentPage, String pageSize, Byte couponTag) throws Exception {
        Pagination page = getCouponReceivedDetail(userId,couponId,userCouponStatus,orderSn,currentPage,pageSize,couponTag);
        List<UserCouponVo> userCouponVos = page.getList();
        List<CouponVo> list = new ArrayList<>();
        if (userCouponVos!=null&&userCouponVos.size()>0){
            for (UserCouponVo userCouponVo : userCouponVos){
                Coupon coupon = couponMapper.selectByPrimaryKey(userCouponVo.getCouponId());
                CouponVo couponVo = new CouponVo();
                BeanUtils.copyProperties(coupon,couponVo);
                Date receivedTime = userCouponVo.getReceivedTime();
                Date expiryEndDate = userCouponVo.getExpiryEndDate();
                if (DateUtils.isSameDay(receivedTime,new Date())){
                    couponVo.setTagName(CouponVo.NEW_RECEIVED);
                }
                if (DateUtils.isSoonExpire(new Date(),expiryEndDate,3)) {
                    couponVo.setTagName(CouponVo.SOON_EXPIRE);
                }
                list.add(couponVo);
            }
        }
        page.setList(list);
        return page;
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
    public Pagination getCouponList(String couponName, Byte couponType, String currentPage, String pageSize, String sort) throws Exception {
        Pagination page = new Pagination<>();
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList<>();
        int pageNumberNew = HookahConstants.PAGE_NUM;
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }
        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }
        if (StringUtils.isNotBlank(couponName)) {
            filters.add(Condition.like("couponName", couponName));
        }
        if (couponType != null) {
            filters.add(Condition.eq("couponType", couponType));
        }
        filters.add(Condition.eq("isDeleted", (byte) 0));
        if (StringUtils.isNotBlank(sort)) {
            orderBys.add(OrderBy.desc(sort));
        } else {
            orderBys.add(OrderBy.asc("expiryEndDate"));
        }
        page = this.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
        List<Coupon> coupons = page.getList();
        List<CouponVo> couponVos = new ArrayList<>();
        if (coupons != null && coupons.size() > 0) {
            for (Coupon coupon : coupons) {
                CouponVo couponVo = new CouponVo();
                BeanUtils.copyProperties(coupon, couponVo);
                couponVo.setUnReceivedCount(coupon.getTotalCount() - coupon.getReceivedCount());
                couponVo.setUnUsedCount(coupon.getReceivedCount() - coupon.getUsedCount());
                couponVos.add(couponVo);
            }
        }
        page.setList(couponVos);
        return page;
    }

    @Override
    public ReturnData getUserCouponById(String userId) throws Exception {
        HashMap<String, Object> condition = new HashMap();
        condition.put("isDeleted",(byte)0);
        condition.put("userId",userId);
        List<UserCouponVo> list = couponMapper.getUserCouponCount(condition);
        return ReturnData.success(list.get(0));
    }

    public ReturnData getUserCouponDetail(String userId, Long userCouponId) throws Exception {
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userId",userId));
        filter.add(Condition.eq("id",userCouponId));
        UserCoupon userCoupon = userCouponService.selectOne(filter);
        Coupon coupon = couponMapper.selectByPrimaryKey(userCoupon.getCouponId());
        CouponVo couponVo = new CouponVo();
        BeanUtils.copyProperties(coupon,couponVo);
        couponVo.setUserCouponStatus(userCoupon.getUserCouponStatus());
        couponVo.setReceivedTime(userCoupon.getReceivedTime());
        couponVo.setOrderSn(userCoupon.getOrderSn());
        couponVo.setUsedTime(userCoupon.getUsedTime());
        couponVo.setReceivedMode(userCoupon.getReceivedMode());
        return ReturnData.success(couponVo);
    }

    public List<Coupon> getUserCoupons(String userId, Long goodsAmount) throws Exception{
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("userId",userId));
        filter.add(Condition.eq("userCouponStatus",(byte)0));
        filter.add(Condition.eq("orderSn",null));
        List<UserCoupon> userCoupons = userCouponService.selectList(filter);
        List<Coupon> coupons = new ArrayList<>();
        for (UserCoupon userCoupon : userCoupons){
            Coupon coupon = couponMapper.selectByPrimaryKey(userCoupon.getCouponId());
            switch (coupon.getApplyChannel()){
                case 0:
                    coupons.add(coupon);
                    break;
                case 1:
                    if (goodsAmount >= coupon.getDiscountValue()){
                        coupons.add(coupon);
                    }
                    break;
            }
        }
        return coupons;
    }

    public synchronized void sendCoupon2User(String userId, Long[] couponIdList) throws Exception{
        for (int i=0;i<couponIdList.length;i++){
            Long couponId = couponIdList[i];
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            Integer receivedCount = coupon.getReceivedCount();
            Integer totalCount = coupon.getTotalCount();
            Integer validDays = coupon.getValidDays();
            if (receivedCount < totalCount){
                UserCoupon userCoupon = new UserCoupon();
                userCoupon.setUserId(userId);
                userCoupon.setUserCouponSn(createUserCouponSn(coupon.getCouponSn()));
                userCoupon.setReceivedMode((byte)0);
                userCoupon.setReceivedTime(new Date());
                userCoupon.setCouponId(coupon.getId());
                userCoupon.setUserCouponStatus((byte)0);
                userCoupon.setExpiryEndDate(coupon.getExpiryEndDate());
                userCoupon.setExpiryStartDate(coupon.getExpiryStartDate());
                coupon.setReceivedCount(receivedCount++);
                userCouponService.insert(userCoupon);
                this.updateByIdSelective(coupon);
            }
        }
    }

    public String createCouponSn(String prefix, Byte type){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        sb.append(prefix);
        sb.append(String.format("%02d",type));
        sb.append(DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
        sb.append(num);
        String couponSn = sb.toString();
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("couponSn",couponSn));
        List<Coupon> coupons = this.selectList(filter);
        if (coupons!=null && coupons.size()>0){
            createCouponSn(prefix,type);
        }
        return sb.toString();
    }

    public String createUserCouponSn(String couponSn){
        Random random = new Random();
        int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String userCouponSn = couponSn+num;
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("couponSn",couponSn));
        List<Coupon> coupons = this.selectList(filter);
        if (coupons!=null && coupons.size()>0){
            createUserCouponSn(couponSn);
        }
        return userCouponSn;
    }
}
