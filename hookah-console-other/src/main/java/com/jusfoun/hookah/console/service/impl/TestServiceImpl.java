package com.jusfoun.hookah.console.service.impl;

import com.jusfoun.hookah.core.dao.TestMapper;
import com.jusfoun.hookah.core.domain.Test;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.other.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class TestServiceImpl extends GenericServiceImpl<Test, String> implements TestService {
    @Resource
    private TestMapper testMapper;


    @Resource
    public void setDao(TestMapper testMapper) {
        super.setDao(testMapper);
    }
}
