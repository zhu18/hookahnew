package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.webiste.config.MyProps;
import com.jusfoun.hookah.webiste.util.SecretUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

/**
 * Created by lt on 2017/9/26.
 */
@Controller
@RequestMapping("/userRecommend")
public class RecommendController extends BaseController{

    @Resource
    MyProps myProps;
    @RequestMapping(value = "/recommendReg", method = RequestMethod.GET)
    public String recommendReg(@RequestParam String recommendToken, RedirectAttributes redirectAttributes){
        String token = recommendToken.split("&")[1].split(":")[1];
        String userId = recommendToken.split("&")[0].split(":")[1];
        String secret = SecretUtil.getSecret(userId).split("&")[1].split(":")[1];
        if (!token.equals(secret)){
            return "";  //错误页面
        }
        redirectAttributes.addAttribute("recommendToken",recommendToken);

        return "redirect:"+myProps.getHost().get("auth")+"/reg/recommendReg";
    }

}
