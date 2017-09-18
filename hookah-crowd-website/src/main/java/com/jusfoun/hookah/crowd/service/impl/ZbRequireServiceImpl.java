package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRequirementMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirement;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbRequireService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZbRequireServiceImpl extends GenericServiceImpl<ZbRequirement, Long> implements ZbRequireService {

    @Resource
    private ZbRequirementMapper zbRequirementMapper;

    @Resource
    public void setDao(ZbRequirementMapper zbRequirementMapper) {
        super.setDao(zbRequirementMapper);
    }


    @Override
    public int insertRecord(ZbRequirement zbRequirement) {
        return zbRequirementMapper.insertAndGetId(zbRequirement);
    }
}
