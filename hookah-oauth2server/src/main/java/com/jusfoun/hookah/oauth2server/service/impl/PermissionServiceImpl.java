package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.PermissionMapper;
import com.jusfoun.hookah.core.domain.Permission;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:35
 * @desc
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, String> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    public void setDao(PermissionMapper permissionMapper){
        super.setDao(permissionMapper);
    }


    @Override
    public Set<String> selectPermissionsByUserId(String userId) {
        return permissionMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public Set<String> selectPermissionsByRoleId(String roleId) {
        return permissionMapper.selectPermissionsByRoleId(roleId);
    }
}
