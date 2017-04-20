package com.jusfoun.hookah.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 短信工具类
 * @author admin
 *
 */

/**
 * 使用示例
 * 1、发送：SMSUtil.sendSMS("18612344322","测试内容");
 * 2、将内容set 到MgSmsValidate对象，并存入mongodb(针对验证码)，有效时长为10分钟，失效后自动删除缓存
 * mgSmsValidateService.insert(mgSmsValidate);
 * 3、校验时按手机号和验证码查询，如果有则对，查不到则说明 验证码错误 或者 已经失效
 */
public class SMSUtil {
	private static String userId ="M10012";
	private static String password ="263575";
	private static String url = "http://61.145.229.28:8027/MWGate/wmgw.asmx/MongateMULTIXSend";
	private static Logger logger = LoggerFactory.getLogger(SMSUtil.class);
	public static void sendSMS(String mobile,String message){
		if(StringUtils.isEmpty(mobile)){
			logger.info(ExceptionConst.get(ExceptionConst.PHONE_NULL_ERROR));
		}
		try{
			String multixmt = makeMultixmt(mobile,message);
			HttpRequestUtil.sendGet(url,makeGetUrl(mobile,message,multixmt));
		}catch(Exception e){
			logger.info(ExceptionConst.get(ExceptionConst.SMS_ERROR_MSG));
		}
	}
	
	private static String makeGetUrl(String phoneNum, String message,
			String multixmt) {
		StringBuilder sb = new StringBuilder();
		sb.append("userId=");
		sb.append(userId);
		sb.append("&");
		sb.append("password=");
		sb.append(password);
		sb.append("&");
		sb.append("multixmt=");
		sb.append(multixmt);
		logger.info(sb.toString());
		return sb.toString();
	}
	private static String makeMultixmt(String phoneNum, String message) throws UnsupportedEncodingException {
		//0|*|13800138000|xOO6wyy7ttOtyrnTwyE=
		StringBuilder sb = new StringBuilder("0%7C*%7C");
		sb.append(phoneNum);
		sb.append("%7C");
		sb.append(StrUtil.getBASE64(message));
		return sb.toString();
	}
}
