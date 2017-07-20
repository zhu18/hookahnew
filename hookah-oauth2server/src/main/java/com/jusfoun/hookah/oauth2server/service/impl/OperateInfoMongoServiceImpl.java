package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OperateInfo;
import com.jusfoun.hookah.core.domain.vo.OperateVO;
import com.jusfoun.hookah.core.generic.GenericMongoServiceImpl;
import com.jusfoun.hookah.rpc.api.OperateInfoMongoService;

/**
 * Created by admin on 2017/7/11.
 */
public class OperateInfoMongoServiceImpl extends GenericMongoServiceImpl<OperateInfo, String> implements OperateInfoMongoService {

    @Override
    public Pagination<OperateInfo> getSoldOrderList(OperateVO perateVO) {
        return null;
    }
}
