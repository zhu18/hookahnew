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
import java.text.SimpleDateFormat;
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
    SupplierService supplierService;

    //用户实名认证信息
    @ResponseBody
    @RequestMapping(value = "/verifiedInfo",method = RequestMethod.GET)
    public ReturnData verifiedInfo() throws HookahException {
        String userId = this.getCurrentUser().getUserId();
        Map<String, Object> map = new HashMap<>(6);
        User user = userService.selectById(userId);
        map.put("userType", user.getUserType());
        UserCheck userCheck = userCheckService.selectUserCheckInfo(userId);
        if(userCheck != null) {
            map.put("checkContent", userCheck.getCheckContent());
        }
        if(user!=null){
            if(user.getUserType() == 4 || user.getUserType() == 5|| user.getUserType() == 7){
                if(StringUtils.isNotBlank(user.getOrgId())) {
                    Organization organization = organizationService.selectById(user.getOrgId());//根据用户查询认证信息
                    map.put("organization",organization);
                }
                return ReturnData.success(map);
            }else {
                return ReturnData.success("未认证");
            }
        }
        return ReturnData.error("用户信息不可为空");
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

}
