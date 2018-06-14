package com.jusfoun.hookah.oauth2server.web.controller;

import org.springframework.stereotype.Controller;

/**
 * Created by lt on 2017/10/31.
 */
@Controller
public class LoginByWeChatController {
//    @Resource
//    private WxUserInfoService wxUserInfoService;
//    @Resource
//    private UserService userService;
//
//    @RequestMapping(value = "/loginByWeChat", method = RequestMethod.POST)
//    public String loginByWeChat(HttpServletRequest request){
//         //微信接口自带 2 个参数
//        String code = request.getParameter("code");
//        String state = request.getParameter("state");
//        if (code!=null && !"".equals(code)){
//            WeChatAuthInfo weChatOAuthInfo = getAccess_token(code);
//            String openId = weChatOAuthInfo.getOpenid();
//            String accessToken = weChatOAuthInfo.getAccess_token();
//
//            if(accessToken == null) {
//                return "redirect:";
//            }
//
//            // 数据库中查询微信号是否绑定平台账号
//            List<Condition> filter = new ArrayList<>();
//            filter.add(Condition.eq("openId",openId));
//            WxUserInfo wxUserInfo = wxUserInfoService.selectOne(filter);
//            if(wxUserInfo == null) {
//                // 尚未绑定账号 重定向到绑定账号页面
//                request.setAttribute("openId", openId);
//                request.setAttribute("accessToken", accessToken);
//                return "bindPhone";
//            }
//            User user = userService.selectById(wxUserInfo.getUserid());
//            //账号已绑定 重定向到登陆
//            return "forward:/login?userName="+user.getUserName()+"&password="+user.getPassword();
//        }
//        // 未授权
//        return null;
//    }
//
//    public String getStartURLToGetCode() throws Exception{
//        String takenUrl = WeChatConfig.getSnsApiUserInfoUrl;
//        takenUrl= takenUrl.replace("APPID", WeChatConfig.appID);
//        takenUrl= takenUrl.replace("REDIRECT_URI", URLEncoder.encode("http://www.lsdashuju.net/exchange/index","UTF-8"));
//        //FIXME ： snsapi_userinfo
//        takenUrl= takenUrl.replace("SCOPE", "snsapi_userinfo");
//        return takenUrl;
//    }
//
//    public WeChatAuthInfo getAccess_token(String code){
//        String authUrl = WeChatConfig.getAccessTokenUrl;
//        authUrl= authUrl.replace("APPID", WeChatConfig.appID);
//        authUrl = authUrl.replace("SECRET", WeChatConfig.appSecret);
//        authUrl = authUrl.replace("CODE", code);
//        WeChatAuthInfo weChatOAuthInfo = null;
//        try {
//            Map resultMap = HttpClientUtil.PostMethod(authUrl,"");
//            if (resultMap.get("resultCode").equals("200")) {
//                weChatOAuthInfo = JsonUtils.toObject((String) resultMap.get("result"), WeChatAuthInfo.class);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return weChatOAuthInfo;
//    }
}
