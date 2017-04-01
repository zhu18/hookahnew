package com.jusfoun.hookah.console.server.pay.unionpay;


import com.jusfoun.hookah.console.server.util.PayConfiguration;

public class UnionpayConfig {
	/** 全渠道固定值*/
	public static String version = "5.0.0";
	
	/** UTF-8或GBK，默认配置的是UTF-8*/
	public static String encoding = "UTF-8";
	
	/** 交易类型 01-消费*/
	public static String txnType = "01";
	
	/** 交易子类型 01-消费*/
	public static String txnSubType = "01";
	
	/** 业务类型，B2C网关支付，手机wap支付*/
	public static String bizType = "000201";
	
	/** 渠道类型07-PC*/
	public static String channelType = "07";
	
	/** 签名方法 目前只支持01-RSA方式证书加密*/
	public static String signMethod = "01";
	
	/** 前台通知地址*/
	public static String frontUrl = PayConfiguration.UNIONPAY_FRONTURL;
	/** 开通卡前台通知地址*/
	public static String openCardFrontUrl = PayConfiguration.UNIONPAY_OPEN_CARD_FRONTURL;
	
	/** 后台通知地址*/
	public static String backUrl = PayConfiguration.UNIONPAY_BACKURL;
	/** 开通卡后台通知地址*/
	public static String openCardBackUrl = PayConfiguration.UNIONPAY_OPEN_CARD_BACKURL;
	/** 交易后台通知地址*/
	public static String  consumeBackUrl = PayConfiguration.UNIONPAY_CONSUME_BACKURL;
	
	/** 商户号码，测试账号777290058110048*/
	public static String merId = "898111948160464";
	
	public static String trId = "62000000221";
	
	/** 接入类型，商户接入固定填0，不需修改*/
	public static String accessType = "0";
	
	/** 交易币种（境内商户一般是156 人民币)*/
	public static String currencyCode = "156";
}