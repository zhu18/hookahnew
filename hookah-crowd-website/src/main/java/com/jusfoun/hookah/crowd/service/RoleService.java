package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.Role;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:25
 * @desc
 */
public interface RoleService extends GenericService<Role, String> {

    Set<String> selectRolesByUserId(String userId);
    List<Role> selectRoleListByUserId(String userId);
}
