package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by lt on 2017/11/7.
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    @Resource
    private CouponService couponService;

    @RequestMapping("/getAllList")
    @ResponseBody
    public ReturnData getAllList(){
        try {
            List<Coupon> coupons = couponService.selectList();
            return ReturnData.success(coupons);
        }catch (Exception e){
            e.printStackTrace();
            return ReturnData.error(e.getMessage());
        }
    }
}
