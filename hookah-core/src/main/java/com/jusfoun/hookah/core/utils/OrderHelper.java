package com.jusfoun.hookah.core.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 
 * @author zhanghanqing
 * @created 2016年6月27日
 */
public class OrderHelper {
	private static Set<String> orderSnSet = new HashSet<String>();
	/**
	 * 生成订单号
	 * @return
	 */
	public static synchronized String genOrderSn(){
		String date = DateUtils.toDateText(new Date(), "yyyyMMddHHmmssSSS");
		Random random = new Random();  
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
		String key = date+rannum;
		if(orderSnSet.contains(key)){
			return genOrderSn();
		}else{
			orderSnSet.add(key);
			return key;
		}
	}
}
