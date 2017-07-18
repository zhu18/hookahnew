package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FundRecordService extends GenericService<PayTradeRecord, String> {


    public Pagination<PayTradeRecord> getDetailListInPage(Integer pageNum, Integer pageSize, List<Condition> filters,
                                                       List<OrderBy> orderBys);

}
