package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

public interface WaitSettleRecordMapper extends GenericDao<WaitSettleRecord> {

    int insertAndGetId(WaitSettleRecord waitSettleRecord);

    int settleOperator(@Param("id") Long id, @Param("settleAmount") Long settleAmount);
}