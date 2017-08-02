package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.generic.GenericService;

public interface AlipayService extends GenericService<PayAccountRecord, String> {

    /**
     * 在线支付
     *
     * @param userId     用户id
     * @param orderId    订单id
     * @param notify_url 异步回调地址
     * @param return_url 同步返回地址
     * @return 表单
     */
    String doPay(String userId, String orderId, String notify_url, String return_url);

    /**
     * 在线充值
     *
     * @param userId     用户id
     * @param money      充值金额
     * @param notify_url 异步回调地址
     * @param return_url 同步返回地址
     * @return 表单
     */
    String doCharge(String userId,Long payAccountId, String money, String notify_url, String return_url);

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
