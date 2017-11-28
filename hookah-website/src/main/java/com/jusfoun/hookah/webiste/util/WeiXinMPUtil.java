package com.jusfoun.hookah.webiste.util;

import com.alibaba.fastjson.JSONObject;
import com.jusfoun.hookah.core.domain.WeChatAuthInfo;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.webiste.config.WeiXinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by ctp on 2017/11/28.
 */
public class WeiXinMPUtil {

    public final static Logger logger = LoggerFactory.getLogger(WeiXinMPUtil.class);

    /**
     * 获取access_token
     * @return
     */
    public static String getAccessToken() {
        String access_token = "";
        String grant_type = "client_credential";//获取access_token填写client_credential
        String AppId= WeiXinConfig.appID;//第三方用户唯一凭证
        String secret=WeiXinConfig.appSecret;//第三方用户唯一凭证密钥，即appsecret
        //这个url链接地址和参数皆不能变
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+AppId+"&secret="+secret;

        try {
            Map resultMap = HttpClientUtil.GetMethod(url);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                JSONObject demoJson = JSONObject.parseObject(result);
                System.out.println("JSON字符串："+demoJson);
                access_token = demoJson.getString("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取access_token失败：" + e.getMessage());
        }
        return access_token;
    }

    /**
     * 获取jsapi_ticket
     * @param access_token
     * @return
     */
    public static String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";//这个url链接和参数不能变
        try {
            Map resultMap = HttpClientUtil.GetMethod(url);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                JSONObject demoJson = JSONObject.parseObject(result);
                System.out.println("JSON字符串："+demoJson);
                ticket = demoJson.getString("ticket");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取ticket失败：" + e.getMessage());
        }
        return ticket;
    }

}
