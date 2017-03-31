package com.jusfoun.hookah.console.server.controller;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.OrderHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author zhanghanqing
 * @created 2016年6月28日
 */
@Controller
public class OrderInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoController.class);
	private static final String PAGE_SIZE = new Integer(HookahConstants.PAGE_SIZE).toString();
	
	@Autowired
	private OrderInfoService orderInfoService;

	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String index(){
		return "redirect:/order/list";
	}

	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param payStatis    支付状态
	 * @param commentFlag   是否评论
	 * @param startDate
	 * @param endDate      结束日期
     * @param domainName  店铺名 模糊查询
     * @return
     */
	@RequestMapping(value="/order/list",method=RequestMethod.GET)
	public String findByPage( @RequestParam(defaultValue="1")Integer pageNum, @RequestParam(defaultValue= "15")Integer pageSize,Integer payStatis,Integer commentFlag,Date startDate,Date endDate,String domainName,Model model){
		try{
			List<Condition> filters = new ArrayList<>();
			if(startDate!=null){
				filters.add(Condition.ge("addTime",startDate));
			}
			if(endDate!=null){
				filters.add(Condition.le("addTime",endDate));
			}
			if(payStatis!=null){
				filters.add(Condition.eq("payStatis",payStatis));
			}
			if(commentFlag!=null){
				filters.add(Condition.eq("commentFlag",commentFlag));
			}
			if(domainName!=null){
				filters.add(Condition.like("domainName","%"+domainName+"%"));
			}
			List<OrderBy> orderBys = new ArrayList<>();
			orderBys.add(OrderBy.desc("addTime"));
			Pagination<OrderInfoVo> pOrders = orderInfoService.getDetailListInPage(pageNum,pageSize,filters,orderBys);
			model.addAttribute("orderList",pOrders);
			return "/mybuyer/order";
		}catch(Exception e){
			logger.error("分页查询错误",e);
			ReturnData.error("系统异常");
		}

		return "/error/500";
	}

	/**
	 * 修改 订单
	 * @param orderInfo
	 * @return
     */
	@RequestMapping(value = "/order/update",method=RequestMethod.POST)
	public ReturnData update(OrderInfoVo orderInfo){
		if(StringUtils.isBlank(orderInfo.getOrderId())){
			return  ReturnData.invalidParameters("参数orderId不可为空");
		}
		try{
			orderInfo.setLastmodify(new Date());
			orderInfoService.updateByIdSelective(orderInfo);
			return ReturnData.success();
		}catch(Exception e){
			logger.error("修改错误",e);
			return ReturnData.error("系统异常");
		}
	}


	/**
	 *  插入订单
	 * @param orderinfo
	 * @param cartIds
     * @return
     */
	@RequestMapping(value="/order/insert",method=RequestMethod.POST)
	public ReturnData insert(OrderInfo orderinfo,String cartIds){
		try{
			init(orderinfo);
			orderinfo = orderInfoService.insert(orderinfo,cartIds);
			return ReturnData.success(orderinfo);
		}catch(Exception e){
			logger.error("插入错误",e);
			return ReturnData.error("系统异常");
		}
	}

	private OrderInfo init(OrderInfo orderinfo) {
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
		orderinfo.setDelFlag(1);
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
