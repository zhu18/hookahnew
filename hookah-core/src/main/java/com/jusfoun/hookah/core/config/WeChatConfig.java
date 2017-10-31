package com.jusfoun.hookah.core.config;

/**
 * Created by lt on 2017/10/30.
 */
public class WeChatConfig {

    /*开发者ID*/
    public static final String appID = "wxaad146db776a22bc";

    /*开发者密码*/
    public static final String appSecret = "43ee266d02159ed8b54e6651ad3e0ca8";

    /*换取token链接*/
    public static final String getAccessTokenUrl =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /*获取用户授权链接*/
    public static final String getSnsApiUserInfoUrl =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
}
