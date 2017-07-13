package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface WaitSettleRecordService extends GenericService<WaitSettleRecord, Long> {

    int handleSettleRecord(Long sid, Long settleAmount);
}
