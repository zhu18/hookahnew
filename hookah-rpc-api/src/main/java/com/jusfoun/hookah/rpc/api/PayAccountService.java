package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface PayAccountService extends GenericService<PayAccount, Integer> {

    int operatorByType(Long payAccountId, byte operatorType, Long money);
}
