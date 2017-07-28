package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

import java.util.Map;

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

    boolean insertPayAccountByUserIdAndName(String userId, String userName);

    /**
     * 设置支付密码
     * @param userId
     * @param payPassword MD5密文
     * @return
     */
    boolean resetPayPassword(String userId, String payPassword);

    void payByBalance(OrderInfo orderInfo) throws Exception;

    String payByAli(OrderInfo orderInfo);

    /**
     * 用户充值接口
     * @param params
     * @return
     */
    ReturnData userRecharge(Map<String,String> params);


    /**
     * 更新账户并插入记录
     * @param params
     */
    ReturnData saveRechargeResult(Map<String,String> params);

    /**
     * 验证支付密码
     * @param payPassword
     * @param userId
     * @return
     */
    boolean verifyPassword (String payPassword,String userId);

    /**
     * 查询虚拟账户信息
     * @param userId
     * @return
     */
    PayAccount findPayAccountByUserId(String userId);

    boolean aliPay(String orderSn, String tradeStatus, Map<String,String> param) throws Exception;
}
