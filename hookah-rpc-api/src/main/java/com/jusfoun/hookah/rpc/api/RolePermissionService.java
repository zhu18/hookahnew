package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.RolePermission;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:28
 * @desc
 */
public interface RolePermissionService extends GenericService<RolePermission, String> {
    int deletePermissionsByRoleId(String roleId);
}
