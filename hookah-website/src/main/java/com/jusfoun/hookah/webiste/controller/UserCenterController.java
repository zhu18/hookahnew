package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author huang lei
 * @date 2017/3/7 下午6:05
 * @desc
 */
@Controller
@RequestMapping(value = "/usercenter")
public class UserCenterController {

    @Resource
    SysNewsService sysNewsService;

    @Resource
    CategoryService categoryService;

    @Resource
    MgCategoryAttrTypeService mgCategoryAttrTypeService;

    @Resource
    GoodsService goodsService;

    @Resource
    MgGoodsService mgGoodsService;

    @Resource
    UserService userService;

    @Resource
    UserDetailService userDetailService;

    @Resource
    UserCheckService userCheckService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String userInfo(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> o = (HashMap<String, String>) session.getAttribute("user");
        String userId = o.get("userId");
        User user = userService.selectById(userId);
        model.addAttribute("userCur",user);

        //用户审核信息
        model.addAttribute("userCheckResult",userCheckService.authDetail(user).getData());

        return "usercenter/userInfo/userInfo";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public String userProfile(Model model) {

        return "usercenter/userInfo/userProfile";
    }

    @RequestMapping(value = "/infoCenter", method = RequestMethod.GET)
    public String infoCenterA() {
        return "usercenter/userInfo/infoCenter";
    }

//    @RequestMapping(value = "/infoDetail", method = RequestMethod.GET)
//    public String infoDetail() {
//        return "usercenter/userInfo/infoDetail";
//    }

    @RequestMapping(value = "/fundmanage", method = RequestMethod.GET)
    public String fundmanage() {
        return "usercenter/userInfo/fundmanage";
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String recharge() {
        return "/usercenter/userInfo/recharge";
    }

    @RequestMapping(value = "/withdrawals", method = RequestMethod.GET)
    public String withdrawals() {
        return "/usercenter/userInfo/withdrawals";
    }

    /**
     * 安全设置
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/safeSet", method = RequestMethod.GET)
    public String safeSet(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> o = (HashMap<String, String>) session.getAttribute("user");
        String userId = o.get("userId");
        User user = userService.selectById(userId);
        UserDetail userDetail = userDetailService.selectById(userId);
        model.addAttribute("userCur", user);
        model.addAttribute("userDetail", userDetail);
        return "usercenter/userInfo/safeSet";
    }


    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public String createOrder() {
        return "/usercenter/userInfo/createOrder";
    }

    /**
     * 根据用户ID获得用户支付密码状态
     * @param
     * @return 支付密码状态  0 未设置 ,1 已设置
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/payPassSta", method = RequestMethod.GET)
    public ReturnData  payPassSta() {
        ReturnData returnData = new ReturnData();
        try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            returnData.setData(user.getPaymentPasswordStatus());
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }


    /**
     * 验证支付密码
     * @param model
     * @return
     */
    @RequestMapping(value = "/verifyPayPassword", method = RequestMethod.GET)
    @ResponseBody
    public ReturnData verifyPayPassword(String paymentPassword,Model model) {
        ReturnData returnData = new ReturnData();
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
        User user = userService.selectById(userMap.get("userId"));
        if(StringUtils.isNotBlank(paymentPassword)){
            String newPassword = new Md5Hash(paymentPassword).toString();

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

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String question() {
        return "/usercenter/question";
    }

}
