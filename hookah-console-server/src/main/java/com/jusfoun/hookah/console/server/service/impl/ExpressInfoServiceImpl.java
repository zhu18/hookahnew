package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.ExpressInfoMapper;
import com.jusfoun.hookah.core.domain.ExpressInfo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.ExpressInfoService;

import javax.annotation.Resource;

/**
 * Created by Gring on 2017/11/27.
 */
public class ExpressInfoServiceImpl extends GenericServiceImpl<ExpressInfo, String> implements ExpressInfoService {

    @Resource
    private ExpressInfoMapper expressInfoMapper;
    @Resource
    public void setDao(ExpressInfoMapper expressInfoMapper) {
        super.setDao(expressInfoMapper);
    }
}
