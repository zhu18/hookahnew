package com.jusfoun.hookah.coupon.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.CouponMapper;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.MessageCode;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCoupon;
import com.jusfoun.hookah.core.domain.mongo.MgCoupon;
import com.jusfoun.hookah.core.domain.vo.CouponVo;
import com.jusfoun.hookah.core.domain.vo.UserCouponVo;
import com.jusfoun.hookah.core.exception.HookahException;
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

    @Resource
    MqSenderService mqSenderService;

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
        coupon.setCouponSn(createCouponSn(PropertiesManager.getInstance().getProperty("couponCode"), coupon.getCouponType()));
        filter.add(Condition.eq("couponName",coupon.getCouponName().trim()));
        List<Coupon> coupons = this.selectList(filter);
        if (coupons!=null&&coupons.size()>0){
            throw new HookahException("名称为["+coupon.getCouponName().trim()+"]的优惠券已存在");
        }
        coupon.setCouponName(coupon.getCouponName().trim());
        if (DateUtils.isSameDay(coupon.getExpiryStartDate(),new Date())){
            coupon.setCouponStatus(HookahConstants.CouponStatus.USED.getCode());
            coupon.setActivatedTime(new Date());
            coupon.setActivatedUser(userId);
        }else {
            coupon.setCouponStatus(HookahConstants.CouponStatus.UN_USED.getCode());
        }
        coupon.setIsDeleted((byte)0);
        coupon.setReceivedCount(0);
        coupon.setUsedCount(0);
        switch (coupon.getApplyGoods()) {
            case 1: //指定商品
//                String[] goodsSnList = goodsList.split(",");
//                for (String goodsSn:goodsSnList){
//                    filter.add(Condition.eq("goodsSn",goodsSn));
//                    goodsService.selectOne(filter);
//                }
//                mgCoupon.setCouponGoods(couponGoods);
                break;
            case 2: //指定分类
                break;
        }
        couponMapper.insertAndGetId(coupon);
        coupon.setId(coupon.getId());
        BeanUtils.copyProperties(coupon,mgCoupon);
        mgCouponService.insert(mgCoupon);
        return ReturnData.success("添加成功");
    }

    @Override
    public ReturnData modify(Coupon coupon, String goodsList, String userId, String categoriesList) throws Exception {
        Date date = new Date();
        MgCoupon mgCoupon = new MgCoupon();
        List<Condition>  filter = new ArrayList<>();
        if (coupon.getCouponName().trim()!=null){
            filter.add(Condition.eq("couponName",coupon.getCouponName().trim()));
            List<Coupon> coupons = this.selectList(filter);
            if (coupons!= null && coupons.size() >0){
                for (Coupon coupon1 : coupons){
                    if (coupon.getId()!=coupon1.getId()){
                        throw new HookahException("名称为["+coupon.getCouponName().trim()+"]的优惠券已存在");
                    }
                }
            }
        }
        coupon.setUpdateUser(userId);
        if (goodsList!=null){

        }
        if (categoriesList!=null){

        }
        BeanUtils.copyProperties(coupon,mgCoupon);
        filter.clear();
        filter.add(Condition.eq("couponId",coupon.getId()));
        List<UserCoupon> userCoupons = userCouponService.selectList(filter);
        if (userCoupons!=null && userCoupons.size()>0){
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setValidDays(coupon.getValidDays());
            userCoupon.setExpiryStartDate(coupon.getExpiryStartDate());
            userCoupon.setExpiryEndDate(coupon.getExpiryEndDate());
            userCoupon.setUpdateUser(userId);
            Long[] ids = new Long[userCoupons.size()];
            for (int i=0;i<userCoupons.size();i++){
                ids[i] = userCoupons.get(i).getId();
            }
            filter.clear();
            filter.add(Condition.in("id",ids));
            userCouponService.updateByConditionSelective(userCoupon,filter);
        }
        couponMapper.updateByPrimaryKeySelective(coupon);
        mgCouponService.updateByIdSelective(mgCoupon);
        return ReturnData.success("修改成功！");
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
        if (userCouponVos != null && userCouponVos.size() > 0){
            for (UserCouponVo userCouponVo : userCouponVos){
                Coupon coupon = couponMapper.selectByPrimaryKey(userCouponVo.getCouponId());
                CouponVo couponVo = new CouponVo();
                BeanUtils.copyProperties(coupon,couponVo);
                couponVo.setUserCouponStatus(userCouponVo.getUserCouponStatus());
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
            if (user.getUserSn().contains(HookahConstants.platformCode)){
                condition.put("userSn",user.getUserSn());
            }else {
                condition.put("userId",user.getUserSn());
            }
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
    public Pagination getCouponList(String couponName, Byte couponType, String currentPage, String pageSize, String sort, Byte type) throws Exception {
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
        if (type==1){
            filters.add(Condition.eq("couponStatus",HookahConstants.CouponStatus.USED.getCode()));
        }
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

    public List<CouponVo> getUserCoupons(String userId, Long goodsAmount) throws Exception{
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("userId",userId));
        filter.add(Condition.eq("userCouponStatus",HookahConstants.UserCouponStatus.UN_USED.getCode()));
        filter.add(Condition.eq("orderSn",null));
        List<UserCoupon> userCoupons = userCouponService.selectList(filter);
        List<CouponVo> coupons = new ArrayList<>();
        for (UserCoupon userCoupon : userCoupons){
            Coupon coupon = couponMapper.selectByPrimaryKey(userCoupon.getCouponId());
            CouponVo couponVo = new CouponVo();
            couponVo.setUserCouponId(userCoupon.getId());
            BeanUtils.copyProperties(coupon,couponVo);
            couponVo.setExpiryEndTime(DateUtils.toDateText(coupon.getExpiryEndDate()));
            switch (couponVo.getApplyChannel()){
                case 0:
                    coupons.add(couponVo);
                    break;
                case 1:
                    if (goodsAmount >= coupon.getDiscountValue()){
                        coupons.add(couponVo);
                    }
                    break;
            }
        }
        return coupons;
    }

    public synchronized void sendCoupon2User(String userId, List<Long> couponIdList, Byte receivedMode) throws Exception{
        for (Long couponId : couponIdList){
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            Integer receivedCount = coupon.getReceivedCount();
            Integer totalCount = coupon.getTotalCount();
            Byte limitedCount = coupon.getLimitedCount();
            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("userId",userId));
            filter.add(Condition.eq("couponId",coupon.getId()));
            Long count = userCouponService.count(filter);
            if (limitedCount <= count){
                throw new HookahException("用户["+userId+"]领取的优惠券["+coupon.getCouponName()+"]已经达到领取上限");
            }
            if (receivedCount >= totalCount){
                throw new HookahException("优惠券["+coupon.getCouponName()+"]已经发完");
            }
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setUserId(userId);
            userCoupon.setUserCouponSn(createUserCouponSn(coupon.getCouponSn()));
            userCoupon.setReceivedMode(receivedMode);
            userCoupon.setReceivedTime(new Date());
            userCoupon.setCouponId(coupon.getId());
            userCoupon.setUserCouponStatus(HookahConstants.UserCouponStatus.UN_USED.getCode());
            userCoupon.setExpiryEndDate(coupon.getExpiryEndDate());
            userCoupon.setExpiryStartDate(coupon.getExpiryStartDate());
            userCoupon.setValidDays(coupon.getValidDays());
            coupon.setReceivedCount(receivedCount+1);
            userCouponService.insert(userCoupon);
            this.updateByIdSelective(coupon);
        }
        // TODO …… 赠送优惠券之后发送消息给用户
        MessageCode messageCode = new MessageCode();
        messageCode.setCode(HookahConstants.MESSAGE_701);//此处填写相关事件编号
        messageCode.setBusinessId(userId);
        mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_NEW_MESSAGE, messageCode);//将数据添加到队列
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
        filter.add(Condition.eq("userCouponSn",userCouponSn));
        List<UserCoupon> userCoupons = userCouponService.selectList(filter);
        if (userCoupons!=null && userCoupons.size()>0){
            createUserCouponSn(couponSn);
        }
        return userCouponSn;
    }

    public void updateStatusEveryDay() throws Exception{
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("couponStatus",HookahConstants.CouponStatus.USED.getCode()));
        List<Coupon> couponList = this.selectList(filter);
        if (couponList.size() == 0){
            return ;
        }
        for (Coupon coupon : couponList){
            Date expireEndDate = coupon.getExpiryEndDate();
            Date now = new Date();
            if (DateUtils.isExpired(expireEndDate,now)){
                filter.clear();
                filter.add(Condition.eq("isDeleted",(byte)0));
                filter.add(Condition.eq("couponId",coupon.getId()));
                filter.add(Condition.eq("userCouponStatus",HookahConstants.UserCouponStatus.UN_USED.getCode()));
                List<UserCoupon> userCoupons = userCouponService.selectList(filter);
                if (userCoupons != null && userCoupons.size() > 0){
                    UserCoupon userCoupon = new UserCoupon();
                    userCoupon.setUserCouponStatus(HookahConstants.UserCouponStatus.EXPIRED.getCode());
                    userCoupon.setUpdateUser("SYSTEM");
                    Long[] ids = new Long[userCoupons.size()];
                    for (int i=0;i<userCoupons.size();i++){
                        ids[i] = userCoupons.get(i).getId();
                    }
                    filter.clear();
                    filter.add(Condition.in("id",ids));
                    userCouponService.updateByConditionSelective(userCoupon,filter);
                }
                coupon.setCouponStatus(HookahConstants.CouponStatus.EXPIRED.getCode());
                coupon.setUpdateUser("SYSTEM");
                this.updateByIdSelective(coupon);
            }
        }
        filter.clear();
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("userCouponStatus",HookahConstants.UserCouponStatus.UN_USED.getCode()));
        List<UserCoupon> userCoupons = userCouponService.selectList(filter);
        for (UserCoupon userCoupon : userCoupons){
            if (DateUtils.isExpired(userCoupon.getReceivedTime(),userCoupon.getValidDays(),new Date())){
                userCoupon.setUserCouponStatus(HookahConstants.UserCouponStatus.EXPIRED.getCode());
                userCoupon.setUpdateUser("SYSTEM");
                userCouponService.updateByIdSelective(userCoupon);
            }
        }
    }

    public void activeCoupons() throws Exception{
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("isDeleted",(byte)0));
        filter.add(Condition.eq("couponStatus",HookahConstants.CouponStatus.UN_USED.getCode()));
        List<Coupon> couponList = this.selectList(filter);
        if (couponList.size() == 0){
            return ;
        }
        for (Coupon coupon : couponList){
            Date expireStartDate = coupon.getExpiryStartDate();
            Date now = new Date();
            if (DateUtils.isSameDay(expireStartDate,now)){
                coupon.setCouponStatus(HookahConstants.CouponStatus.USED.getCode());
                coupon.setActivatedUser("SYSTEM");
                coupon.setActivatedTime(now);
            }
            this.updateByIdSelective(coupon);
        }
    }
}
