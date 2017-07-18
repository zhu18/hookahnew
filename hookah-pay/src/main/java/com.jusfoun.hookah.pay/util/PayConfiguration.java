package com.jusfoun.hookah.pay.util;

import org.apache.logging.log4j.util.PropertiesUtil;

import java.io.InputStream;
import java.util.Properties;

public class PayConfiguration {

	public static Properties properties;
    public static String ALIPAY_NOTIFY_URL;
    public static String ALIPAY_RETURN_URL;

    static {
		InputStream in;
		try {
			in = PropertiesUtil.class.getResourceAsStream("/pay-conf-mall.online.properties");
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
 	}
}
