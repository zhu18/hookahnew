package com.jusfoun.hookah.crowd.service;

import com.jusfoun.hookah.core.domain.zb.ZbRefundRecord;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

public interface ZbRefundRecordService extends GenericService<ZbRefundRecord, Long> {

    ZbRefundRecord insertRecord(Long requirementId, Integer refundType, String refundDesc);

    ReturnData selectRefundInfo(String userId, String requirementId);

    int insertData(ZbRefundRecord zbRefundRecord);
}
