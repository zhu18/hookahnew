package com.jusfoun.hookah.webiste.controller;

import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserCheck;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.UserCheckService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  zhaoshuai
 * 前台账户中心-实名认证项
 * Created by computer on 2017/7/10.
 */

@Controller
@RequestMapping(value = "/regInfo")
public class CertificationController{
    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Resource
    OrganizationService organizationService;

    @Resource
    UserService userService;

    @Resource
    UserCheckService userCheckService;
    
    //用户实名认证信息
    @ResponseBody
    @RequestMapping(value = "/verifiedInfo",method = RequestMethod.GET)
    public ReturnData verifiedInfo(String userId) {
        Map<String, Object> map = new HashMap<>(6);
        User user = userService.selectById(userId);
        if(StringUtils.isNotBlank(user.getOrgId())) {
            Organization organization = organizationService.selectById(user.getOrgId());//根据用户查询认证信息
            //机构中 已认证状态 与 认证不通过状态
            if (organization.getIsAuth() == 2 || organization.getIsAuth() == 3) {
                List<Condition> filters = new ArrayList();
                filters.add(Condition.eq("userId", userId));
                UserCheck userCheck = userCheckService.selectOne(filters);
                map.put("userType", userCheck.getUserType());
                map.put("isAuth", organization.getIsAuth());
                map.put("checkContent", userCheck.getCheckContent());

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
            //organizationService.updateByIdSelective(org);
            organizationService.updateById(org);
            return ReturnData.success();
        }catch (Exception e){
            logger.error("修改错误", e);
            return ReturnData.error("系统异常");
        }
    }
}
