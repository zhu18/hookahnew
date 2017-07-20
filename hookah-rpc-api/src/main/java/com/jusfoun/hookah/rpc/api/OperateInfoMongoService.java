package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OperateInfo;
import com.jusfoun.hookah.core.domain.vo.OperateVO;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * Created by admin on 2017/7/11.
 */
public interface OperateInfoMongoService extends GenericService<OperateInfo,String> {
    public Pagination<OperateInfo> getSoldOrderList(OperateVO perateVO);
}
