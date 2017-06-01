package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.SysNoticeMapper;
import com.jusfoun.hookah.core.domain.SysNotice;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.SysNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by admin on 2017/5/9.
 */
@Service
public class SysNoticeServiceImpl extends GenericServiceImpl<SysNotice, String> implements SysNoticeService {
    @Resource
    SysNoticeMapper sysNoticeMapper;

    @Resource
    public void setDao(SysNoticeMapper sysNoticeMapper) {
        super.setDao(sysNoticeMapper);
    }
}
