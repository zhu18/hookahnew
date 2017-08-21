package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.MgOrderInfoService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Controller
public class ShowController {
    @Resource
    GoodsService goodsService;

    @Resource
    UserService userService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @Resource
    OrderInfoService orderInfoService;

    //商品未删除
    public static final Byte IS_DEL_NO = 1;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show() {
        return "show/demo";
    }


    //上架商品数量
    @ResponseBody
    @RequestMapping(value = "/show/goodsCount", method = RequestMethod.GET)
    public ReturnData goodsCount(){
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
        long off = goodsService.count(offGoodsFifters);
        long api = goodsService.count(apiGoodsFifters);
        long mxGoods = goodsService.count(mxGoodsFifters);
        long gjGoods = goodsService.count(gjGoodsFifters);
        long gjSaasGoods = goodsService.count(gjSaasGoodsFifters);
        long cjGoods = goodsService.count(cjGoodsFifters);
        long cjSaasGoods = goodsService.count(cjSaasGoodsFifters);
        List list = new ArrayList();
        Map offMap = new HashMap<>(5);
        offMap.put("name","离线数据");
        offMap.put("value",off);
        list.add(offMap);

        Map apiMap = new HashMap<>(5);
        apiMap.put("name","api数据");
        apiMap.put("value",api);
        list.add(apiMap);

        Map mxMap = new HashMap<>(5);
        mxMap.put("name","数据模型");
        mxMap.put("value",mxGoods);
        list.add(mxMap);

        Map gjMap = new HashMap<>(5);
        gjMap.put("name","分析工具--独立软件");
        gjMap.put("value",gjGoods);
        list.add(gjMap);

        Map gjSaasMap = new HashMap<>(5);
        gjSaasMap.put("name","分析工具--SaaS");
        gjSaasMap.put("value",gjSaasGoods);
        list.add(gjSaasMap);

        Map cjMap = new HashMap<>(5);
        cjMap.put("name","应用场景--独立软件");
        cjMap.put("value",cjGoods);
        list.add(cjMap);

        Map cjSaasMap = new HashMap<>(5);
        cjSaasMap.put("name","应用场景--SaaS");
        cjSaasMap.put("value",cjSaasGoods);
        list.add(cjSaasMap);

        Map map1 = new HashMap<>(5);
        List goodCountList = new ArrayList();
        map1.put("value",goods);
        map1.put("name","上架商品数量");
        goodCountList.add(map1);

        List list1 = new ArrayList();
        list1.add(list);
        list1.add(goodCountList);
        return ReturnData.success(list1);
    }

    //用户注册数
    @ResponseBody
    @RequestMapping(value = "/show/userCountList", method = RequestMethod.GET)
    public ReturnData userCount(String userId){
        List<Condition> userFilters = new ArrayList<>();
        List<Condition> companyAuthFilters = new ArrayList<>();
        List<Condition> authFilters = new ArrayList<>();
        List<Condition> noFilters = new ArrayList<>();
        //用户注册数
        userFilters.add(Condition.eq("userId",userId));
        //企业认证数
        companyAuthFilters.add(Condition.eq("userType", 4));
        //个人认证数---暂不展示
        authFilters.add(Condition.eq("userType", 2));
        //未认证数
        noFilters.add(Condition.eq("userType", 1));

        long user = userService.count(userFilters);
        long companyAuth = userService.count(companyAuthFilters);
        long auth =userService.count(authFilters);
        long no = userService.count(noFilters);
        List list = new ArrayList();
        Map map1 = new HashMap<>(5);
        map1.put("value",user);
        map1.put("name","用户注册数");
        Map map2 = new HashMap<>(5);
        map2.put("value",companyAuth);
        map2.put("name","企业认证用户");
        Map map3 = new HashMap<>(5);
        map3.put("value",auth);
        map3.put("name","个人认证用户");
        Map map4 = new HashMap<>(5);
        map4.put("value",no);
        map4.put("name","未认证用户");
        list.add(map2);
        list.add(map3);
        list.add(map4);
        List userRegList = new ArrayList();
        userRegList.add(map1);

        List list1 = new ArrayList();
        list1.add(list);
        list1.add(userRegList);
        return ReturnData.success(list1);
    }

    //订单数量
    @ResponseBody
    @RequestMapping(value = "/show/orderInfoCount", method = RequestMethod.GET)
    public ReturnData orderInfoCount(){
        Map map = new HashMap<>(5);
        List<Condition> orderSumFilters = new ArrayList<>();
        List<Condition> paidFilters = new ArrayList<>();
        List<Condition> unpaidFilters = new ArrayList<>();
        List<Condition> tradeGoodsFilters = new ArrayList<>();
        //交易订单总笔数
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
        //未删除的已付款订单
        paidFilters.add(Condition.eq("payStatus", 2));
        paidFilters.add(Condition.eq("isDeleted",0));
        //未删除的未付款订单
        unpaidFilters.add(Condition.ne("payStatus", 2));
        unpaidFilters.add(Condition.eq("isDeleted",0));

        long paid = orderInfoService.count(paidFilters);
        long unpaid = orderInfoService.count(unpaidFilters);
        long orderSum = orderInfoService.count(orderSumFilters);
        List list = new ArrayList();
        Map paidMap = new HashMap<>(5);
        List amountList = new ArrayList();
        paidMap.put("value",paid);
        paidMap.put("name","已支付订单");
        list.add(paidMap);

        Map unpaidMap = new HashMap<>(5);
        unpaidMap.put("value",unpaid);
        unpaidMap.put("name","未支付订单");
        list.add(unpaidMap);

        Map orderSumMap = new HashMap<>(5);
        orderSumMap.put("value",orderSum);
        orderSumMap.put("name","交易订单总笔数");
        amountList.add(orderSumMap);

        Map goodsMap = new HashMap<>(5);
        goodsMap.put("value",goodsList.size());
        goodsMap.put("name","交易商品总数");
        amountList.add(goodsMap);

        List list1 = new ArrayList();
        list1.add(list);
        list1.add(amountList);
        return ReturnData.success(list1);
    }
}
