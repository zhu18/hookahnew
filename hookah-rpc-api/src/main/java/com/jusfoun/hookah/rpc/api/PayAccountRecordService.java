package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericService;


/**
 * dengxu
 */
public interface PayAccountRecordService extends GenericService<PayAccountRecord, Integer> {

    /**
     * 出入金接口
     */
    public void entryAndExitPayments(MoneyInOutBo moneyInOutBo);
}
