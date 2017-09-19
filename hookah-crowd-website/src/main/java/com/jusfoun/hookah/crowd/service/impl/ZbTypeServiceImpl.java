package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbTypeMapper;
import com.jusfoun.hookah.core.domain.zb.ZbType;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhaoshuai on 2017/9/19.
 */
@Service
public class ZbTypeServiceImpl extends GenericServiceImpl<ZbType, String> implements ZbTypeService {

    @Resource
    ZbTypeMapper zbTypeMapper;

    @Resource
    public void setDao(ZbTypeMapper zbTypeMapper) {
        super.setDao(zbTypeMapper);
    }
}
