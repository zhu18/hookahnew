package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.AdvertMapper;
import com.jusfoun.hookah.core.domain.Advert;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.AdvertService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 购物车服务
 *
 * @author:jsshao
 * @date: 2017-3-17
 */
@Service
public class AdvertServiceImpl extends GenericServiceImpl<Advert, String> implements AdvertService {

    @Resource
    private AdvertMapper advertMapper;

    @Resource
    public void setDao(AdvertMapper advertMapper) {
        super.setDao(advertMapper);
    }

}
