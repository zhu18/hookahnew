package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.vo.PayTradeRecordVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.generic.OrderBy;
import java.util.List;
import java.util.Map;

/**
 * dx
 */
public interface PayTradeRecordService extends GenericService<PayTradeRecord, Integer> {

    PayTradeRecord initPayTradeRecord(MoneyInOutBo moneyInOutBo, String s);

    int insertAndGetId(PayTradeRecord payTradeRecord);

    Pagination<PayTradeRecordVo> getListForPage(int pageNumberNew, int pageSizeNew, String startDate, String endDate, Integer tradeType, Integer tradeStatus);

    int selectStatusByOrderSn(Map<String,String> paramMap);

    Pagination<PayTradeRecordVo> getFlowListInPage(Integer pageNum, Integer pageSize,  List<Condition> filters,
                                                   List<OrderBy> orderBys);
}
