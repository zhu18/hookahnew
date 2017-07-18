package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.List;

/**
 * dx
 */
public interface WithdrawRecordService extends GenericService<WithdrawRecord, Long> {

    int insertRecord(WithdrawRecord withdrawRecord);

    ReturnData applyWithdraw(WithdrawRecord withdrawRecord);

    List<WithdrawRecord> getListForPage(String startDate, String endDate, String checkStatus, String orgName);
}
