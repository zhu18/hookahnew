package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.PayBank;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

public interface PayBankCardService extends GenericService<PayBankCard, String> {

    List<PayBank> selectBankName();
}
