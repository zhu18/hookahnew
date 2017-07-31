package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.generic.GenericDao;

import java.util.Map;

public interface PayAccountRecordMapper extends GenericDao<PayAccountRecord> {
    int insertAndGetId(PayAccountRecord payAccountRecord);

    void updatePayAccountRecordStatusByOrderSn(Map<String, String> params);

}