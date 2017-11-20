package com.jusfoun.hookah.core.config;

/**
 * Created by lt on 2017/10/30.
 */
public class WeChatConfig {

    /*开发者ID*/
    public static final String appID = "wx7549cb36340fd780";

    /*开发者密码*/
    public static final String appSecret = "1a94a158b0bbc3092f5a548f8634c8c2";

    /*获取用户授权链接*/
    public static final String getAuthorizationUrl =
            "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /*换取AccessToken链接*/
    public static final String getAccessTokenUrl =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    /*刷新AccessToken链接*/
    public static final String refreshAccessTokenUrl =
            "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

    /*获取用户信息链接*/
    public static final String getUserInfoUrl =
            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**检查access_token有效性**/
    public static final String checkAccessTokenAuthUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";

}
