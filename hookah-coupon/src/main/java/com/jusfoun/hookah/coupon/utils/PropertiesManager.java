package com.jusfoun.hookah.coupon.utils;

/**
 * 
 * @ClassName: PropertiesManager 
 * @Description: 获取配置文件
 * @author Timothy guozhifengvip@163.com 
 * @date 2016年8月13日 下午2:38:49 
 *
 */
public class PropertiesManager {
	
	public static PropertiesLoader ploader;
	
	private PropertiesManager() {
		
	}
	
	public static PropertiesLoader getInstance() {
		if(ploader == null) {
			ploader = new PropertiesLoader("classpath:coupon.properties");
		}
		
		return ploader;
	}
}