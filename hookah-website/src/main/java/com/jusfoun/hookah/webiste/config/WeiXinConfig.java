package com.jusfoun.hookah.webiste.config;

import com.jusfoun.hookah.core.domain.GeneralCodes;
import com.jusfoun.hookah.core.utils.SHAUtils;
import com.jusfoun.hookah.webiste.util.WeiXinMPUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ctp on 2017/11/28.
 */
public class WeiXinConfig extends GeneralCodes {

    /*开发者ID*/
    public static final String appID = "wxaad146db776a22bc";

    /*开发者密码*/
    public static final String appSecret = "526edae8d93174271ef6fe24fe36f6de";

    /**
     * 获取微信配置信息
     * @return
     */
    public static Map<String,Object> getWXMPConfig(String url){

        Map<String,Object> configMap = new HashMap<String, Object>();

        //1、获取AccessToken
        String accessToken = WeiXinMPUtil.getAccessToken();

        //2、获取Ticket
        String jsapi_ticket = WeiXinMPUtil.getTicket(accessToken);

        //3、时间戳和随机字符串
        String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳


        //4、将参数排序并拼接字符串
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;

        //5、将字符串进行sha1加密
        String signature = SHAUtils.encryptSHA1(str);

        configMap.put("appId",appID);
        configMap.put("timestamp",timestamp);
        configMap.put("nonceStr",noncestr);
        configMap.put("signature",signature);
        return configMap;
    }

}
