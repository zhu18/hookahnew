package com.jusfoun.hookah.core.dao.zb;

import com.jusfoun.hookah.core.domain.zb.ZbRefundRecord;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface ZbRefundRecordMapper extends GenericDao<ZbRefundRecord> {

    int insertAndGetId(ZbRefundRecord zbRefundRecord);
}