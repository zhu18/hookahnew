package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.dao.zb.ZbTrusteeRecordMapper;
import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.ZbTrusteeRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZbTrusteeRecordServiceImpl extends GenericServiceImpl<ZbTrusteeRecord, Long> implements ZbTrusteeRecordService {

    @Resource
    ZbTrusteeRecordMapper zbTrusteeRecordMapper;

    @Resource
    public void setDao(ZbTrusteeRecordMapper zbTrusteeRecordMapper) {
        super.setDao(zbTrusteeRecordMapper);
    }
}
