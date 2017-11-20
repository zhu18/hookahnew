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
import com.jusfoun.hookah.webiste.util.WXConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.*;

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
       String redirectUrl = myProps.getHost().get("website") + "/sns/loginByWeChat";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("微信回调地址编码出错！");
            return "redirect:/sns/weChat";
        }
        String state = GeneratorUtil.getUUID();
        //微信回调状态存入redis
        redisOperate.set("WeChatOauthState:"+state,"1",600);
        authorizationUrl = authorizationUrl.replace("APPID",WeChatConfig.appID);
        authorizationUrl = authorizationUrl.replace("REDIRECT_URI",redirectUrl);
        authorizationUrl = authorizationUrl.replace("SCOPE","snsapi_login");
        authorizationUrl = authorizationUrl.replace("STATE",state);
        return "redirect:"+ authorizationUrl;
    }

    @RequestMapping("/loginByWeChat")
    public String loginByWeChat(String state, String code){
        //1.微信超时页面 2.无效code页面
        if (!StringUtils.isNotBlank(redisOperate.get("WeChatOauthState:"+state))){
            //回调状态错误或者超时 重新扫描二维码
            return "redirect:/sns/weChat";
        }
        if (!StringUtils.isNotBlank(code)){
            //未获取用户授权
            return "redirect:/sns/weChat";
        }
        //通过code获取access_token  有效期默认为两个小时
        WeChatAuthInfo weChatAuthInfo = WXConfigUtils.getAccessToken(code);
        String openid = weChatAuthInfo.getOpenid();
        String accessToken = weChatAuthInfo.getAccess_token();
        if (weChatAuthInfo == null || weChatAuthInfo.getErrcode()!=null || !StringUtils.isNotBlank(openid)){
            //获取access_token出错  重新扫码
            logger.error("获取微信用户access_token错误！");
            return "redirect:/sns/weChat";
        }

        //检查access_token是否有效
        String refresh_token = weChatAuthInfo.getRefresh_token();
        Boolean checkAccessToken = WXConfigUtils.checkAccessToken(openid,accessToken);
        if(!checkAccessToken){
            //无效刷新access_token
            weChatAuthInfo = WXConfigUtils.refreshAccessToken(refresh_token);
            openid = weChatAuthInfo.getOpenid();
            accessToken = weChatAuthInfo.getAccess_token();
            if (weChatAuthInfo == null || weChatAuthInfo.getErrcode()!=null || !StringUtils.isNotBlank(openid)){
                //获取access_token出错  重新扫码
                logger.error("刷新微信用户access_token错误！");
                return "redirect:/sns/weChat";
            }
        }

        //获取微信用户信息
        WxUserInfo wx = WXConfigUtils.getWxUserInfo(openid,accessToken);
        if(Objects.isNull(wx)){
            //获取access_token出错  重新扫码
            logger.error("获取微信用户信息错误！");
            return "redirect:/sns/weChat";
        }

        //扫描用户信息是否存在
        String wxOpenid = wx.getOpenid();
        List<Condition> filter = new ArrayList<>();
        filter.add(Condition.eq("openid",wxOpenid));
        //获取用户openid之后 检查是否已经绑定平台账号
        WxUserInfo wxUserInfo = wxUserInfoService.selectOne(filter);
        System.out.println("查看wxUserInfoservice的实现类:" + wxUserInfoService);
        //该用户没有绑定过且用户没有扫描过
        if (wxUserInfo == null){
            //保存微信用户信息
            wx.setAddTime(new Date());
            wx.setStatus(Byte.valueOf("0"));//默认值
            //用户特权信息
            String[] privilegeArray = wx.getPrivilege();
            if(Objects.nonNull(privilegeArray) && privilegeArray.length > 0){
                StringBuffer privileges = new StringBuffer();
                for (String pr : privilegeArray) {
                    privileges.append(pr).append(",");
                }
                wx.setPrivileges(privileges.toString());
            }
            wxUserInfoService.insert(wx);
            //未绑定账号 重定向到绑定手机页面
            return "redirect:"+myProps.getHost().get("auth")+"/sns/bindWeChat?openid"+openid+"&state="+state;
        }

        //该用户没有绑定过且用户扫描过
        String userid = wxUserInfo.getUserid();
        if(Objects.isNull(userid)){
            //未绑定账号 重定向到绑定手机页面
            return "redirect:"+myProps.getHost().get("auth")+"/sns/bindWeChat?openid="+openid+"&state="+state;
        }

        User user = userService.selectById(wxUserInfo.getUserid());
        redisOperate.del("WeChatOauthState:"+state);
        return "redirect:"+myProps.getHost().get("auth")+"login?userName="+user.getUserName()+"&password="+user.getPassword();
    }

}
