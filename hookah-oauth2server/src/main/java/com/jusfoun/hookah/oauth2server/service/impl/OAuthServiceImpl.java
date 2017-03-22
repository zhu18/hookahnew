package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.domain.OauthAccessToken;
import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.domain.OauthCode;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthClientService;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthService;
import com.jusfoun.hookah.oauth2server.dao.OauthAccessTokenMapper;
import com.jusfoun.hookah.oauth2server.dao.OauthCodeMapper;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:29
 * @desc
 */
@Service
public class OAuthServiceImpl implements OAuthService {


    @Resource
    private OAuthClientService clientService;

    @Resource
    private OauthCodeMapper oAuthCodeDao;

    @Resource
    private OauthAccessTokenMapper oAuthAccessTokenDao;

    @Resource
    private OAuthIssuer oAuthIssuer;

    @Override
    public OauthCode insertAuthCode(String authCode, OauthClient oauthClient) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        OauthCode oAuthCode = new OauthCode();
        oAuthCode.setCode(authCode);
        oAuthCode.setClientId(oauthClient.getClientId());
        oAuthCode.setUsername(username);
        oAuthCodeDao.insert(oAuthCode);
//        oAuthCode.setId(id); //TODO
        return oAuthCode;
    }

    public OauthAccessToken insertAccessToken(OauthCode oauthCode) throws OAuthSystemException {
        final String accessToken = oAuthIssuer.accessToken();
        OauthAccessToken oAuthAccessToken = new OauthAccessToken();
        oAuthAccessToken.setTokenId(accessToken);
        oAuthAccessToken.setUsername(oauthCode.getUsername());
        oAuthAccessToken.setClientId(oauthCode.getClientId());
        oAuthAccessToken.setAuthenticationId(oauthCode.getCode());
        oAuthAccessToken.setRefreshToken(oAuthIssuer.refreshToken());
        oAuthAccessToken.setTokenType("Bearer");
        oAuthAccessToken.setTokenExpiredSeconds(60 * 60 * 12);//12小时
        oAuthAccessToken.setRefreshTokenExpiredSeconds(60 * 60 * 24 * 30); //30天
        oAuthAccessTokenDao.insert(oAuthAccessToken);
//        oAuthAccessToken.setId(id);
        return oAuthAccessToken;
    }

    public boolean checkAccessToken(String tokenId){
        //TODO 检查过期
//        return oAuthAccessTokenDao.selectByTokenId(tokenId) != null;
        return false;
    }
    public OauthAccessToken selectByTokenId(String tokenId){
//        return oAuthAccessTokenDao.selectByTokenId(tokenId);
        return null;
    }


    public OauthCode selectByCode(String code) {
//        return oAuthCodeDao.selectByCode(code);
        return null;
    }

    public boolean checkClientId(String clientId) {
        return clientService.selectByClientId(clientId) != null;
    }

    public boolean checkClientSecret(String clientSecret) {
        return false;
    }



}
