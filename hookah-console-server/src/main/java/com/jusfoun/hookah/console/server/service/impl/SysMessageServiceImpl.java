package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.SysMessageMapper;
import com.jusfoun.hookah.core.domain.SysMessage;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.SysMessageService;

import javax.annotation.Resource;

/**
 * 购物车服务
 *
 * @author:jsshao
 * @date: 2017-3-17
 */
public class SysMessageServiceImpl extends GenericServiceImpl<SysMessage, String> implements SysMessageService {

    @Resource
    private SysMessageMapper sysMessageMapper;

    @Resource
    public void setDao(SysMessageMapper sysMessageMapper) {
        super.setDao(sysMessageMapper);
    }
}
