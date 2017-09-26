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

    /**
     * 前台奖励推荐页面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/recommendInfo")
    public String recommendInfo(HttpServletRequest request) throws Exception {
        try{
            String userId = getCurrentUser().getUserId();
            String secret = URLEncoder.encode(SecretUtil.getSecret(userId), "UTF-8");
            String url=PropertiesManager.getInstance().getProperty("recommend.url");
            HashMap<String , Integer> map=wxUserRecommendService.countInviteeAndReward(userId);
            //将推荐人数（inviteeNum），推荐获得奖金（rewardMoney）返回页面
            request.setAttribute("inviteeInfo",map);
            //将我的专属推广链接返回页面
            request.setAttribute("recommendUrl",url+"?token="+secret);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/usercenter/userInfo/rewardRecommend";
    }

}
