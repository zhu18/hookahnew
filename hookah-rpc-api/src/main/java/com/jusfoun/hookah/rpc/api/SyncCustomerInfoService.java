package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * ndf
 */
public interface SyncCustomerInfoService  extends GenericService<PayAccount, Integer> {

    /**
     * 客户信息同步
     */
    public void sendFirmReg(PayAccount payAccount);

}
