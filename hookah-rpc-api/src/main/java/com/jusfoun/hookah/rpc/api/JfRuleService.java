package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.GenericService;
import com.jusfoun.hookah.core.utils.ReturnData;

/**
 * ${DESCRIPTION}
 *
 * @author : dengxu
 * @create 2017-11-21 19:28
 **/
public interface JfRuleService extends GenericService<JfRule, Integer> {

    /**
     * 修改积分规则
     * @param id
     * @param type
     * @param score
     * @param upperLimitScore
     * @param upperLimitTime
     * @param lowerLimitScore
     * @param lowerLimitTime
     * @param note
     * @param userName
     * @return
     */
    ReturnData updateRule(String id, String type, String score, String upperLimitScore,
                          String upperLimitTime, String lowerLimitScore, String lowerLimitTime,
                          String note, String userName) throws Exception;
}
