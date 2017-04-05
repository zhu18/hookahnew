package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.Role;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleMapper extends GenericDao<Role> {

    Set<String> selectRolesByUserId(@Param("userId") String userId);
}