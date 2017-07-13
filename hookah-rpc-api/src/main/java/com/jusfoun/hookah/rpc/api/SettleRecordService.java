package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.SettleRecord;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * dx
 */
public interface SettleRecordService extends GenericService<SettleRecord, Long> {

    ReturnData handleSettle(Long sid, Long supplierAmount, Long tradeCenterAmount, String userId);
}
