package com.jusfoun.hookah.core.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @name:HttpRequestUtil.class
 * @description:使用http的方式请求数据
 * @author:丁建伟
 * @date:2016年7月14日 下午5:56:27
 * @location:com.example.sbdm.HttpRequestUtil.class
 * Copyright 2015 by Jusfoun.com. All Right Reserved
 */
public class HttpRequestUtil {
	
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//                log.debug(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	System.out.println("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	System.out.println("发送GET请求关闭流异常！" + e2);
            }
        }
        //System.out.println(result);
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url 发送请求的 URL
     * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("Pragma:", "no-cache");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type",
                    "text/xml");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println("发送 POST 请求出现异常！" + e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	System.out.println("发送 POST 请求关闭流异常！" + ex);
            }
        }
        System.out.println(result);
        return result;
    }
    
    public static String getImgBase64(String path)  throws Exception{ 
	    	URL url = new URL(path); //get //post 
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
		    conn.setRequestMethod("GET"); 
		    conn.setConnectTimeout(5*1000); 
		    InputStream inStream = conn.getInputStream(); 
		    ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
		    byte[] buffer = new byte[1024]; 
		    int len = 0; 
		    while( (len = inStream.read(buffer)) !=-1 ){ 
		    	outStream.write(buffer, 0, len); 
		    } 
		    byte[] data = outStream.toByteArray();//图片的二进制数据  
	    
		    return new String(Base64.encodeBase64(data));  
	    }
}