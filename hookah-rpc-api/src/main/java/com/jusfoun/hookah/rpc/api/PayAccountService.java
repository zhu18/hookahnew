package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface PayAccountService extends GenericService<PayAccount, Integer> {
    int operatorByType(MoneyInOutBo moneyInOutBo);

    int operatorByType(Long payAccountId, byte operatorType, Long money);
}
