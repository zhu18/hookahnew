package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbProgramMapper;
import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbProgramService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hhh on 2017/9/20.
 */
@Service
public class ZbProgramServiceImpl extends GenericServiceImpl<ZbProgram, Long> implements ZbProgramService{

    @Resource
    private ZbProgramMapper zbProgramMapper;
    @Resource
    public void setDao(ZbProgramMapper ZbProgramMapper) {
        super.setDao(ZbProgramMapper);
    }
    @Override
    public int insertRecord(ZbProgram zbProgram) {
        return 0;
    }
}
