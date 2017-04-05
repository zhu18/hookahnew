package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.UserRoleMapper;
import com.jusfoun.hookah.core.domain.UserRole;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:30
 * @desc
 */
@Service
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole, String> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    public void setDao(UserRoleMapper userRoleMapper){
        super.setDao(userRoleMapper);
    }
}
