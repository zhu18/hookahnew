package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.rpc.api.WXUserRecommendService;
import com.jusfoun.hookah.webiste.util.PropertiesManager;
import com.jusfoun.hookah.webiste.util.SecretUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by ndf on 2017/9/22.
 */

@Controller
@RequestMapping("/userRecommend")
public class UserRecommendController extends BaseController{

    @Resource
    private WXUserRecommendService wxUserRecommendService;

    @RequestMapping("/recommendInfo")
    public String recommendInfo(HttpServletRequest request) throws Exception {
        try{
            String userId = getCurrentUser().getUserId();
            String secret = URLEncoder.encode(SecretUtil.getSecret(userId), "UTF-8");
            String url=PropertiesManager.getInstance().getProperty("recommend.url");
            HashMap<String , String> map=wxUserRecommendService.countInviteeAndReward(userId);
            request.setAttribute("inviteeInfo",map);
            request.setAttribute("recommendUrl",url+"?token="+secret);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/usercenter/userInfo/rewardRecommend";
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
    };
}
