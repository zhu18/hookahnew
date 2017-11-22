package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfRuleMapper;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ${DESCRIPTION}
 *
 * @author : dengxu
 * @create 2017-11-21 19:29
 **/
@Service
public class JfRuleServiceImpl extends GenericServiceImpl<JfRule, Integer> implements JfRuleService {

    @Resource
    JfRuleMapper jfRuleMapper;

    @Resource
    public void setDao(JfRuleMapper jfRuleMapper) {
        super.setDao(jfRuleMapper);
    }
}
