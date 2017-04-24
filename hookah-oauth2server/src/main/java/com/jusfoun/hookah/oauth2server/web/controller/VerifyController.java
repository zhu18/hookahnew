package com.jusfoun.hookah.oauth2server.web.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * @author huang lei
 * @date 2017/4/12 上午9:39
 * @desc
 */
@Controller
@RequestMapping("/verify")
public class VerifyController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String search(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String,String> userMap = (HashMap<String,String>)session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        model.addAttribute("userObj",user);
        model.addAttribute("title","验证身份");
        return "modify/verify";
    }

    /**
     * 验证支付密码
     * @param userForm
     * @param model
     * @return
     */
    @RequestMapping(value = "/verifyPayPassword", method = RequestMethod.POST)
    public ReturnData verifyPayPassword(User userForm,Model model) {
        ReturnData returnData = new ReturnData();
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        if(StringUtils.isNotBlank(userForm.getPaymentPassword())){
            String newPassword = new Md5Hash(userForm.getPaymentPassword()).toString();

           String oldPayPassword =  user.getPaymentPassword();
           if(oldPayPassword.equals(newPassword)){
               returnData.setMessage("支付密码正确！");
           }else{
               returnData.setCode(ExceptionConst.Failed);
               returnData.setMessage("支付密码错误！");
           }
        }else{
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("支付密码错误！");
        }
        return  returnData;
    }

}
