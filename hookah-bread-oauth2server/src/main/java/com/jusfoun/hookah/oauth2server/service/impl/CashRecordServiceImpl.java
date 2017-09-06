package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.dao.CashRecordMapper;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.CashRecord;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.CashRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * dx
 */
@Service
public class CashRecordServiceImpl extends GenericServiceImpl<CashRecord, String> implements CashRecordService {

    @Resource
    private CashRecordMapper cashRecordMapper;

    @Resource
    public void setDao(CashRecordMapper cashRecordMapper) {
        super.setDao(cashRecordMapper);
    }



}
