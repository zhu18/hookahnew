package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfRecordMapper;
import com.jusfoun.hookah.rpc.api.JfRecordCacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JfRecordCacheServiceImpl implements JfRecordCacheService {

    @Resource
    JfRecordMapper jfRecordMapper;

    @Cacheable(value = "personUseJfSum")
    @Override
    public Integer getUseScoreByUserId(String userId) {
        System.out.println("查询userId可用积分");
        return jfRecordMapper.getUseScoreByUserId(userId);
    }
}
