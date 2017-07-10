package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface PayAccountService extends GenericService<PayAccount, Long> {

    int operatorByType(Long payAccountId, byte operatorType, Long money);

    int operatorByType(MoneyInOutBo moneyInOutBo, Long id);

    void insertPayAccountByUserIdAndName(String userId, String userName);

    void resetPayPassword(Long id, String payPassword);
}
