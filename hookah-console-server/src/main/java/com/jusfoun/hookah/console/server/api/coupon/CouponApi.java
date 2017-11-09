package com.jusfoun.hookah.console.server.api.coupon;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Category;
import com.jusfoun.hookah.core.domain.Coupon;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lt on 2017/11/8.
 */
@Controller
@RequestMapping("/api/coupon")
public class CouponApi extends BaseController {
    @Resource
    private CouponService couponService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData addCoupon(Coupon coupon, String goodsList, String categoriesList){
        try {
            return couponService.addCoupon(coupon,goodsList,categoriesList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券添加失败，请联系技术人员！");
        }
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData modifyCoupon(Coupon coupon, String goodsList, String categoriesList){
        try {
            return couponService.modify(coupon,goodsList,categoriesList);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券编辑失败，请联系技术人员！");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData deleteCoupon(Long couponId){
        try {
            couponService.delete(couponId);
            return ReturnData.success();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return ReturnData.error("优惠券删除失败，请联系技术人员！");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
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
            if (StringUtils.isNotBlank(sort)){
                orderBys.add(OrderBy.desc(sort));
            }else {
                orderBys.add(OrderBy.asc("expiryEndDate"));
            }
            page = couponService.getListInPage(pageNumberNew,pageSizeNew,filters,orderBys);
            return ReturnData.success(page);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("");
            return ReturnData.error("");
        }
    }

    @RequestMapping(value = "/getCouponDetail", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getCouponDetail(Integer couponId){
        try {
            couponService.getDetailById(couponId);
        }catch (Exception e){

        }
        return null;
    }


}
