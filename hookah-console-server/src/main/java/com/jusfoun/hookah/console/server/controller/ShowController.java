package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.domain.vo.ShowVO;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhaoshuai
 * Created by computer on 2017/7/4.
 */
@RestController
@RequestMapping("/show")
public class ShowController {

    @Resource
    ShowService showService;

    @Resource
    UserService userService;

    @Resource
    GoodsService goodsService;

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @Resource
    RedisOperate redisOperate;

    //商品未删除
    public static final Byte IS_DEL_NO = 1;

    //日注册用户
    @RequestMapping("/registerUserCount")
    public ReturnData registerUser(){
        List<ShowVO> registerUserCount = showService.getRegisterUserCount();
        return ReturnData.success(registerUserCount);
    }

    //日活跃用户
    @RequestMapping("/activeUserCount")
    public ReturnData activeUser(){
        List<ShowVO> activeUserCount = showService.getActiveUserCount();
        return ReturnData.success(activeUserCount);
    }

    //日交易金额
    @RequestMapping("/payAmount")
    public ReturnData transactionAmount(){
        List<ShowVO> amount = showService.getPayAmount();
        return ReturnData.success(amount);
    }

    //待支付金额
    @RequestMapping("/unPayAmount")
    public ReturnData unTransactionAmount(){
        List<ShowVO> amount = showService.getUnPayAmount();
        return ReturnData.success(amount);
    }

    //注册用户地域分布
    @RequestMapping("/userArea")
    public ReturnData userArea(){
        List<ShowVO> area = showService.userArea();
        return ReturnData.success(area);
    }


    //用户注册数
    @RequestMapping("/userCountList")
    public ReturnData userCount(String userId){
        Map map = new HashMap<>(5);
        List<Condition> userFilters = new ArrayList<>();
        List<Condition> companyAuthFilters = new ArrayList<>();
        List<Condition> authFilters = new ArrayList<>();
        List<Condition> noFilters = new ArrayList<>();
        //用户注册数
        userFilters.add(Condition.eq("userId",userId));
        //企业认证数
        companyAuthFilters.add(Condition.eq("userId",userId));
        companyAuthFilters.add(Condition.eq("userType", 4));
        //个人认证数---暂不展示
        authFilters.add(Condition.eq("userId",userId));
        authFilters.add(Condition.eq("userType", 2));
        //未认证数
        noFilters.add(Condition.eq("userId",userId));
        noFilters.add(Condition.eq("userType", 2));

        long user = userService.count(userFilters);
        long companyAuth = userService.count(companyAuthFilters);
        long auth =userService.count(authFilters);
        long no = userService.count(noFilters);
        map.put("userCount",user);
        map.put("companyAuthCount",companyAuth);
        map.put("authCount",auth);
        map.put("noCount",no);
        return ReturnData.success(map);
    }

    //上架商品数量
    @RequestMapping("/goodsCount")
    public ReturnData goodsCount(){
        Map map = new HashMap<>(5);
        List<Condition> goodsFifters = new ArrayList<Condition>();
        List<Condition> offGoodsFifters = new ArrayList<Condition>();
        List<Condition> apiGoodsFifters = new ArrayList<Condition>();
        List<Condition> mxGoodsFifters = new ArrayList<Condition>();
        List<Condition> gjGoodsFifters = new ArrayList<Condition>();
        List<Condition> gjSaasGoodsFifters = new ArrayList<Condition>();
        List<Condition> cjGoodsFifters = new ArrayList<Condition>();
        List<Condition> cjSaasGoodsFifters = new ArrayList<Condition>();
        //当前已上架商品总数
        goodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        goodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        goodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        //商品类型: 0 离线数据；1 api；2 数据模型；4 分析工具--独立软件；5 分析工具--SaaS；6 应用场景--独立软件； 7 应用场景--SaaS
        //0.离线数据数量
        offGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        offGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        offGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        offGoodsFifters.add(Condition.eq("goodsType", 0));
        //1.API数据数量
        apiGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        apiGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        apiGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        apiGoodsFifters.add(Condition.eq("goodsType", 1));
        //2.数据模型数量
        mxGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        mxGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        mxGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        mxGoodsFifters.add(Condition.eq("goodsType", 2));
        //4分析工具--独立软件数量
        gjGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        gjGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        gjGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        gjGoodsFifters.add(Condition.eq("goodsType", 4));
        //5分析工具--SaaS数量
        gjSaasGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        gjSaasGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        gjSaasGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        gjSaasGoodsFifters.add(Condition.eq("goodsType", 5));
        //6应用场景--独立软件数量
        cjGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        cjGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        cjGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        cjGoodsFifters.add(Condition.eq("goodsType", 6));
        //7应用场景--SaaS数量
        cjSaasGoodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        cjSaasGoodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        cjSaasGoodsFifters.add(Condition.eq("isDelete", IS_DEL_NO));
        cjSaasGoodsFifters.add(Condition.eq("goodsType", 7));

        long goods = goodsService.count(goodsFifters);
        long api = goodsService.count(apiGoodsFifters);
        long mxGoods = goodsService.count(mxGoodsFifters);
        long gjGoods = goodsService.count(gjGoodsFifters);
        long gjSaasGoods = goodsService.count(gjSaasGoodsFifters);
        long cjGoods = goodsService.count(cjGoodsFifters);
        long cjSaasGoods = goodsService.count(cjSaasGoodsFifters);
        map.put("goodsCount",goods);
        map.put("apiCount",api);
        map.put("mxGoodsCount",mxGoods);
        map.put("gjGoodsCount",gjGoods);
        map.put("gjSaasCount",gjSaasGoods);
        map.put("cjGoodsCount",cjGoods);
        map.put("cjSaasCount",cjSaasGoods);

        return ReturnData.success(map);
    }

    //订单数量
    @RequestMapping("/orderInfoCount")
    public ReturnData orderInfoCount(String userId){
        Map map = new HashMap<>(5);
        List<Condition> orderSumFilters = new ArrayList<>();
        List<Condition> paidFilters = new ArrayList<>();
        List<Condition> unpaidFilters = new ArrayList<>();
        List<Condition> tradeGoodsFilters = new ArrayList<>();
        //交易订单总笔数
        orderSumFilters.add(Condition.eq("userId", userId));
        orderSumFilters.add(Condition.eq("isDeleted",0));
        //交易商品总数
        tradeGoodsFilters.add(Condition.eq("isDeleted",0));
        tradeGoodsFilters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(tradeGoodsFilters);
        List<MgOrderGoods> goodsList = orderList.stream().parallel()
                .flatMap(new Function<OrderInfoVo, Stream<MgOrderGoods>>() {
                    @Override
                    public Stream<MgOrderGoods> apply(OrderInfoVo t) {
                        List<MgOrderGoods> list = t.getMgOrderGoodsList().stream().collect(Collectors.toList());
                        list.forEach(goods->{goods.setPayTime(t.getPayTime());goods.setOrderSn(t.getOrderSn());});
                        return list.stream();
                    }
                })
                .collect(Collectors.toList());
        /*int count = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                count++;
            }
        }*/
        //未删除的已付款订单
        paidFilters.add(Condition.eq("userId", userId));
        paidFilters.add(Condition.eq("payStatus", 2));
        paidFilters.add(Condition.eq("isDeleted",0));
        //未删除的未付款订单
        unpaidFilters.add(Condition.eq("userId", userId));
        unpaidFilters.add(Condition.ne("payStatus", 2));
        unpaidFilters.add(Condition.eq("isDeleted",0));

        long paid = orderInfoService.count(paidFilters);
        long unpaid = orderInfoService.count(unpaidFilters);
        long orderSum = orderInfoService.count(orderSumFilters);
        map.put("paidCount",paid);
        map.put("unpaidCount",unpaid);
        map.put("orderSumCount",orderSum);
        map.put("goodsCount",goodsList.size());

        return ReturnData.success(map);
    }

    /**pu vu趋势分析
     * pu页面浏览量  uv独立访问量
     */
    @RequestMapping("/viewsCount")
    public ReturnData viewsCount(){
        Map<String, Object> map = userService.getPUVCountByDate();
        //System.out.println("日期" + map.get("puvdate"));
        System.out.println("PV" + map.get("pvdata"));
        System.out.println("UV" + map.get("uvdata"));
        return ReturnData.success(map);
    }

    //商品类型交易金额
    @RequestMapping("/typeAmount")
    public ReturnData typeAmount(){
        Map map = new HashMap<>(5);
        map.put("离线商品",offType().getData());
        map.put("API商品",apiType().getData());
        map.put("数据模型",modelType().getData());
        map.put("分析工具",toolType().getData());
        map.put("分析工具-SaaS",toolSaasType().getData());
        map.put("应用场景",scenesType().getData());
        map.put("应用场景--SaaS",scenesSaasType().getData());
        return ReturnData.success(map);
    }

    //离线商品类型交易金额
    public ReturnData offType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() == 0){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","离线商品");
        return ReturnData.success(map);
    }

    //API商品类型交易金额
    public ReturnData apiType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() ==1){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","api商品");
        return ReturnData.success(map);
    }

    //数据模型商品类型交易金额
    public ReturnData modelType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() ==2){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","数据模型商品");

        return ReturnData.success(map);
    }

    //分析工具商品类型交易金额
    public ReturnData toolType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() ==4){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","分析工具商品");
        return ReturnData.success(map);
    }

    //分析工具-SaaS商品类型交易金额
    public ReturnData toolSaasType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() ==5){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","分析工具-SaaS商品");
        return ReturnData.success(map);
    }

    //应用场景--独立软件商品类型交易金额
    public ReturnData scenesType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() == 6){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","应用场景--独立软件商品");
        return ReturnData.success(map);
    }

    //应用场景--SaaS商品类型交易金额
    public ReturnData scenesSaasType(){
        Map map = new HashMap<>(5);
        List<Condition> filters = new ArrayList<Condition>();
        filters.add(Condition.eq("isDeleted",0));
        filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));
        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters);
        long price = 0;
        for (OrderInfoVo order:orderList) {
            List<MgOrderGoods> mgOrderGoodsList = order.getMgOrderGoodsList();
            for (MgOrderGoods mgOrder: mgOrderGoodsList){
                if(mgOrder.getGoodsType() == 7){
                    price +=  mgOrder.getGoodsPrice();
                }
            }
        }
        map.put("price",price);
        map.put("goodsType","应用场景--SaaS商品");
        return ReturnData.success(map);
    }
}
