package com.jusfoun.hookah.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.*;
import org.apache.http.client.params.ClientPNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class HttpClientUtil {
	public static Map<String, String> getUrl(String url, String username, String password) {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);

		Map<String, String> resMap = new HashMap<String, String>();
		Base64 b = new Base64();
		String encoding = b.encodeAsString(new String(username + ":" + password).getBytes());
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
		GetMethod get = new GetMethod(url);
		get.addRequestHeader("Authorization", "Basic " + encoding);
		get.setDoAuthentication(true);
		try {

			client.executeMethod(get);
			//System.out.println(get.getResponseBodyAsString());
			InputStream inputStream = get.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str= "";
			while((str = br.readLine()) != null){
			stringBuffer .append(str );
			}

			resMap.put("result", stringBuffer.toString());
			resMap.put("resultCode", String.valueOf(get.getStatusCode()));
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			get.releaseConnection();
		}
		return resMap;
	}
	/**
     * 
     * @Title: BasicPutMethod 
     * @Description: basic 认证的put请求
     * @param url
     * @param username
     * @param password
     * @param transJson
     * @return
     * @throws HttpException
     * @throws IOException
     * @return: Map<String,String>
     */
	public  Map<String, String> BasicPutMethod(String url,String username,String password, String transJson) throws HttpException, IOException {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		Map<String, String> resMap = new HashMap<String, String>();
		PutMethod method = null;
		try {
          method = new PutMethod(url);
          Base64 b = new Base64();
  		String encoding = b.encodeAsString(new String(username + ":" + password).getBytes());
  		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
  		method.addRequestHeader("Authorization", "Basic " + encoding);
  		method.addRequestHeader("X-Requested-By","ambari");
  		method.setDoAuthentication(true);
		  RequestEntity se = new StringRequestEntity(transJson, "text/plain", "UTF-8");
			method.setRequestEntity(se);
			
			// 使用系统提供的默认的恢复策略
           // System.out.println(transJson);
			//method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			// 设置超时的时间

			//method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			client.executeMethod(method);
			resMap.put("result", method.getResponseBodyAsString());
			resMap.put("resultCode", String.valueOf(method.getStatusCode()));
		} catch (IllegalArgumentException e) {

		} catch (java.io.UnsupportedEncodingException e) {

			e.printStackTrace();
		}finally {

			method.releaseConnection();
		}

		return resMap;
	}
	/**
	 * 
	 * @Title: GetMethod 
	 * @Description: 普通的get请求
	 * @param url
	 * @return
	 * @return: Map<String,String>
	 */
	public static Map<String, String> GetMethod(String url) {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);

		Map<String, String> resMap = new HashMap<String, String>();

		GetMethod get = new GetMethod(url);
	

		try {

			client.executeMethod(get);
			InputStream inputStream = get.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str= "";
			while((str = br.readLine()) != null){
			stringBuffer .append(str );
			}
			System.out.println("hhh"+str);
			resMap.put("result", String.valueOf(stringBuffer));
			resMap.put("resultCode", String.valueOf(get.getStatusCode()));
		
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			get.releaseConnection();
		}
		return resMap;
	}
	
	/**
     * 
     * @Title: PostMethod 
     * @Description: 普通的提交json的post请求
     * @param url  请求的url
     * @param data   post提交的json 字符串
     * @return
     * @throws HttpException
     * @throws IOException
     * @return: Map<String,String>
     */
	public static  Map<String, String> PostMethod(String url, Map<String,String> data) throws HttpException, IOException {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		Map<String, String> resMap = new HashMap<String, String>();
		PostMethod method = null;
		try {
                    
			method = new PostMethod(url);
			if (data!=null) {
				
			
			 Object[] keys = data.keySet().toArray();    
		        NameValuePair[] pairs = new NameValuePair[keys.length];
		    
		        for (int i = 0; i < keys.length; i++) {    
		            String key = (String) keys[i];    
		            pairs[i] = new NameValuePair(key, data.get(key));    
		        }    
			
		      method.setRequestBody(pairs);
			}
//			RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
//			method.setRequestEntity(se);
			// 使用系统提供的默认的恢复策略
          // System.out.println(transJson);
			//method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			// 设置超时的时间

			//method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			client.executeMethod(method);
			resMap.put("result", method.getResponseBodyAsString());
			resMap.put("resultCode", String.valueOf(method.getStatusCode()));
		} catch (IllegalArgumentException e) {

		} catch (java.io.UnsupportedEncodingException e) {

			e.printStackTrace();
		}finally {

			method.releaseConnection();
		}

		return resMap;
	}

	public static void main(String[] args) {
		String url = "http://open.galaxybigdata.com/shop/insert/userapi";
		Map<String,String> param = new HashMap<>();
		param.put("username","tytyty");

		try {
			Map rs = HttpClientUtil.PostMethod(url,param);
			System.out.println(JsonUtils.toJson(rs));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
