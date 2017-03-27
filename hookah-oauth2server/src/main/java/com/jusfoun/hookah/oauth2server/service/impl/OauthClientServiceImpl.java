package com.jusfoun.hookah.oauth2server.service.impl;


import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.domain.shared.GuidGenerator;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.oauth2server.dao.OauthClientMapper;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthClientService;
import com.jusfoun.hookah.oauth2server.dao.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:29
 * @desc
 */
@Service("clientService")
public class OauthClientServiceImpl extends GenericServiceImpl<OauthClient,Long> implements OAuthClientService {

    @Resource
    private OauthClientMapper oauthClientMapper;

    @Autowired
    private RoleMapper roleDao;

    @Resource
    public void setDao(OauthClientMapper oauthClientMapper){
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

    public OauthClient selectByClientId(String clientId){

//        return oauthClientMapper.selectByClientId(clientId);
        return null;
    }

    @Override
    public int saveClientDetails(OauthClient formDto) {

//        return clientDao.save(formDto);
        return 0;
    }


    public int createClient(OauthClient OauthClient) {
        OauthClient.setClientId(UUID.randomUUID().toString());
        OauthClient.setClientSecret(UUID.randomUUID().toString());
//        return clientDao.save(OauthClient);
        return 0;
    }


    public OauthClient updateClient(OauthClient OauthClient) {
//        return clientDao.save(OauthClient);
        return null;
    }

    public void deleteClient(String clientId) {
//        clientDao.delete(clientId);

    }


    public OauthClient findOne(String clientId) {
//        return clientDao.getOne(clientId);
        return null;
    }

    public List<OauthClient> findAll() {
//        return clientDao.findAll();
        return null;
    }


    public OauthClient findByClientId(String clientId) {
//        return clientDao.getOne(clientId);
        return null;
    }


    public OauthClient findByClientSecret(String clientSecret) {
//        return clientDao.findByClientSecret(clientSecret);
        return null;
    }
}
