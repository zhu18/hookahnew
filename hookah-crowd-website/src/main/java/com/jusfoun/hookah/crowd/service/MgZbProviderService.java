package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

public interface MgZbProviderService extends GenericService<MgZbProvider, String> {

    ReturnData userAuth(MgZbProvider provider);
}
