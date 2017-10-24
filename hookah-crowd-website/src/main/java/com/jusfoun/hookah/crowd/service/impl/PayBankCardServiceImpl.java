package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.PayBankCardMapper;
import com.jusfoun.hookah.core.domain.PayBank;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.PayBankCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PayBankCardServiceImpl extends GenericServiceImpl<PayBankCard, String> implements PayBankCardService {

    @Resource
    private PayBankCardMapper payBankCardMapper;

    @Resource
    public void setDao(PayBankCardMapper payBankCardMapper) {
        super.setDao(payBankCardMapper);
    }

    public List<PayBank> selectBankName(){
        return payBankCardMapper.selectBankName();
    }
}