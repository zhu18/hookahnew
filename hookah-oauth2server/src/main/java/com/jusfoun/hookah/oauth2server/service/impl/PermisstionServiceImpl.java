package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.PermissionMapper;
import com.jusfoun.hookah.core.domain.Permission;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PermisstionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:35
 * @desc
 */
@Service
public class PermisstionServiceImpl extends GenericServiceImpl<Permission, String> implements PermisstionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    public void setDao(PermissionMapper permissionMapper){
        super.setDao(permissionMapper);
    }
}
