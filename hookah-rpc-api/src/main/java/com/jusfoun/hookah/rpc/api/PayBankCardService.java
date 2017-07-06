package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericService;


public interface PayBankCardService extends GenericService<PayBankCard, String> {

    /**
     * @param userId
     * @param customerNum   客户号
     * @param bankName      银行名称
     * @param bankCardNum   银行卡号
     * @param bankCardOwner 持卡人
     * @param ip
     * @param ukey          k宝信息
     * @return
     */
    boolean bankCardSignOn(String userId, String customerNum, String bankName, String bankCardNum, String bankCardOwner, String ip, String ukey);

    /**
     * @param id            主键
     * @param userId        用户id
     * @param customerNum   客户号
     * @param bankCardNum   银行卡号
     * @param bankCardOwner 持卡人
     * @param ip            IP地址
     * @param ukey          k宝信息
     * @return
     */
    boolean bankCardSignOff(Integer id, String userId, String customerNum, String bankCardNum, String bankCardOwner, String ip, String ukey);
}
