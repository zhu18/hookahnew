package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.rpc.api.PayCoreService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * z支付接口
 */
@Controller
@RequestMapping("/payCore")
public class PayCoreController {

	protected final static Logger logger = LoggerFactory.getLogger(PayCoreController.class);

	@Resource
	private PayCoreService payCoreService;

	@Resource
	UserService userService;

	/**订单支付
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping
	Object pay(@RequestParam(required=true) String orderId,
			   HttpSession session,
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		//TODO 锁

		//处理请求
		/*String reqHtml = payCoreService.doPay(orderId, getUserId(session));*/
		Session sessionUs = SecurityUtils.getSubject().getSession();
		HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
		String reqHtml = payCoreService.doPay(orderId,userMap.get("userId"));
		System.out.println(reqHtml + "xxx");
		if(StringUtils.isEmpty(reqHtml)){
			return "redirect:/404.html";
		}else
			return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
	}
	
	@RequestMapping(value="/wallet_rtn", method = RequestMethod.GET)
	ModelAndView returnBack4Wallet(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = null;
		//商户订单号
		String orderSn = new String(request.getParameter("orderSn"));
		//交易状态
		String tradeStatus = new String(request.getParameter("status"));
		//TODO 验签
		if(tradeStatus.equals("1")){
			//交易成功
			view = new ModelAndView("redirect:/pages/paySucess.html?orderSn="+orderSn);
		}else{
			//交易失败
			view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
		}
		PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
		if("1".equals(tradeStatus)){
			paied.setPayStatus(PayCore.PayStatus.success.getValue());//成功
		}
//		else if("-1".equals(tradeStatus)){
//			paied.setPayStatus(PayStatus.balanceLow.getValue());
//		}
		else{
//			paied.setPayStatus(PayStatus.failure.getValue());//失败
			paied.setPayStatus(PayCore.PayStatus.unpay.getValue());//失败
		}
		//更新订单状态
		payCoreService.updatePayCore(paied);
		return view;
	}
	
	@RequestMapping(value="/alipay_rtn", method = RequestMethod.GET)
	ModelAndView returnBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = null;
		//商户订单号
		String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		if(payCoreService.verifyAlipay(getRequestParams(request))){
			PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
			if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
				//交易成功
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
				view = new ModelAndView("redirect:/pages/paySucess.html?orderSn="+orderSn);
			}else{
				//交易失败
				view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
			}
			paied.setTradeNo(tradeNo);
			//更新订单状态
			payCoreService.updatePayCore(paied);
		}else{
			view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
		}
		return view;
	}
	
	@RequestMapping(value="/alipay_ntf", method = RequestMethod.POST)
	String notifyBack4Alipay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//商户订单号
		String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		if(payCoreService.verifyAlipay(getRequestParams(request))){
			PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
			if(tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
			}
			paied.setTradeNo(tradeNo);
			//更新订单状态
			payCoreService.updatePayCore(paied);
			return "success";
		}
		return "fail";
	}
	
	@RequestMapping(value="/unionpay_rtn", method = RequestMethod.GET)
	ModelAndView returnBack4Unionpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = null;
		//商户订单号
		String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		if(payCoreService.verifyAlipay(getRequestParams(request))){
			PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
			if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
				//交易成功
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
				view = new ModelAndView("redirect:/pages/paySucess.html?orderSn="+orderSn);
			}else{
				//交易失败
//				paied.setPayStatus(PayStatus.failure.getValue());
				view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
			}
			paied.setTradeNo(tradeNo);
			//更新订单状态
			payCoreService.updatePayCore(paied);
		}else{
			view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
		}
		return view;
	}
	
	@RequestMapping(value="/unionpay_ntf", method = RequestMethod.POST)
	String notifyBack4Unionpay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//商户订单号
		String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		if(payCoreService.verifyAlipay(getRequestParams(request))){
			PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
			if(tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
				paied.setTradeNo(tradeNo);
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
			}
			else{
				paied.setTradeNo(tradeNo);
			}
			//更新订单状态
			payCoreService.updatePayCore(paied);
			return "success";
		}
		return "fail";
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String,String> getRequestParams(HttpServletRequest request){
			//处理通知参数
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			return params;
	}
}
