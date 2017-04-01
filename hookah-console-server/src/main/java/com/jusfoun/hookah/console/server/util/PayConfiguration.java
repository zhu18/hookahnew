package com.jusfoun.hookah.console.server.util;

import org.apache.logging.log4j.util.PropertiesUtil;

import java.io.InputStream;
import java.util.Properties;

public class PayConfiguration {

	public static Properties properties;
    public static String ALIPAY_NOTIFY_URL;
    public static String ALIPAY_RETURN_URL;
    
    public static String UNIONPAY_FRONTURL;
    public static String UNIONPAY_BACKURL;
    public static String UNIONPAY_OPEN_CARD_FRONTURL;
    public static String UNIONPAY_OPEN_CARD_BACKURL;
    public static String UNIONPAY_CONSUME_BACKURL;
    
    public static String WALLET_RETURNURL;
    public static String WALLET_CONSUME_URL;

    static {
		InputStream in;
		try {
			in = PropertiesUtil.class.getResourceAsStream("/pay-conf-mall.properties");
			properties = new Properties();
			properties.load(in);
			initConfig();
			System.out.println("PropertiesUtil初始化完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    // 初始化全局配置
 	public static void initConfig(){
 		ALIPAY_NOTIFY_URL = properties.getProperty("alipay_notify_url");
 		ALIPAY_RETURN_URL = properties.getProperty("alipay_return_url");
 		UNIONPAY_FRONTURL = properties.getProperty("unionpay_fronturl");
 		UNIONPAY_OPEN_CARD_FRONTURL = properties.getProperty("unionpay_open_card_fronturl");
 		UNIONPAY_BACKURL = properties.getProperty("unionpay_backurl");
 		UNIONPAY_OPEN_CARD_BACKURL = properties.getProperty("unionpay_open_card_backurl");
 		UNIONPAY_CONSUME_BACKURL = properties.getProperty("unionpay_consume_backurl");
 		WALLET_RETURNURL = properties.getProperty("wallet_returnurl");
 		WALLET_CONSUME_URL = properties.getProperty("wallet_consume_url");
 	}
}
