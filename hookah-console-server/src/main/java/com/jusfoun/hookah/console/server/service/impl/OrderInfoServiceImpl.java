package com.jusfoun.hookah.console.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.console.server.config.MyProps;
import com.jusfoun.hookah.console.server.util.PropertiesManager;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.OrderInfoMapper;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgGoodsOrder;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.http.HttpException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

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
    MyProps myProps;

    /**
     * 评论接口
     */
    @Resource
    CommentService commentService;

    @Resource
    PayCoreService payCoreService;

    @Resource
    UserService userService;

    @Resource
    UserDetailService userDetailService;

    @Resource
    OrganizationService organizationService;

    @Resource
    WaitSettleRecordService waitSettleRecordService;

    @Resource
    MgGoodsOrderService mgGoodsOrderService;

    @Resource
    private WXUserRecommendService wxUserRecommendService;

    @Resource
    public void setDao(OrderInfoMapper orderinfoMapper) {
        super.setDao(orderinfoMapper);
    }

    private OrderInfo init(OrderInfo orderinfo) {
        Date date = new Date();
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
        orderinfo.setForceDeleted((byte)0);
        orderinfo.setCommentFlag(0);
        return orderinfo;
    }

    /**
     * 购物车生成mongo订单
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
        og.setGoodsUserId(addUser);
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
     * 直接购买生成mongo订单
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
        og.setGoodsUserId(addUser);
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

    /**
     * 逻辑取消订单
     * @param id
     */
    @Override
    @Transactional
    public void deleteByLogic(String id) {
        OrderInfo order = selectById(id);
        order.setIsDeleted(new Byte("1"));
        updateByIdSelective(order);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrderId(id);
        orderInfoVo.setIsDeleted((byte)1);
        mgOrderInfoService.updateByIdSelective(orderInfoVo);
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("orderSn",order.getOrderSn()));
        List<MgGoodsOrder> mgGoodsOrders = mgGoodsOrderService.selectList(filter);
        for (MgGoodsOrder mgGoodsOrder:mgGoodsOrders){
            mgGoodsOrder.setIsDeleted((byte)1);
            mgGoodsOrderService.updateByIdSelective(mgGoodsOrder);
        }
    }

    /**
     * 逻辑删除
     * @param id
     */
    @Override
    @Transactional
    public void deleteOrder(String id){
        OrderInfo order = selectById(id);
        order.setOrderId(id);
        order.setForceDeleted(new Byte("1"));
        updateByIdSelective(order);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrderId(id);
        orderInfoVo.setForceDeleted((byte)1);
        mgOrderInfoService.updateByIdSelective(orderInfoVo);
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("orderSn",order.getOrderSn()));
        List<MgGoodsOrder> mgGoodsOrders = mgGoodsOrderService.selectList(filter);
        for (MgGoodsOrder mgGoodsOrder:mgGoodsOrders){
            mgGoodsOrder.setForceDeleted((byte)1);
            mgGoodsOrderService.updateByIdSelective(mgGoodsOrder);
        }
    }

    /**
     * 批量逻辑取消
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatchByLogic(String[] ids) {
        OrderInfo order = new OrderInfo();
        order.setIsDeleted(new Byte("1"));
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("orderId", ids));
        updateByConditionSelective(order,filters);
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setIsDeleted((byte)1);
        mgOrderInfoService.updateByCondition(orderInfoVo,filters);

//        List<Condition> filter = new ArrayList<>();
//        filter.add(Condition.eq("orderSn",order.getOrderSn()));
//        List<MgGoodsOrder> mgGoodsOrders = mgGoodsOrderService.selectList(filter);
//        for (MgGoodsOrder mgGoodsOrder:mgGoodsOrders){
//            mgGoodsOrder.setIsDeleted((byte)1);
//            mgGoodsOrderService.updateByIdSelective(mgGoodsOrder);
//        }
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

    /**
     * 根据 用户id商品id返回已取消的订单  不知道有什么用
     * @param userId
     * @param goodsId
     * @return
     */
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
     * 购物车生成订单前验证
     * @param userId
     * @param cartIdArray
     */
    @Override
    public void checkOrderExist(String userId, String[] cartIdArray) throws Exception{
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.in("recId",cartIdArray));
        List<CartVo> cartList = cartService.selectDetailList(filters);
        if (cartList!=null && cartList.size()>0){
            //先查看是否有相同的订单
            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("userId", userId));
            filter.add(Condition.ne("payStatus", 2));
            filter.add(Condition.eq("isDeleted",0));
            filter.add(Condition.eq("forceDeleted",0));
            List<OrderInfo> orderInfos = selectList(filter);
            for (OrderInfo orderInfo1 : orderInfos){
                OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfo1.getOrderId());
                List<MgOrderGoods> mgOrderGoodss = mgOrder.getMgOrderGoodsList();
                int count = 0;
                if (cartList.size() == mgOrderGoodss.size()){
                    for (MgOrderGoods mgOrderGoods : mgOrderGoodss){
                        for (CartVo cart:cartList){
                            Goods g = cart.getGoods();
                            if (mgOrderGoods.getGoodsId().equals(g.getGoodsId()) &&
                                    mgOrderGoods.getGoodsNumber()==cart.getGoodsNumber().intValue()){
                                count++;
                            }
                        }
                    }
                }
                if (count == cartList.size()){
                    throw new HookahException("已存在相同订单，请先支付或取消");
                }
            }
        }
    }

    /**
     * 直接生成订单前验证
     * @param userId
     * @param goodsId
     * @param goodsNumber
     */
    @Override
    public void checkOrderExist(String userId, String goodsId, Long goodsNumber) throws Exception{
        //先查看是否有相同的订单
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userId", userId));
        filter.add(Condition.ne("payStatus", 2));
        filter.add(Condition.eq("isDeleted",0));
        filter.add(Condition.eq("forceDeleted",0));
        List<OrderInfo> orderInfos = selectList(filter);
        for (OrderInfo orderInfo1:orderInfos){
            OrderInfoVo mgOrder = mgOrderInfoService.selectById(orderInfo1.getOrderId());
            List<MgOrderGoods> mgOrderGoodss = mgOrder.getMgOrderGoodsList();
            if (mgOrderGoodss.size() == 1){
                MgOrderGoods mgOrderGoods = mgOrderGoodss.get(0);
                if (mgOrderGoods.getGoodsId().equals(goodsId) && mgOrderGoods.getGoodsNumber() == goodsNumber.intValue()){
                    throw new HookahException("已存在相同订单，请先支付或取消");
                }
            }
        }
    }
    //验证是否是限购商品
    private boolean checkIsLimitedGoods(String goodsSn, String userId){
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("userId",userId));
        filter.add(Condition.eq("mgOrderGoods.goodsSn",goodsSn));
        filter.add(Condition.eq("isDeleted",(byte)0));
        List<MgGoodsOrder> list = mgGoodsOrderService.selectList(filter);
        if (list!=null&&list.size()>0){
            return true;
        }
        return false;
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
        if(cartList!=null && cartList.size()>0){
            ordergoodsList = new ArrayList<MgOrderGoods>();
            Long goodsAmount = new Long(0);
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            MgGoodsOrder mgGoodsOrder = new MgGoodsOrder();

            for(CartVo cart:cartList){
                //验证商品是否下架
                Goods g = cart.getGoods();
                if(g.getIsOnsale()==null||g.getIsOnsale()!=1){
                    throw new HookahException("商品["+g.getGoodsName()+"]未上架");
                }
                //验证是否是限购商品，且是否已经购买
                if (PropertiesManager.getInstance().getProperty("limitedGoodsSn").contains(g.getGoodsSn())){
                    if (cart.getGoodsNumber()>1 || checkIsLimitedGoods(g.getGoodsSn(),orderInfo.getUserId())){
                        throw new HookahException("限购商品["+g.getGoodsName()+"]只能购买一次");
                    }
                }

                goodsAmount += cart.getFormat().getPrice()  * cart.getGoodsNumber();  //商品单价 * 套餐内数量 * 购买套餐数量

                MgOrderGoods og = getMgOrderGoodsByCart(cart,orderInfo);
                ordergoodsList.add(og);
            }
            orderInfo.setGoodsAmount(goodsAmount);
            orderInfo.setOrderAmount(goodsAmount);

            insertOrder(ordergoodsList, orderInfoVo, orderInfo ,mgGoodsOrder);
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
        MgGoodsOrder mgGoodsOrder = new MgGoodsOrder();
        ordergoodsList = new ArrayList<MgOrderGoods>();
        Long goodsAmount = new Long(0);

        //验证商品是否下架
        GoodsVo g = goodsService.findGoodsById(goodsId);
        if(g.getIsOnsale()==null||g.getIsOnsale()!=1){
            throw new HookahException("商品["+g.getGoodsName()+"]未上架");
        }
        //验证是否是限购商品，且是否已经购买
        String limitedGoodsSn = PropertiesManager.getInstance().getProperty("limitedGoodsSn");
        if (limitedGoodsSn.contains(g.getGoodsSn())){
            if (goodsNumber>1 || checkIsLimitedGoods(g.getGoodsSn(),orderInfo.getUserId())){
                throw new HookahException("限购商品["+g.getGoodsName()+"]只能购买一次");
            }
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

        insertOrder(ordergoodsList, orderInfoVo, orderInfo, mgGoodsOrder);
//        if(goodsAmount.compareTo(0L)==0){
//            updatePayStatus(orderInfo.getOrderSn(),2);
//        }

        return orderInfo;
    }

    public void insertOrder(List<MgOrderGoods> ordergoodsList, OrderInfoVo orderInfoVo, OrderInfo orderInfo,
                            MgGoodsOrder mgGoodsOrder){
        if(ordergoodsList != null && ordergoodsList.size() > 0){
            //插入到mySql
            orderInfo = super.insert(orderInfo);
            BeanUtils.copyProperties(orderInfo,orderInfoVo);
            BeanUtils.copyProperties(orderInfo,mgGoodsOrder);
            User user = userService.selectById(orderInfo.getUserId());
            orderInfoVo.setUserName(user.getUserName());
            orderInfoVo.setUserType(user.getUserType());
            orderInfoVo.setSolveStatus(0);
            mgGoodsOrder.setUserName(user.getUserName());
            mgGoodsOrder.setUserType(user.getUserType());
            UserDetail userDetail = null;
            Organization organization = null;
            for (MgOrderGoods mgOrderGoods:ordergoodsList){
                mgGoodsOrder.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
                if (mgOrderGoods.getSolveStatus()==2){
                    orderInfoVo.setSolveStatus(2);
                }
                mgGoodsOrder.setSolveStatus(mgOrderGoods.getSolveStatus());
                mgGoodsOrder.setMgOrderGoods(mgOrderGoods);
                if (user.getUserType() == 2){
                    userDetail = userDetailService.selectById(orderInfo.getUserId());
                    mgGoodsOrder.setRealName(userDetail.getRealName());
                }else if (user.getUserType() == 4){
                    organization = organizationService.selectById(user.getOrgId());
                    mgGoodsOrder.setRealName(organization.getOrgName());
                }
                //拆单插入mongo
                mgGoodsOrderService.insert(mgGoodsOrder);
            }
            if (user.getUserType() == 2){
                userDetail = userDetailService.selectById(orderInfo.getUserId());
                orderInfoVo.setRealName(userDetail.getRealName());
            }else if (user.getUserType() == 4){
                organization = organizationService.selectById(user.getOrgId());
                orderInfoVo.setRealName(organization.getOrgName());
            }
            orderInfoVo.setMgOrderGoodsList(ordergoodsList);

            //插入到mongo
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

        //修改mongo
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(orderInfo.getOrderId());
        mgOrderInfoService.delete(orderInfo.getOrderId());
        orderInfoVo.setPayId(payMode.toString());
        orderInfoVo.setPayName(payName);
        orderInfoVo.setPayTime(new Date());
        orderInfoVo.setLastmodify(new Date());
        orderInfoVo.setPayStatus(payStatus);
        mgOrderInfoService.insert(orderInfoVo);

        //修改拆单
        List<MgGoodsOrder> mgGoodsOrders = mgGoodsOrderService.selectList(filters);
        for (MgGoodsOrder mgGoodsOrder:mgGoodsOrders){
            mgGoodsOrderService.delete(mgGoodsOrder.getOrderId());
            mgGoodsOrder.setPayId(payMode.toString());
            mgGoodsOrder.setPayName(payName);
            mgGoodsOrder.setPayTime(new Date());
            mgGoodsOrder.setLastmodify(new Date());
            mgGoodsOrder.setPayStatus(payStatus);
            mgGoodsOrderService.insert(mgGoodsOrder);
        }


        //支付成功后,API类商品调用api平台接口，启用api调用跟踪
        //进行商品销量统计
        if(OrderInfo.PAYSTATUS_PAYED == payStatus){
            managePaySuccess(orderInfo);
            countSales(orderInfo.getOrderId());

            //更新微信推荐是否成功交易状态
//            wxUserRecommendService.updateWXUserRecommendIsDeal(orderInfo.getUserId());

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

    @Override
    public void waitSettleRecordInsert(String orderSn){
        List<Condition> mgfilters = new ArrayList<>();
        mgfilters.add(Condition.eq("orderSn", orderSn));
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectOne(mgfilters);
        if (orderInfoVo != null) {
            List<MgOrderGoods> mgOrderGoodsList = orderInfoVo.getMgOrderGoodsList();
            List<WaitSettleRecord> waitSettleRecords = new ArrayList<>();
            if (mgOrderGoodsList != null && mgOrderGoodsList.size() > 0) {
                for (MgOrderGoods mgOrderGoods : mgOrderGoodsList) {
                    WaitSettleRecord waitSettleRecord = new WaitSettleRecord();
                    waitSettleRecord.setOrderSn(mgOrderGoods.getOrderSn());
                    waitSettleRecord.setGoodsId(mgOrderGoods.getGoodsId());
                    waitSettleRecord.setOrderId(orderInfoVo.getOrderId());
                    waitSettleRecord.setOrderAmount(mgOrderGoods.getGoodsPrice());
//					waitSettleRecord.setOrderTime(orderInfoVo.getPayTime());
                    waitSettleRecord.setOrderTime(orderInfoVo.getAddTime());
                    waitSettleRecord.setHasSettleAmount(0L);
                    waitSettleRecord.setNoSettleAmount(mgOrderGoods.getGoodsPrice());
                    waitSettleRecord.setAddTime(new Date());
                    waitSettleRecord.setSettleStatus((byte) 0);
                    waitSettleRecord.setShopName(mgOrderGoods.getAddUser());
                    waitSettleRecord.setGoodsName(mgOrderGoods.getGoodsName());
                    waitSettleRecords.add(waitSettleRecord);
                }

                int n = waitSettleRecordService.insertBatch(waitSettleRecords);
                System.out.println(n);
            }
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
//            String url = "http://open.bdgstore.cn/shop/insert/userapi";
            String apiUrl = myProps.getApi().get("url");
            List<Map> list = new ArrayList();

            orderInfoVo.getMgOrderGoodsList().stream()
                    .filter(g -> g.getGoodsType() == 1)
                    .forEach(goods -> {
                        Map<String, Object> apiParam = new HashMap<>();
//                        Map<String, String> param = new HashMap<>();
//                        param.put("userId", orderInfoVo.getUserId());
//                        param.put("endTime",orderInfo.getUserId());
//                        param.put("orderNo", orderInfo.getOrderSn());
//                        param.put("apiId", goods.getSourceId());
//                        param.put("goodsId", goods.getGoodsId());
//                        param.put("totalCount", new Long(goods.getGoodsNumber() * goods.getFormatNumber()).toString());

                        apiParam.put("userId", orderInfoVo.getUserId());
                        apiParam.put("orderSn", orderInfoVo.getOrderSn());
                        apiParam.put("goodsSn", goods.getGoodsSn());
                        //截取URL
                        String urlstr = goods.getApiInfo().getApiUrl().replace("http://","");
                        urlstr=urlstr.substring(urlstr.indexOf("/"));
                        apiParam.put("url", urlstr);
                        Date startTime = null;
                        Date endTime = null;
                        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DEFAULT_DATE_TIME_FORMAT);
                        switch (goods.getGoodsFormat()){
                            case 0:
                                apiParam.put("type", "1");
                                apiParam.put("totalCount", new Long(goods.getGoodsNumber() * goods.getFormatNumber()).toString());
                                break;
                            case 1:
                                apiParam.put("type", "2");
                                startTime = orderInfoVo.getPayTime();
                                apiParam.put("startTime",startTime);
                                endTime = DateUtils.thisTimeNextMonth(startTime,new Long(goods.getGoodsNumber() * goods.getFormatNumber()).intValue());
                                apiParam.put("endTime",endTime);
                                break;
                            case 2:
                                apiParam.put("type", "2");
                                startTime = orderInfoVo.getPayTime();
                                apiParam.put("startTime",startTime);
                                endTime = DateUtils.thisTimeNextYear(startTime,new Long(goods.getGoodsNumber() * goods.getFormatNumber()).intValue());
                                apiParam.put("endTime",endTime);
                                break;
                        }
//                        list.add(param);
                        list.add(apiParam);
                    });
//            Map rs = HttpClientUtil.PostMethod(url, JsonUtils.toJson(list));
//            logger.info("订单{}商品放api平台返回信息：{}", orderInfo.getOrderId(),  JsonUtils.toJson(rs));
            Map apiRs = HttpClientUtil.PostMethod(apiUrl,JsonUtils.toJson(list));
            logger.info("订单{}API商品插入网管接口：{}", orderInfo.getOrderId(),  JsonUtils.toJson(apiRs));
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

    /**
     * 前台已售出商品列表
     * @param pageNum
     * @param pageSize
     * @param filters
     * @param goodsType
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
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
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort(Sort.Direction.DESC,"addTime"));
        Pagination<OrderInfoVo> pagination = mgOrderInfoService.getListInPageFromMongo(pageNum, pageSize, filters, sorts, startTime, endTime);
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
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort(Sort.Direction.DESC,"addTime"));
        Pagination<OrderInfoVo> pagination = mgOrderInfoService.getListInPageFromMongo(pageNum, pageSize, filters, sorts, startTime, endTime);
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
    public Pagination<MgGoodsOrder> getMgGoodsOrderList(Integer pageNum, Integer pageSize, String orderSn,String goodsName,
                                                        String addUser, Date startTime, Date endTime){
        List<Condition> filter = new ArrayList<>();
        if (orderSn!=null)
            filter.add(Condition.eq("orderSn",orderSn));
        if (goodsName!=null)
            filter.add(Condition.like("mgOrderGoods.goodsName",goodsName));
        if (addUser!=null)
            filter.add(Condition.eq("mgOrderGoods.addUser",addUser));
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort(Sort.Direction.DESC,"addTime"));
        Pagination<MgGoodsOrder> pagination = mgGoodsOrderService.getListInPageFromMongo(pageNum,pageSize,filter,sorts,startTime,endTime);
        return pagination;
    }

    @Override
    public Pagination<MgOrderGoods> getGoodsListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys) {
        PageHelper.startPage(pageNum, pageSize);
        Page<MgOrderGoods> page = new Page<>(pageNum,pageSize);
        Pagination<MgOrderGoods> pagination = new Pagination<MgOrderGoods>();

        List<OrderInfoVo> orderList = mgOrderInfoService.selectList(filters,orderBys);
        //使用方法引用简化lambda,并生成1个Comparator，供下面排序使用
        Comparator<MgOrderGoods> byPayTime = Comparator.comparing(MgOrderGoods::getPayTime);
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
                .sorted(byPayTime.reversed())
                .skip((pageNum-1)*pageSize)
                .limit(pageSize).collect(Collectors.toList())
                .forEach(goods->{page.add(goods);});
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        logger.info(JsonUtils.toJson(pagination));
        return pagination;
    }

    @Override
    public PayVo getPayParam(String orderId) {
        OrderInfo orderInfo = selectById(orderId);
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
        List<Sort> sorts = new ArrayList<>();
        sorts.add(new Sort(Sort.Direction.DESC,"addTime"));
        Pagination<OrderInfoVo> pagination = mgOrderInfoService.getListInPageFromMongo(pageNum, pageSize, filters,sorts, startTime, endTime);

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
                                mgOrderGood.getOffLineData().setLocalUrl(myProps.getHost().get("static") + localUrl);
                            }
                            map.put("data",mgOrderGood.getOffLineData());
                        }else {
                            map.put("data",mgOrderGood.getOffLineInfo());
                        }
                        break;
                    case 1:  //API
                        String tokenUrl = myProps.getApi().get("tokenUrl");
                        tokenUrl = tokenUrl+"?userId="+orderInfoVo.getUserId()+"&orderSn="+orderInfoVo.getOrderSn()+
                                "&goodsSn="+mgOrderGood.getGoodsSn();
                        try {
                            map = HttpClientUtil.GetMethod(tokenUrl);
                            logger.info("获取API商品{}token！{} {}", mgOrderGood.getGoodsSn(), orderInfoVo.getOrderSn(), JsonUtils.toJson(map));
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error("获取API商品token失败!订单号:"+orderInfoVo.getOrderSn()+"商品编号:"+mgOrderGood.getGoodsSn());
                        }
                        break;
                    case 2:  //数据模型
                        if (mgOrderGood.getIsOffline() == 0){
                            String configFile = mgOrderGood.getDataModel().getConfigFile().getFileAddress();
                            String configParams = mgOrderGood.getDataModel().getParamFile().getFileAddress();
                            String modelFile = mgOrderGood.getDataModel().getModelFile().getFileAddress();
                            String prefix = myProps.getHost().get("static");
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

    @Override
    public Map reCreateToken(MgOrderGoods mgOrderGoods){
        String reCreateTokenUrl = myProps.getApi().get("changeUrl");
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(mgOrderGoods.getOrderId());
        List<MgOrderGoods> goodsList = orderInfoVo.getMgOrderGoodsList();
        Map resultMap = new HashMap();
        for (MgOrderGoods mgOrderGood:goodsList) {
            if (mgOrderGood.getGoodsId().equals(mgOrderGoods.getGoodsId())){
                Map<String, String> map = new HashMap<>();
                map.put("userId", orderInfoVo.getUserId());
                map.put("orderSn", orderInfoVo.getOrderSn());
                map.put("goodsSn", mgOrderGood.getGoodsSn());
                try {
                    resultMap = HttpClientUtil.PostMethod(reCreateTokenUrl,map);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("重新生成Token错误!订单号:"+orderInfoVo.getOrderSn()+"商品编号:"+mgOrderGood.getGoodsSn());
                }
            }
        }
        return resultMap;
    }

    @Override
    public ReturnData findInvokeStatus(String orderSn, String goodsSn, Integer pageNumber,
                                Integer pageSize, List<Condition> filters) throws Exception{
        Map resultMap = new HashMap();
        String apiRestUrl = myProps.getApi().get("apiRestUrl");
        StringBuilder apiRestUri = new StringBuilder();
        apiRestUri.append(apiRestUrl).append("?pageNum=").append(pageNumber).append("&pageSize=").append(pageSize)
                .append("&orderSn=").append(orderSn).append("&goodsSn=").append(goodsSn);
        apiRestUrl = apiRestUri.toString();

        Pagination pagination = new Pagination();
        pagination.setTotalItems(0L);
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNumber);
        resultMap = HttpClientUtil.GetMethod(apiRestUrl);
        if (resultMap.get("resultCode").equals("200")){
            resultMap = JsonUtils.toObject((String) resultMap.get("result"),Map.class);
            Map<String, Object> map = (Map) resultMap.get("data");
            if (resultMap.get("code").equals(1)){
                if (map!=null){
                    Map<String , Object> pagenation = (Map<String, Object>) map.get("pagenation");
                    pagination.setTotalItems((int)pagenation.get("count"));
                    pagination.setTotalPage(new Double((double)pagenation.get("totalPage")).intValue());
                    pagination.setList((List) pagenation.get("list"));
                }
            }else{
                return ReturnData.error((String) resultMap.get("message"));
            }
        }
        logger.info("获取API调用日志！{} {}", orderSn, goodsSn, JsonUtils.toJson(resultMap));
        return ReturnData.success(pagination);
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional
    public void deleteOrderByTime(){
        Date date = new Date();
        long now = date.getTime();
        List<OrderInfo> orderInfos = orderinfoMapper.selectAll();
        for (OrderInfo orderInfo:orderInfos){
            long addTime = orderInfo.getAddTime().getTime();
            long time = now - addTime;
            if (orderInfo.getPayStatus()==0 && time>24*60*60*1000){
                deleteByLogic(orderInfo.getOrderId());
            }
        }
    }
//    @Scheduled(cron = "0 55 12 * * ?")
//    @Transactional
//    public void reOrderByTime(){
//        List<Condition> filter = new ArrayList<>();
//        filter.add(Condition.eq("payStatus",2));
//        filter.add(Condition.eq("isDeleted",1));
//        List<OrderInfo> orderInfos = selectList(filter);
////        List<OrderInfo> orderInfos = orderinfoMapper.selectAll();
//        for (OrderInfo orderInfo:orderInfos){
//            orderInfo.setIsDeleted((byte)0);
//            updateByIdSelective(orderInfo);
//            OrderInfoVo orderInfoVo = new OrderInfoVo();
//            orderInfoVo.setOrderId(orderInfo.getOrderId());
//            orderInfoVo.setIsDeleted((byte)0);
//            mgOrderInfoService.updateByIdSelective(orderInfoVo);
//            List<Condition> filters = new ArrayList<>();
//            filters.add(Condition.eq("orderSn",orderInfo.getOrderSn()));
//            List<MgGoodsOrder> mgGoodsOrders = mgGoodsOrderService.selectList(filters);
//            for (MgGoodsOrder mgGoodsOrder:mgGoodsOrders){
//                mgGoodsOrder.setIsDeleted((byte)0);
//                mgGoodsOrderService.updateByIdSelective(mgGoodsOrder);
//            }
//        }
//    }

    @Override
    public Map getStatistics(){
        Map<String,Object> map=new HashMap<String,Object>();

        Date today = new Date();
        map.put("orderNum",getOrderByToday(today)); // 订单数
        map.put("effectiveOrderAmount",getOrderMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED)); //有效订单金额
        map.put("effectiveOrderNum",getOrderNumByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED)); //有效订单数
        map.put("commentNum",getCommentNumByStatus(today,1)); //评论数
        map.put("payEDOrderNum",getOrderNumByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED)); //已付款订单数
        map.put("payingOrderNum",getOrderNumByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYING)); //待付款订单数
        map.put("cancelOrderNum",getOrderNumByToday(today,OrderInfoVo.ORDERSTATUS_CANCEL)); //已取消订单数

        map.put("goodsSoldNum",getGoodsNumByOut(today));  //已售商品数
        map.put("goodsClassification",10); //商品分类数
        map.put("dataSourceMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"101"));  //数据源交易金额
        map.put("dataModelMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"102"));  //数据模型交易金额
        map.put("visualizationMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"103"));  //可视化交易金额
        map.put("applicationMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"104"));  //应用交易金额
        map.put("acquisitionMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"105"));  //采集工具交易金额
        map.put("cleaningMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"106"));  //清洗工具交易金额
        map.put("analysisMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"107")); //分析平台交易金额
        map.put("developmentMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"108"));  //开发平台交易金额
        map.put("managementMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"109"));  //管理平台交易金额
        map.put("securityMoney",getGoodsMoneyByPayStatus(today,OrderInfoVo.PAYSTATUS_PAYED,"110"));  //安全组件交易金额

        map.put("isDeNoGoods",getIsDelNoGoods(today)); //已上架商品数
        map.put("isDeGoods",getIsDeGoods(today)); //下架商品数
        map.put("goodsByDataSource",getGoodsNumByCatId(today,"101")); //数据源上架数
        map.put("goodsByDataModel",getGoodsNumByCatId(today,"102")); //数据模型上架数
        map.put("goodsByVisualization",getGoodsNumByCatId(today,"103")); //可视化上架数
        map.put("goodsByApplication",getGoodsNumByCatId(today,"104")); //应用上架数
        map.put("goodsByAcquisition",getGoodsNumByCatId(today,"105")); //采集工具上架数
        map.put("goodsByCleaning",getGoodsNumByCatId(today,"106")); //清洗工具上架数
        map.put("goodsByAnalysis",getGoodsNumByCatId(today,"107")); //分析平台上架数
        map.put("goodsByDevelopment",getGoodsNumByCatId(today,"108")); //开发平台上架数
        map.put("goodsByManagement",getGoodsNumByCatId(today,"109")); //管理平台上架数
        map.put("goodsBySecurity",getGoodsNumByCatId(today,"110"));//安全组件上架数

        return map;
    }

    /**
     * 获取当日订单数
     * @return
     */
    public int  getOrderByToday(Date stime){
        Query query = new Query();
        query.addCriteria(Criteria.where("addTime").gte(startTime(stime)).lt(endTime(stime)));
        List<OrderInfoVo> orderList =mongoTemplate.find(query,OrderInfoVo.class);
        return orderList.size();
    }


    /**
     * 已售商品数量
     * @return
     */
    public int  getGoodsNumByOut(Date stime){
        Query query = new Query();
        query.addCriteria(Criteria.where("addTime").gte(startTime(stime)).lt(endTime(stime)));
        query.addCriteria(Criteria.where("payStatus").is(OrderInfoVo.PAYSTATUS_PAYED));
        List<MgGoodsOrder> list = mongoTemplate.find(query,MgGoodsOrder.class);
        return list.size();

    }




    /**
     * 根据订单付款状态获取当天订单数 （0：未付款 1：付款中 2：已付款 ）
     * @return
     */
    public int  getOrderNumByPayStatus(Date stime,int payStatus){

        Query query = new Query();
        query.addCriteria(Criteria.where("addTime").gte(startTime(stime)).lt(endTime(stime)));
        query.addCriteria(Criteria.where("payStatus").is(payStatus));

        List<OrderInfoVo> orderList =mongoTemplate.find(query,OrderInfoVo.class);
        return orderList.size();
    }

    /**
     * 根据当天评论数 （0.待审核 1.审核通过，2.审核不通过）
     * @return
     */
    public int  getCommentNumByStatus(Date stime,int commStatus){
        List<Condition> filters = new ArrayList();
        filters.add(Condition.ge("addTime",startTime(stime)));
        filters.add(Condition.le("addTime",endTime(stime)));
        filters.add(Condition.eq("status",commStatus));
        List<Comment> list = commentService.selectList(filters);
        return list.size();

    }


    /**
     * 根据订单交易状态获取当天订单数 （0未确认,1确认,2已取消,3无效,4退货 ）
     * @return
     */
    public int  getOrderNumByToday(Date stime,int orderStatus){

        Query query = new Query();
        query.addCriteria(Criteria.where("addTime").gte(startTime(stime)).lt(endTime(stime)));
        query.addCriteria(Criteria.where("orderStatus").is(orderStatus));
        List<OrderInfoVo> orderList =mongoTemplate.find(query,OrderInfoVo.class);
        return orderList.size();

    }

    /**
     * 根据状态获取某天的总交易金额
     * @param stime
     * @param payStatus （0：未付款 1：付款中 2：已付款 ）
     * @return
     */
    public long getOrderMoneyByPayStatus(Date stime,int payStatus){
        Long total = 0l;
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("addTime").gte(startTime(stime)).lte(endTime(stime)).and("payStatus").is(payStatus)),
                group().sum("orderAmount").as("orderAmount")
        );

        AggregationResults<OrderInfoVo> ar = mongoTemplate.aggregate(aggregation, "message", OrderInfoVo.class);
        List<OrderInfoVo> list = ar.getMappedResults();
        if(list.size() > 0){
            total = list.get(0).getOrderAmount();
        }
        return total;
    }


    /**
     * 根据商品分类获取相关类型商品总交易金额
     * @param stime
     * @param payStatus （0：未付款 1：付款中 2：已付款 ）
     * @return
     */
    public long getGoodsMoneyByPayStatus(Date stime,int payStatus,String catId){
        Long total = 0l;
//左匹配
        Pattern pattern = Pattern.compile("^"+catId+".*$", Pattern.CASE_INSENSITIVE);
/*//模糊匹配
        Pattern pattern = Pattern.compile("^.*王.*$", Pattern.CASE_INSENSITIVE);
        Query query = Query.query(Criteria.where(fieldName).regex(pattern));
       //右匹配
        Pattern pattern = Pattern.compile("^.*"+catId+"$", Pattern.CASE_INSENSITIVE);


        */

        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("addTime").gte(startTime(stime)).lte(endTime(stime)).and("payStatus").is(payStatus)
                        .and("mgOrderGoods.catId").regex(pattern)),
                group().sum("mgOrderGoods.goodsPrice").as("orderAmount")
        );

        AggregationResults<MgGoodsOrder> ar = mongoTemplate.aggregate(aggregation, "message", MgGoodsOrder.class);
        List<MgGoodsOrder> list = ar.getMappedResults();
        if(list.size() > 0){
            total = list.get(0).getOrderAmount();
        }
        return total;
    }

    /**
     * 获取今日上架商品数量
     * @param stime
     * @return
     */
    public long getIsDelNoGoods(Date stime){
        Long total = 0l;
        //当前已上架商品总数
        List<Condition> goodsFifters = new ArrayList<Condition>();
        goodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        goodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        goodsFifters.add(Condition.ge("onsaleStartDate", startTime(stime)));
        goodsFifters.add(Condition.lt("onsaleStartDate",endTime(stime)));
        goodsFifters.add(Condition.eq("isDelete", 1));
        total = goodsService.count(goodsFifters);
        return total;
    }

    /**
     * 获取今日下架商品数量
     * @param stime
     * @return
     */
    public long getIsDeGoods(Date stime){
        Long total = 0l;
        //当前已下架商品总数
        List<Condition> goodsFifters= new ArrayList<Condition>();
        goodsFifters.add(Condition.in("isOnsale", new Byte[]{Byte.valueOf(HookahConstants.SaleStatus.off.getCode()),
                Byte.valueOf(HookahConstants.SaleStatus.forceOff.getCode())}));
        goodsFifters.add(Condition.ge("onsaleEndDate", startTime(stime)));
        goodsFifters.add(Condition.lt("onsaleEndDate",endTime(stime)));
        goodsService.count(goodsFifters);
        return total;
    }

    /**
     * 根据商品种类字典获取今日上架数量
     * @param stime
     * @param catId
     * @return
     */
    public long getGoodsNumByCatId(Date stime,String catId){
        Long total = 0l;
        //当前已上架商品总数
        List<Condition> goodsFifters = new ArrayList<Condition>();
        goodsFifters.add(Condition.eq("isOnsale", Byte.valueOf(HookahConstants.SaleStatus.sale.getCode())));
        goodsFifters.add(Condition.eq("checkStatus", Byte.valueOf(HookahConstants.CheckStatus.audit_success.getCode())));
        goodsFifters.add(Condition.ge("onsaleStartDate", startTime(stime)));
        goodsFifters.add(Condition.lt("onsaleStartDate",endTime(stime)));
        goodsFifters.add(Condition.like("catId",catId+"%"));
        goodsFifters.add(Condition.eq("isDelete", 1));
        total = goodsService.count(goodsFifters);
        return total;
    }

    /**
     * 开始时间
     * @return
     */
    public Date startTime(Date stime){
        Calendar monEight = Calendar.getInstance();
        monEight.setTime(stime);
        monEight.set(Calendar.HOUR_OF_DAY, 0);
        monEight.set(Calendar.MINUTE, 0);
        monEight.set(Calendar.SECOND, 0);
        monEight.set(Calendar.MILLISECOND, 0);
        return monEight.getTime();
    }

    /**
     * 结束时间
     * @return
     */
    public Date endTime(Date stime){
        Calendar c = Calendar.getInstance();
        c.setTime(stime);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }
}
