package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayBank;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.List;

public interface PayBankCardMapper extends GenericDao<PayBankCard> {

    List<PayBank> selectBankName();
}