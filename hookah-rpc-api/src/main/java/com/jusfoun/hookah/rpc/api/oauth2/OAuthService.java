package com.jusfoun.hookah.rpc.api.oauth2;


import com.jusfoun.hookah.core.domain.OauthAccessToken;
import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.domain.OauthCode;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
public interface OAuthService {

    boolean checkClientId(String clientId);

    boolean checkClientSecret(String clientSecret);

    boolean checkAccessToken(String tokenId);

    OauthAccessToken selectByTokenId(String tokenId);

    OauthCode insertAuthCode(String authCode, OauthClient oauthClient);

    OauthCode selectByCode(String code);

    OauthAccessToken insertAccessToken(OauthCode oauthCode)throws OAuthSystemException;
}
