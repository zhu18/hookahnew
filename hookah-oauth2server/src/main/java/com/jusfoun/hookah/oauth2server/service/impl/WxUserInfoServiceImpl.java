package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.WXUserInfoMapper;
import com.jusfoun.hookah.core.domain.WxUserInfo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.WxUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lt on 2017/10/31.
 */
@Service
public class WxUserInfoServiceImpl extends GenericServiceImpl<WxUserInfo, Integer> implements WxUserInfoService{

    @Resource
    private WXUserInfoMapper wxUserInfoMapper;

    @Resource
    public void setDao(WXUserInfoMapper wxUserInfoMapper) {
        super.setDao(wxUserInfoMapper);
    }
}
