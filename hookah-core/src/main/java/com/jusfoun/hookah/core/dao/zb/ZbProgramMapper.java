package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbProgram;
import com.jusfoun.hookah.core.domain.zb.ZbProgramWithBLOBs;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbProgramMapper extends GenericDao<ZbProgram> {
    int insertAndGetId(ZbProgram zbProgram);
}