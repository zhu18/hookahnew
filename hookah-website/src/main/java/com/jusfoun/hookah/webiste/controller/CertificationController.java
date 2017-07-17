package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author  zhaoshuai
 * 前台账户中心-实名认证项
 * Created by computer on 2017/7/10.
 */

@Controller
@RequestMapping(value = "/regInfo")
public class CertificationController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    OrganizationService organizationService;

    @Resource
    UserService userService;

    @Resource
    LoginLogService loginLogService;

    @Resource
    UserDetailService userDetailService;

    @Resource
    UserCheckService userCheckService;

    @Resource
    PayAccountService payAccountService;
    
    //用户实名认证信息
    @ResponseBody
    @RequestMapping(value = "/verifiedInfo",method = RequestMethod.GET)
    public ReturnData verifiedInfo() throws HookahException {
        String userId = this.getCurrentUser().getUserId();
        Map<String, Object> map = new HashMap<>(6);
        User user = userService.selectById(userId);
        if(StringUtils.isNotBlank(user.getOrgId())) {
            Organization organization = organizationService.selectById(user.getOrgId());//根据用户查询认证信息
            //机构中 已认证状态 与 认证不通过状态
            if (organization.getIsAuth() == 2 || organization.getIsAuth() == 3) {
                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("userId", userId));
                UserCheck userCheck = userCheckService.selectOne(filters);
                    if(userCheck != null) {
                        map.put("userType", userCheck.getUserType());
                        map.put("checkContent", userCheck.getCheckContent());
                    }
                    map.put("isAuth", organization.getIsAuth());
                    //详细信息
                    map.put("orgName", organization.getOrgName());
                    map.put("certificateCode", organization.getCertificateCode());
                    map.put("certifictePath", organization.getCertifictePath());
                    map.put("licenseCode", organization.getLicenseCode());
                    map.put("licensePath", organization.getLicensePath());
                    map.put("taxCode", organization.getTaxCode());
                    map.put("industry", organization.getIndustry());
                    map.put("lawPersonName", organization.getLawPersonName());
                    map.put("region", organization.getRegion());
                    map.put("contactAddress", organization.getContactAddress());
                    map.put("orgPhone", organization.getOrgPhone());
                }else {
                    return ReturnData.success("未认证");
                }
            }
        return ReturnData.success(map);
    }

    //修改用户实名认证信息
    @ResponseBody
    @RequestMapping(value = "/updateVerifiedInfo",method = RequestMethod.GET)
    public ReturnData updateVerifiedInfo(Organization org) {
        if (StringUtils.isBlank(org.getOrgId())) {
            return ReturnData.invalidParameters("参数orgId不可为空");
        }
        try {
            organizationService.updateByIdSelective(org);
            return ReturnData.success();
        }catch (Exception e){
            logger.error("修改错误", e);
            return ReturnData.error("系统异常");
        }
    }

    //查询账户信息
    @ResponseBody
    @RequestMapping(value = "/selectAccountInfo",method = RequestMethod.GET)
    public ReturnData getUserById() {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            String userId = this.getCurrentUser().getUserId();
            Map<String, Object> map = new HashedMap();
            User user = userService.selectById(userId);

            if(user == null){
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("未查询到此用户，如有疑问请联系管理员！");
                return returnData;
            }

            if(user.getMoneyBalance() != null && user.getMoneyBalance() != 0){
                user.setMoneyBalance(user.getMoneyBalance() / 100);
            }

            List<Condition> filtersl = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            filtersl.add(Condition.eq("loginName", user.getUserName()));
            List<LoginLog> loginLogs = loginLogService.selectList(filtersl, orderBys);
            if(loginLogs != null){
                if(loginLogs.size() > 5){
                    map.put("loginLogs", loginLogs.subList(0, 5));
                }else {
                    map.put("loginLogs", loginLogs);
                }
            }
            if(user.getUserType() != null){
                ///if(user.getUserType() == 1){
                    map.put("user", user);
                    returnData.setData(map);
               /* }else if(user.getUserType() == 2){

                    List<Condition> filters = new ArrayList();
                    filters.add(Condition.eq("userId", userId));
                    UserDetail userDetail = userDetailService.selectOne(filters);
                    map.put("user", user);
                    map.put("userDetail", userDetail);
                    returnData.setData(map);
                }else if(user.getUserType() == 4 && user.getOrgId() != null){
                    Organization organization = organizationService.selectById(user.getOrgId());
                    map.put("user", user);
                    map.put("organization", organization);
                    returnData.setData(map);
                }else{
                    returnData.setCode(ExceptionConst.Failed);
                    returnData.setMessage("系统出错，请联系管理员！");
                }*/
            }else{
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage("系统出错，请联系管理员！");
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("系统出错，请联系管理员！");
            e.printStackTrace();
        }
        return returnData;
    }



    //验证 支付密码是否正确
    @ResponseBody
    @RequestMapping(value = "/paymentPass", method = RequestMethod.GET)
    public boolean paymentPass (String payPassword) throws HookahException {
        String userId = this.getCurrentUser().getUserId();
        List<Condition> filters = new ArrayList();
        if(StringUtils.isNotBlank(userId)){
            filters.add(Condition.eq("userId", userId));
        }
        PayAccount payAccount = payAccountService.selectOne(filters);
        if(StringUtils.isNotBlank(payAccount.getPayPassword())){
            if(payAccount.getPayPassword().equals(payPassword)){
                return true;
            }
        }
        return false;
    }

}
