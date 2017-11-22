package com.jusfoun.hookah.oauth2server.config;

import com.jusfoun.hookah.core.config.WeChatConfig;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WeChatAuthInfo;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ctp on 2017/11/16.
 * 微信联合登录工具类
 */
public class WXConfigUtils {

    public final static Logger logger = LoggerFactory.getLogger(WXConfigUtils.class);

    /**
     * 通过code获取access_token  有效期为两个小时
     * @param code
     * @return
     */
    public static WeChatAuthInfo getAccessToken(String code) {
        String getAccessTokenUrl = WeChatConfig.getAccessTokenUrl;
        getAccessTokenUrl = getAccessTokenUrl.replace("APPID",WeChatConfig.appID);
        getAccessTokenUrl = getAccessTokenUrl.replace("SECRET",WeChatConfig.appSecret);
        getAccessTokenUrl = getAccessTokenUrl.replace("CODE",code);
        WeChatAuthInfo weChatAuthInfo = null;
        try {
            Map resultMap = HttpClientUtil.GetMethod(getAccessTokenUrl);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                weChatAuthInfo = JsonUtils.toObject(result, WeChatAuthInfo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("通过code获取access_token失败：" + e.getMessage());
        }
        return weChatAuthInfo;
    }

    /**
     * 刷新或续期access_token使用
     * @param refreshToken
     * @return
     */
    public static  WeChatAuthInfo refreshAccessToken(String refreshToken){
        String refreshAccessTokenUrl = WeChatConfig.refreshAccessTokenUrl;
        //"?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        refreshAccessTokenUrl = refreshAccessTokenUrl.replace("APPID",WeChatConfig.appID);
        refreshAccessTokenUrl = refreshAccessTokenUrl.replace("REFRESH_TOKEN",refreshToken);
        WeChatAuthInfo weChatAuthInfo = null;
        try {
            Map resultMap = HttpClientUtil.GetMethod(refreshAccessTokenUrl);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                weChatAuthInfo = JsonUtils.toObject(result, WeChatAuthInfo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("刷新获取access_token失败：" + e.getMessage());
        }
        return weChatAuthInfo;
    }

    /**
     * 检验授权凭证（access_token）是否有效 true:有效 false:无效
     */
    public static  Boolean checkAccessToken(String openId,String accessToken){
        String refreshAccessTokenUrl = WeChatConfig.checkAccessTokenAuthUrl;
        //https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
        refreshAccessTokenUrl = refreshAccessTokenUrl.replace("OPENID",openId);
        refreshAccessTokenUrl = refreshAccessTokenUrl.replace("ACCESS_TOKEN",accessToken);
        Boolean isAccessToken = false;
        try {
            Map resultMap = HttpClientUtil.GetMethod(refreshAccessTokenUrl);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                WeChatAuthInfo weChatAuthInfo = JsonUtils.toObject(result, WeChatAuthInfo.class);
                if(Objects.nonNull(weChatAuthInfo) && "0".equals(weChatAuthInfo.getErrcode())){
                    isAccessToken = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("检验access_token是否有效失败：" + e.getMessage());
        }
        return isAccessToken;
    }

    /**
     * 获取微信用户信息
     */
    public static WxUserInfo getWxUserInfo(String openId, String accessToken){
        String getWxUserInfo = WeChatConfig.getUserInfoUrl;
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        getWxUserInfo = getWxUserInfo.replace("OPENID",openId);
        getWxUserInfo = getWxUserInfo.replace("ACCESS_TOKEN",accessToken);
        WxUserInfo wxUserInfo = null;
        try {
            Map resultMap = HttpClientUtil.GetMethod(getWxUserInfo);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                wxUserInfo  = JsonUtils.toObject(result, WxUserInfo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("access_token失败：" + e.getMessage());
        }
        return wxUserInfo;
    }

    public static String generateUserName(String openId){
        StringBuffer username = new StringBuffer();
        username.append("weixin_");
        if(Objects.nonNull(openId) && openId.length() > 10){
            username.append(openId.substring(openId.length()-10));
        } else {
            username.append(openId);
        }
        return username.toString();
    }

}
