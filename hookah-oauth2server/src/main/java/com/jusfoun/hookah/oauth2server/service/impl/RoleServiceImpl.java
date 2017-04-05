package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.RoleMapper;
import com.jusfoun.hookah.core.domain.Role;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author huang lei
 * @date 2017/4/5 上午9:32
 * @desc
 */
@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, String> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    public void setDao(RoleMapper roleMapper){
        super.setDao(roleMapper);
    }

    @Override
    public Set<String> selectRolesByUserId(String userId) {
        Set<String> s = roleMapper.selectRolesByUserId(userId);
        return s;
    }
}
