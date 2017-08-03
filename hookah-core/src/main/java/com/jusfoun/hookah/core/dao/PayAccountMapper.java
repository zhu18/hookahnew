package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.Map;

public interface PayAccountMapper extends GenericDao<PayAccount> {

    int insertAndGetId(PayAccount PayAccount);

    int OperatorByType(Map<String, Object> map);

    void updatePayAccountMoney(Map<String, Object> map);
}