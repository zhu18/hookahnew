package com.jusfoun.hookah.console.server.pay.wallet;

import com.jusfoun.hookah.console.server.pay.FormFactory;
import com.jusfoun.hookah.console.server.util.PayConfiguration;
import com.jusfoun.hookah.core.domain.vo.PayVo;

import java.util.HashMap;
import java.util.Map;



public class WalletBuilder {
	public static final String returnUrl = PayConfiguration.WALLET_RETURNURL;
	public static final String consumeUrl = PayConfiguration.WALLET_CONSUME_URL;

	public static String build(PayVo payVo) {
		Map<String, String> map = new HashMap<String, String>();
		//基本信息
		map.put("return_url", returnUrl);
		//订单信息
		map.put("orderNumber", payVo.getOrderSn());//订单号
		map.put("money", payVo.getTotalFee().toString());//该笔订单的资金总额,单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
		map.put("userid", payVo.getUserId());//用户id
		
		Map<String, String> params = FormFactory.paramFilter(map);
		return FormFactory.buildForm(params, consumeUrl, "get");
	}
}
