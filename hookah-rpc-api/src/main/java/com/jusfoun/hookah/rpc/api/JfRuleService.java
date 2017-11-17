package com.jusfoun.hookah.rpc.api;

import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.GenericService;

/**
 * @author dx
 * @date 2017/4/13 下午1:46
 * @desc
 */
public interface JfRuleService extends GenericService<JfRule, Long> {

    int insertAndGetId(JfRule jfRule);

    JfRule selectBySn(Integer sn);

}
