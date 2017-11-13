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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/11/7.
 */


@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
    @Resource
    private UserCouponService userCouponService;

//    @Resource
//    private CouponService couponService;
//
//    @RequestMapping("/getAllList")
//    @ResponseBody
//    public ReturnData getAllList(){
//        try {
////            List<Coupon> coupons = couponService.selectList();
//            return ReturnData.success(coupons);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ReturnData.error(e.getMessage());
//        }
//    }
@RequestMapping("/getAllCoupon")
@ResponseBody
public ReturnData getAllCoupon(String currentPage, String
        pageSize, Short userCouponStatus){
    ReturnData returnData = new ReturnData<>();
    returnData.setCode(ExceptionConst.Success);
    try {
        String userId =this.getCurrentUser().getUserId();
        int pageNumberNew = HookahConstants.PAGE_NUM;
        if
                (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank
                (currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }

        int pageSizeNew = HookahConstants.PAGE_SIZE;
        if
                (com.jusfoun.hookah.core.utils.StringUtils.isNotBlank(pageSize))
        {
            pageSizeNew = Integer.parseInt(pageSize);
        }
        PageHelper.startPage(pageNumberNew, pageSizeNew);

        List<Condition> filters = new ArrayList();
        List<OrderBy> orderBys = new ArrayList();
        orderBys.add(OrderBy.desc("addTime"));

        if (userCouponStatus!=null) {
            filters.add(Condition.like
                    ("userCouponStatus",userCouponStatus));
        }
        if (userId!=null) {
            filters.add(Condition.eq("userId",userId));
        }
        if (StringUtils.isNotBlank(currentPage)) {
            pageNumberNew = Integer.parseInt(currentPage);
        }

        if (StringUtils.isNotBlank(pageSize)) {
            pageSizeNew = Integer.parseInt(pageSize);
        }
        Pagination<UserCoupon> page =
                userCouponService.getListInPage(pageNumberNew, pageSizeNew,
                        filters, orderBys);
        returnData.setData(page);
    } catch (Exception e) {
        returnData.setCode(ExceptionConst.Error);
        returnData.setMessage(e.toString());
        e.printStackTrace();
    }
    return returnData;
}
}
