package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfOverdueDetailsMapper;
import com.jusfoun.hookah.core.dao.jf.JfRuleMapper;
import com.jusfoun.hookah.core.domain.jf.JfOverdueDetails;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.JfOverdueDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 积分服务
 * @author:jsshao
 * @date: 2017-3-17
 */

@Service
public class JfOverdueDetailsServiceImpl extends GenericServiceImpl<JfOverdueDetails, Long> implements JfOverdueDetailsService {

    @Resource
    private JfOverdueDetailsMapper jfOverdueDetailsMapper;

    @Resource
    public void setDao(JfOverdueDetailsMapper jfRecordMapper) {
        super.setDao(jfRecordMapper);
    }


}
