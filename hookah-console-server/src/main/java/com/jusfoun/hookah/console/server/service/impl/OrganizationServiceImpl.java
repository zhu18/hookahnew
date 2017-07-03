package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.OrganizationMapper;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author huang lei
 * @date 2017/4/6 上午10:10
 * @desc
 */
@Service
public class OrganizationServiceImpl extends GenericServiceImpl<Organization, String> implements OrganizationService {

    @Resource
    private OrganizationMapper organizationMapper;

    @Resource
    private UserService userService;

    @Resource
    public void setDao(OrganizationMapper organizationMapper) {
        super.setDao(organizationMapper);
    }

    @Override
    public Organization findOrgByUserId(String userId) {
        if(StringUtils.isNotBlank(userId)){
            User user = userService.selectById(userId);
            if(Objects.nonNull(user)){
                return this.selectById(user.getOrgId());
            }
        }
        return null;
    }
}
