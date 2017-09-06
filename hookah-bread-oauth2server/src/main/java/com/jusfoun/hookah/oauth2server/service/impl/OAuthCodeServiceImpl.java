package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.domain.OauthCode;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.oauth2server.dao.OauthCodeMapper;
import com.jusfoun.hookah.rpc.api.oauth2.OAuthCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/3/28 上午11:14
 * @desc
 */
@Service
public class OAuthCodeServiceImpl extends GenericServiceImpl<OauthCode,String> implements OAuthCodeService {

    @Resource
    private OauthCodeMapper authCodeMapper;

    @Resource
    public void setDao(OauthCodeMapper authCodeMapper){
        super.setDao(authCodeMapper);
    }

}
