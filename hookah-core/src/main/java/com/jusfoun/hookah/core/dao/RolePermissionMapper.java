package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.RolePermission;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper extends GenericDao<RolePermission> {

    int deletePermissionsByRoleId(@Param("roleId") String roleId);
}