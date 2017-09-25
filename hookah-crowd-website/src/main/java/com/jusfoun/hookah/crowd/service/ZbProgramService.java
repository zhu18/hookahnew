package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * Created by hhh on 2017/9/20.
 */
public interface ZbProgramService extends GenericService<ZbProgram, Long> {
    int insertRecord(ZbProgram zbProgram);
}
