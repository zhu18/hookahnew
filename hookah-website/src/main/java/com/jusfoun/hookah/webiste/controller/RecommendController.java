package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.webiste.config.MyProps;
import com.jusfoun.hookah.webiste.util.SecretUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lt on 2017/9/26.
 */
@Controller
@RequestMapping("/userRecommend")
public class RecommendController extends BaseController{

    @Resource
    MyProps myProps;
    @RequestMapping(value = "recommendReg", method = RequestMethod.GET)
    public String recommendReg(String token, Model model){
        try {
            String unSecret = URLDecoder.decode(token,"UTF-8");
            token = unSecret.split("&")[1].split(":")[1];
            String userId = unSecret.split("&")[0].split(":")[1];
            if (!token.equals(SecretUtil.getSecret(userId))){
                return "";
            }
            model.addAttribute("userId",userId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:"+myProps.getHost().get("auth")+"/reg";
    }

}
