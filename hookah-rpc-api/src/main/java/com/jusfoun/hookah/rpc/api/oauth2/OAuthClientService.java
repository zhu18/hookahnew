package com.jusfoun.hookah.rpc.api.oauth2;


import com.jusfoun.hookah.core.domain.OauthClient;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:21
 * @desc
 */
public interface OAuthClientService extends GenericService<OauthClient,String> {

    OauthClient loadClientDetailsFormDto();

}