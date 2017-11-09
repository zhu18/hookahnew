package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.config.WeChatConfig;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WeChatAuthInfo;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.GeneratorUtil;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import com.jusfoun.hookah.rpc.api.WxUserInfoService;
import com.jusfoun.hookah.webiste.config.MyProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2017/11/3.
 */
@Controller
@RequestMapping("/sns")
public class SnsController {
    private static final Logger logger = LoggerFactory.getLogger(SnsController.class);

    @Resource
    private MyProps myProps;

    @Resource
    private RedisOperate redisOperate;

    @Resource
    WxUserInfoService wxUserInfoService;

    @Resource
    UserService userService;

    /**
     * 微信二维码
     * @return
     */
    @RequestMapping("/weChat")
    public String authorize(){
        String authorizationUrl = WeChatConfig.getAuthorizationUrl;
        String redirectUrl = myProps.getHost().get("website")+"/sns/loginByWeChat";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("微信回调地址编码出错！");
            return "redirect:/sns/weChat";
        }
        String state = GeneratorUtil.getUUID();
        //微信回调状态存入redis
        redisOperate.set("WeChatOauthState:"+state,"1",0);
        authorizationUrl.replace("APPID",WeChatConfig.appID);
        authorizationUrl.replace("REDIRECT_URI",redirectUrl);
        authorizationUrl.replace("SCOPE","snsapi_login");
        authorizationUrl.replace("STATE",state);
        return "redirect:"+ authorizationUrl;
    }

    @RequestMapping("/loginByWeChat")
    public String loginByWeChat(String state, String code){
        if (redisOperate.get("WeChatOauthState:"+state) == null){
            //回调状态错误或者超时 重新扫描二维码
            return "redirect:/sns/weChat";
        }
        if (StringUtils.isNotBlank(code)){
            //未获取用户授权
            return "redirect:/sns/weChat";
        }
        //通过code获取access_token  有效期默认为两个小时
        WeChatAuthInfo weChatAuthInfo = getAccessToken(code);
        String openid = weChatAuthInfo.getOpenid();
        String accessToken = weChatAuthInfo.getAccess_token();
        if (weChatAuthInfo == null || weChatAuthInfo.getErrcode()!=null || !StringUtils.isNotBlank(openid)){
            //获取access_token出错  重新扫码
            logger.error("获取微信用户access_token错误！");
            return "redirect:/sns/weChat";
        }
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("openid",openid));
        //获取用户openid之后 检查是否已经绑定平台账号
        WxUserInfo wxUserInfo = wxUserInfoService.selectOne(filter);
        if (wxUserInfo == null){
            //未绑定账号 重定向到绑定手机页面
            redisOperate.set("WeChatAccessToken:"+openid,accessToken,7200);
            return "redirect:"+myProps.getHost().get("auth")+"/sns/bindWeChat?openid"+openid+"&state="+state;
        }
        User user = userService.selectById(wxUserInfo.getUserid());
        redisOperate.del("WeChatOauthState:"+state);
        return "redirect:"+myProps.getHost().get("auth")+"login?userName="+user.getUserName()+"&password="+user.getPassword();
    }

    private WeChatAuthInfo getAccessToken(String code) {
        String getAccessTokenUrl = WeChatConfig.getAccessTokenUrl;
        getAccessTokenUrl.replace("APPID",WeChatConfig.appID);
        getAccessTokenUrl.replace("SECRET",WeChatConfig.appSecret);
        getAccessTokenUrl.replace("CODE",code);
        WeChatAuthInfo weChatAuthInfo = null;
        try {
            Map resultMap = HttpClientUtil.GetMethod(getAccessTokenUrl);
            if (resultMap.get("resultCode").equals("200")){
                String result = (String) resultMap.get("result");
                weChatAuthInfo = JsonUtils.toObject(result, WeChatAuthInfo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return weChatAuthInfo;
    }
}
