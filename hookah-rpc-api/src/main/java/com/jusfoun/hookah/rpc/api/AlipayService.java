package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.generic.GenericService;

public interface AlipayService extends GenericService<PayAccountRecord, String> {

    /**
     * 在线支付、充值
     *
     * @param userId    用户id
     * @param orderId   订单id
     * @param notifyUrl 异步通知地址
     * @param returnUrl 同步通知地址
     * @return 表单
     */
    String doPay(String userId, String orderId, String notifyUrl, String returnUrl);

    /**
     * 记账
     *
     * @param record 记账对象
     * @return 是否成功
     */
    boolean insertRecord(PayAccountRecord record);

    /**
     * 更新记账状态
     *
     * @param record 记账对象
     * @return 是否成功
     */
    boolean updateRecordStatus(PayAccountRecord record);
}
