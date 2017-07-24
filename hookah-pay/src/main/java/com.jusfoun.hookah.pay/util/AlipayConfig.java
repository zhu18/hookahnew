package com.jusfoun.hookah.pay.util;

public class AlipayConfig {

	public static final String alipayGateway = "https://mapi.alipay.com/gateway.do?";
	
	/** 合作身份者ID，签约账号*/
	public static final String partner = "2088421313381178";
	
	/** 收款支付宝账号*/
	public static final String seller_id = partner;
	
	/** MD5密钥，安全检验码*/
	public static String key = "jffjabg1gda0ob1p98q8x5x05wy5d026";
	
	/** 服务器异步通知页面路径*/
//	public static String notify_url = "http://商户网址/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp";
//	public static String notify_url = PayConfiguration.ALIPAY_NOTIFY_URL;
	
	/** 页面跳转同步通知页面路径*/
//	public static String return_url = "http://商户网址/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp";
//	public static String return_url = PayConfiguration.ALIPAY_RETURN_URL;
	
	/** 签名方式*/
	public static String sign_type = "MD5";
	
	/** 调试用，创建TXT日志文件夹路径*/
	public static String log_path = "C:\\";
	
	/** 字符编码格式 目前支持 gbk 或 utf-8*/
	public static String input_charset = "utf-8";
	
	/** 支付类型 ，无需修改*/
	public static String payment_type = "1";
	
	/** 调用的接口名，无需修改*/
	public static String service = "create_direct_pay_by_user";
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/** 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数*/
	public static String anti_phishing_key = "";
	
	/** 客户端的IP地址*/
	public static String exter_invoke_ip = "";
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}