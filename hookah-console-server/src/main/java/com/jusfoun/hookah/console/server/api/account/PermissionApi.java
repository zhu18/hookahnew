package com.jusfoun.hookah.console.server.api.account;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Permission;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.PermissionService;
import com.jusfoun.hookah.rpc.api.RoleService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huang lei
 * @date 05/07/2017 1:45 PM
 * @desc
 */
@RestController
@RequestMapping(value = "/api/permission")
public class PermissionApi extends BaseController {

    @Resource
    UserService userService;

    @Resource
    PermissionService permissionService;

    @RequestMapping(value = "/current_user_permission",method = RequestMethod.GET)
    public ReturnData getCurrentUserPermission(String userId){
        Set<String> permissionSet = permissionService.selectPermissionsByUserId(userId);
        return ReturnData.success(permissionSet);
    }

    @RequestMapping(value = "/permission_all", method = RequestMethod.GET)
    public ReturnData getPermissions(String currentPage, String pageSize, Permission permission) {
        Pagination<Permission> page = new Pagination<Permission>();
        try {
            List<Condition> filters = new ArrayList<>();
            List<OrderBy> orderBys = new ArrayList();
            orderBys.add(OrderBy.desc("addTime"));
            int pageNumberNew = HookahConstants.PAGE_NUM;
            if (StringUtils.isNotBlank(currentPage)) {
                pageNumberNew = Integer.parseInt(currentPage);
            }
            int pageSizeNew = HookahConstants.PAGE_SIZE;
            if (StringUtils.isNotBlank(pageSize)) {
                pageSizeNew = Integer.parseInt(pageSize);
            }
            page = permissionService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    public ReturnData savePermission(Permission permission) throws Exception {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        try {
            boolean isExists = true;
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("permissionName", permission.getPermissionName()));
            isExists = permissionService.exists(filters);
            if (isExists) {
                throw new Exception("该权限已存在");
            } else {
                permission.setAddTime(new Date());
                Permission savedPermission = permissionService.insert(permission);
            }
        } catch (Exception e) {
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return returnData;
    }

    @RequestMapping("/delete")
    public ReturnData deletePermission(String permissionId) {
        try {
            if (StringUtils.isNotBlank(permissionId)) {
                permissionService.delete(permissionId);
            } else {
                return ReturnData.error("删除失败");
            }
        } catch (Exception e) {
            return ReturnData.error("删除失败");
        }
        return ReturnData.success("删除成功");
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object getListInPage() {
        List<Permission> permissionTreeList = permissionService.selectTree();
        return permissionTreeList;
    }

    @RequestMapping(value = "/initCheckedTree", method = RequestMethod.GET)
    public Object initCheckedTree(String roleId) {
        List<Permission> permissionTreeList = permissionService.selectTree();
        Set<String> permisssions = permissionService.selectPermissionsByRoleId(roleId);
        Boolean isFatherSelected=false;
        if (permisssions.size()>0) {
            for (Permission p : permissionTreeList) {
                isFatherSelected = false;
                for (Permission child : p.getChildren()) {
                    if (permisssions.contains(child.getPermissionId())) {
                        isFatherSelected = true;  // 如果有子级被选中，那么父级也选中
                        child.setSelected(true);
                    }
                }
                p.setSelected(isFatherSelected);
            }
        }
        return permissionTreeList;
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public Object getPermissionGroup() {
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("permissionParentId", "0"));
        List<Permission> permissions= permissionService.selectList(filters);
        return permissions;
    }
}
