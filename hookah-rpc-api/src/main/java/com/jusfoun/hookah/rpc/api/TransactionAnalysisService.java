package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.TransactionAnalysis;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * Created by lt on 2017/12/19.
 */
public interface TransactionAnalysisService extends GenericService<TransactionAnalysis,Long> {

    public ReturnData countOrderList(String startTime, String endTime);
}
