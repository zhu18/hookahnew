package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.vo.CartVo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.OrderHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.CartService;
import com.jusfoun.hookah.rpc.api.GoodsService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private CartService cartService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String index() {
        return "redirect:/order/list";
    }

    @RequestMapping(value = "/order/orderInfo", method = RequestMethod.POST)
    public String orderInfo(String[] cartIds,Model model) {
        try {
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.in("recId",cartIds));
            List<CartVo> carts = cartService.selectDetailList(filters,null);
            model.addAttribute("cartList",carts);
            return "order/orderInfo";
        }catch (Exception e){
            logger.info(e.getMessage());
            return "/error/500";
        }

    }
    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param payStatus   支付状态
     * @param commentFlag 是否评论
     * @param startDate
     * @param endDate     结束日期
     * @param domainName  店铺名 模糊查询
     * @return
     */
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public String findByPage(@RequestParam(defaultValue = PAGE_NUM) Integer pageNum, @RequestParam(defaultValue = PAGE_SIZE) Integer pageSize, Integer payStatus, Integer commentFlag, Date startDate, Date endDate, String domainName, Model model) {
        try {
            List<Condition> filters = new ArrayList<>();
            if (startDate != null) {
                filters.add(Condition.ge("addTime", startDate));
            }
            if (endDate != null) {
                filters.add(Condition.le("addTime", endDate));
            }
            if (payStatus != null) {
                filters.add(Condition.eq("payStatus", payStatus));
            }
            if (commentFlag != null) {
                filters.add(Condition.eq("commentFlag", commentFlag));
            }
            if (domainName != null) {
                filters.add(Condition.like("domainName", "%" + domainName + "%"));
            }
            String userId = getCurrentUser().getUserId();
            filters.add(Condition.eq("userId", userId));
            filters.add(Condition.eq("isDeleted", 0));

            List<OrderBy> orderBys = new ArrayList<>();
            orderBys.add(OrderBy.desc("addTime"));
            Pagination<OrderInfoVo> pOrders = orderInfoService.getDetailListInPage(pageNum, pageSize, filters, orderBys);
            model.addAttribute("orderList", pOrders);
            return "/1/mybuyer/order";
        } catch (Exception e) {
            logger.error("分页查询订单错误", e);
            ReturnData.error("系统异常");
        }

        return "/error/500";
    }

    /**
     * 修改 订单
     *
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/order/update", method = RequestMethod.POST)
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
     *
     * @param orderinfo
     * @param cartIds
     * @return
     */
    @RequestMapping(value = "/order/createOrder", method = RequestMethod.GET)
    public String createOrder(OrderInfo orderinfo, String cartIds,Model model) {
        try {
            init(orderinfo);
            orderinfo = orderInfoService.insert(orderinfo, cartIds);
            model.addAttribute("orderInfo",orderinfo);
            return  "pay/cash";
        } catch (Exception e) {
            logger.error("插入错误", e);
            return "/error/500";
        }
    }

    /**
     * 直接购买
     * @param orderinfo
     * @param goodsId
     * @param formatId
     * @param goodsNumber
     * @return
     */
    @RequestMapping(value = "/order/direct", method = RequestMethod.GET)
    public String directCreate(OrderInfo orderinfo, String goodsId, Integer formatId,Long goodsNumber,Model model) {
        try {
            init(orderinfo);
            orderinfo = orderInfoService.insert(orderinfo, goodsId, formatId,goodsNumber);
            model.addAttribute("orderInfo",orderinfo);
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
     * @param user
     * @return
     */
	/*@RequestMapping(value="/delete",method=RequestMethod.POST)
	public Result delete(String orderinfoIds){
		Result result = new Result();
		result.setRetCode(Result.RETCODE_SUCCESS);	
		try{
			service.deleteBatchByPK(orderinfoIds);
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
     * 订单详情
     * @param orderId
     * @return
     */
	/*@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String detail(String orderId){
		try{
			OrderInfo orderInfo = service.findByPrimaryKey(orderId);
			result.setData(orderInfo);
		}catch(ShopException e){
			result.setRetCode(Result.RETCODE_ERROR);
			result.setErrMsg(e.getMessage());
		}catch(Exception e){
			logger.error("查询订单详情错误",e);
			result.setRetCode(Result.RETCODE_ERROR);
			result.setErrMsg("系统异常");
		}
		
		return result;
	}*/
}
