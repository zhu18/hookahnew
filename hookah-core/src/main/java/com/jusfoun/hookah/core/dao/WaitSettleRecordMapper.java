package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.domain.vo.WaitSettleRecordVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WaitSettleRecordMapper extends GenericDao<WaitSettleRecord> {

    int insertAndGetId(WaitSettleRecord waitSettleRecord);

    int settleOperator(@Param("id") Long id, @Param("settleAmount") Long settleAmount);

    List<WaitSettleRecordVo> getListForPage(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("settleStatus") Integer settleStatus,
            @Param("shopName") String shopName,
            @Param("orderSn") String orderSn);

    WaitSettleRecordVo selectDetailById(@Param("id") Long id);
}