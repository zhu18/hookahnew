package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
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

    /**
     * 根据类型处理金额 添加内部流水
     * @param payAccountId  账户主键
     * @param userId        账户的用户主键
     * @param operatorType  操作类型
     * @param money         操作金额
     * @param orderSn       订单金额
     * @param operatorId    操作员id
     * @return
     */
    void operatorByType(Long payAccountId, String userId, Integer operatorType, Long money, String orderSn, String operatorId);

    int operatorByType(MoneyInOutBo moneyInOutBo, Long id);

    void insertPayAccountByUserIdAndName(String userId, String userName);

    void resetPayPassword(Long id, String payPassword);

    void payOperator(String userId, String orderId, String orderSn, Long money, String payMode) throws Exception;
}
