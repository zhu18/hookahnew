package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;

import java.util.List;

public interface FundRecordService extends GenericService<PayTradeRecord, String> {


    public Pagination<PayTradeRecord> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                       List<OrderBy> orderBys);

}
