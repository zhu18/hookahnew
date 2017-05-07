package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.Goods;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.mongo.MgGoods;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.CartService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/order/orderInfo", method = RequestMethod.POST)
    public String orderInfo(String[] cartIds,Model model) {
        try {
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

                    if (cart.getFormat().getPrice() != null && cart.getGoodsNumber() != null) {
                        goodsAmount += cart.getFormat().getPrice() *  cart.getGoodsNumber();  //商品单价 * 套餐内数量 * 购买套餐数量
                    }
                }
            }
            model.addAttribute("orderAmount",goodsAmount);
            model.addAttribute("cartOrder",carts);
            return "/order/orderInfo";
        }catch (Exception e){
            logger.info(e.getMessage());
            return "/error/500";
        }

    }

//    @RequestMapping(value = "/order/directInfo", method = RequestMethod.POST)
//    public String orderInfo(String goodsId, Integer formatId,Long goodsNumber,Model model) {
//        List<CartVo> list = new ArrayList<>(1);
//        try {
//            Long goodsAmount = new Long(0);
//
//            //验证商品是否下架
//            Goods g = goodsService.selectById(goodsId);
//            if (g.getIsOnsale() == null || g.getIsOnsale() != 1) {
//                throw new HookahException("商品[" + g.getGoodsName() + "]未上架");
//            }
//            MgGoods.FormatBean format = goodsService.getFormat(goodsId,formatId);
//            goodsAmount += format.getPrice() * goodsNumber;  //商品单价 * 套餐内数量 * 购买套餐数量
//
//            CartVo vo = new CartVo();
//            vo.setRecId("-1");
//            vo.setGoodsId(goodsId);
//            vo.setGoodsNumber(goodsNumber);
//            vo.setGoodsName(g.getGoodsName());
//            vo.setGoodsFormat(formatId);
//            vo.setFormatNumber((long)format.getNumber());
//            vo.setGoodsPrice(format.getPrice());
//            vo.setFormat(format);
//            vo.setFormatId(formatId);
//            vo.setGoods(g);
//            list.add(vo);
//            model.addAttribute("orderAmount",goodsAmount);
//            model.addAttribute("cartOrder",list);
//            return "/order/orderInfo";
//        }catch (Exception e){
//            logger.info(e.getMessage());
//            return "/error/500";
//        }
//
//    }


    @RequestMapping(value = "/order/directInfo", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData orderInfo(String goodsId, Integer formatId,Long goodsNumber) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        List<CartVo> list = new ArrayList<>(1);
        try {
            Long goodsAmount = new Long(0);

            //验证商品是否下架
            Goods g = goodsService.selectById(goodsId);
            if (g.getIsOnsale() == null || g.getIsOnsale() != 1) {
                throw new HookahException("商品[" + g.getGoodsName() + "]未上架");
            }
            MgGoods.FormatBean format = goodsService.getFormat(goodsId,formatId);
            goodsAmount += format.getPrice() * goodsNumber;  //商品单价 * 套餐内数量 * 购买套餐数量

            CartVo vo = new CartVo();
            vo.setRecId("-1");
            vo.setGoodsId(goodsId);
            vo.setGoodsNumber(goodsNumber);
            vo.setGoodsName(g.getGoodsName());
            vo.setGoodsFormat(formatId);
            vo.setFormatNumber((long)format.getNumber());
            vo.setGoodsPrice(format.getPrice());
            vo.setFormat(format);
            vo.setFormatId(formatId);
            vo.setGoods(g);
            list.add(vo);
            //cartOrder
            returnData.setData(list);
            //orderAmount
            returnData.setData2(goodsAmount);
        }catch (Exception e){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            logger.info(e.getMessage());
        }
        return returnData;
    }

    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public String list(Model model) {
            return "/usercenter/buyer/orderManagement";
    }

    /**
     * 分页查询
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
        Map map = new HashMap<>(3);
        try {
            String userId = this.getCurrentUser().getUserId();

            if (pageNumber==null) pageNumber = Integer.parseInt(PAGE_NUM);
            if (pageSize==null) pageSize = Integer.parseInt(PAGE_SIZE);

            List<Condition> filters = new ArrayList<>();
            if (StringUtils.isNotBlank(startDate)) {
                filters.add(Condition.ge("addTime", DateUtils.getDate(startDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (StringUtils.isNotBlank(endDate)) {
                filters.add(Condition.le("addTime", DateUtils.getDate(endDate,DateUtils.DEFAULT_DATE_TIME_FORMAT)));
            }
            if (commentFlag != null) {
                filters.add(Condition.eq("commentFlag", commentFlag));
            }
            Condition condition = null;
            if (payStatus != null) {
                if(payStatus==1) {
                    condition = Condition.eq("payStatus", 2);
                }else{
                    condition = Condition.ne("payStatus", 2);
                }
                filters.add(condition);
            }
            if (domainName != null) {
                filters.add(Condition.like("domainName", "%" + domainName + "%"));
            }
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("isDeleted", 0));

            //查询列表
            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<OrderInfoVo> pOrders = orderInfoService.getDetailListInPage(pageNumber, pageSize, filters, orderBys);
            map.put("orders",pOrders);
            filters.remove(condition); //移除支付状态条件
            //查询数量
            filters.add(Condition.eq("payStatus", 2));
            Long paid = orderInfoService.count(filters);  //已支付数量
            map.put("paidCount",paid);

            filters.remove(filters.size()-1);
            filters.add(Condition.ne("payStatus", 2));
            Long unpaid = orderInfoService.count(filters); //未支付数量
            map.put("unpaidCount",unpaid);

            logger.info(JsonUtils.toJson(map));
            return ReturnData.success(map);
        } catch (Exception e) {
            logger.error("分页查询订单错误", e);
            return ReturnData.error("分页查询错误");
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
     * 订单结算
     * @param orderinfo
     * @param cartIdArray
     * @param goodsId
     * @param formatId
     * @param goodsNumber
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/createOrder", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderinfo, String[] cartIdArray,String goodsId, Integer formatId,Long goodsNumber,HttpServletRequest request) {
        try {
            init(orderinfo);
            if(cartIdArray[0].equals("-1")){
                orderinfo = orderInfoService.insert(orderinfo, goodsId, formatId,goodsNumber);
            }else{
                orderinfo = orderInfoService.insert(orderinfo, cartIdArray);
            }
            HttpSession session = request.getSession();
            List<Map> paymentList = initPaymentList(session);

            //余额
            Map userMap = (Map)session.getAttribute("user");
            User user = userService.selectById((String)userMap.get("userId"));
            session.setAttribute("moneyBalance",user.getMoneyBalance());
            session.setAttribute("payments",paymentList);
            session.setAttribute("orderInfo",orderinfo);
            logger.info("订单信息:{}", JsonUtils.toJson(orderinfo));
            logger.info("支付列表:{}", JsonUtils.toJson(paymentList));
            return   "redirect:/pay/cash";
        } catch (Exception e) {
            logger.error("插入错误", e);
            return "/error/500";
        }
    }

    private List<Map> initPaymentList(HttpSession session){
        Map userMap = (Map)session.getAttribute("user");
        User user = userService.selectById((String)userMap.get("userId"));
        Object[][] payments = {{"账户余额",user.getMoneyBalance()},{"支付宝",user.getMobile()},{"银联","6*** ***6  3*** 9**1"}};
        List<Map> list = new ArrayList<>(3);
        for(int i=0;i<3;i++){
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
    public String directCreate(OrderInfo orderinfo, String goodsId, Integer formatId,Long goodsNumber,Model model,HttpServletRequest request) {
        try {
            init(orderinfo);
            orderinfo = orderInfoService.insert(orderinfo, goodsId, formatId,goodsNumber);
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
        orderinfo.setOrderSn(OrderHelper.genOrderSn());
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
        orderinfo.setCommentFlag(0);
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
     * 删除订单
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
     * 删除订单
     * @param
     * @return
     */
    @RequestMapping(value="/order/delete",method=RequestMethod.GET)
    @ResponseBody
    public ReturnData delete(@RequestParam String orderId){
        try{
            orderInfoService.deleteByLogic(orderId);
            return ReturnData.success();
        }catch(Exception e){
            logger.error("删除错误",e);
            return ReturnData.error("删除错误");
        }
    }
}
