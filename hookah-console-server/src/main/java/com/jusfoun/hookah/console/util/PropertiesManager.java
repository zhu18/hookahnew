/**
 * All rights Reserved, Designed By ZTE-ITS
 * Copyright:    Copyright(C) 2016-2020
 * Company       JUSFOUN GuoZhiFeng LTD.
 * @author:    郭志峰
 * @version    V1.0 
 * Createdate:         2016年8月13日 下午2:38:17
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年8月13日       guozhifeng         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
package com.jusfoun.hookah.console.util;

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
			ploader = new PropertiesLoader("classpath:console.properties");
		}
		
		return ploader;
	}
}