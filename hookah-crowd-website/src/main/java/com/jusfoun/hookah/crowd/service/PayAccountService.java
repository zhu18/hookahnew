package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * dx
 */
public interface PayAccountService extends GenericService<PayAccount, Long> {

    /**
     * 根据类型处理金额 不添加内部流水
     * @param payAccountId
     * @param operatorType
     * @param money
     * @return
     */
    int operatorByType(Long payAccountId, Integer operatorType, Long money);
}
