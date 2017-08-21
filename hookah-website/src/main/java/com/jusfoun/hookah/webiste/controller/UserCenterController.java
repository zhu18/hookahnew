package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.webiste.util.PropertiesManager;
import org.apache.commons.lang3.StringUtils;
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
import java.io.UnsupportedEncodingException;
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

    @Resource
    PayAccountService payAccountService;

    @Resource
    OrganizationService organizationService;

//    账户中心
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String userInfo(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> o = (HashMap<String, String>) session.getAttribute("user");
        String userId = o.get("userId");
        User user = userService.selectById(userId);
        PayAccount payAccount=payAccountService.findPayAccountByUserId(userId);
        Organization organization = organizationService.selectById(user.getOrgId());
        if(organization != null)
            model.addAttribute("orgName",organization.getOrgName());
        model.addAttribute("userCur",user);
        model.addAttribute("payAccount",payAccount);

        //用户审核信息
        model.addAttribute("userCheckResult",userCheckService.authDetail(user).getData());

        return "usercenter/userInfo/userInfo";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public String userProfile(Model model) {

        return "usercenter/userInfo/userProfile";
    }

    @RequestMapping(value = "/infoStation", method = RequestMethod.GET)
    public String infoStation(){
        return "usercenter/userInfo/infoStation";
    }

    @RequestMapping(value = "/unreadInfo", method = RequestMethod.GET)
    public String infoStation2(){
        return "usercenter/userInfo/unreadInfo";
    }

    @RequestMapping(value = "/infoCenter", method = RequestMethod.GET)
    public String infoCenterA() {
        return "usercenter/userInfo/infoCenter";
    }

//    @RequestMapping(value = "/infoDetail", method = RequestMethod.GET)
//    public String infoDetail() {
//        return "usercenter/userInfo/infoDetail";
//    }

    //       账户信息
    @RequestMapping(value = "/accountInformation", method = RequestMethod.GET)
    public String accountInformation() {
        return "usercenter/userInfo/accountInformation";
    }
    //       我要成为供应商第一步
    @RequestMapping(value = "/becomingSupplierStep1", method = RequestMethod.GET)
    public String becomingSupplierStep1(Model model) throws UnsupportedEncodingException {
        String address = PropertiesManager.getInstance().getProperty("protocol.address");
        model.addAttribute("address", new String(address.getBytes("ISO-8859-1"),"UTF-8"));
        model.addAttribute("email",PropertiesManager.getInstance().getProperty("protocol.email"));
        String name = PropertiesManager.getInstance().getProperty("protocol.name");
        model.addAttribute("name",new String(name.getBytes("ISO-8859-1"),"UTF-8"));
        model.addAttribute("phone",PropertiesManager.getInstance().getProperty("protocol.phone"));
        model.addAttribute("mobile",PropertiesManager.getInstance().getProperty("protocol.mobile"));
        return "usercenter/userInfo/becomingSupplierStep1";
    }
    //       我要成为供应商第二步
    @RequestMapping(value = "/becomingSupplierStep2", method = RequestMethod.GET)
    public String becomingSupplierStep2() {
        return "usercenter/userInfo/becomingSupplierStep2";
    }
    //       我要成为供应商第三步
    @RequestMapping(value = "/becomingSupplierStep3", method = RequestMethod.GET)
    public String becomingSupplierStep3() {
        return "usercenter/userInfo/becomingSupplierStep3";
    }
    //       实名认证
    @RequestMapping(value = "/nameAuthentication", method = RequestMethod.GET)
    public String nameAuthentication(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> o = (HashMap<String, String>) session.getAttribute("user");
        String userId = o.get("userId");
        User user = userService.selectById(userId);
        model.addAttribute("userCur",user);
        return "usercenter/userInfo/nameAuthentication";
    }
    //       联系信息
    @RequestMapping(value = "/contactInformation", method = RequestMethod.GET)
    public String contactInformation() {
        return "usercenter/userInfo/contactInformation";
    }
    //       联系信息编辑
    @RequestMapping(value = "/contactInformationEdit", method = RequestMethod.GET)
    public String contactInformationEdit() {
        return "usercenter/userInfo/contactInformationEdit";
    }
    //     资金总览页面
    @RequestMapping(value = "/fundmanage", method = RequestMethod.GET)
    public String fundmanage(Model model) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> o = (HashMap<String, String>) session.getAttribute("user");
        String userId = o.get("userId");
        User user = userService.selectById(userId);
        model.addAttribute("userCur",user);
        return "usercenter/userInfo/fundmanage";
    }

    //     绑定银行卡
    @RequestMapping(value = "/bindBankCard", method = RequestMethod.GET)
    public String bindBankCard() {
        return "/usercenter/userInfo/bindBankCard";
    }
    //     充值页面第一步
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String recharge() {
        return "/usercenter/userInfo/recharge";
    }
    //     充值页面第二步
    @RequestMapping(value = "/rechargeStep2", method = RequestMethod.GET)
    public String rechargeStep2() {
        return "/usercenter/userInfo/rechargeStep2";
    }
    // 充值失败
    @RequestMapping(value = "/rechargeFailure", method = RequestMethod.GET)
    public String rechargeFailure() {
        return "/usercenter/userInfo/rechargeFailure";
    }
    // 充值成功
    @RequestMapping(value = "/rechargeSuccess", method = RequestMethod.GET)
    public String rechargeSuccess() {
        return "/usercenter/userInfo/rechargeSuccess";
    }
    //     取款页面
    @RequestMapping(value = "/withdrawals", method = RequestMethod.GET)
    public String withdrawals() {
        return "/usercenter/userInfo/withdrawals";
    }
    //     取款页面第二步
    @RequestMapping(value = "/withdrawalStep2", method = RequestMethod.GET)
    public String withdrawalStep2() {
        return "/usercenter/userInfo/withdrawalStep2";
    }
    //     取款页面第三步
    @RequestMapping(value = "/withdrawalStep3", method = RequestMethod.GET)
    public String withdrawalStep3() {
        return "/usercenter/userInfo/withdrawalStep3";
    }
    //     资金记录页面
    @RequestMapping(value = "/capitalRecord", method = RequestMethod.GET)
    public String capitalRecord() {
        return "/usercenter/userInfo/capitalRecord";
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
    @RequestMapping(value = "/verifyPayPassword", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData verifyPayPassword(String passWord,Model model) {
        ReturnData returnData = new ReturnData();
        try {
            Session session = SecurityUtils.getSubject().getSession();
            HashMap<String, String> userMap = (HashMap<String, String>) session.getAttribute("user");
            User user = userService.selectById(userMap.get("userId"));
            if(StringUtils.isNotBlank(passWord)){
                boolean flag = payAccountService.verifyPassword(passWord,user.getUserId());
                if(flag){
                    returnData.setMessage("支付密码正确！");
                }else{
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage("支付密码错误！");
                }
            }else{
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("请输入支付密码！");
            }
        }catch (Exception e){
                e.printStackTrace();
        }
        return  returnData;
    }

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String question() {
        return "/usercenter/question";
    }

    /**
     * 安全设置
     *

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/safetyScoreSet", method = RequestMethod.GET)
    public ReturnData safetyScoreSet(String land,String pay,String  phone,String mail) {
        Session session = SecurityUtils.getSubject().getSession();
        HashMap<String, String> o = (HashMap<String, String>) session.getAttribute("user");
        String userId = o.get("userId");
        User user = userService.selectById(userId);

        if (StringUtils.isNotBlank(land)) {
            user.setSafetyLandScore(Integer.parseInt(land));
        }
        if (StringUtils.isNotBlank(pay)) {
            user.setSafetyPayScore(Integer.parseInt(pay));
        }

        if (StringUtils.isNotBlank(phone)) {
            user.setSafetyPhoneScore(Integer.parseInt(phone));
        }
        if (StringUtils.isNotBlank(mail)) {
            user.setSafetyMailScore(Integer.parseInt(mail));
        }
        userService.updateByIdSelective(user);
        return ReturnData.success();
    }

}
