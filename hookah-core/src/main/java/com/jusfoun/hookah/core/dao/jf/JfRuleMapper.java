package com.jusfoun.hookah.core.dao.jf;

import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.GenericDao;

public interface JfRuleMapper extends GenericDao<JfRule> {

    /**
     * 返回主键
     * @param jfRule
     * @return
     */
    int insertAndGetId(JfRule jfRule);
}