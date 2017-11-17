package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfRuleMapper;
import com.jusfoun.hookah.core.domain.jf.JfRule;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.JfRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 积分规则服务
 * @author:jsshao
 * @date: 2017-3-17
 */

@Service
public class JfRuleServiceImpl extends GenericServiceImpl<JfRule, Long> implements JfRuleService {

    @Resource
    private JfRuleMapper jfRuleMapper;

    @Resource
    public void setDao(JfRuleMapper jfRecordMapper) {
        super.setDao(jfRecordMapper);
    }

    @Override
    public int insertAndGetId(JfRule jfRule) {
        return jfRuleMapper.insertAndGetId(jfRule);
    }

    @Override
    public JfRule selectBySn(Integer sn) {

//        List<Condition> filters = new ArrayList<>();
//        filters.add(Condition.eq("sn"), sn);
//        jfRuleMapper.select

        return null;
    }
}
