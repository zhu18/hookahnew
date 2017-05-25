package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.OrganizationMapper;
import com.jusfoun.hookah.core.domain.Organization;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.OrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public void setDao(OrganizationMapper organizationMapper) {
        super.setDao(organizationMapper);
    }
}
