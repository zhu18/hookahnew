package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.RolePermissionMapper;
import com.jusfoun.hookah.core.domain.RolePermission;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.RolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:37
 * @desc
 */
@Service
public class RolePermissionServiceImpl extends GenericServiceImpl<RolePermission, String> implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    public void setDao(RolePermissionMapper rolePermissionMapper) {
        super.setDao(rolePermissionMapper);
    }

    @Override
    public int deletePermissionsByRoleId(String roleId) {
        return rolePermissionMapper.deletePermissionsByRoleId(roleId);
    }
}
