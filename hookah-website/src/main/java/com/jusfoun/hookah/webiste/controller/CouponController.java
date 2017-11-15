package com.jusfoun.hookah.webiste.controller;

import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.UserCoupon;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.CouponService;
import com.jusfoun.hookah.rpc.api.UserCouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lt on 2017/11/7.
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
    @Resource
    private UserCouponService userCouponService;

    @Resource
    private CouponService couponService;

    @RequestMapping(value = "/getCouponByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getCouponByUserId(String currentPage, String pageSize, Byte userCouponStatus, Byte couponTag){
        try {
            String userId =this.getCurrentUser().getUserId();
            Long couponId = null;
            String orderSn = null;
            Pagination page = couponService.getCouponByUserId(userId,couponId,userCouponStatus,orderSn,currentPage,pageSize,couponTag);
            return ReturnData.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("");
            return ReturnData.error(e.getMessage());
        }
    }
}
