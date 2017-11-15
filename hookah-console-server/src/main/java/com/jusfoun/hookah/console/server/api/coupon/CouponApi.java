package com.jusfoun.hookah.console.server.api.coupon;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.CouponVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.CouponService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/11/8.
 */
@RestController
@RequestMapping("/api/coupon")
public class CouponApi extends BaseController {
    @Resource
    private CouponService couponService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ReturnData addCoupon(CouponVo couponVo, String goodsList, String categoriesList){
        try {
            String userId = this.getCurrentUser().getUserId();
            if (StringUtils.isNotBlank(couponVo.getExpiryStartTime())){
                String time = DateUtils.transferTime(couponVo.getExpiryStartTime());
                Date expiryStartTime = DateUtils.getDate(DateUtils.transferTime(couponVo.getExpiryStartTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT);
                couponVo.setExpiryStartDate(expiryStartTime);
            }
            if (StringUtils.isNotBlank(couponVo.getExpiryEndTime())){
                Date expiryEndTime = DateUtils.getDate(DateUtils.transferTime(couponVo.getExpiryEndTime()),DateUtils.DEFAULT_DATE_TIME_FORMAT);
                couponVo.setExpiryEndDate(expiryEndTime);
            }
            return couponService.addCoupon(couponVo,goodsList,userId,categoriesList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券添加失败，请联系技术人员！");
        }
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ReturnData modifyCoupon(Coupon coupon, String expiryStartDate, String expiryEndDate, String goodsList, String categoriesList){
        try {
            String userId = this.getCurrentUser().getUserId();
            if (StringUtils.isNotBlank(expiryStartDate)){
                Date expiryStartTime = DateUtils.getDate(expiryStartDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
                coupon.setExpiryStartDate(expiryStartTime);
            }
            if (StringUtils.isNotBlank(expiryEndDate)){
                Date expiryEndTime = DateUtils.getDate(expiryEndDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
                coupon.setExpiryEndDate(expiryEndTime);
            }
            return couponService.modify(coupon,goodsList,userId,categoriesList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券编辑失败，请联系技术人员！");
        }
    }

    /**
     * 逻辑删除
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ReturnData deleteCoupon(Long couponId){
        try {
            couponService.logicDelete(couponId);
            return ReturnData.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券删除失败，请联系技术人员！");
        }
    }

    /**
     * 后台优惠券管理列表
     * @param couponName
     * @param couponType
     * @param currentPage
     * @param pageSize
     * @param sort
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getAllCoupon(String couponName, Byte couponType, String currentPage, String pageSize,String sort){
        Pagination page = new Pagination<>();
        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList<>();
        try {
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            if (StringUtils.isNotBlank(couponName)){
                filters.add(Condition.like("couponName",couponName));
            }
            if (couponType != null){
                filters.add(Condition.eq("couponType",couponType));
            }
            filters.add(Condition.eq("isDeleted",(byte)0));
            if (StringUtils.isNotBlank(sort)){
                orderBys.add(OrderBy.desc(sort));
            }else {
                orderBys.add(OrderBy.asc("expiryEndDate"));
            }
            page = couponService.getListInPage(pageNumberNew,pageSizeNew,filters,orderBys);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 优惠券明细
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/getCouponById", method = RequestMethod.GET)
    public ReturnData getCouponById(Long couponId){
        try {
            Coupon coupon = couponService.selectById(couponId);
            return ReturnData.success(coupon);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     *用户领取优惠券明细
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserCouponById", method = RequestMethod.GET)
    public ReturnData getUserCouponById(String userId){
        try {
            return couponService.getUserCouponById(userId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 优惠券 用户 明细列表
     * @param userId
     * @param couponId
     * @param userCouponStatus
     * @param orderSn
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getCouponDetail", method = RequestMethod.GET)
    public ReturnData getCouponReceivedDetail(String userId, Long couponId, Byte userCouponStatus, String orderSn,
                                              String currentPage, String pageSize){
        try {
            Byte couponTag = null;
            Pagination page = couponService.getCouponReceivedDetail(userId,couponId,userCouponStatus,orderSn,currentPage,pageSize,couponTag);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }

    /**
     * 后台 用户优惠券数据列表
     * @param user
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getUserCouponList", method = RequestMethod.GET)
    public ReturnData getUserCouponList(User user, String currentPage, String pageSize){
        try {
            Pagination page = couponService.getUserCouponList(user,currentPage,pageSize);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("");
        }
    }
}
