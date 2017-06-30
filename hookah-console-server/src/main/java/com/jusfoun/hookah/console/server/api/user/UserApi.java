package com.jusfoun.hookah.console.server.api.user;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.LoginLog;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.LoginLogService;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.UserDetailService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/4/8 下午2:25
 * @desc
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserApi {

    @Resource
    UserService userService;

    @Resource
    UserDetailService userDetailService;

    @Resource
    OrganizationService organizationService;

    @Resource
    LoginLogService loginLogService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getAllUser(String currentPage, String pageSize, HttpServletRequest request,
                                 String userName, String mobile, String email, String userType) {
        Pagination<User> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询组织ID不为0的用户
            filters.add(Condition.ne("userType", 0));

            if(StringUtils.isNotBlank(userName)){
                filters.add(Condition.like("userName", userName));
            }
            if(StringUtils.isNotBlank(mobile)){
                filters.add(Condition.like("mobile", mobile));
            }
            if(StringUtils.isNotBlank(email)){
                filters.add(Condition.like("email", email));
            }

            if(StringUtils.isNotBlank(userType) && !"-1".equals(userType)){
                filters.add(Condition.like("userType", Byte.valueOf(userType)));
            }
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = userService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    /**
     * 根据用户类型获取详细信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            Map<String, Object> map = new HashedMap();
            User user = userService.selectById(id);

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
                if(loginLogs.size() > 10){
                    map.put("loginLogs", loginLogs.subList(0, 10));
                }else {
                    map.put("loginLogs", loginLogs);
                }
            }
            if(user.getUserType() != null){
                if(user.getUserType() == 1){
                    map.put("user", user);
                    returnData.setData(map);
                }else if(user.getUserType() == 2){

                    List<Condition> filters = new ArrayList();
                    filters.add(Condition.eq("userId", id));
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
                }
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

    @RequestMapping(value = "/org/{id}", method = RequestMethod.GET)
    public ReturnData getOrgById(@PathVariable String id) {
//        List<Condition> fifters = new ArrayList<Condition>();
//        fifters.add(Condition.eq("userId",id));
//        Organization organization = organizationService.selectOne(fifters);
        Organization organization = organizationService.selectById(id);
        return ReturnData.success(organization);
    }

    @RequestMapping(value = "/verify/all", method = RequestMethod.GET)
    public ReturnData getAllVerifyUser(String currentPage, String pageSize, HttpServletRequest request, HttpServletResponse response ,
                                       String userName, String mobile, String email,String userType) {
        Pagination<User> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询组织ID不为0的用户
            filters.add(Condition.in("userType", new Integer[]{3, 5}));

            if(StringUtils.isNotBlank(userName)){
                filters.add(Condition.like("userName", userName));
            }
            if(StringUtils.isNotBlank(mobile)){
                filters.add(Condition.like("mobile", mobile));
            }
            if(StringUtils.isNotBlank(email)){
                filters.add(Condition.like("email", email));
            }

            if(StringUtils.isNotBlank(userType) && !"-1".equals(userType)){
                filters.add(Condition.like("userType", Byte.valueOf(userType)));
            }



            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = userService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/verify/person", method = RequestMethod.GET)
    public ReturnData getAllPersonUser(String currentPage, String pageSize, HttpServletRequest request, HttpServletResponse response) {
        Pagination<User> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询组织ID不为0的用户
            filters.add(Condition.eq("userType", 3));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = userService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/verify/company", method = RequestMethod.GET)
    public ReturnData getAllCompanyUser(String currentPage, String pageSize, HttpServletRequest request, HttpServletResponse response) {
        Pagination<User> page = new Pagination<>();
        try {
            List<Condition> filters = new ArrayList();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            //只查询组织ID不为0的用户
            filters.add(Condition.eq("userType", 5));
            //参数校验
            int pageNumberNew = HookahConstants.PAGE_NUM;

            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = userService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

}
