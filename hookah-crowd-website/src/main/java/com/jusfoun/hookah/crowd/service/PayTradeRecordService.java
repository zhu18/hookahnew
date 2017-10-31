package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface PayTradeRecordService extends GenericService<PayTradeRecord, Integer> {

    int insertAndGetId(PayTradeRecord payTradeRecord);
}
