package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface PayAccountMapper extends GenericDao<PayAccount> {
    int insertAndGetId(PayAccount PayAccount);
}