package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbAnnex;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbAnnexMapper extends GenericDao<ZbAnnex> {

    int insertAndGetId(ZbAnnex zbAnnex);
}