package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.dao.WaitSettleRecordMapper;
import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created dx .
 */
@Service
public class WaitSettleRecordServiceImpl extends GenericServiceImpl<WaitSettleRecord, Long> implements WaitSettleRecordService {

    @Resource
    private WaitSettleRecordMapper waitSettleRecordMapper;

    @Resource
    public void setDao(WaitSettleRecordMapper waitSettleRecordMapper) {
        super.setDao(waitSettleRecordMapper);
    }

    @Transactional
    public int handleSettleRecord(Long sid, Long settleAmount) {
        return waitSettleRecordMapper.settleOperator(sid, settleAmount);
    }
}
