package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface PayTradeRecordService extends GenericService<PayTradeRecord, Integer> {

    PayTradeRecord initPayTradeRecord(MoneyInOutBo moneyInOutBo);
}
