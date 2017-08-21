package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.vo.PayTradeRecordVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PayTradeRecordMapper extends GenericDao<PayTradeRecord> {

    int insertAndGetId(PayTradeRecord payTradeRecord);

    List<PayTradeRecordVo> getListForPage(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("tradeType") Integer tradeType,
                                @Param("tradeStatus") Integer tradeStatus);

    void updatePayTradeRecordStatusByOrderSn(Map<String, String> params);

    int selectStatusByOrderSn(Map<String,String> paramMap);
}