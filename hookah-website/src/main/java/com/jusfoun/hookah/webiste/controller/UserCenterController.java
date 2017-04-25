package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.domain.vo.SysNewsVo;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.shiro.SecurityUtils;
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


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String userInfo() {
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
     * @param userId
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
}
