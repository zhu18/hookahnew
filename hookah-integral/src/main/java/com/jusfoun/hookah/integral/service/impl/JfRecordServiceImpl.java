package com.jusfoun.hookah.integral.service.impl;

import com.jusfoun.hookah.core.dao.jf.JfRecordMapper;
import com.jusfoun.hookah.core.domain.jf.JfRecord;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.JfRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 积分服务
 * @author:jsshao
 * @date: 2017-3-17
 */

@Service
public class JfRecordServiceImpl extends GenericServiceImpl<JfRecord, Long> implements JfRecordService {

    @Resource
    private JfRecordMapper jfRecordMapper;

    @Resource
    public void setDao(JfRecordMapper jfRecordMapper) {
        super.setDao(jfRecordMapper);
    }

    @Override
    public int insertAndGetId(JfRecord jfRecord) {
        return jfRecordMapper.insertAndGetId(jfRecord);
    }
}
