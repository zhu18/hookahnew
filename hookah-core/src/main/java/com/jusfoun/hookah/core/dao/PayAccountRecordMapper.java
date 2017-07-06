package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface PayAccountRecordMapper extends GenericDao<PayAccountRecord> {
    int insertAndGetId(PayAccountRecord payAccountRecord);
}