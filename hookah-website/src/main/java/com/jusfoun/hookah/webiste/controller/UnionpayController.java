package com.jusfoun.hookah.webiste.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.utils.*;

import com.jusfoun.hookah.rpc.api.AccNoTokenService;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.PayCoreService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping("/pay/unionpay")
public class UnionpayController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UnionpayController.class);

	@Resource
	private PayCoreService payCoreService;
	@Resource
	private AccNoTokenService accNoTokenService;
	@Resource
	private OrderInfoService orderService;
	
	/**开通卡并消费
	 * @param orderId  订单ID
	 * @param accNo  银行卡号
	 * @param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/oac", method = RequestMethod.GET)
	Object openAndConsume(@RequestParam(required=true) String orderId,
			@RequestParam(required=true) String accNo,
			HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//处理请求
		String reqHtml = payCoreService.openUnionpay(orderId, accNo, "00003443e3ce74e3fbf2ca02b3baa64c");
		/*String reqHtml = payCoreService.openUnionpay("23", "6221558812340000", "00003443e3ce74e3fbf2ca02b3baa64c");*/
		if(StringUtils.isEmpty(reqHtml)){
			return "redirect:/404.html";
		}else
			return new ResponseEntity<String>(reqHtml, HttpStatus.OK);
	}

	
	/**开通卡并支付，前台通知地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ocf", method = RequestMethod.POST)
	ModelAndView openCardFrontUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = null;
		Map<String, String> params = getRequestParams(request);
		String orderSn = params.get("orderId");
		if(payCoreService.verifyUnionpay(params)){
			String respCode = params.get("respCode");
			if("00".equals(respCode)){
				//根据订单号查询token
				String token = payCoreService.getTokenByOrderSn(orderSn);
				//更新支付状态
				PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
				payCoreService.updatePayCore(paied);
				// 更新token
				accNoTokenService.updateTokenByOrderSn(orderSn, token==null?"-1":token);
				view = new ModelAndView("redirect:/pages/paySucess.html?orderSn="+orderSn);
			}else{
				view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
			}
		}else{
			view = new ModelAndView("redirect:/pages/payError.html?orderSn="+orderSn);
		}
		return view;
	}
	
	/**开通卡并支付，后台通知地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ocb", method = RequestMethod.POST)
	String openCardBackUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> params = getRequestParams(request);
		String orderSn = params.get("orderId");
		if(payCoreService.verifyUnionpay(params)){
			String respCode = params.get("respCode");
			if("00".equals(respCode)){
				//根据订单号查询token
				String token = payCoreService.getTokenByOrderSn(orderSn);
				//更新支付状态
				PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
				payCoreService.updatePayCore(paied);
				// 更新token
				accNoTokenService.updateTokenByOrderSn(orderSn, token==null?"-1":token);
				return "ok";
			}else{
				return "fail";
			}
		}else{
			return "fail";
		}
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
	
	/**获取用户银行卡列表
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/accnos", method = RequestMethod.GET)
	public ReturnData accNoList(HttpSession session) {
		ReturnData result = new ReturnData();
		try {
			result.setData(accNoTokenService.findAccNoList(null));
		}catch (Exception e) {
			result.setCode(ExceptionConst.Failed);
			result.setMessage(e.toString());
			e.printStackTrace();
		}
		return result;
	} 
	

	/**
	 *交易时发送短信
	 * @param orderId
	 * @param accNo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendSms", method = RequestMethod.GET)
	public ReturnData sendSms(@RequestParam(required=true) Integer orderId,
			@RequestParam(required=true) String accNo,
			HttpSession session) throws Exception {
		ReturnData result = new ReturnData();

		try {
			/*PayVo payVo = orderService.getPayParam(orderId);
			result.setData(payCoreService.unionpaySendSMS(payVo.getOrderSn(), accNo, payVo.getTotalFee(), getUserId(session)));*/
		}catch (Exception e) {
				result.setCode(ExceptionConst.Failed);
				result.setMessage(e.toString());
				e.printStackTrace();
		}
		return result;
	}
	
	
	/**解除绑定
	 * @param orderId
	 * @param accNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAccno", method = RequestMethod.GET)
	public ReturnData deleteAccno(@RequestParam(required=true) String orderId,
			@RequestParam(required=true) String accNo,
			@RequestParam(required=true) String password,
			HttpSession session) throws Exception {
		ReturnData result = new ReturnData();

		try {
			/*String userId = getUserId(session);*/
			String userId = "00003443e3ce74e3fbf2ca02b3baa64c";
			//验证登录密码
			/*String postResult = HttpRequestUtil.sendPost("http://192.168.15.15:8080/user/checkUserPassword", "userid="+userId+"&password="+ StrUtil.encoderByMd5(password));
			Result parseObject = JSONObject.parseObject(postResult, Result.class);
			if(parseObject.getRetCode()==0){
				result.setCode(ExceptionConst.Failed);
				result.setMessage("密码不正确");
				return result;
			}*/
			PayVo payVo = orderService.getPayParam(orderId);
			result.setData(payCoreService.deleteAccno(payVo.getOrderSn(), accNo, userId));
		} catch (Exception e) {
			result.setCode(ExceptionConst.Failed);
			result.setMessage(e.toString());
			e.printStackTrace();
		}
		return result;
	}
	
	/**交易
	 * @param orderId
	 * @param accNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/pay", method = RequestMethod.GET)
	ModelAndView pay(@RequestParam(required=true) String orderId,
					 @RequestParam(required=true) String accNo,
					 @RequestParam(required=true) String smsCode,
					 HttpSession session) throws Exception {
		ModelAndView view = null;
		PayVo payVo = orderService.getPayParam(orderId);
		/*String url = "redirect:/pages/payError.html?orderSn="+payVo.getOrderSn();*/
		String url = "redirect:/pages/payError.html?orderSn="+"42343";
		try {
			/*String res = payCoreService.unionpayConsume(payVo,orderId, getUserId(session), accNo, smsCode);*/
			String res = payCoreService.unionpayConsume(payVo,"23", "00003443e3ce74e3fbf2ca02b3baa64c","6221558812340000", "123456");
			if("ok".equals(res))
				 url = "redirect:/pages/paySucess.html?orderSn="+payVo.getOrderSn();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		view = new ModelAndView(url);
		return view;
	}
	
	/**交易后台通知地址
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cb", method = RequestMethod.POST)
	String consumeBackUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> params = getRequestParams(request);
		String orderSn = params.get("orderId");
		if(payCoreService.verifyUnionpay(params)){
			String respCode = params.get("respCode");
			if("00".equals(respCode)){
				//更新支付状态
				PayCore paied = payCoreService.findPayCoreByOrderSn(orderSn);
				paied.setPayStatus(PayCore.PayStatus.success.getValue());
				payCoreService.updatePayCore(paied);
				return "ok";
			}else{
				return "fail";
			}
		}else{
			return "fail";
		}
	}
}
