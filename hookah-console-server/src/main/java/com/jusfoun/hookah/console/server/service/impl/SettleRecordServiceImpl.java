package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.dao.SettleRecordMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.SettleRecord;
import com.jusfoun.hookah.core.domain.WaitSettleRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.SettleRecordService;
import com.jusfoun.hookah.rpc.api.WaitSettleRecordService;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    PayAccountService payAccountService;

    @Resource
    PayTradeRecordMapper payTradeRecordMapper;

    @Transactional
    public synchronized ReturnData handleSettle(Long sid, Long supplierAmount, Long tradeCenterAmount, String userId) {

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

        if(!StringUtils.isNotBlank(record.getShopName())){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("无法获取商品所属用户信息！");
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

        // 结算总金额等于剩余待结算金额  修改待结算记录的状态
        if(record.getNoSettleAmount() == (supplierAmount + tradeCenterAmount)){
            WaitSettleRecord waitSettleRecord = new WaitSettleRecord();
            waitSettleRecord.setId(record.getId());
            waitSettleRecord.setSettleStatus(HookahConstants.HAS_SETTLE_STATUS);
            waitSettleRecord.setUpdateTime(new Date());
            waitSettleRecordService.updateByIdSelective(waitSettleRecord);
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

        List<Condition> filters = new ArrayList();
        filters.add(Condition.eq("userId", record.getShopName()));
        PayAccount payAccount = payAccountService.selectOne(filters);
        if(payAccount == null){
            logger.error("无法获取用户的账户信息， 操作时间-->" + LocalDateTime.now() );
            throw new RuntimeException("无法获取用户的账户信息");
        }



        // 请使用以下代码替换SettleRecordServiceImpl.java类中的127行至143行
        // 1.释放手续费 添加释放划出流水
        payAccountService.operatorByType(
                HookahConstants.TRADECENTERACCOUNT,
                HookahConstants.TRADECENTERUSERID,
                HookahConstants.TradeType.releaseDraw.code,
                tradeCenterAmount,
                record.getOrderSn(),
                userId.toString());

        // 2.添加手续费流水
        PayTradeRecord payTradeRecord = new PayTradeRecord();
        payTradeRecord.setPayAccountId( HookahConstants.TRADECENTERACCOUNT);
        payTradeRecord.setUserId(HookahConstants.TRADECENTERUSERID);
        payTradeRecord.setMoney(tradeCenterAmount);
        payTradeRecord.setTradeType(HookahConstants.TradeType.ChargeIn.code);
        payTradeRecord.setTradeStatus(PayConstants.TransferStatus.success.getCode());
        payTradeRecord.setAddTime(new Date());
        payTradeRecord.setOrderSn(record.getOrderSn());
        payTradeRecord.setAddOperator(userId.toString());
        payTradeRecord.setTransferDate(new Date());

        int l = payTradeRecordMapper.insert(payTradeRecord);
        if(l != 1){
            throw new RuntimeException();
        }

        // 3.释放供应商结算金额
        payAccountService.operatorByType(
                HookahConstants.TRADECENTERACCOUNT,
                HookahConstants.TradeType.SettleCut.code,
                supplierAmount);


        // 4.添加释放流水
        PayTradeRecord ptr = new PayTradeRecord();
        ptr.setPayAccountId( HookahConstants.TRADECENTERACCOUNT);
        ptr.setUserId(HookahConstants.TRADECENTERUSERID);
        ptr.setMoney(supplierAmount);
        ptr.setTradeType(HookahConstants.TradeType.releaseDraw.code);
        ptr.setTradeStatus(PayConstants.TransferStatus.success.getCode());
        ptr.setAddTime(new Date());
        ptr.setOrderSn(record.getOrderSn());
        ptr.setAddOperator(userId.toString());
        ptr.setTransferDate(new Date());
        int p = payTradeRecordMapper.insert(ptr);
        if(p != 1){
            throw new RuntimeException();
        }

      /*  // 1.先从交易中心账户扣除 欲结算的金额
        payAccountService.operatorByType(
                HookahConstants.TRADECENTERACCOUNT,
                PropertiesManager.getInstance().getProperty("jusfounOrgId"),
                HookahConstants.TradeType.SettleCut.code,
                (tradeCenterAmount + supplierAmount),
                record.getOrderSn(),
                userId.toString());

        // 2.交易中心可获得手续费收入
        payAccountService.operatorByType(
                HookahConstants.TRADECENTERACCOUNT,
                PropertiesManager.getInstance().getProperty("jusfounOrgId"),
                HookahConstants.TradeType.ChargeIn.code,
                tradeCenterAmount,
                record.getOrderSn(),
                userId.toString());
*/
        // 3.向供应商账户增加所得金额
        payAccountService.operatorByType(
                payAccount.getId(),
                payAccount.getUserId(),
                HookahConstants.TradeType.SalesIn.code,
                supplierAmount,
                record.getOrderSn(),
                userId.toString());

        returnData.setMessage("本次结算处理成功！");
        return returnData;
    }
}
