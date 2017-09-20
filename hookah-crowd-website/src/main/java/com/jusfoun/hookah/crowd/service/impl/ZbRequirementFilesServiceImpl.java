package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbRequirementFilesMapper;
import com.jusfoun.hookah.core.domain.zb.ZbRequirementFiles;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbRequirementFilesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhaoshuai on 2017/9/19.
 */
@Service
public class ZbRequirementFilesServiceImpl extends GenericServiceImpl<ZbRequirementFiles, String> implements ZbRequirementFilesService {
    @Resource
    ZbRequirementFilesMapper zbRequirementFilesMapper;

    @Resource
    public void setDao(ZbRequirementFilesMapper zbRequirementFilesMapper) {
        super.setDao(zbRequirementFilesMapper);
    }
}
