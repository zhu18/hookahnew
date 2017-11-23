package com.jusfoun.hookah.webiste.controller;

import com.alibaba.fastjson.JSON;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.*;
import com.jusfoun.hookah.webiste.config.MyProps;
import com.jusfoun.hookah.webiste.util.PropertiesManager;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
public class AuthController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    MyProps myProps;

    @Resource
    UserDetailService userDetailService;

    @Resource
    OrganizationService organizationService;

    @Resource
    UserService userService;

    @Resource
    RedisOperate redisOperate;

    @Resource
    UserCheckService userCheckService;

    @Resource
    SupplierService supplierService;


    //认证状态(0.未认证 1.认证中 2.已认证 3.认证失败)
    public static final Byte AUTH_STATUS_SUCCESS = 2;
    public static final Byte AUTH_STATUS_CHECKING = 1;

    //是否成为供应商
    public static final Byte IS_SUPPLIER_NO = 0;
    public static final Byte IS_SUPPLIER_YES = 1;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(String redirect_uri, HttpServletRequest request) {
        if (!StringUtils.isEmpty(redirect_uri)) {
            return "redirect:" + redirect_uri;
        } else {
            userService.setUVCountByDate();
            return "redirect:/exchange/index";
        }
    }

    @RequestMapping(value = "/islogin", method = RequestMethod.POST)
    @ResponseBody
    public boolean isLogin(Model model) {
        Map userMap = null;
        try {
            userMap = (HashMap) SecurityUtils.getSubject().getSession().getAttribute("user");
            if (userMap == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

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
        return "redirect:" + myProps.getHost().get("website") + "/exchange/index";
    }

    /**
     * 用户，个人，企业认证首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/auth/index")
    public String auth(Model model) throws Exception {
        return "/auth/auth_index";
    }

    /**
     * 个人认证
     *
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
    @RequestMapping(value = "/auth/user_auth_init_step5", method = RequestMethod.GET)
    public String userAuth5(Model model) throws Exception {
        User user = userService.selectById(this.getCurrentUser().getUserId());
        if(null != user && user.getUserType().equals(HookahConstants.UserType.PERSON_CHECK_FAIL.getCode())){
            model.addAttribute("message",((UserCheck)userCheckService.checkDetail(user.getUserId()).getData()).getCheckContent());
        }
        return "/auth/user_auth_init_step5";
    }

    /**
     * 公司认证
     *
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
        String address = PropertiesManager.getInstance().getProperty("protocol.address");
        model.addAttribute("address", new String(address.getBytes("ISO-8859-1"),"UTF-8"));
        model.addAttribute("email",PropertiesManager.getInstance().getProperty("protocol.email"));
        String name = PropertiesManager.getInstance().getProperty("protocol.name");
        model.addAttribute("name",new String(name.getBytes("ISO-8859-1"),"UTF-8"));
        model.addAttribute("phone",PropertiesManager.getInstance().getProperty("protocol.phone"));
        model.addAttribute("mobile",PropertiesManager.getInstance().getProperty("protocol.mobile"));
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
    @RequestMapping(value = "/auth/company_auth_init_step5", method = RequestMethod.GET)
    public String companyAuth5(Model model) throws Exception {
        User user = userService.selectById(this.getCurrentUser().getUserId());
        if(null != user && user.getUserType().equals(HookahConstants.UserType.ORGANIZATION_CHECK_FAIL.getCode())){
            model.addAttribute("message",((UserCheck)userCheckService.checkDetail(user.getUserId()).getData()).getCheckContent());
        }
        return "/auth/company_auth_init_step5";
    }

    @RequestMapping(value = "/auth/personAuth", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData personAuth(Model model, UserDetail userDetail) {
        try {
            String userId = this.getCurrentUser().getUserId();
            userDetail.setUserId(userId);
            userDetail.setIsAuth(AUTH_STATUS_CHECKING);

            // 判断该身份证是否已绑定
            User user = userService.selectById(userId);
            if(user.getUserType() == 1 || user.getUserType() == 2 ||
                    user.getUserType() == 3 || user.getUserType() == 4 ||
                    user.getUserType() == 5){
                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("cardNum", userDetail.getCardNum()));
                boolean exists = userDetailService.exists(filters);
                if(exists == true){
                    return ReturnData.error("该身份证已绑定信息");
                }
            }

            // 验证个人身份
            Map<String, String> map = new HashMap<>(6);
            // 获取身份证认证url
            String url = PropertiesManager.getInstance().getProperty("identity.url");
            map.put("name", userDetail.getRealName());
            map.put("idCard", userDetail.getCardNum());
            // 定义请求头
            String token = PropertiesManager.getInstance().getProperty("identity.token");
            Header header = new BasicHeader("token",token);
            String result = HttpClientUtil.sendHttpPost(url, JSON.toJSONString(map), header);
            Map mapTypes = JSON.parseObject(result);
            // 验证个人认证超限-超过3次认证失败，24小时后重新认证
            String personAuth = redisOperate.get("personAuth:" + userId);
            if(personAuth != null){
                String  errNum = redisOperate.get("person:" + userId);
                if(errNum != null && Integer.parseInt(errNum) < 3){
                    if(mapTypes.get("code").equals("2000")){ // 成功
                        redisOperate.del("personAuth:" + userId);
                        redisOperate.del("person:" + userId);
//                        returnData.setMessage("恭喜您！验证成功！");
//                        returnData.setData(personAuthInfo(userDetail, userId));
                        personAuthInfo(userDetail, userId);
                        logger.info("恭喜您！验证成功！");
                        return ReturnData.success("恭喜您！验证成功！");
                    }else { // 验证失败
                        redisOperate.incr("person:" + userId);
                        logger.info("验证失败！请重新验证！");
                        return ReturnData.error("验证失败！请重新验证！");
                    }
                }else {
                    return ReturnData.error("验证超限！请24小时后重新认证！");
                }
            }else {
                redisOperate.del("personAuth:" + userId);
                redisOperate.del("person:" + userId);
                if(mapTypes.get("code").equals("2000")) { // 成功
                    personAuthInfo(userDetail, userId);
                    logger.info("恭喜您！验证成功！");
                    return ReturnData.success("恭喜您！验证成功！");
                }else { // 验证失败
                    redisOperate.set("personAuth:" + userId, "1", 60 * 60 * 24);
                    redisOperate.incr("person:" + userId);
                    logger.info("验证失败！请重新验证！");
                    return ReturnData.error("验证失败！请重新验证！");
                }
            }
        } catch (Exception e) {
            logger.error("个人认证信息操作失败！^_^", e);
            return ReturnData.error("个人认证信息操作失败！^_^");
        }
    }

    // 个人认证
    public UserDetail personAuthInfo(UserDetail userDetail, String userId){
        UserDetail userDetail1 = userDetailService.selectById(userId);
        if (userDetail1 != null) {
            int count = userDetailService.updateByIdSelective(userDetail);
        } else {
            userDetail = userDetailService.insert(userDetail);
        }

        User user = new User();
        user.setUserId(userId);
        // 用户待审核状态
        user.setUserType(HookahConstants.UserType.PERSON_CHECK_NO.getCode());
        userService.updateByIdSelective(user);
        return  userDetail;
    }

    @Log(platform = "front",logType = "f0007",optType = "insert")
    @RequestMapping(value = "/auth/orgAuth", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData orgAuth(Model model, Organization organization) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();

            // 判断该身份证是否已绑定
            User user = userService.selectById(userId);
            if(user.getUserType() == 1 || user.getUserType() == 2 ||
                    user.getUserType() == 3 || user.getUserType() == 4 ||
                    user.getUserType() == 5){
                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("lawPersonNum", organization.getLawPersonNum()));
                boolean exists = organizationService.exists(filters);
                if(exists == true){
                    return ReturnData.error("该身份证已绑定信息");
                }
            }

            // 验证企业身份
            Map<String, String> map = new HashMap<>(6);
            // 获取身份证认证url
            String url = PropertiesManager.getInstance().getProperty("identity.url");
            map.put("name", organization.getLawPersonName());
            map.put("idCard", organization.getLawPersonNum());
            // 定义请求头
            String token = PropertiesManager.getInstance().getProperty("identity.token");
            Header header = new BasicHeader("token",token);
            String result = HttpClientUtil.sendHttpPost(url, JSON.toJSONString(map), header);
            Map mapTypes = JSON.parseObject(result);
            // 验证企业认证超限-超过3次认证失败，24小时后重新认证
            String personAuth = redisOperate.get("orgAuth:" + userId);
            if(personAuth != null){
                String  errNum = redisOperate.get("org:" + userId);
                if(errNum != null && Integer.parseInt(errNum) < 3){
                    if(mapTypes.get("code").equals("2000")){ // 成功
                        redisOperate.del("orgAuth:" + userId);
                        redisOperate.del("org:" + userId);
                        orgAuthInfo(organization, userId);
                        logger.info("恭喜您！验证成功！");
                        return ReturnData.success("恭喜您！验证成功！");
                    }else { // 验证失败
                        redisOperate.incr("org:" + userId);
                        logger.info("验证失败！请重新验证！");
                        return ReturnData.error("验证失败！请重新验证！");
                    }
                }else {
                    return ReturnData.error("验证超限！请24小时后重新认证！");
                }
            }else {
                redisOperate.del("orgAuth:" + userId);
                redisOperate.del("org:" + userId);
                if(mapTypes.get("code").equals("2000")) { // 成功
                    orgAuthInfo(organization, userId);
                    logger.info("恭喜您！验证成功！");
                    return ReturnData.success("恭喜您！验证成功！");
                }else { // 验证失败
                    redisOperate.set("orgAuth:" + userId, "1", 60 * 60 * 24);
                    redisOperate.incr("org:" + userId);
                    logger.info("验证失败！请重新验证！");
                    return ReturnData.error("验证失败！请重新验证！");
                }
            }
        } catch (Exception e) {
            logger.error("企业认证信息操作失败！^_^", e);
            return ReturnData.error("企业认证信息操作失败！^_^");
        }
    }

    // 企业认证
    public void orgAuthInfo(Organization organization, String userId){
        User user = userService.selectById(userId);
        String orgId = user.getOrgId();

        User user1 = new User();
        user1.setUserId(userId);
        if(!StringUtils.isEmpty(orgId) && !"0".equals(orgId)){
            //            organization.setUserId(userId);
            organization.setIsAuth(AUTH_STATUS_SUCCESS);
            organization.setOrgId(orgId);
            organizationService.updateByIdSelective(organization);
            //给注册的供应商orgId赋值
            user1.setOrgId(orgId);
        }else {
            organization = organizationService.insert(organization);
            user1.setOrgId(organization.getOrgId());
        }

        //成为供应商
        if(IS_SUPPLIER_YES.equals(organization.getIsSupplier())){
            Supplier supplier = new Supplier();
            supplier.setAddTime(new Date());
            supplier.setUserId(userId);
            supplier.setOrgId(user1.getOrgId());
            supplier.setCheckStatus(Byte.valueOf("0"));
            supplier.setOrgName(organization.getOrgName());
            supplier.setContactName(user.getContactName());
            //重新审核 查看是否存在
            if(!StringUtils.isEmpty(orgId) && !"0".equals(orgId)){
                List<Condition> fifters = new ArrayList<>();
                fifters.add(Condition.eq("orgId",orgId));
                List<Supplier> suppliers = supplierService.selectList(fifters);
                if(Objects.nonNull(suppliers) && suppliers.size() > 0){
                    supplier.setId(suppliers.get(0).getId());
                    supplierService.updateByIdSelective(supplier);
                }else{
                    supplierService.insert(supplier);
                }
            }else{
                supplierService.insert(supplier);
            }

            //供应商待审核状态
//                user1.setUserType(HookahConstants.UserType.ORGANIZATION_SUPPLIER_CHECK_NO.getCode());
            user1.setSupplierStatus(HookahConstants.SupplierStatus.CHECK_STATUS.getCode());
        }
        //企业待审核状态
        user1.setUserType(HookahConstants.UserType.ORGANIZATION_CHECK_NO.getCode());

        userService.updateByIdSelective(user1);

    }
}
