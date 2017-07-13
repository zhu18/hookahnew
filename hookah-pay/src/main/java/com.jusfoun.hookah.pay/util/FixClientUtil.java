package com.jusfoun.hookah.pay.util;

import com.apex.etm.qss.client.FixClientImpl;
import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixCommInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class FixClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(FixClientUtil.class);

    private static IFixClient fixClient = null;

    public static IFixClient createClient() {
        if(fixClient != null){
            return fixClient;
        }
        fixClient = new FixClientImpl();
        FixCommInfoBean bean = new FixCommInfoBean();
        //设置fix 服务器IP地址，格式：{ip}@{port}/tcp
        bean.setAddress(String.format("%1$s@%2$s/tcp", PayConstants.QD_TEST_IP, PayConstants.QD_TEST_PORT));
        bean.setPassword(""); //通信用户(可以为空)
        bean.setUser("");//通信密码(可以为空)
        bean.setName("清算所客户端");//设置应用名称
        bean.setVersion("1.0.0.1");//设置应用程序版本号
        bean.setVerify(false);//是否校验服务器证书 false:不校验，true:校验
        bean.setTrustListStr("");//可选项目,可以多次增加信任证书(作为可配置，允许为空字符串) ，多个用逗号“,”分隔
        bean.setCertPath("");//设置客户端证书
        bean.setCertPwd("");//客户端证书的密码(可配置参数，允许全部为空字符串)
        bean.setEnableSSL(false); //启用SSL协议,true|false
        bean.setEnableTLS(false); //启用TSL协议,true|false
        bean.setClientInfo("00:e0:4c:68:06:e6 192.168.1.1");//设置客户端信息。格式：mac地址 + 一个空格 + ip地址
        fixClient.createClient(bean, "32");
        return fixClient;
    }

    public static IFixClient createClientSSL()  {
        if(fixClient != null){
            return fixClient;
        }

        File cfgFile = null;
        try {
            cfgFile = ResourceUtils.getFile("classpath:");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("FixClientUtil ---- 获取根路径失败");
        }
        String absolutePath = cfgFile.getAbsolutePath();
        String certDir = absolutePath + File.separator + "cers" + File.separator;
        String cer = certDir + "QSZX120_ca.cer";
        String pfx = certDir + "QSZX120_api_BigData.pfx";

        fixClient = new FixClientImpl();
        FixCommInfoBean bean = new FixCommInfoBean();
        //设置fix 服务器IP地址，格式：{ip}@{port}/tcp
        bean.setAddress(String.format("%1$s@%2$s/tcp", PayConstants.QD_TEST_IP, PayConstants.QD_TEST_PORT));
        bean.setPassword(""); //通信用户(可以为空)
        bean.setUser("");//通信密码(可以为空)
        //特别说明：设置的name + version 名称必须 = 证书的名称（如证书名称是 QSZX_api_HR.pfx， 则设置name=HR，version设置空）
        bean.setName("BigData");//设置应用名称
        bean.setVersion("");//设置应用程序版本号
        bean.setVerify(true);//是否校验服务器证书 false:不校验，true:校验
        bean.setTrustListStr(cer);//可选项目,可以多次增加信任证书(作为可配置，允许为空字符串) ，多个用逗号“,”分隔
        bean.setCertPath(pfx);//设置客户端证书
        bean.setCertPwd("123456");//客户端证书的密码(可配置参数，允许全部为空字符串)
        bean.setEnableSSL(false); //启用SSL协议,true|false
        bean.setEnableTLS(false); //启用TSL协议,true|false
        bean.setClientInfo("00:e0:4c:68:06:e6 192.168.1.1");//设置客户端信息。格式：mac地址 + 一个空格 + ip地址
        fixClient.createClient(bean, "32");
        return fixClient;
    }
}