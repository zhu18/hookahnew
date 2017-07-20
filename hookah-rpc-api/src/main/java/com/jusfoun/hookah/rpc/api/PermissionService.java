package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.Permission;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:26
 * @desc
 */
public interface PermissionService extends GenericService<Permission, String> {
    Set<String> selectPermissionsByRoleId(String roleId);

    Set<String> selectPermissionsByRoleName(String roleName);

    Set<String> selectPermissionsByUserId(String userId);

    List<Permission> selectTree();
}
