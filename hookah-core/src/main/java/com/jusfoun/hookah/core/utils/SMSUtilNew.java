package com.jusfoun.hookah.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.exception.SmsException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SMSUtilNew {

	public static String smsUser="qddata";
	public static String smsKey="KeKFp18MLR8HRQoqCTRSsw5i76IKMvig";

    private static Logger logger = LoggerFactory.getLogger(SMSUtilNew.class);
	
	public static String send(String mobile,String vars,String templateId) throws SmsException{
        String retVal = HookahConstants.SMS_FAIL;
        if(StrUtil.isBlank(mobile)){
            logger.error("手机号码为空");
            throw new SmsException("手机号码为空");
        }
        if(StrUtil.isBlank(vars)){
            logger.error("短信模板内参数为空");
            throw new SmsException("短信模板内参数为空");
        }
        if(StrUtil.isBlank(templateId)){
            logger.error("短信模板编号为空");
            throw new SmsException("短信模板编号为空");
        }
		String url = "http://www.sendcloud.net/smsapi/send";

        // 填充参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("smsUser", smsUser);
        params.put("templateId", templateId);
        params.put("msgType", "0");
        params.put("phone", mobile);
        params.put("vars", vars);

        // 对参数进行排序
        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                // 忽略大小写
                return arg0.compareToIgnoreCase(arg1); 
            }
        });
        sortedMap.putAll(params);

        // 计算签名
        StringBuilder sb = new StringBuilder();
        sb.append(smsKey).append("&");
        for (String s : sortedMap.keySet()) {
            sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
        }
        sb.append(smsKey);
        String sig = DigestUtils.md5Hex(sb.toString());

        // 将所有参数和签名添加到post请求参数数组里
        List<NameValuePair> postparams = new ArrayList<NameValuePair>();
        for (String s : sortedMap.keySet()) {
            postparams.add(new BasicNameValuePair(s, sortedMap.get(s)));
        }
        postparams.add(new BasicNameValuePair("signature", sig));

        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postparams, "utf8"));
            CloseableHttpClient httpClient;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(100000).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            logger.info("mobile:" + result);
            JSONObject object = JSON.parseObject(result);
            retVal = "true".equals(object.get("result").toString()) ? HookahConstants.SMS_SUCCESS : HookahConstants.SMS_FAIL;
            EntityUtils.consume(entity);
        } catch (Exception e) {
            throw new SmsException(e);
        } finally {
            httpPost.releaseConnection();
        }
		return retVal;
	}

    public static String sendX(String  tos,String templateId){
        if(StrUtil.isBlank(tos)){
            logger.error("短信参数为空");
            return null;
        }
        if(StrUtil.isBlank(templateId)){
            logger.error("短信模板编号为空");
            return null;
        }

		String url = "http://www.sendcloud.net/smsapi/sendx";

        // 填充参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("smsUser", smsUser);
        params.put("templateId", templateId);
        params.put("msgType", "0");
        params.put("tos", tos);

        // 对参数进行排序
        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                // 忽略大小写
                return arg0.compareToIgnoreCase(arg1); 
            }
        });
        sortedMap.putAll(params);

        // 计算签名
        StringBuilder sb = new StringBuilder();
        sb.append(smsKey).append("&");
        for (String s : sortedMap.keySet()) {
            sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
        }
        sb.append(smsKey);
        String sig = DigestUtils.md5Hex(sb.toString());

        // 将所有参数和签名添加到post请求参数数组里
        List<NameValuePair> postparams = new ArrayList<NameValuePair>();
        for (String s : sortedMap.keySet()) {
            postparams.add(new BasicNameValuePair(s, sortedMap.get(s)));
        }
        postparams.add(new BasicNameValuePair("signature", sig));

        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postparams, "utf8"));
            CloseableHttpClient httpClient;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(100000).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            logger.info(EntityUtils.toString(entity));
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            httpPost.releaseConnection();
        }
		
		return null;
	}
}
