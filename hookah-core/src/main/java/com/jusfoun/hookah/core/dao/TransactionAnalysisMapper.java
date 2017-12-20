package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.TransactionAnalysis;
import com.jusfoun.hookah.core.domain.vo.TransactionAnalysisVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionAnalysisMapper extends GenericDao<TransactionAnalysis> {

    TransactionAnalysisVo selectSum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<TransactionAnalysisVo> selectBySourceList(@Param("startTime") String startTime, @Param("endTime") String endTime);
}