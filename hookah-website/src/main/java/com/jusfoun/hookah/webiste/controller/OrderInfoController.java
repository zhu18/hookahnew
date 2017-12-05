package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.GoodsVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 订单
 *
 * @author zhanghanqing
 * @created 2016年6月28日
 *
 */
@Controller
public class OrderInfoController extends BaseController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisOperate redisOperate;

    @Resource
    CouponService couponService;

    @Resource
    UserCouponService userCouponService;


    /**
     * 购物车确定订单信息
     * @param cartIds
     * @param model
     * @return
     */
    @RequestMapping(value = "/order/orderInfo", method = RequestMethod.POST)
    public String orderInfo(String[] cartIds,Model model) {
        try {
            String userId = this.getCurrentUser().getUserId();
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.in("recId",cartIds));
            List<CartVo> carts = cartService.selectDetailList(filters);
            List<MgOrderGoods> ordergoodsList = null;
            Long goodsAmount = new Long(0);
            if(carts!=null&&carts.size()>0) {

                for (CartVo cart : carts) {
                    //验证商品是否下架
                    Goods g = cart.getGoods();
                    if (g.getIsOnsale() == null || g.getIsOnsale() != 1) {
                        throw new HookahException("商品[" + g.getGoodsName() + "]未上架");
                    }
                    //验证该用户是否能购买该商品
                    User user = userService.selectById(userId);
                    if (user.getUserType()==HookahConstants.UserType.PERSON_CHECK_OK.getCode() &&
                            g.getPurchaseLimit()==Byte.parseByte(HookahConstants.PurchaseLimitName.enterprise.getCode())) {
                        throw new HookahException("["+g.getGoodsName()+"]为企业类型商品，您已认证成为个人用户，不能购买该商品");
                    }

                    if (cart.getFormat().getPrice() != null && cart.getGoodsNumber() != null) {
                        goodsAmount += cart.getFormat().getPrice() *  cart.getGoodsNumber();  //商品单价 * 套餐内数量 * 购买套餐数量
                    }
                }
            }
            List<Coupon> couponList = couponService.getUserCoupons(userId,goodsAmount);
            if (couponList.size() == 0){
                couponList = null;
            }
            model.addAttribute("couponList",couponList);
            model.addAttribute("orderAmount",goodsAmount);
            model.addAttribute("cartOrder",carts);
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            model.addAttribute("perOrderInfoNum",uuid);
            redisOperate.set("orderConfirm:"+uuid,"1",0);
            return "/order/orderInfo";
        }catch (Exception e){
            logger.info(e.getMessage());
            return "/error/500";
        }

    }

    /**
     * 直接购买确定订单信息
     * @param goodsId
     * @param formatId
     * @param goodsNumber
     * @param model
     * @return
     */
    @Log(platform = "front",logType = "f0012",optType = "insert")
    @RequestMapping(value = "/order/directInfo")
    public String orderInfo(String goodsId, Integer formatId,Long goodsNumber,Model model) {
        List<CartVo> list = new ArrayList<>(1);
        try {
            String userId = this.getCurrentUser().getUserId();
            Long goodsAmount = new Long(0);

            //验证商品是否下架
            GoodsVo g = goodsService.findGoodsById(goodsId);
            if (g.getIsOnsale() == null || !g.getIsOnsale().equals((byte)1)) {
                throw new HookahException("商品[" + g.getGoodsName() + "]未上架");
            }
            //验证该用户是否能购买该商品
            User user = userService.selectById(userId);
            if (user.getUserType()==HookahConstants.UserType.PERSON_CHECK_OK.getCode() &&
                    g.getPurchaseLimit()==Byte.parseByte(HookahConstants.PurchaseLimitName.enterprise.getCode())) {
                throw new HookahException("["+g.getGoodsName()+"]为企业类型商品，您已认证成为个人用户，不能购买该商品");
            }
            MgGoods.FormatBean format = goodsService.getFormat(goodsId,formatId);
            goodsAmount += format.getPrice() * goodsNumber;  //商品单价 * 套餐内数量 * 购买套餐数量

            CartVo vo = new CartVo();
            vo.setRecId("-1");
            vo.setGoodsId(goodsId);
            vo.setGoodsNumber(goodsNumber);
            vo.setGoodsName(g.getGoodsName());
            vo.setGoodsFormat(format.getFormat());
            vo.setFormatNumber((long)format.getNumber());
            vo.setGoodsPrice(format.getPrice());
            vo.setFormat(format);
            vo.setFormatId(formatId);
            vo.setGoods(g);
            list.add(vo);
            //所有能用的优惠券
            List<Coupon> couponList = couponService.getUserCoupons(userId,goodsAmount);
            if (couponList.size() == 0){
                couponList = null;
            }
            model.addAttribute("couponList",couponList);
            model.addAttribute("orderAmount",goodsAmount);
            model.addAttribute("cartOrder",list);
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            model.addAttribute("perOrderInfoNum",uuid);
            redisOperate.set("orderConfirm:"+uuid,"1",0);
            return "/order/orderInfo";
        } catch (HookahException e) {
            model.addAttribute("message",e.getMessage());
            return "error/limitedGoodsError";
        }catch (Exception e){
            logger.info(e.getMessage());
            return "/error/500";
        }

    }

    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public String list(Model model) {
            return "/usercenter/buyer/orderManagement";
    }



    /**
     * 前台分页查询订单列表
     *
     * @param pageNumber
     * @param pageSize
     * @param payStatus   支付状态
     * @param commentFlag 是否评论
     * @param startDate
     * @param endDate     结束日期
     * @param domainName  店铺名 模糊查询
     * @return
     */
    @RequestMapping(value = "/order/pageData", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData findByPage(Integer pageNumber, Integer pageSize, Integer payStatus, Integer commentFlag, String startDate, String endDate, String domainName) {
        Map map = new HashMap<>(5);
        try {
            String userId = this.getCurrentUser().getUserId();

            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);

            List<Condition> paidFilters = new ArrayList<>();
            List<Condition> unpaidFilters = new ArrayList<>();
            List<Condition> deletedFilters = new ArrayList<>();
            List<Condition> allFilters = new ArrayList<>();
            List<Condition> listFilters = new ArrayList<>();
            Long paid = 0L,unpaid=0L,deleted=0L,total=0L;

            //未取消的已付款订单
            paidFilters.add(Condition.eq("userId", userId));
            paidFilters.add(Condition.eq("payStatus", 2));
            paidFilters.add(Condition.eq("isDeleted",0));
            paidFilters.add(Condition.eq("forceDeleted",0));
            //未取消的未付款订单
            unpaidFilters.add(Condition.eq("userId", userId));
            unpaidFilters.add(Condition.ne("payStatus", 2));
            unpaidFilters.add(Condition.eq("isDeleted",0));
            unpaidFilters.add(Condition.eq("forceDeleted",0));
            //已取消的订单
            deletedFilters.add(Condition.eq("userId", userId));
            deletedFilters.add(Condition.eq("isDeleted",1));
            deletedFilters.add(Condition.eq("forceDeleted",0));
            //用户所有未删除订单
            allFilters.add(Condition.eq("userId",userId));
            allFilters.add(Condition.eq("forceDeleted",0));

            if (StringUtils.isNotBlank(startDate)) {
                if(payStatus != null && payStatus == 1){
                    paidFilters.add(Condition.ge("addTime", DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
                }else if (payStatus != null && payStatus == 0){
                    unpaidFilters.add(Condition.ge("addTime", DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
                }
                listFilters.add(Condition.ge("addTime", DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(endDate)) {
                if(payStatus != null && payStatus == 1){
                    paidFilters.add(Condition.le("addTime", DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
                }else if (payStatus != null && payStatus == 0){
                    unpaidFilters.add(Condition.le("addTime", DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
                }
                listFilters.add(Condition.le("addTime", DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (commentFlag != null) {
                if(payStatus != null && payStatus == 1){
                    paidFilters.add(Condition.eq("commentFlag", commentFlag));
                }
                listFilters.add(Condition.eq("commentFlag", commentFlag));
                listFilters.add(Condition.eq("payStatus",2));
            }
            if (payStatus != null) {
                if(payStatus==1) {
                    listFilters.add(Condition.eq("payStatus", 2));
                    listFilters.add(Condition.eq("isDeleted",0));
                }else if (payStatus == 0){
                    listFilters.add(Condition.ne("payStatus", 2));
                    listFilters.add(Condition.eq("isDeleted",0));
                }else {
                    listFilters.add(Condition.eq("isDeleted",1));
                }
            }

            if (domainName != null) {
                if (payStatus==1){
                    paidFilters.add(Condition.like("domainName", "%" + domainName + "%"));
                }else{
                    unpaidFilters.add(Condition.like("domainName", "%" + domainName + "%"));
                }
                listFilters.add(Condition.like("domainName", "%" + domainName + "%"));
            }
            listFilters.add(Condition.eq("userId", userId));
            //未被删除的订单
            listFilters.add(Condition.eq("forceDeleted",0));

            //查询列表
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<OrderInfoVo> pOrders = orderInfoService.getDetailListInPage(pageNumber, pageSize, listFilters, orderBys);
            map.put("orders",pOrders);

            unpaid = orderInfoService.count(unpaidFilters);
            deleted = orderInfoService.count(deletedFilters);
            paid = orderInfoService.count(paidFilters);
            total = orderInfoService.count(allFilters);

            map.put("paidCount",paid);
            map.put("deletedCount",deleted);
            map.put("unpaidCount",unpaid);
            map.put("totalCount",total);

            logger.info(JsonUtils.toJson(map));
            return ReturnData.success(map);
        } catch (Exception e) {
            logger.error("分页查询订单错误", e);
            return ReturnData.error("分页查询错误");
        }
    }

    /**
     * 供应商已卖出的商品订单
     * @param pageNumber
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param domainName
     * @return
     */
    @RequestMapping(value = "/order/soldOrder", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getSoldOrder(Integer pageNumber, Integer pageSize, String startDate, String endDate, String domainName, Byte goodsType){
        try {
            String userId = this.getCurrentUser().getUserId();

            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);

            List<Condition> listFilters = new ArrayList<>();
            Date startTime = null;
            Date endTime = null;
            if (StringUtils.isNotBlank(startDate)) {
                startTime = DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
            }
            if (StringUtils.isNotBlank(endDate)) {
                endTime = DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT);
            }

            listFilters.add(Condition.eq("payStatus", 2));

            if (domainName != null) {
                listFilters.add(Condition.like("domainName", "%" + domainName + "%"));
            }
//            listFilters.add(Condition.eq("isDeleted", 0));
            listFilters.add(Condition.eq("forceDeleted", 0));

            //查询列表
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<OrderInfoVo> pOrders = orderInfoService.getSoldOrderListInPage(pageNumber, pageSize, listFilters, goodsType ,startTime, endTime, userId);

//            logger.info(JsonUtils.toJson(map));
            return ReturnData.success(pOrders);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("分页查询已售出商品错误", e);
            return ReturnData.error("查询已售出商品错误");
        }
    }

    /**
     * 前台进入个人中心时调用，显示已购买商品
     * @param pageNumber
     * @param pageSize
     * @param payStatus
     * @param commentFlag
     * @param startDate
     * @param endDate
     * @param domainName
     * @return
     */
    @RequestMapping(value="order/goodsList",method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getOrderGooodsList(Integer pageNumber, Integer pageSize, Integer payStatus, Integer commentFlag, String startDate, String endDate, String domainName){
        Map map = new HashMap<>(3);
        try {
            String userId = this.getCurrentUser().getUserId();

            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);

            List<Condition> filters = new ArrayList<>();
            if (StringUtils.isNotBlank(startDate)) {
                filters.add(Condition.ge("payTime", DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(endDate)) {
                filters.add(Condition.le("payTime", DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (commentFlag != null) {
                filters.add(Condition.eq("commentFlag", commentFlag));
            }

            if (domainName != null) {
                filters.add(Condition.like("domainName", "%" + domainName + "%"));
            }
            filters.add(Condition.eq("userId", userId));
//            filters.add(Condition.eq("isDeleted", 0));
            filters.add(Condition.eq("payStatus", OrderInfo.PAYSTATUS_PAYED));


            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("payTime"));
            Pagination<MgOrderGoods> goodsList = orderInfoService.getGoodsListInPage(pageNumber,pageSize,filters,orderBys);
            return ReturnData.success(goodsList);
        }catch (Exception e){
            logger.error("分页查询已购商品错误", e);
            return ReturnData.error("分页查询已购商品错误");
        }
    }

    @RequestMapping(value = "/order/viewDetails", method = RequestMethod.GET)
    public String getOrderDetail(@RequestParam String orderId,@RequestParam Integer num,Model model){
        try{
            OrderInfoVo vo = orderInfoService.findDetailById(orderId);
            model.addAttribute("order",vo);
            model.addAttribute("num",num);
            logger.info(JsonUtils.toJson(vo));
            return "/usercenter/buyer/viewDetails";
        }catch (Exception e){
            logger.info(e.getMessage());
            return "/error/500";
        }
    }

    @RequestMapping(value = "/order/viewSoldDetails", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getSoldOrderDetail(@RequestParam String orderId){
        try{
            OrderInfoVo vo = orderInfoService.findDetailById(orderId);
            return ReturnData.success(vo);
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error("已售订单详情查询错误");
        }
    }

    @RequestMapping(value = "/order/sunAlone", method = RequestMethod.GET)
    public String getOrderDetail(@RequestParam String orderId, Model model){
        try{
            OrderInfoVo vo = orderInfoService.findDetailById(orderId);
            model.addAttribute("order",vo);
            logger.info(JsonUtils.toJson(vo));
            return "/usercenter/buyer/sunAlone";
        }catch (Exception e){
            logger.info(e.getMessage());
            return "/error/500";
        }
    }

    /**
     * 修改 订单
     *
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/order/update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData update(OrderInfoVo orderInfo) {
        if (StringUtils.isBlank(orderInfo.getOrderId())) {
            return ReturnData.invalidParameters("参数orderId不可为空");
        }
        try {
            orderInfo.setLastmodify(new Date());
            orderInfoService.updateByIdSelective(orderInfo);
            return ReturnData.success();
        } catch (Exception e) {
            logger.error("修改错误", e);
            return ReturnData.error("系统异常");
        }
    }

    /**
     * 生成订单包括购物车生成和直接生成
     * @param orderinfo
     * @param cartIdArray
     * @param goodsId
     * @param formatId
     * @param goodsNumber
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/createOrder", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderinfo, String[] cartIdArray,String goodsId, Integer formatId,Long goodsNumber,
                              HttpServletRequest request, Model model, Long userCouponId, String invoiceId) {
        try {
            String perOrderInfoNum = request.getParameter("perOrderInfoNum");
            if (perOrderInfoNum.isEmpty() || redisOperate.get("orderConfirm:"+perOrderInfoNum) == null){
                return "/error/confirmOrderError";
            }
            init(orderinfo);
            //开发票
            if (invoiceId != null){
                orderinfo.setInvoiceOrNot((byte)1);
                orderinfo.setInvoiceNo(invoiceId);
            }
            if(cartIdArray[0].equals("-1")){
                orderinfo = orderInfoService.insert(orderinfo, goodsId, formatId,goodsNumber, userCouponId);
            }else{
                orderinfo = orderInfoService.insert(orderinfo, cartIdArray, userCouponId);
            }
            HttpSession session = request.getSession();
            List<Map> paymentList = initPaymentList(session);

            //余额
            Map userMap = (Map)session.getAttribute("user");
            User user = userService.selectById((String)userMap.get("userId"));
            session.setAttribute("moneyBalance",user.getMoneyBalance());
            session.setAttribute("payments",paymentList);
            session.setAttribute("orderInfo",orderinfo);
            //logger.info("订单信息:{}", JsonUtils.toJson(orderinfo));
            //logger.info("支付列表:{}", JsonUtils.toJson(paymentList));
            redisOperate.del("orderConfirm:"+perOrderInfoNum);
            return   "redirect:/pay/cash";
        }catch (HookahException e){
            logger.error("生成订单失败", e);
            model.addAttribute("message",e.getMessage());
            return "/error/limitedGoodsError";
        }catch (Exception e) {
            logger.error("插入错误", e);
            return "/error/500";
        }
    }

    @RequestMapping(value = "/order/checkOrderExist", method = RequestMethod.GET)
    public void checkOrderExist(OrderInfo orderinfo, String[] cartIdArray,String goodsId, Integer formatId,Long goodsNumber,HttpServletRequest request){
        try {
            String userId = getCurrentUser().getUserId();
            if(cartIdArray[0].equals("-1")){
                orderInfoService.checkOrderExist(userId, goodsId, goodsNumber);
            }else{
                orderInfoService.checkOrderExist(userId, cartIdArray);
            }
        }catch (Exception e){
            logger.error("验证错误", e);
//            return
        }
    }

    /**
     * 待付款订单去付款
     * 付款前检查一下订单中使用的优惠券是否过期
     * 过期的话重新下单
     * @param orderSn
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/payOrder", method = RequestMethod.GET)
    public String payOrder(String orderSn,HttpServletRequest request, Model model) {
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("orderSn",orderSn));
            OrderInfo orderinfo = orderInfoService.selectOne(filters);
            List<UserCoupon> userCoupons = userCouponService.selectList(filters);
            if (userCoupons != null && userCoupons.size() > 0){
                for (UserCoupon userCoupon : userCoupons){
                    Coupon coupon = couponService.selectById(userCoupon.getCouponId());
                    if (userCoupon.getUserCouponStatus()== HookahConstants.UserCouponStatus.EXPIRED.getCode()){
                        orderInfoService.deleteByLogic(orderinfo.getOrderId());
                        throw new HookahException("订单中的优惠券已过期，请重新下单");
                    }else if (userCoupon.getIsDeleted()==1){
                        orderInfoService.deleteByLogic(orderinfo.getOrderId());
                        throw new HookahException("订单中的优惠券已失效，请重新下单");
                    }
                }
            }

            HttpSession session = request.getSession();
            List<Map> paymentList = initPaymentList(session);

            //余额
            Map userMap = (Map)session.getAttribute("user");
            User user = userService.selectById((String)userMap.get("userId"));
            session.setAttribute("moneyBalance",user.getMoneyBalance());
            session.setAttribute("payments",paymentList);
            session.setAttribute("orderInfo",orderinfo);
            //logger.info("订单信息:{}", JsonUtils.toJson(orderinfo));
            //logger.info("支付列表:{}", JsonUtils.toJson(paymentList));
            return   "redirect:/pay/cash";
        } catch (HookahException e) {
            model.addAttribute("message",e.getMessage());
            return "/error/limitedGoodsError";
        }catch (Exception e) {
            logger.error("插入错误", e);
            return "/error/500";
        }
    }

    private List<Map> initPaymentList(HttpSession session){
        Map userMap = (Map)session.getAttribute("user");
        User user = userService.selectById((String)userMap.get("userId"));
        Object[][] payments = {{"账户余额",user.getMoneyBalance()},{"支付宝",user.getMobile()}};
        List<Map> list = new ArrayList<>(2);
        for(int i=0;i<2;i++){
            Map pay = new HashMap();
            pay.put("payCode",i+1);
            pay.put("payName",payments[i][0]);
            pay.put("payDetail",payments[i][1]);
            list.add(pay);
        }
        return list;

    }

    /**
     * 直接购买
     * @param orderinfo
     * @param goodsId
     * @param formatId
     * @param goodsNumber
     * @return
     */
    @RequestMapping(value = "/order/directOrder", method = RequestMethod.POST)
    public String directCreate(OrderInfo orderinfo, String goodsId, Integer formatId,Long goodsNumber,
                               Model model,HttpServletRequest request,Long userCouponId) {
        try {
            init(orderinfo);
            orderinfo = orderInfoService.insert(orderinfo, goodsId, formatId,goodsNumber,userCouponId);
            model.addAttribute("orderInfo",orderinfo);
            model.addAttribute("payments",initPaymentList(request.getSession()));
            return  "pay/cash";
        } catch (Exception e) {
            logger.error("插入错误", e);
            return "/error/500";
        }
    }

    private OrderInfo init(OrderInfo orderinfo) throws HookahException{
        String userId = getCurrentUser().getUserId();
        orderinfo.setUserId(userId);
        Date date = new Date();

        String now = DateUtils.toDateText(date, "yyMMdd");
//        List<Condition> filter = new ArrayList<>();
//        filter.add(Condition.ge("addTime", DateUtils.toDateText(new Date(),"yyyy-MM-dd 00:00:00")));
//        long count = new AtomicLong(orderInfoService.count(filter)).getAndIncrement();
        String number = String.format("%06d",Integer.parseInt(redisOperate.incr("orderNumPerDay")));
        String orderSn = userService.selectById(userId).getUserSn()+ now + number;
        orderinfo.setOrderSn(orderSn);
        orderinfo.setOrderStatus(OrderInfo.ORDERSTATUS_CONFIRM);
        orderinfo.setShippingStatus(0);
        orderinfo.setShippingId("");
        orderinfo.setShippingName("");
        orderinfo.setPayId("");
        orderinfo.setPayStatus(0);
        orderinfo.setPayName("");
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
        orderinfo.setIsDeleted(new Integer(0).byteValue());
        orderinfo.setForceDeleted((byte)0);
        orderinfo.setCommentFlag(0);
        orderinfo.setInvoiceOrNot((byte)0);
        return orderinfo;
    }

    /**
     * 确认定单
     * @param user
     * @return
     */
    /*@RequestMapping(value="/confirm",method=RequestMethod.POST)
	public Result confirm(String orderinfoIds){
		Result result = new Result();
		result.setRetCode(Result.RETCODE_SUCCESS);	
		try{
			service.updateStatus(orderinfoIds,1);
		}catch(ShopException e){
			result.setRetCode(Result.RETCODE_ERROR);
			result.setErrMsg(e.getMessage());
		}catch(Exception e){
			logger.error("插入错误",e);
			result.setRetCode(Result.RETCODE_ERROR);
			result.setErrMsg("系统异常");
		}
		
		return result;
	}*/


    /**
     * 批量取消订单
     * @param
     * @return
     */
	@RequestMapping(value="/order/deleteBatch",method=RequestMethod.POST)
    @ResponseBody
	public ReturnData delete(@RequestBody  String[] orderIds){
		try{
			orderInfoService.deleteBatchByLogic(orderIds);
            return ReturnData.success();
        }catch(Exception e){
			logger.error("删除错误",e);
            return ReturnData.error("删除错误");
        }
	}

    /**
     * 取消订单
     * @param
     * @return
     */
    @RequestMapping(value = "/order/delete", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData delete(@RequestParam String orderId){
        try{
            logger.info("取消订单：{}", orderId);
            orderInfoService.deleteByLogic(orderId);
            return ReturnData.success();
        }catch(Exception e){
            logger.error("取消错误",e);
            return ReturnData.error("取消错误");
        }
    }

    /**
     * 删除订单
     * @param
     * @return
     */
    @RequestMapping(value = "/order/forceDelete", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData forceDelete(@RequestParam String orderId){
        try{
            logger.info("逻辑删除订单：{}", orderId);
            orderInfoService.deleteOrder(orderId);
            return ReturnData.success("订单已删除");
        }catch(Exception e){
            logger.error("删除错误",e);
            return ReturnData.error("删除错误");
        }
    }

    @RequestMapping(value = "/order/getRemark", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData getRemark(MgOrderGoods mgOrderGoods){
        Map map = new HashMap();
        try {
            if (StringUtils.isNotBlank(mgOrderGoods.getOrderId()) && StringUtils.isNotBlank(mgOrderGoods.getGoodsId())){
                map = orderInfoService.getRemark(mgOrderGoods);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return ReturnData.error(e.getMessage());
        }
        return ReturnData.success(map);
    }

    /**
     * 重新生成api Token
     * @param mgOrderGoods
     * @return
     */
    @RequestMapping(value = "/order/reCreateToken", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData reCreateToken(MgOrderGoods mgOrderGoods){
        Map map = new HashMap();
        if (StringUtils.isNotBlank(mgOrderGoods.getOrderId()) && StringUtils.isNotBlank(mgOrderGoods.getGoodsId())){
            map = orderInfoService.reCreateToken(mgOrderGoods);
        }
        return ReturnData.success(map);
    }

    /**
     * 查询api调用日志
     * @return
     */
    @RequestMapping(value = "/order/findInvokeStatus", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData findInvokeStatus(String orderSn, String goodsSn, Integer pageNumber, Integer pageSize){
        try {
            String userId = getCurrentUser().getUserId();
            Pagination pagination = new Pagination();
            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("orderSn",orderSn));
            if (!orderInfoService.selectOne(filter).getUserId().equals(userId)){
                return ReturnData.success(pagination);
            }
            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);
            List<Condition> filters = new ArrayList<>();
            return orderInfoService.findInvokeStatus(orderSn,goodsSn,pageNumber,pageSize,filters);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取API调用日志失败！{} {}", orderSn, goodsSn);
            return ReturnData.error(e.getMessage());
        }
    }
}
