package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.PayBankCardMapper;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by computer on 2017/7/12.
 */
@Service
public class PayBankCardServiceImpl extends GenericServiceImpl<PayBankCard, String> implements PayBankCardService {
    @Resource
    private PayBankCardMapper payBankCardMapper;

    @Resource
    public void setDao(PayBankCardMapper payBankCardMapper) {
        super.setDao(payBankCardMapper);
    }


    @Override
    public boolean bankCardSignOn(String userId, String customerNum, String bankName, String bankCardNum, String bankCardOwner, String ip, String ukey) {
        return false;
    }

    @Override
    public boolean bankCardSignOff(Integer id, String userId, String customerNum, String bankCardNum, String bankCardOwner, String ip, String ukey) {
        return false;
    }
}
