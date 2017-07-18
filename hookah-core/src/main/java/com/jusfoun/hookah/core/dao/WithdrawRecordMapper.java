package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithdrawRecordMapper extends GenericDao<WithdrawRecord> {

    int insertAndGetId(WithdrawRecord withdrawRecord);

    List<WithdrawRecord> getListForPage(@Param("startDate") String startDate,
                                        @Param("endDate") String endDate,
                                        @Param("checkStatus") String checkStatus,
                                        @Param("orgName") String orgName);
}