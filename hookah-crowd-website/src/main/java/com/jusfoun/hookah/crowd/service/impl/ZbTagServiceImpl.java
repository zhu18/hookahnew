package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.HelpMapper;
import com.jusfoun.hookah.core.dao.zb.ZbTagMapper;
import com.jusfoun.hookah.core.domain.zb.ZbTag;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhaoshuai on 2017/9/19.
 */
@Service
public class ZbTagServiceImpl extends GenericServiceImpl<ZbTag, String> implements ZbTagService {

    @Resource
    ZbTagMapper zbTagMapper;

    @Resource
    public void setDao(ZbTagMapper zbTagMapper) {
        super.setDao(zbTagMapper);
    }
}
