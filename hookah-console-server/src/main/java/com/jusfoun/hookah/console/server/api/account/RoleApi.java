package com.jusfoun.hookah.console.server.api.account;

import com.jusfoun.hookah.console.server.controller.BaseController;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.domain.Role;
import com.jusfoun.hookah.core.domain.RolePermission;
import com.jusfoun.hookah.core.domain.vo.RolePermissionVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.PermissionService;
import com.jusfoun.hookah.rpc.api.RolePermissionService;
import com.jusfoun.hookah.rpc.api.RoleService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping(value = "/api/role")
public class RoleApi extends BaseController {

    @Resource
    UserService userService;

    @Resource
    RoleService roleService;

    @Resource
    RolePermissionService rolePermisstionService;

    @Resource
    PermissionService permissionService;

    @RequestMapping(value = "/role_all", method = RequestMethod.GET)
    public ReturnData getRoles(String currentPage, String pageSize, Role role) {
        Pagination<Role> page = new Pagination<Role>();
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
            page = roleService.getListInPage(pageNumberNew, pageSizeNew, filters, orderBys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnData.success(page);
    }


    @RequestMapping(value = "/user_role", method = RequestMethod.GET)
    public ReturnData getRoles(String userId) {
        Set roles = roleService.selectRolesByUserId(userId);
        return ReturnData.success(roles);
    }


    @RequestMapping(value = "/getPermissionsByRoleId", method = RequestMethod.GET)
    public ReturnData getPermissionsByRoleId(String roleId){
        Set<String> permisssions = permissionService.selectPermissionsByRoleId(roleId);
        return ReturnData.success(permisssions);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    public ReturnData saveRole(RolePermissionVo rolePermissionVo) throws Exception {
        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);
        if (StringUtils.isNotBlank(rolePermissionVo.getRoleId()) && StringUtils.isNotBlank(rolePermissionVo.getRoleName()) && StringUtils.isNotBlank(rolePermissionVo.getRoleExplain())) {
            Role role = new Role();
            role.setRoleId(rolePermissionVo.getRoleId());
            role.setRoleName(rolePermissionVo.getRoleName());
            role.setRoleExplain(rolePermissionVo.getRoleExplain());
            try {
                roleService.updateById(role);
                String[] permArray = rolePermissionVo.getPermissions().split(",");
                rolePermisstionService.deletePermissionsByRoleId(rolePermissionVo.getRoleId());
                for (int i = 0; i < permArray.length; i++) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(rolePermissionVo.getRoleId());
                    rolePermission.setPermissionId(permArray[i]);
                    rolePermisstionService.insert(rolePermission);
                }


            } catch (Exception e) {
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage(e.getMessage());
                e.printStackTrace();
            }

        } else if (!StringUtils.isNotBlank(rolePermissionVo.getRoleId()) && StringUtils.isNotBlank(rolePermissionVo.getRoleName()) && StringUtils.isNotBlank(rolePermissionVo.getRoleExplain())) {
            try {
                boolean isExists = true;
                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("roleName", rolePermissionVo.getRoleName()));
                isExists = roleService.exists(filters);
                if (isExists) {
                    throw new Exception("该角色已存在");
                } else {
                    Role role = new Role();
                    role.setRoleName(rolePermissionVo.getRoleName());
                    role.setRoleExplain(rolePermissionVo.getRoleExplain());
                    role.setIsEnable(Byte.decode("1"));
                    role.setAddTime(new Date());
                    Role savedRole = roleService.insert(role);
                    String[] permArray = rolePermissionVo.getPermissions().split(",");
//                    rolePermisstionService.deletePermissionsByRoleId(rolePermissionVo.getRoleId());
                    for (int i = 0; i < permArray.length; i++) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(savedRole.getRoleId());
                        rolePermission.setPermissionId(permArray[i]);
                        rolePermisstionService.insert(rolePermission);
                    }
                }
            } catch (Exception e) {
                returnData.setCode(ExceptionConst.Failed);
                returnData.setMessage(e.getMessage());
                e.printStackTrace();
            }
        }


        return returnData;
    }

    @RequestMapping("/delete")
    public ReturnData deleteRole(String roleId) {
        try {
            if (StringUtils.isNotBlank(roleId)) {
                roleService.delete(roleId);
                rolePermisstionService.deletePermissionsByRoleId(roleId);
            } else {
                return ReturnData.error("删除失败");
            }
        } catch (Exception e) {
            return ReturnData.error("删除失败");
        }
        return ReturnData.success("删除成功");
    }
}
