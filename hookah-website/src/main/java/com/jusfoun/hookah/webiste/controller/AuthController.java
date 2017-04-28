package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.UserDetailService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * All rights Reserved, Designed By
 * Copyright:  Copyright(C) 2016-2020
 * Company:    ronwo.com LTD.
 *
 * @version V1.0
 *          Createdate: 2016/11/12 下午11:53
 *          Modification  History:
 *          Date         Author        Version        Discription
 *          -----------------------------------------------------------------------------------
 *          2016/11/12     huanglei         1.0             1.0
 *          Why & What is modified: <修改原因描述>
 * @author: huanglei
 */
@Controller
public class AuthController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    UserDetailService userDetailService;

    @Resource
    OrganizationService organizationService;

    //认证状态(0.未认证 1.认证中 2.已认证 3.认证失败)
    public static final Byte AUTH_STATUS_SUCCESS = 2;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Model pLogin(Model model) {
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        return "register";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:http://localhost:9900/logout";
    }

    /**
     * 用户，个人，企业认证首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/auth/index", method = RequestMethod.GET)
    public String auth(Model model) throws Exception {
        return "/auth/auth_index";
    }

    /**
     * 个人认证
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/auth/user_auth_init", method = RequestMethod.GET)
    public String userAuth(Model model) throws Exception {
        return "/auth/user_auth_init";
    }
    @RequestMapping(value = "/auth/user_auth_init_step2", method = RequestMethod.GET)
    public String userAuth2(Model model) throws Exception {
        return "/auth/user_auth_init_step2";
    }
    @RequestMapping(value = "/auth/user_auth_init_step3", method = RequestMethod.GET)
    public String userAuth3(Model model) throws Exception {
        return "/auth/user_auth_init_step3";
    }
    @RequestMapping(value = "/auth/user_auth_init_step4", method = RequestMethod.GET)
    public String userAuth4(Model model) throws Exception {
        return "/auth/user_auth_init_step4";
    }
    /**
     * 公司认证
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/auth/company_auth_init", method = RequestMethod.GET)
    public String companyAuth(Model model) throws Exception {
        return "/auth/company_auth_init";
    }
    @RequestMapping(value = "/auth/company_auth_init_step2", method = RequestMethod.GET)
    public String companyAuth2(Model model) throws Exception {
        return "/auth/company_auth_init_step2";
    }
    @RequestMapping(value = "/auth/company_auth_init_step3", method = RequestMethod.GET)
    public String companyAuth3(Model model) throws Exception {
        return "/auth/company_auth_init_step3";
    }
    @RequestMapping(value = "/auth/company_auth_init_step4", method = RequestMethod.GET)
    public String companyAuth4(Model model) throws Exception {
        return "/auth/company_auth_init_step4";
    }

    @RequestMapping(value = "/auth/personAuth", method = RequestMethod.GET)
    public ReturnData personAuth(Model model, UserDetail userDetail)  {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            userDetail.setUserId(userId);
            userDetail.setIsAuth(AUTH_STATUS_SUCCESS);
            userDetailService.updateByIdSelective(userDetail);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping(value = "/auth/orgAuth", method = RequestMethod.GET)
    public ReturnData orgAuth(Model model, Organization organization)  {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
//            organization.setUserId(userId);
            organization.setIsAuth(AUTH_STATUS_SUCCESS);
            organizationService.updateByIdSelective(organization);
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.toString());
            e.printStackTrace();
        }
        return returnData;
    }

}
