package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.domain.OauthAccessToken;
import com.jusfoun.hookah.core.domain.OauthCode;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.oauth2server.dao.OauthAccessTokenMapper;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthAccessTokenService;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/28 上午11:39
 * @desc
 */
@Service
public class OAuthAccessTokenServiceImpl extends GenericServiceImpl<OauthAccessToken,String> implements OAuthAccessTokenService {

    @Resource
    private OauthAccessTokenMapper oauthAccessTokenMapper;

    @Resource
    private OAuthIssuer oAuthIssuer;

    @Resource
    public void setDao(OauthAccessTokenMapper oauthAccessTokenMapper){
        super.setDao(oauthAccessTokenMapper);
    }

}
