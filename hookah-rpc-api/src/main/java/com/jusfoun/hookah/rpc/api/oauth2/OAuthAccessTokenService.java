package com.jusfoun.hookah.rpc.api.oauth2;

import com.jusfoun.hookah.core.domain.OauthAccessToken;
import com.jusfoun.hookah.core.domain.OauthCode;
import com.jusfoun.hookah.core.generic.GenericService;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * @author huang lei
 * @date 2017/3/28 上午11:06
 * @desc
 */
public interface OAuthAccessTokenService extends GenericService<OauthAccessToken,String> {

    OauthAccessToken insertAccessToken(OauthCode oauthCode)throws OAuthSystemException;
}
