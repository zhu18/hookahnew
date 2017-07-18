package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.OrderInfoMapper;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.OrderHelper;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.core.utils.FormatCheckUtil;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.http.HttpException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;

/**
 * 订单基本信息
 * @author:jsshao
 * @date: 2017-3-17
 */
public class OrderInfoServiceImpl extends GenericServiceImpl<OrderInfo, String> implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderinfoMapper;

    @Resource
    private CartService cartService;

    @Resource
    private GoodsService goodsService;

    @Resource
    MgGoodsService mgGoodsService;

    @Resource
    MgOrderInfoService mgOrderInfoService;

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    PayCoreService payCoreService;

    @Resource
    UserService userService;

    @Resource
    UserDetailService userDetailService;

    @Resource
    OrganizationService organizationService;

    @Resource
    public void setDao(OrderInfoMapper orderinfoMapper) {
        super.setDao(orderinfoMapper);
    }

    private OrderInfo init(OrderInfo orderinfo) {
        Date date = new Date();
        orderinfo.setOrderSn(OrderHelper.genOrderSn());
        orderinfo.setOrderStatus(OrderInfo.ORDERSTATUS_CONFIRM);
        orderinfo.setShippingStatus(0);
        orderinfo.setShippingId("");
        orderinfo.setShippingName("");
        orderinfo.setPayId("0");
        orderinfo.setPayStatus(0);
        orderinfo.setPayName("账户余额");
        orderinfo.setHowOos("");
        orderinfo.setHowSurplus("");
        orderinfo.setPackName("");
        orderinfo.setCardName("");
        orderinfo.setCardMessage("");
        orderinfo.setGoodsAmount(0L);
        orderinfo.setShippingFee(0L);
        orderinfo.setInsureFee(0L);
        orderinfo.setPayFee(0L);
        orderinfo.setPackFee(0L);
        orderinfo.setCardFee(0L);
        orderinfo.setGoodsDiscountFee(0L);
        orderinfo.setMoneyPaid(0L);
        orderinfo.setSurplus(0L);
        orderinfo.setIntegral(0);
        orderinfo.setIntegralMoney(0L);
        orderinfo.setBonus(0L);
        orderinfo.setOrderAmount(0L);
        orderinfo.setFromAd(0);
//		orderinfo.setReferer("管理员添加");
        orderinfo.setAddTime(date);
        orderinfo.setConfirmTime(date);
        orderinfo.setPayTime(date);
        orderinfo.setShippingTime(date);
        orderinfo.setPackId("");
        orderinfo.setCardId("");
        orderinfo.setBonusId("");
        orderinfo.setInvoiceNo("");
        orderinfo.setExtensionCode("");
        orderinfo.setExtensionId("");
        orderinfo.setToBuyer("");
        orderinfo.setPayNote("");
        orderinfo.setAgencyId(0);
        orderinfo.setParentId("");
        orderinfo.setTax(0L);
        orderinfo.setIsSeparate(0);
        orderinfo.setDiscount(0L);
        orderinfo.setCallbackStatus("true");
        orderinfo.setLastmodify(date);
        orderinfo.setEmail("");
        orderinfo.setIsDeleted((byte)0);
        orderinfo.setCommentFlag(0);
        return orderinfo;
    }

    /**
     * 购物车生成订单
     * @param cart
     * @param orderInfo
     * @return
     * @author jsshao
     */
    private MgOrderGoods getMgOrderGoodsByCart(CartVo cart,OrderInfo orderInfo) {
        MgOrderGoods og = new MgOrderGoods();
        BeanUtils.copyProperties(cart.getGoods(),og);
        og.setDiscountFee(0L);
        og.setIsReal(0);
        og.setMarketPrice(cart.getMarketPrice());
        og.setGoodsPrice(cart.getGoodsPrice());
        og.setOrderSn(orderInfo.getOrderSn());
        og.setPayTime(orderInfo.getPayTime());
        og.setGoodsFormat(cart.getGoodsFormat());
        og.setFormatNumber(cart.getFormatNumber());
        og.setFormatList(cart.getGoods().getFormatList());
        og.setGoodsNumber(cart.getGoodsNumber().intValue());
        String addUser = cart.getGoods().getAddUser();
        User user = userService.selectById(addUser);
        og.setAddUser(user.getUserName());
        Organization organization = organizationService.selectById(user.getOrgId());
        og.setSupplier(organization.getOrgName());
        og.setFormatId(cart.getFormatId());
        og.setSourceId(cart.getGoods().getSourceId());
        og.setGoodsType(cart.getGoods().getGoodsType());
        og.setIsOnsale(cart.getGoods().getIsOnsale());
//        og.setAtAloneSoftware(cart.getGoods().getAtAloneSoftware());
//        og.setAsAloneSoftware(cart.getGoods().getAsAloneSoftware());
//        og.setAtSaaS(cart.getGoods().getAtSaaS());
//        og.setAsSaaS(cart.getGoods().getAsSaaS());
        og.setRemark("");
        og.setIsOffline(cart.getGoods().getIsOffline());
        og.setOffLineInfo(cart.getGoods().getOffLineInfo());
        og.setOffLineData(cart.getGoods().getOffLineData());
        og.setDataModel(cart.getGoods().getDataModel());
        og.setPayInfoFileUrl("");
        og.setPayInfoPassword("");
        og.setPayInfoSerialNumber("");
        og.setPayInfoUserName("");
        switch (cart.getGoods().getGoodsType()){
            case 4:case 5:case 6:case 7:
                if (cart.getGoods().getIsOffline()==0){
                    og.setSolveStatus(2);
                }else {
                    og.setSolveStatus(0);
                }
                break;
            default:
                og.setSolveStatus(0);
        }
//		og.setSendNumber(cart.getS);
        return og;
    }

    /**
     * 直接购买生成订单
     * @param goods
     * @param format
     * @param goodsNumber
     * @param orderInfo
     * @return
     * @author jsshao
     */
    private MgOrderGoods getMgOrderGoodsByGoodsFormat(GoodsVo goods, MgGoods.FormatBean format, Long goodsNumber,OrderInfo orderInfo) {
        MgOrderGoods og = new MgOrderGoods();
        og.setDiscountFee(0L);
        BeanUtils.copyProperties(goods,og);

        og.setGoodsName(goods.getGoodsName());
        og.setGoodsNumber(goodsNumber.intValue());
        og.setGoodsType(goods.getGoodsType());
        String addUser = goods.getAddUser();
        User user = userService.selectById(addUser);
        og.setAddUser(user.getUserName());
        Organization organization = organizationService.selectById(user.getOrgId());
        og.setSupplier(organization.getOrgName());
        og.setIsOnsale(goods.getIsOnsale());
        og.setSourceId(goods.getSourceId());
        og.setGoodsPrice(format.getPrice());
        og.setGoodsFormat(format.getFormat());
        og.setFormatId(format.getFormatId());
        og.setFormatNumber((long)format.getNumber());
        og.setIsGift(new Integer(0).shortValue());
        og.setIsReal(0);
        og.setPayTime(orderInfo.getPayTime());
        og.setOrderSn(orderInfo.getOrderSn());
//        og.setAtAloneSoftware(goods.getAtAloneSoftware());
//        og.setAsAloneSoftware(goods.getAsAloneSoftware());
//        og.setAtSaaS(goods.getAtSaaS());
//        og.setAsSaaS(goods.getAsSaaS());
        og.setRemark("");
        og.setIsOffline(goods.getIsOffline());
        og.setOffLineInfo(goods.getOffLineInfo());
        og.setOffLineData(goods.getOffLineData());
        og.setDataModel(goods.getDataModel());
        og.setPayInfoFileUrl("");
        og.setPayInfoPassword("");
        og.setPayInfoSerialNumber("");
        og.setPayInfoUserName("");
        switch (goods.getGoodsType()){
            case 4:case 5:case 6:case 7:
                if (goods.getIsOffline()==0){
                    og.setSolveStatus(2);
                }else {
                    og.setSolveStatus(0);
                }
                break;
            default:
                og.setSolveStatus(0);
        }
        //og.setMarketPrice(goods.getShopPrice());
//		og.setSendNumber(cart.getS);
        return og;
    }

    @Override
    public void deleteByLogic(String id) {
        OrderInfo order = new OrderInfo();
        order.setOrderId(id);
        order.setIsDeleted(new Byte("1"));
        updateByIdSelective(order);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrderId(id);
        orderInfoVo.setIsDeleted((byte)1);
        mgOrderInfoService.updateByIdSelective(orderInfoVo);
    }

    @Override
    public void deleteBatchByLogic(String[] ids) {
        OrderInfo order = new OrderInfo();
        order.setIsDeleted(new Byte("1"));
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("orderId", ids));
        updateByConditionSelective(order,filters);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setIsDeleted((byte)1);
        mgOrderInfoService.updateByCondition(orderInfoVo,filters);
    }

    /**
     * 输入参数 为支付时间 的范围
     * 返回值  key为 goodsId， value为Long计数
     * @param startDate
     * @param endDate
     * @return
     * @throws HookahException
     */
    @Override
    public Map<String,Long> getOrderStatisticWithBuydate(Date startDate,Date endDate) throws HookahException{
        List<Condition> filters = new ArrayList<>(5); //预留几个空间
        filters.add(Condition.ge("payTime",startDate));
        filters.add(Condition.ge("payTime",endDate));
        filters.add(Condition.eq("payStatus",2));
        filters.add(Condition.eq("isDeleted",0));
        List<OrderInfo> orders = selectList(filters);
        filters.clear();
        String[] orderIds = new String[orders.size()];
        orders.forEach(item-> Arrays.fill(orderIds,item.getOrderId()));
        filters.add(Condition.in("orderId",orderIds));
        List<OrderInfoVo> vos = mgOrderInfoService.selectList(filters);
        Map<String, Long> goodsCount = vos.stream()
                .parallel()
                //.filter(order->order.getIsDeleted()!=1)   //不能是已经删除的
                .flatMap(new Function<OrderInfoVo, Stream<MgOrderGoods>>() {
                    @Override
                    public Stream<MgOrderGoods> apply(OrderInfoVo t) {
                        return t.getMgOrderGoodsList().stream();
                    }
                })
                .parallel()
                .map(goods -> new AbstractMap.SimpleEntry<>(goods.getGoodsId(), 1))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, counting()));
        return goodsCount;
    }

    @Override
    public MgOrderGoods getGoodsUserBuyed(String userId, String goodsId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("payStatus").is(OrderInfo.PAYSTATUS_PAYED));
        query.addCriteria(Criteria.where("isDeleted").is("1"));
        query.addCriteria(Criteria.where("orderGoodsList.goodsId").is(goodsId));
        OrderInfoVo orderInfoVo = mongoTemplate.findOne(query,OrderInfoVo.class);
        if(orderInfoVo!=null){
            return orderInfoVo.getMgOrderGoodsList().get(0);
        }
        return null;
    }


    /**
     * 购物车生成订单
     * @param orderInfo
     * @param cartIdArray
     * @return
     * @throws Exception
     */
    @Transactional(readOnly=false)
    @Override
    public OrderInfo insert(OrderInfo orderInfo,String[] cartIdArray) throws Exception {
        init(orderInfo);
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("recId",cartIdArray));
        List<CartVo> cartList = cartService.selectDetailList(filters);
        List<MgOrderGoods> ordergoodsList = null;
        if(cartList!=null&&cartList.size()>0){
            ordergoodsList = new ArrayList<MgOrderGoods>();
            Long goodsAmount = new Long(0);
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            for(CartVo cart:cartList){
                //验证商品是否下架
                Goods g = cart.getGoods();
                if(g.getIsOnsale()==null||g.getIsOnsale()!=1){
                    throw new HookahException("商品["+g.getGoodsName()+"]未上架");
                }

                goodsAmount += cart.getFormat().getPrice()  * cart.getGoodsNumber();  //商品单价 * 套餐内数量 * 购买套餐数量

                MgOrderGoods og = getMgOrderGoodsByCart(cart,orderInfo);
                ordergoodsList.add(og);
            }
            orderInfo.setGoodsAmount(goodsAmount);
            orderInfo.setOrderAmount(goodsAmount);

            insertOrder(ordergoodsList, orderInfoVo, orderInfo);
//            if(goodsAmount.compareTo(0L)==0){
//                updatePayStatus(orderInfo.getOrderSn(),2);
//            }

            //逻辑删除已经提交的购物车商品
            cartService.deleteBatchByLogic(cartIdArray);
        }
        return orderInfo;
    }

    /**
     * 直接购买生成订单
     * @param orderInfo
     * @param goodsId
     * @param formatId
     * @param goodsNumber
     * @return
     * @throws Exception
     */
    @Transactional(readOnly=false)
    @Override
    public OrderInfo insert(OrderInfo orderInfo, String goodsId, Integer formatId, Long goodsNumber) throws Exception {
        init(orderInfo);

        List<MgOrderGoods> ordergoodsList = null;

        ordergoodsList = new ArrayList<MgOrderGoods>();
        Long goodsAmount = new Long(0);

        //验证商品是否下架
        GoodsVo g = goodsService.findGoodsById(goodsId);
        if(g.getIsOnsale()==null||g.getIsOnsale()!=1){
            throw new HookahException("商品["+g.getGoodsName()+"]未上架");
        }
        MgGoods.FormatBean format= goodsService.getFormat(goodsId,formatId);
        if(goodsNumber!=null){
            goodsAmount += format.getPrice()  * goodsNumber;  //商品单价 * 套餐内数量 * 购买套餐数量
        }
        MgOrderGoods og = getMgOrderGoodsByGoodsFormat(g,format,goodsNumber,orderInfo);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        ordergoodsList.add(og);
        orderInfo.setGoodsAmount(goodsAmount);
        orderInfo.setOrderAmount(goodsAmount);

        insertOrder(ordergoodsList, orderInfoVo, orderInfo);
//        if(goodsAmount.compareTo(0L)==0){
//            updatePayStatus(orderInfo.getOrderSn(),2);
//        }

        return orderInfo;
    }

    public void insertOrder(List<MgOrderGoods> ordergoodsList, OrderInfoVo orderInfoVo, OrderInfo orderInfo){
        if(ordergoodsList != null && ordergoodsList.size() > 0){
            orderInfo = super.insert(orderInfo);
            BeanUtils.copyProperties(orderInfo,orderInfoVo);
            User user = userService.selectById(orderInfoVo.getUserId());
            orderInfoVo.setUserName(user.getUserName());
            orderInfoVo.setUserType(user.getUserType());
            orderInfoVo.setSolveStatus(0);
            for (MgOrderGoods mgOrderGoods:ordergoodsList){
                if (mgOrderGoods.getSolveStatus()==2){
                    orderInfoVo.setSolveStatus(2);
                }
            }
            if (user.getUserType() == 2){
                UserDetail userDetail = userDetailService.selectById(orderInfoVo.getUserId());
                orderInfoVo.setRealName(userDetail.getRealName());
            }else if (user.getUserType() == 4){
                Organization organization = organizationService.selectById(user.getOrgId());
                orderInfoVo.setRealName(organization.getOrgName());
            }
            orderInfoVo.setMgOrderGoodsList(ordergoodsList);

            mgOrderInfoService.insert(orderInfoVo);
        }
    }

    /**
     * 支付订单，修改支付状态为2（已支付），订单状态改为 5（完成）
     * 统计订单中商品的销量
     * 支付完成后，API类商品调用api平台接口，启用api调用跟踪
     * 支付完成后，api类商品保存api
     * @param orderSn
     * @param  payStatus
     * @throws HookahException
     */
    @Transactional(readOnly=false)
    @Override
    public void updatePayStatus(String orderSn, Integer payStatus, Integer payMode) throws Exception {
        logger.info("updatePayStatus status = {}",payStatus);
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("orderSn",orderSn));
        OrderInfo orderInfo =  selectOne(filters);

        String payName="账户余额";
        String[] payments = {"账户余额","支付宝","银联"};
        payName = Arrays.asList(payments).get(payMode);
        orderInfo.setPayId(payMode.toString());
        orderInfo.setPayName(payName);

        orderInfo.setPayTime(new Date());
        orderInfo.setLastmodify(new Date());
        orderInfo.setPayStatus(payStatus);

        updateByIdSelective(orderInfo);

        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(orderInfo.getOrderId());
        mgOrderInfoService.delete(orderInfo.getOrderId());

        orderInfoVo.setPayTime(new Date());
        orderInfoVo.setLastmodify(new Date());
        orderInfoVo.setPayStatus(payStatus);
        mgOrderInfoService.insert(orderInfoVo);

        //支付成功后,API类商品调用api平台接口，启用api调用跟踪
        //进行商品销量统计
        if(OrderInfo.PAYSTATUS_PAYED == payStatus){
            managePaySuccess(orderInfo);
//            countSales(orderInfo.getOrderId());
        }
        //        if(list!=null&&list.size()>0){
        //            mapper.updatePayStatus(orderSn,status);
        //        }else{
        //            throw new ShopException("无法设置支付状态");
        //        }

    }

    /**
     * 支付完成后进行商品销量统计
     * @param orderId
     */
    public void countSales(String orderId){
        try {
            OrderInfoVo orderInfoVo = findDetailById(orderId);
            orderInfoVo.getMgOrderGoodsList().stream().forEach(
                    mgOrderGoods -> {
                        MgGoods mgGoods = mgGoodsService.selectById(mgOrderGoods.getGoodsId());
                        long sales = mgGoods.getSales()+mgOrderGoods.getGoodsNumber();
                        mgGoods.setSales(sales);
                        mgGoodsService.updateByIdSelective(mgGoods);
                    });
        }catch (Exception e){
            e.printStackTrace();
            logger.error("统计商品销量错误"+orderId);
        }
    }

    /**
     * 支付完成后，API类商品调用api平台接口，启用api调用跟踪
     * 支付完成后，api类商品保存api
     * @param orderInfo
     * @throws HttpException
     * @throws IOException
     */
    private void managePaySuccess(OrderInfo orderInfo)  {
        try {
            OrderInfoVo orderInfoVo = findDetailById(orderInfo.getOrderId());
            //支付成功,
            //1、发送api平台
            String url = "http://open.galaxybigdata.com/shop/insert/userapi";
            String apiUrl = "192.168.15.90:5555/gateway/insert";
            List<Map> list = new ArrayList();
            List<Map> apiList = new ArrayList<>();

            orderInfoVo.getMgOrderGoodsList().stream()
                    .filter(g -> {
                        if (g.getGoodsType() == 1 && !StringUtils.isNotBlank(g.getSourceId())) {
                            logger.info("指定商品id{} 的sourceId为空", g.getGoodsId());
                        }
                        return g.getGoodsType() == 1 && StringUtils.isNotBlank(g.getSourceId());
                    })
                    .forEach(goods -> {
                        Map<String, String> param = new HashMap<>();
                        Map<String, Object> apiParam = new HashMap<>();
                        param.put("userId", orderInfoVo.getUserId());
                        //param.put("endTime",orderInfo.getUserId());
                        param.put("orderNo", orderInfo.getOrderSn());
                        param.put("apiId", goods.getSourceId());
                        param.put("goodsId", goods.getGoodsId());
                        param.put("totalCount", new Long(goods.getGoodsNumber() * goods.getFormatNumber()).toString());

//                        apiParam.put("totalCount", new Long(goods.getGoodsNumber() * goods.getFormatNumber()).toString());
//                        apiParam.put("userId", orderInfoVo.getUserId());
//                        apiParam.put("orderSn", orderInfoVo.getOrderSn());
//                        apiParam.put("goodsSn", goods.getOrderSn());
//                        apiParam.put("type", goods.getGoodsFormat());
//                        apiParam.put("url", goods.getApiInfo().getApiUrl());
                        list.add(param);
                    });
            Map rs = HttpClientUtil.PostMethod(url, JsonUtils.toJson(list));
            logger.info("订单{}商品放api平台返回信息：{}", orderInfo.getOrderId(),  JsonUtils.toJson(rs));
//            Map apiRs = HttpClientUtil.PostMethod(apiUrl,JsonUtils.toJson(list));
//            logger.info("订单{}API商品插入网管接口：{}", orderInfo.getOrderId(),  JsonUtils.toJson(apiRs));
        }catch (Exception e){
            e.printStackTrace();
            logger.error("发送商品到api平台发生异常");
        }
    }


    /**
     * 前台订单管理列表
     * @param pageNum
     * @param pageSize
     * @param filters
     * @param orderBys
     * @return
     */
    @Override
    public Pagination<OrderInfoVo> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                       List<OrderBy> orderBys) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        Page<OrderInfo> list =  (Page<OrderInfo>) super.selectList(filters,orderBys);
        Page<OrderInfoVo> page = new Page<OrderInfoVo>(pageNum,pageSize);
        for(OrderInfo order:list){
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            this.copyProperties(order,orderInfoVo,null);

            OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfoVo.getOrderId());
            List<MgOrderGoods> goodsList = mgOrder.getMgOrderGoodsList();
            if(goodsList!=null){
                //未支付订单处理
                if(order.getPayStatus()!=OrderInfo.PAYSTATUS_PAYED){
                    for(MgOrderGoods goods:goodsList){
                        goods.setUploadUrl(null);
                        try {
                            Goods curGoods = goodsService.findGoodsById(goods.getGoodsId());
                            goods.setIsOnsale(curGoods.getIsOnsale());
                        } catch (HookahException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }

            orderInfoVo.setMgOrderGoodsList(goodsList);
            page.add(orderInfoVo);
        }

        Pagination<OrderInfoVo> pagination = new Pagination<OrderInfoVo>();
        pagination.setTotalItems(list.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        logger.info(JsonUtils.toJson(pagination));
        return pagination;
    }

    @Override
    public Pagination<OrderInfoVo> getSoldOrderListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                          Byte goodsType, Date startTime, Date endTime, String userId){
        User user = userService.selectById(userId);
        String addUser = user.getUserName();
        if (!userId.equals("1")){
            filters.add(Condition.eq("orderGoodsList.addUser", addUser));
        }
        if (goodsType != null){
            filters.add(Condition.eq("orderGoodsList.goodsType", goodsType));
        }
        Pagination<OrderInfoVo> pagination = mgOrderInfoService.getSoldOrderList(pageNum, pageSize, filters, startTime, endTime);
        List<OrderInfoVo> orderInfoVos = pagination.getList();
        for (OrderInfoVo orderInfoVo:orderInfoVos){
            if (!userId.equals("1")){
                List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
                List<MgOrderGoods> goods = new ArrayList<MgOrderGoods>();
                for (MgOrderGoods mgOrderGoods : goodsList){
                    if (goodsType!=null && mgOrderGoods.getGoodsType().equals(goodsType) && mgOrderGoods.getAddUser().equals(addUser)){
                        goods.add(mgOrderGoods);
                    }else if (goodsType == null && mgOrderGoods.getAddUser().equals(addUser)){
                        goods.add(mgOrderGoods);
                    }
                }
                orderInfoVo.setMgOrderGoodsList(goods);
            }else {
                List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
                List<MgOrderGoods> goods = new ArrayList<MgOrderGoods>();
                for (MgOrderGoods mgOrderGoods : goodsList){
                    if (goodsType!=null && mgOrderGoods.getGoodsType().equals(goodsType)){
                        goods.add(mgOrderGoods);
                    }else if (goodsType == null){
                        goods.add(mgOrderGoods);
                    }
                }
                orderInfoVo.setMgOrderGoodsList(goods);
            }
        }
        return pagination;
    }

    @Override
    public Pagination<OrderInfoVo> getSoldOrderListByCondition(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                         Date startTime, Date endTime, String addUser){
        Pagination<OrderInfoVo> pagination = mgOrderInfoService.getSoldOrderList(pageNum, pageSize, filters, startTime, endTime);
        List<OrderInfoVo> orderInfoVos = pagination.getList();
        for (OrderInfoVo orderInfoVo:orderInfoVos) {
            List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
            List<MgOrderGoods> goods = new ArrayList<MgOrderGoods>();
            for (MgOrderGoods mgOrderGoods : goodsList) {
                if (addUser!=null && mgOrderGoods.getAddUser().equals(addUser)) {
                    goods.add(mgOrderGoods);
                }else if (addUser==null){
                    goods.add(mgOrderGoods);
                }
            }
            orderInfoVo.setMgOrderGoodsList(goods);
        }
        return pagination;
    }

    @Override
    public Pagination<MgOrderGoods> getGoodsListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys) {
        PageHelper.startPage(pageNum, pageSize);
        Page<MgOrderGoods> page = new Page<>(pageNum,pageSize);
        Pagination<MgOrderGoods> pagination = new Pagination<MgOrderGoods>();

        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters,orderBys);
        List<MgOrderGoods> goodsList = orderList.stream().parallel()
                .flatMap(new Function<OrderInfoVo, Stream<MgOrderGoods>>() {
                    @Override
                    public Stream<MgOrderGoods> apply(OrderInfoVo t) {
                        List<MgOrderGoods> list = t.getMgOrderGoodsList().stream().collect(Collectors.toList());
                        list.forEach(goods->{goods.setPayTime(t.getPayTime());goods.setOrderSn(t.getOrderSn());goods.setOrderId(t.getOrderId());});
                        return list.stream();
                    }
                })
                .collect(Collectors.toList());
        pagination.setTotalItems(goodsList.size());
        goodsList.stream().parallel()
                .skip((pageNum-1)*pageSize)
                .limit(pageSize)
                .forEach(goods->{page.add(goods);});
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        logger.info(JsonUtils.toJson(pagination));
        return pagination;
    }

    @Override
    public PayVo getPayParam(String orderId) {
        OrderInfo orderInfo = orderinfoMapper.selectByPrimaryKey(orderId);
        if(orderInfo!=null){
            PayVo payVo = new PayVo();
            payVo.setOrderSn(orderInfo.getOrderSn());
            payVo.setPayId(Integer.parseInt(orderInfo.getPayId()));
            BigDecimal totalFee = new BigDecimal(orderInfo.getGoodsAmount());
            payVo.setTotalFee(totalFee);
            try {
                OrderInfoVo infoVo = this.findDetailById(orderId);
                List<MgOrderGoods> orderGoodsList = infoVo.getMgOrderGoodsList();
                if(orderGoodsList!=null&&orderGoodsList.size()>0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < orderGoodsList.size(); i++) {
                        sb.append(orderGoodsList.get(i).getGoodsName());
                        if (i < orderGoodsList.size() - 1) {
                            sb.append(",");
                        }
                    }
                    payVo.setOrderTitle(sb.toString());
                }

            }catch (Exception e){
                e.printStackTrace();
            }
           /* MgOrderGoods t = new MgOrderGoods();
            t.setOrderId(orderId);
            List<MgOrderGoods> list = orderGoodsService.list(t );
            if(list!=null&&list.size()>0){
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<list.size();i++){
                    sb.append(list.get(i).getGoodsName());
                    if(i<list.size()-1){
                        sb.append(",");
                    }
                }
                payVo.setOrderTitle(sb.toString());
            }*/
            return payVo;
        }
        return null;
    }

    @Override
    public OrderInfoVo findDetailById(String orderId) throws HookahException {
        // TODO Auto-generated method stub
        OrderInfo order = super.selectById(orderId);

        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(order,orderInfoVo);
        OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfoVo.getOrderId());
        List<MgOrderGoods> goodsList = mgOrder.getMgOrderGoodsList();
        //未支付订单处理
        if(order.getPayStatus()!=OrderInfo.PAYSTATUS_PAYED){
            for(MgOrderGoods goods:goodsList){
                goods.setUploadUrl(null);
            }
        }

        orderInfoVo.setMgOrderGoodsList(goodsList);

        return orderInfoVo;
    }

    /**
     * 后台订单列表
     * @param pageNum
     * @param pageSize
     * @param filters
     * @return
     */
    @Override
    public Pagination<OrderInfoVo> getUserListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                     Date startTime, Date endTime) {
        // TODO Auto-generated method stub
//        PageHelper.startPage(pageNum, pageSize);
//        Page<OrderInfo> list =  (Page<OrderInfo>) super.selectList(filters,orderBys);
//        Page<OrderInfoVo> page = new Page<OrderInfoVo>(pageNum,pageSize);
//        for(OrderInfo order:list){
//            OrderInfoVo orderInfoVo = new OrderInfoVo();
//            this.copyProperties(order,orderInfoVo,null);
//
//            OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfoVo.getOrderId());
//            if (mgOrder != null){
//                User user = userService.selectById(orderInfoVo.getUserId());
//                orderInfoVo.setUserName(user.getUserName());
//                orderInfoVo.setUserType(user.getUserType());
//                orderInfoVo.setSolveStatus(mgOrder.getSolveStatus());
//                orderInfoVo.setRealName(mgOrder.getRealName());
//                page.add(orderInfoVo);
//            }
//        }
//
//        Pagination<OrderInfoVo> pagination = new Pagination<OrderInfoVo>();
//        pagination.setTotalItems(list.getTotal());
//        pagination.setPageSize(pageSize);
//        pagination.setCurrentPage(pageNum);
//        pagination.setList(page);
//        logger.info(JsonUtils.toJson(pagination));
        Pagination<OrderInfoVo> pagination = mgOrderInfoService.getSoldOrderList(pageNum, pageSize, filters, startTime, endTime);

        return pagination;
    }

    @Override
    public Map<String,Integer> getOrderCount(){
        Map<String,Integer> map = new HashMap();
        List<OrderInfo> notPay = new ArrayList();
        List<OrderInfo> paid = new ArrayList();
        List<OrderInfo> isDeleted = new ArrayList();
        List<OrderInfo> list = orderinfoMapper.selectAll();
        int userCount = orderinfoMapper.getUserCount();
        if (!list.isEmpty()){
            for (OrderInfo orderInfo : list){
                if (orderInfo.getIsDeleted() == 0){
                    if (orderInfo.getPayStatus() == 2){
                        paid.add(orderInfo);
                    }else {
                        notPay.add(orderInfo);
                    }
                }else {
                    if (orderInfo.getPayStatus() != 2){
                        isDeleted.add(orderInfo);
                    }
                }
            }
        }
        map.put("notPay",notPay.size());
        map.put("paid",paid.size());
        map.put("isDelete",isDeleted.size());
        map.put("userCount",userCount);
        return map;
    }

    @Transactional
    @Override
    public void updateMgOrderGoodsRemark(MgOrderGoods mgOrderGoods) throws HookahException{
        String orderId = mgOrderGoods.getOrderId();
        String goodsId = mgOrderGoods.getGoodsId();
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(orderId);
        List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
        for (MgOrderGoods mgOrderGood:goodsList) {
            if (mgOrderGood.getGoodsId().equals(goodsId)){
                String remark = mgOrderGoods.getRemark();
                String[] data = remark.split(",");
                switch (mgOrderGood.getGoodsType()){
                    case 5:case 7:
                        String name = data[0];
                        String password = data[1];
                        if (name!=null&&password!=null){
                            mgOrderGood.setPayInfoUserName(name);
                            mgOrderGood.setPayInfoPassword(password);
                            mgOrderGood.setSolveStatus(1);
                        }
                        break;
                    case 4:case 6:
                        String serialNumber = data[0];
                        String fileUrl = data[1];
                        if (serialNumber!=null&&fileUrl!=null){
                            mgOrderGood.setPayInfoFileUrl(fileUrl);
                            mgOrderGood.setPayInfoSerialNumber(serialNumber);
                            mgOrderGood.setSolveStatus(1);
                        }
                        break;
                    case 2:
                        String concatName = data[0];
                        String concatPhone = data[1];
                        String concatEmail = data[2];
                        if (FormatCheckUtil.checkMobile(concatPhone)){
                            mgOrderGood.getDataModel().getConcatInfo().setConcatPhone(concatPhone);
                        }else {
                            throw new HookahException("联系电话格式不正确，请重新输入");
                        }
                        if (FormatCheckUtil.checkEmail(concatEmail)){
                            mgOrderGood.getDataModel().getConcatInfo().setConcatEmail(concatEmail);
                        }else {
                            throw new HookahException("联系邮箱格式不正确，请重新输入");
                        }
                        mgOrderGood.getDataModel().getConcatInfo().setConcatName(concatName);
                }
            }
        }
        if (orderInfoVo.getSolveStatus()==2){
            boolean flag = true;
            for (MgOrderGoods goods:goodsList){
                if (goods.getSolveStatus()==2){
                    flag = false;
                }
            }
            if (flag){
                orderInfoVo.setSolveStatus(1);
            }
        }

        mongoTemplate.save(orderInfoVo);
    }

    @Transactional
    @Override
    public void updateConcatInfo(String orderId,String goodsId,String concatName,String concatPhone,String concatEmail) throws HookahException{
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(orderId);
        List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
        for (MgOrderGoods mgOrderGood:goodsList) {
            if (mgOrderGood.getGoodsId().equals(goodsId)){
                if (FormatCheckUtil.checkMobile(concatPhone)){
                    mgOrderGood.getOffLineInfo().setConcatPhone(concatPhone);
                }else {
                    throw new HookahException("联系电话格式不正确，请重新输入");
                }
                if (FormatCheckUtil.checkEmail(concatEmail)){
                    mgOrderGood.getOffLineInfo().setConcatEmail(concatEmail);
                }else {
                    throw new HookahException("联系邮箱格式不正确，请重新输入");
                }
                mgOrderGood.getOffLineInfo().setConcatName(concatName);
            }
        }
        mongoTemplate.save(orderInfoVo);
    }

    @Override
    public Map getRemark(MgOrderGoods mgOrderGoods){
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(mgOrderGoods.getOrderId());
        List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
        Map map = new HashMap();
        for (MgOrderGoods mgOrderGood:goodsList) {
            if (mgOrderGood.getGoodsId().equals(mgOrderGoods.getGoodsId())){
                switch (mgOrderGood.getGoodsType()){
                    case 0:  //离线数据包
                        if (mgOrderGood.getIsOffline() == 0){
                            if (mgOrderGood.getOffLineData().getIsOnline().equals("0") &&
                                    !mgOrderGood.getOffLineData().getLocalUrl().contains("http")){
                                String localUrl = mgOrderGood.getOffLineData().getLocalUrl();
                                mgOrderGood.getOffLineData().setLocalUrl("http://static.qddata.com.cn/" + localUrl);
                            }
                            map.put("data",mgOrderGood.getOffLineData());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
//                    case 1:  //API
//                        if (mgOrderGood.getIsOffline() == 0){
//                            String localUrl = mgOrderGood.getOffLineData().getLocalUrl();
//                            mgOrderGood.getOffLineData().setLocalUrl("http://static.qddata.com.cn/" + localUrl);
//                            map.put("data",mgOrderGood.getOffLineData());
//                        }else {
//                            map.put("data",mgOrderGood.getOffLineInfo());
//                        }
//                        break;
                    case 2:  //数据模型
                        if (mgOrderGood.getIsOffline() == 0){
                            String configFile = mgOrderGood.getDataModel().getConfigFile().getFileAddress();
                            String configParams = mgOrderGood.getDataModel().getParamFile().getFileAddress();
                            String modelFile = mgOrderGood.getDataModel().getModelFile().getFileAddress();
                            String prefix = "http://static.qddata.com.cn/";
                            configFile = prefix + configFile;
                            mgOrderGood.getDataModel().getConfigFile().setFileAddress(configFile);
                            configParams = prefix + configParams;
                            mgOrderGood.getDataModel().getParamFile().setFileAddress(configParams);
                            modelFile = prefix + modelFile;
                            mgOrderGood.getDataModel().getModelFile().setFileAddress(modelFile);
                            map.put("data",mgOrderGood.getDataModel());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
                    case 4:  //分析工具--独立软件
                        if (mgOrderGood.getIsOffline() == 0){
                            map.put("data",mgOrderGood.getRemark());
                            map.put("url",mgOrderGood.getAtAloneSoftware());
                            map.put("payInfoFileUrl",mgOrderGood.getPayInfoFileUrl());
                            map.put("payInfoSerialNumber",mgOrderGood.getPayInfoSerialNumber());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
                    case 5:  //分析工具--saas
                        if (mgOrderGood.getIsOffline() == 0){
                            map.put("data",mgOrderGood.getRemark());
                            map.put("url",mgOrderGood.getAtSaaS());
                            map.put("payInfoUserName",mgOrderGood.getPayInfoUserName());
                            map.put("payInfoPassword",mgOrderGood.getPayInfoPassword());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
                    case 6:  //应用场景--独立软件
                        if (mgOrderGood.getIsOffline() == 0){
                            map.put("data",mgOrderGood.getRemark());
                            map.put("url",mgOrderGood.getAsAloneSoftware());
                            map.put("payInfoFileUrl",mgOrderGood.getPayInfoFileUrl());
                            map.put("payInfoSerialNumber",mgOrderGood.getPayInfoSerialNumber());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
                    case 7:  //应用场景--saas
                        if (mgOrderGood.getIsOffline() == 0){
                            map.put("data",mgOrderGood.getRemark());
                            map.put("url",mgOrderGood.getAsSaaS());
                            map.put("payInfoUserName",mgOrderGood.getPayInfoUserName());
                            map.put("payInfoPassword",mgOrderGood.getPayInfoPassword());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
                }
            }
        }
        return map;
    }

    @Scheduled()
    public void deleteOrderByTime(){

        Date date = new Date();

        List<OrderInfo> orderInfos = orderinfoMapper.selectAll();
        for (OrderInfo orderInfo:orderInfos){
            long addTime = orderInfo.getAddTime().getTime();

        }
    }
}
