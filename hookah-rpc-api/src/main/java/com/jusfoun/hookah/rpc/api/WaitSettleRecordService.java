package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.domain.vo.WaitSettleRecordVo;
import com.jusfoun.hookah.core.generic.GenericService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dx
 */
public interface WaitSettleRecordService extends GenericService<WaitSettleRecord, Long> {

    int handleSettleRecord(@Param("id") Long sid, @Param("settleAmount") Long settleAmount);

    List<WaitSettleRecordVo> getListForPage(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("settleStatus") Integer settleStatus,
            @Param("shopName") String shopName,
            @Param("orderSn") String orderSn);

    WaitSettleRecordVo selectDetailById(@Param("id") Long id);
}
