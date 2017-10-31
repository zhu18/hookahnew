package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.config.WeChatConfig;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.WeChatOAuthInfo;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import com.jusfoun.hookah.rpc.api.WxUserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2017/10/31.
 */
@Controller
public class LoginByWeChatController {
    @Resource
    private WxUserInfoService wxUserInfoService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/loginByWeChat", method = RequestMethod.POST)
    public String loginByWeChat(HttpServletRequest request){
         //微信接口自带 2 个参数
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (code!=null && !"".equals(code)){
            WeChatOAuthInfo weChatOAuthInfo = getAccess_token(code);
            String openId = weChatOAuthInfo.getOpenid();
            String accessToken = weChatOAuthInfo.getAccess_token();

            if(accessToken == null) {
                return "redirect:";
            }

            // 数据库中查询微信号是否绑定平台账号
            List<Condition> filter = new ArrayList<>();
            filter.add(Condition.eq("openId",openId));
            WxUserInfo wxUserInfo = wxUserInfoService.selectOne(filter);
            if(wxUserInfo == null) {
                //request.getSession().setAttribute(openid, randomStr);
                // 尚未绑定账号 重定向到绑定账号页面
                return "redirect:/index.jsp?openid=";
            }
            User user = userService.selectById(wxUserInfo.getUserid());
            //账号已绑定 重定向到登陆
            return "forward:/login?userName="+user.getUserName()+"&password="+user.getPassword();
        }
        // 未授权
        return null;
    }

    public String getStartURLToGetCode() throws Exception{
        String takenUrl = WeChatConfig.getSnsApiUserInfoUrl;
        takenUrl= takenUrl.replace("APPID", WeChatConfig.appID);
        takenUrl= takenUrl.replace("REDIRECT_URI", URLEncoder.encode("http://www.bdgstore.cn/exchange/index","UTF-8"));
        //FIXME ： snsapi_userinfo
        takenUrl= takenUrl.replace("SCOPE", "snsapi_userinfo");
        return takenUrl;
    }

    public WeChatOAuthInfo getAccess_token(String code){
        String authUrl = WeChatConfig.getAccessTokenUrl;
        authUrl= authUrl.replace("APPID", WeChatConfig.appID);
        authUrl = authUrl.replace("SECRET", WeChatConfig.appSecret);
        authUrl = authUrl.replace("CODE", code);
        Map resultMap = HttpClientUtil.GetMethod(authUrl);
        WeChatOAuthInfo weChatOAuthInfo = null;
        try {
            if (resultMap.get("resultCode").equals("200")) {
                weChatOAuthInfo = JsonUtils.toObject((String) resultMap.get("result"), WeChatOAuthInfo.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return weChatOAuthInfo;
    }
}
