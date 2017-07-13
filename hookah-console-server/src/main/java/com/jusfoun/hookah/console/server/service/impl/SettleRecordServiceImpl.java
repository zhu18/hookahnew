package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.SettleRecordMapper;
import com.jusfoun.hookah.core.domain.SettleRecord;
import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.rpc.api.SettleRecordService;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created dx .
 */
@Service
public class SettleRecordServiceImpl extends GenericServiceImpl<SettleRecord, Long> implements SettleRecordService {

    @Resource
    private SettleRecordMapper settleRecordMapper;

    @Resource
    public void setDao(SettleRecordMapper settleRecordMapper) {
        super.setDao(settleRecordMapper);
    }

    @Resource
    WaitSettleRecordService waitSettleRecordService;

    @Transactional
    public ReturnData handleSettle(Long sid, Long supplierAmount, Long tradeCenterAmount, String userId) {

        ReturnData returnData = new ReturnData<>();
        returnData.setCode(ExceptionConst.Success);

        WaitSettleRecord record = waitSettleRecordService.selectById(sid);

        if(record == null){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("未查询到此订单！");
            return returnData;
        }

        if(HookahConstants.NO_SETTLE_STATUS != record.getSettleStatus()){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("该订单已结算！");
            return returnData;
        }

        if((supplierAmount + tradeCenterAmount) <= 0){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("结算金额应大于0元！");
            return returnData;
        }

        if(record.getNoSettleAmount() < (supplierAmount + tradeCenterAmount)){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("本次结算金额超出未结清金额！");
            return returnData;
        }

        // 修改待结算记录
        int n = waitSettleRecordService.handleSettleRecord(sid, (supplierAmount + tradeCenterAmount));
        if(n != 1){
            logger.error("本次结算金额失败，订单号-{}---操作时间-" + LocalDateTime.now(), record.getOrderSn());
            throw new RuntimeException();
        }

        // 添加结算记录
        SettleRecord settleRecord = new SettleRecord();
        settleRecord.setGoodsId(record.getGoodsId());
        settleRecord.setWaitSettleRecordId(record.getId());
        settleRecord.setSettleDate(new Date());
        settleRecord.setSettleAmount(supplierAmount + tradeCenterAmount);
        settleRecord.setSupplierAmount(supplierAmount);
        settleRecord.setTradeCenterAmount(tradeCenterAmount);
        settleRecord.setRefundAmount(0l);
        settleRecord.setSettleStatus((byte)0);
        settleRecord.setAddTime(new Date());
        settleRecord.setAddOperator(userId);
        int m = settleRecordMapper.insert(settleRecord);
        if(m != 1){
            logger.error("本次添加结算记录失败，订单号-{}---操作时间-" + LocalDateTime.now(), record.getOrderSn());
            throw new RuntimeException();
        }

        // 进行虚拟账户 内部转账

        return returnData;
    }
}
