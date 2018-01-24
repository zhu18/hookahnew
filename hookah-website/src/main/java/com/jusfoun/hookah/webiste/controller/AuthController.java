package com.jusfoun.hookah.webiste.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.jusfoun.hookah.core.annotation.Log;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.constants.TongJiEnum;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.bo.JfBo;
import com.jusfoun.hookah.core.domain.mongo.MgTongJi;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import com.jusfoun.hookah.webiste.config.MyProps;
import com.jusfoun.hookah.webiste.util.PropertiesManager;
import com.jusfoun.hookah.webiste.util.ReadCookieUtil;
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
import javax.servlet.http.Cookie;
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

    @Resource
    MgTongJiService mgTongJiService;

    @Resource
    MqSenderService mqSenderService;

    @Resource
    private WXUserRecommendService wXUserRecommendService;


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
    public ReturnData personAuth(Model model, UserDetail userDetail, HttpServletRequest request) {
        try {
            String userId = this.getCurrentUser().getUserId();
            userDetail.setUserId(userId);
            userDetail.setIsAuth(AUTH_STATUS_CHECKING);

            // 判断该身份证是否已绑定
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("cardNum", userDetail.getCardNum()));
            boolean exists = userDetailService.exists(filters);
            if(exists == true){
                return ReturnData.error("该身份证已进行个人认证，无法重复认证!");
            }

            // 验证个人身份
            Map<String, String> map = new HashMap<>(6);
            // 获取身份证认证url
            map.put("name", userDetail.getRealName());
            map.put("idCard", userDetail.getCardNum());
            // 定义请求头
            Header header = new BasicHeader("token", HookahConstants.PERSON_AUTH_TOKEN);
            String result = HttpClientUtil.sendHttpPost(HookahConstants.PERSON_AUTH_URL,
                    JSON.toJSONString(map), header);
            JsonNode data = JsonUtils.toObject(result.toString(), JsonNode.class);
            // 验证个人认证超限-超过3次认证失败，24小时后重新认证
            String personAuth = redisOperate.get("personAuth:" + userId);
            String code = data.get("code").textValue();

            if(personAuth != null){
                String  errNum = redisOperate.get("person:" + userId);
                if(errNum != null && Integer.parseInt(errNum) < 3){
                    // 验证个人认证信息返回是否正确
                    if(code.equals("2000")){
                        String checkResult = data.get("payload").get("checkResult").textValue();
                        // 判断姓名与身份证号是否一致  1： 一致 2：不一致 3：无此记录
                        if(checkResult.equals("1")){
                            redisOperate.del("personAuth:" + userId);
                            redisOperate.del("person:" + userId);
                            personAuthInfo(userDetail, userId, request);
                            logger.info("恭喜您！验证成功！");
                            return ReturnData.success("恭喜您！验证成功！");
                        }else {
                            redisOperate.incr("person:" + userId);
                            logger.info("姓名与身份证号码不匹配，请重新录入!");
                            return ReturnData.error("姓名与身份证号码不匹配，请重新录入!");
                        }
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
                if(code.equals("2000")) { // 成功
                    String checkResult = data.get("payload").get("checkResult").textValue();
                    if(checkResult.equals("1")){
                        personAuthInfo(userDetail, userId, request);
                        logger.info("恭喜您！验证成功！");
                        return ReturnData.success("恭喜您！验证成功！");
                    }else {
                        redisOperate.incr("person:" + userId);
                        logger.info("姓名与身份证号码不匹配，请重新录入!");
                        return ReturnData.error("姓名与身份证号码不匹配，请重新录入!");
                    }
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
    public void personAuthInfo(UserDetail userDetail, String userId, HttpServletRequest request){
        UserDetail userDetail1 = userDetailService.selectById(userId);
        if (userDetail1 != null) {
            int count = userDetailService.updateByIdSelective(userDetail);
        } else {
            userDetail = userDetailService.insert(userDetail);
        }
        User user = new User();
        user.setUserId(userId);
        // 个人认证成功状态
        user.setUserType(HookahConstants.UserType.PERSON_CHECK_OK.getCode());
        userService.updateByIdSelective(user);

        if(user.getUserType().equals(HookahConstants.UserType.PERSON_CHECK_OK.getCode())){
            mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSGINFO, new JfBo(user.getUserId(), 3, ""));
            logger.info("个人用户通过审核发放积分【账号身份认证】>>>>>userId = " + user.getUserId());
        }

        //更新微信用户推荐表
        wXUserRecommendService.updateWXUserRecommendIsAuthenticate(user.getUserId());

        // 个人认证之后插入统计地址
        try {
            Map<String, Cookie> cookieMap = ReadCookieUtil.ReadCookieMap(request);
            Cookie tongJi = cookieMap.get("TongJi");
            if(tongJi != null){
                MgTongJi tongJiInfo = mgTongJiService.getTongJiInfo(tongJi.getValue());
                mgTongJiService.setTongJiInfo(TongJiEnum.PERSON_URL, tongJiInfo.getTongJiId(),
                        tongJiInfo.getUtmSource(), tongJiInfo.getUtmTerm(), userId);
            }
        }catch (Exception e){
            logger.error("插入个人认证统计信息失败{}{}",userId,e.getMessage());
        }
    }

    @Log(platform = "front",logType = "f0007",optType = "insert")
    @RequestMapping(value = "/auth/orgAuth", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData orgAuth(Model model, Organization organization, HttpServletRequest request) {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            User user = userService.selectById(userId);

            // 判断该身份证是否已绑定
            List<Condition> filters = new ArrayList();
            filters.add(Condition.eq("orgName", organization.getOrgName()));
            boolean exists = organizationService.exists(filters);
            // 验证当前是否为已认证和待审核状态
            if(user.getUserType() == 1 || user.getUserType() == 4 || user.getUserType() == 5){
                if(exists == true){
                    return ReturnData.error("该企业已进行单位认证，无法重复认证!");
                }
            }

            // 验证企业身份
            Map<String, Object> map = new HashMap<>(6);
            map.put("entName", organization.getOrgName());
            // 定义请求头
            Header header = new BasicHeader("token", HookahConstants.ORGANIZATION_AUTH_TOKEN);
            String result = HttpClientUtil.sendHttpFormPost(HookahConstants.ORGANIZATION_AUTH_URL,
                    com.jusfoun.hookah.core.utils.StringUtils.getUrlParamsByMap(map),  header);
            JsonNode data = JsonUtils.toObject(result.toString(), JsonNode.class);
            //判断企业认证信息是否正确
            String isCheck = "0";
            if(data.get("ReturnCode").toString().equals("1")){
                String societyCode = data.get("Result").get("societyCode").textValue();
                String name = data.get("Result").get("name").textValue();
                if(!societyCode.equals(organization.getCreditCode()) ||
                        !name.equals(organization.getOrgName())){
                    return ReturnData.error("企业名称与社会信用代码不匹配，请重新录入!");
                }else {
                    orgAuthInfo(organization, userId, request, isCheck);
                    return ReturnData.success("恭喜您！验证成功！");
                }
            }else {
                Map<String, String> mapCheck = new HashMap<>(6);
                mapCheck.put("isCheck", "1");
                orgAuthInfo(organization, userId, request, "1");
                return ReturnData.success(mapCheck);
            }
        } catch (Exception e) {
            logger.error("企业认证信息操作失败！^_^", e);
            return ReturnData.error("企业认证信息操作失败！^_^");
        }
    }

    // 企业认证
    public void orgAuthInfo(Organization organization, String userId, HttpServletRequest request, String isCheck){
        User user = userService.selectById(userId);
        String orgId = user.getOrgId();

        User user1 = new User();
        user1.setUserId(userId);
        if(!StringUtils.isEmpty(orgId) && !"0".equals(orgId)){
            //            organization.setUserId(userId);
            organization.setIsAuth(AUTH_STATUS_SUCCESS);
            organization.setOrgId(orgId);
            if(isCheck.equals("0")){ // 自动认证通过  企业状态为2 否则为0
                organization.setStatus("2");
            }else {
                organization.setStatus("0");
            }
            organizationService.updateByIdSelective(organization);
            //给注册的供应商orgId赋值
            user1.setOrgId(orgId);
        }else {
            if(isCheck.equals("0")){ // 自动认证通过  企业状态为2 否则为0
                organization.setStatus("2");
            }else {
                organization.setStatus("0");
            }
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

            // 供应商待审核状态
//                user1.setUserType(HookahConstants.UserType.ORGANIZATION_SUPPLIER_CHECK_NO.getCode());
            user1.setSupplierStatus(HookahConstants.SupplierStatus.CHECK_STATUS.getCode());
        }
        // 企业认证成功状态
        //当isCheck为0时，直接认证通过,否则变为待审核状态
        if(isCheck.equals("0")){ // 自动认证通过
            user1.setUserType(HookahConstants.UserType.ORGANIZATION_CHECK_OK.getCode());
            userService.updateByIdSelective(user1);

            if(user1.getUserType().equals(HookahConstants.UserType.ORGANIZATION_CHECK_OK.getCode())){
                mqSenderService.sendDirect(RabbitmqQueue.CONTRACE_JF_MSGINFO, new JfBo(user1.getUserId(), 3, ""));
                logger.info("企业用户通过审核发放积分【账号身份认证】>>>>>userId = " + user.getUserId());
            }

            //更新微信用户推荐表
            wXUserRecommendService.updateWXUserRecommendIsAuthenticate(user1.getUserId());
        }else {  // 企业待审核
            user1.setUserType(HookahConstants.UserType.ORGANIZATION_CHECK_NO.getCode());
            userService.updateByIdSelective(user1);
        }

        try {
            Map<String, Cookie> cookieMap = ReadCookieUtil.ReadCookieMap(request);
            Cookie tongJi = cookieMap.get("TongJi");
            if(tongJi != null){
                MgTongJi tongJiInfo = mgTongJiService.getTongJiInfo(tongJi.getValue());
                mgTongJiService.setTongJiInfo(TongJiEnum.ORG_URL, tongJiInfo.getTongJiId(),
                        tongJiInfo.getUtmSource(), tongJiInfo.getUtmTerm(), userId);
            }
        }catch (Exception e){
            logger.error("插入单位认证统计信息失败{}{}",userId,e.getMessage());
        }
    }
}
