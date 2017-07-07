package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.mongo.MgMoneyInOutLog;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.Map;

/**
 * dx
 */
public interface MgMoneyInOutLogService extends GenericService<MgMoneyInOutLog, String> {

    void initMgMoneyInOutLog(MoneyInOutBo moneyInOutBo, Map<String, String> paramMap, Long id);

    void updateMgMoneyInOutLog(MoneyInOutBo moneyInOutBo, Map<String, Object> resultMap, Long id);
}
