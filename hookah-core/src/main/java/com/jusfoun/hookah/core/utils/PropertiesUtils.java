package com.jusfoun.hookah.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertiesUtils {
	/**
	 * 获取数据库连接信息
	 * @param propertiesType 数据库连接属性配置类别
	 * cbLanding：编目落地数据库 kettleLog：KETTLE日志数据库 transLanding转换落地数据库 filter：问题库
	 * @return
	 */
	public static Map<String, String> getCenterProperties(String propertiesType){
		Map<String,String> map = new HashMap<>();
		Resource rs = new ClassPathResource("centerServer.properties");
		Properties properties=new Properties();
		try {
			properties.load(rs.getInputStream());
			String hostKey=propertiesType+".host";
			String dbNameKey=propertiesType+".dbName";
			String portKey=propertiesType+".port";
			String usernameKey=propertiesType+".username";
			String passwordKey=propertiesType+".password";
			String host = properties.getProperty(hostKey);
			String dbName = properties.getProperty(dbNameKey);
			String port = properties.getProperty(portKey);
			String username = properties.getProperty(usernameKey);
			String password = properties.getProperty(passwordKey);
			map.put(hostKey, host);
			map.put(dbNameKey, dbName);
			map.put(portKey, port);
			map.put(usernameKey, username);
			map.put(passwordKey, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
//	public static void main(String[] args) {
//		PropertiesUtils.getCenterProperties("cbLanding");
//	}
	

}
