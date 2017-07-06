package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface PayTradeRecordMapper extends GenericDao<PayTradeRecord> {
    int insertAndGetId(PayTradeRecord payTradeRecord);
}