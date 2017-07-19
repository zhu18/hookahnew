package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.domain.vo.WaitSettleRecordVo;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

/**
 * dx
 */
public interface WaitSettleRecordService extends GenericService<WaitSettleRecord, Long> {

    int handleSettleRecord(Long sid, Long settleAmount);

    List<WaitSettleRecordVo> getListForPage(String startDate, String endDate, Integer settleStatus, String shopName, String orderSn);

    WaitSettleRecordVo selectDetailById(Long id);
}
