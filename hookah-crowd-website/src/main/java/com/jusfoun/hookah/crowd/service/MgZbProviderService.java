package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.mongo.MgZbProvider;
import com.jusfoun.hookah.core.domain.zb.vo.MgZbProviderVo;
import com.jusfoun.hookah.core.domain.zb.vo.ZbCheckVo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

public interface MgZbProviderService extends GenericService<MgZbProvider, String> {

    ReturnData getAuthInfo(String userId);

    ReturnData checkAuthInfo(ZbCheckVo vo);

    ReturnData getTradeRecod(String userId, String pageNumber, String pageSize);

    ReturnData optAuthInfo(MgZbProviderVo vo);
}
