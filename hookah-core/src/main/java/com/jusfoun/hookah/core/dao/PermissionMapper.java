package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Permission;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PermissionMapper extends GenericDao<Permission> {

    Set<String> selectPermissionsByRoleId(@Param("roleId") String roleId);

    Set<String> selectPermissionsByRoleName(@Param("roleName") String roleName);

    Set<String> selectPermissionsByUserId(@Param("userId") String userId);
}