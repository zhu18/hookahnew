package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.vo.HomeVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ctp
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderInfoService orderInfoService;

    //商品未删除
    public static final Byte IS_DEL_NO = 1;

    //是否由管理员新建(1：是)
    private static final  String IS_CATSIGN_YES = "1";

    @Override
    public ReturnData init() {
        ReturnData returnData = new ReturnData();
        returnData.setCode(ExceptionConst.Success);
        HomeVo homeVo = new HomeVo();
        try {
            //已注册会员数量
            List<Condition> userFifters = new ArrayList<Condition>();
            homeVo.setUserCount(userService.count(userFifters));

            //已注册未审核会员数量
            userFifters.add(Condition.in("userType",new Integer[]{3,5}));
            homeVo.setPendCheckUserCount(userService.count(userFifters));

            //已认证的会员数量
            List<Condition> userFifters1 = new ArrayList<Condition>();
            userFifters1.add(Condition.in("userType",new Integer[]{2,4}));
            homeVo.setRealUserCount(userService.count(userFifters1));

            //当前已上架商品总数
            List<Condition> goodsFifters = new ArrayList<Condition>();
            goodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
            goodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
            goodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
            homeVo.setAddedGoodsCount(goodsService.count(goodsFifters));

            //当前已下架商品总数
            List<Condition> goodsFifters1 = new ArrayList<Condition>();
            goodsFifters1.add(Condition.in("isOnsale", new Byte[]{Byte.valueOf(HookahConstants.SaleStatus.off.getCode()),
                    Byte.valueOf(HookahConstants.SaleStatus.forceOff.getCode())}));
            homeVo.setOffGoodsCount(goodsService.count(goodsFifters1));

            //当前商品一级分类个数
            List<Condition> categoryFifters = new ArrayList<Condition>();
            categoryFifters.add(Condition.eq("level",(byte)1));
            categoryFifters.add(Condition.eq("catSign",IS_CATSIGN_YES));
            homeVo.setGoodsOneCategoryCount(categoryService.count(categoryFifters));

            //未支付订单
            Map<String,Integer> orderMap = orderInfoService.getOrderCount();
            homeVo.setOrderNotPay(orderMap == null?0:orderMap.get("notPay"));
            //已支付订单
            homeVo.setOrderPaid(orderMap == null?0:orderMap.get("paid"));
            //已经删除的未支付订单
            homeVo.setOrderIsDeleted(orderMap == null?0:orderMap.get("isDelete"));
            //购买人数
            homeVo.setOrderUserCount(orderMap == null?0:orderMap.get("userCount"));

            returnData.setData(homeVo);
        }catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            returnData.setData(homeVo);
            e.printStackTrace();
        }
        return returnData;
    }

}
