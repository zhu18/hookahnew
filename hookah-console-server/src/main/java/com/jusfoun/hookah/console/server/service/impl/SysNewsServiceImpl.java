package com.jusfoun.hookah.console.server.service.impl;

;
import com.jusfoun.hookah.core.dao.SysNewsMapper;

import com.jusfoun.hookah.core.domain.SysNews;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;

import com.jusfoun.hookah.rpc.api.SysNewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 新闻中心
 */
@Service
public class SysNewsServiceImpl extends GenericServiceImpl<SysNews, String> implements SysNewsService {
    @Resource
    private SysNewsMapper sysNewsMapper;


    @Resource
    public void setDao(SysNewsMapper sysNewsMapper) {
        super.setDao(sysNewsMapper);
    }
}
