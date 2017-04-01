package com.jusfoun.hookah.oauth2server.service.impl;


import com.jusfoun.hookah.core.dao.RoleMapper;
import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.domain.shared.GuidGenerator;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.oauth2server.dao.OauthClientMapper;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:29
 * @desc
 */
@Service("clientService")
public class OAuthClientServiceImpl extends GenericServiceImpl<OauthClient, String> implements OAuthClientService {

    @Resource
    private OauthClientMapper oauthClientMapper;

    @Autowired
    private RoleMapper roleDao;

    @Resource
    public void setDao(OauthClientMapper oauthClientMapper) {
        super.setDao(oauthClientMapper);
    }

    @Override
    public OauthClient loadClientDetailsFormDto() {
        OauthClient OauthClient = new OauthClient();
//        List<Role> roles = roleDao.findAll();
//        OauthClient.setRoleList(roles);
        OauthClient.setClientId(GuidGenerator.generateClientId());
        OauthClient.setClientSecret(GuidGenerator.generateClientSecret());
//    clientDetails.setGrantTypes();
        return OauthClient;
    }
}
