package com.jusfoun.hookah.console.server.api.user;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.UserDetail;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.UserDetailService;
import com.jusfoun.hookah.rpc.api.UserService;
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ReturnData getAllUser(String currentPage, String pageSize, HttpServletRequest request,
                                 String userName, String mobile, String email) {
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReturnData getUserById(@PathVariable String id) {
        UserDetail userDetail = userDetailService.selectById(id);
        return ReturnData.success(userDetail);
    }


    @RequestMapping(value = "/org/{id}", method = RequestMethod.GET)
    public ReturnData getOrgById(@PathVariable String id) {
        List<Condition> fifters = new ArrayList<Condition>();
        fifters.add(Condition.eq("userId",id));
        Organization organization = organizationService.selectOne(fifters);
        return ReturnData.success(organization);
    }

    @RequestMapping(value = "/verify/all", method = RequestMethod.GET)
    public ReturnData getAllVerifyUser(String currentPage, String pageSize, HttpServletRequest request, HttpServletResponse response ,
                                       String userName, String mobile, String email) {
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
