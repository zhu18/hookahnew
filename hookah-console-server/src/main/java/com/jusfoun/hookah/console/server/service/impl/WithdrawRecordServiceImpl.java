package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.dao.PayAccountRecordMapper;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.dao.WithdrawRecordMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.OrderHelper;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.WithdrawRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 提现
 */
@Service
public class WithdrawRecordServiceImpl extends GenericServiceImpl<WithdrawRecord, Long> implements WithdrawRecordService {

    @Resource
    private WithdrawRecordMapper withdrawRecordMapper;

    @Resource
    PayAccountService payAccountService;

    @Resource
    PayAccountMapper payAccountMapper;

    @Resource
    private PayAccountRecordMapper payAccountRecordMapper;

    @Resource
    PayTradeRecordMapper payTradeRecordMapper;


    @Resource
    public void setDao(WithdrawRecordMapper withdrawRecordMapper) {
        super.setDao(withdrawRecordMapper);
    }

    @Override
    public int insertRecord(WithdrawRecord withdrawRecord) {
        return withdrawRecordMapper.insertAndGetId(withdrawRecord);
    }

    @Transactional
    public ReturnData applyWithdraw(WithdrawRecord withdrawRecord) {
        ReturnData returnData = new ReturnData();
        if(!StringUtils.isNotBlank(withdrawRecord.getPayPwd())){
            return ReturnData.fail("请输入交易密码！");
        }
        try {
            // todo 添加提现记录之后  冻结用户账户金额
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", withdrawRecord.getUserId()));
            PayAccount payAccount = payAccountService.selectOne(filters);
            if (payAccount == null) {
                return returnData.fail("未查询到账户信息！");
            }

            if (!StringUtils.isNotBlank(withdrawRecord.getPayPwd()) ||
                    !withdrawRecord.getPayPwd().equals(payAccount.getPayPassword())
                    ) {
                return returnData.fail("交易密码不匹配！");
            }

            if (withdrawRecord.getMoney() > payAccount.getUseBalance()) {
                return ReturnData.fail("可用余额不足！");
            }

            // 产品未确定 暂时注释
//        List<Condition> filters = new ArrayList();
//        filters.add(Condition.eq("checkStatus", HookahConstants.WithdrawStatus.waitCheck.code));
//        filters.add(Condition.eq("userId", withdrawRecord.getUserId()));
//        List<WithdrawRecord> list = this.selectList(filters);
//        if(list.size() > 0){
//            returnData.setCode(ExceptionConst.Failed);
//            returnData.setMessage("您还有未处理完成的提现申请！");
//            return returnData;
//        }

            String orderSn = OrderHelper.genOrderSn();
            //withdrawRecord.setSerialNo(DateUtils.getCurrentTimeFormat(new Date()) + Thread.currentThread().getId());
            withdrawRecord.setSerialNo(orderSn);
            withdrawRecord.setAddTime(new Date());
            withdrawRecord.setCheckStatus(HookahConstants.WithdrawStatus.waitCheck.code);
            String userId = withdrawRecord.getUserId();
            Long money = withdrawRecord.getMoney();
            Long payAccountId = payAccount.getId();
            int tradeType = HookahConstants.TradeType.OnlineCash.getCode();

            //插入审核表
            int n = insertRecord(withdrawRecord);

            //插入记录表
            insertPayTradeRecord(userId, money, payAccountId, orderSn, HookahConstants.WithdrawStatus.waitCheck.code, tradeType);
            insertPayAccountRecord(userId, money, payAccountId, orderSn, HookahConstants.WithdrawStatus.waitCheck.code, tradeType);

            //扣除账号可用余额，增加冻结金额
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("payAccountId", payAccount.getId());
            paramMap.put("userId", withdrawRecord.getUserId());
            paramMap.put("balance", 0); //总额不变
            paramMap.put("useBalance", -withdrawRecord.getMoney()); // 扣除可用金额
            paramMap.put("frozenBalance", withdrawRecord.getMoney()); // 增加冻结金额

            int m = payAccountMapper.updatePayAccountAllMoney(paramMap);  //更新账户表

            returnData.success("提现申请成功，等待系统审核！");

       /* payAccountService.operatorByType(payAccount.getId(),
                payAccount.getUserId().toString(),
                HookahConstants.TradeType.CashFreeza.getCode(),
                withdrawRecord.getMoney(), OrderHelper.genOrderSn(),
                payAccount.getUserName());*/
        }catch (Exception e){
            e.printStackTrace();
            returnData.error("提现申请失败，系统繁忙或稍后再试！");
        }finally {

            return returnData;
        }
    }

    @Override
    public List<WithdrawVo> getListForPage(String startDate, String endDate, String checkStatus, String orgName) {
        return withdrawRecordMapper.getListForPage(startDate, endDate, checkStatus, orgName);
    }

    @Transactional
    public ReturnData checkOneApply(Long id, byte checkStatus, String checkMsg, String userName) {

        ReturnData returnData = new ReturnData();

        if(checkStatus == HookahConstants.WithdrawStatus.checkFail.code && !StringUtils.isNotBlank(checkMsg)){
            return returnData.error("提现审核失败，请填写失败原因！");
        }

        try {
            // todo 查询提现申请
            WithdrawRecord record = this.selectById(id);
            if (record == null) {
                return returnData.error("未获取到有效的提现申请记录！");
            }

            if (record.getCheckStatus() != HookahConstants.WithdrawStatus.waitCheck.code) {
                return returnData.error("该申请已审批，请勿重复审批！");
            }
            String userId = record.getUserId();
            Long money = record.getMoney();
            // todo 根据提现申请中的user_id查询用户的资金账户
            List<Condition> filters = new ArrayList<>();
            filters.add(Condition.eq("userId", userId));
            PayAccount payAccount = payAccountService.selectOne(filters);

            if (payAccount == null) {
                return returnData.error("未获取到用户的资金账户！");
            }

            // todo 修改提现申请记录状态
            record.setCheckStatus(checkStatus);
            record.setCheckMsg(checkMsg);
            record.setCheckTime(new Date());
            record.setCheckOperator(userName);
            int n = this.updateByIdSelective(record);
            int tradeStatus;
            if (n == 1) {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("payAccountId", payAccount.getId());
                paramMap.put("userId", userId);
                // todo 修改成功并且审核状态为通过
                if (checkStatus == HookahConstants.WithdrawStatus.CheckSuccess.code) {
                    tradeStatus = PayConstants.TransferStatus.success.getCode();
                    paramMap.put("balance", -money); //扣除总余额
                    paramMap.put("useBalance", 0);  //可用余额不变（提现时已扣除过了）
                    returnData.success("审核通过，已扣除客户账！");
                } else {  // todo 修改成功并且审核状态为不通过
                    tradeStatus = PayConstants.TransferStatus.fail.getCode();
                    paramMap.put("balance", 0); //总余额不变
                    paramMap.put("useBalance", money); // 增加可用金额
                    returnData.success("审核不通过，资金已退回可用余额！");
                }

                paramMap.put("frozenBalance", -money); //扣除冻结金额

                //扣除账号可用余额，增加冻结金额
                int m = payAccountMapper.updatePayAccountAllMoney(paramMap);

                //更新记录表
                Map<String, String> params = new HashMap<>();
                params.put("orderSn", record.getSerialNo());
                params.put("tradeStatus", String.valueOf(tradeStatus));
                payTradeRecordMapper.updatePayTradeRecordStatusByOrderSn(params);
                payAccountRecordMapper.updatePayAccountRecordStatusByOrderSn(params);
            } else {
                returnData.error("系统繁忙审核失败，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnData.error("系统繁忙审核失败，请稍后再试！");
        }finally {
            return returnData;
        }
/*
        // todo 修改成功并且审核状态为通过
        if(n == 1 && checkStatus == HookahConstants.WithdrawStatus.CheckSuccess.code){

            // todo 账户总金额- 冻结金额-
            payAccountService.operatorByType(payAccount.getId(),
                    payAccount.getUserId().toString(),
                    HookahConstants.TradeType.OnlineCash.getCode(),
                    record.getMoney(), OrderHelper.genOrderSn(),
                    userName);

            returnData.success("审核通过，已扣除客户账！");
        }else if(n == 1 && checkStatus == HookahConstants.WithdrawStatus.checkFail.code){

            // todo 修改成功并且审核状态为不通过 该笔提现的冻结金额- 可用余额+
            payAccountService.operatorByType(payAccount.getId(),
                    payAccount.getUserId().toString(),
                    HookahConstants.TradeType.CashRelease.getCode(),
                    record.getMoney(), OrderHelper.genOrderSn(),
                    userName);

            returnData.success("审核不通过，资金已退回可用余额！");
        }else{
            returnData.error("系统繁忙审核失败，请稍后再试！");
        }*/

    }

    public void insertPayTradeRecord(String userId,Long money,Long payAccountId,String orderSn,int tradeStatus,int tradeType){
        PayTradeRecord ptr=new PayTradeRecord();
        ptr.setMoney(Math.abs(money));
        ptr.setPayAccountId(payAccountId);
        ptr.setOrderSn(orderSn);
        ptr.setUserId(userId);
        ptr.setTradeStatus((byte)tradeStatus);
        ptr.setTradeType(tradeType);
        ptr.setAddOperator(userId);
        Date now=new Date();
        ptr.setTransferDate(now);
        ptr.setAddTime(now);
        ptr.setUpdateOperator(userId);
        ptr.setUpdateTime(now);
        payTradeRecordMapper.insert(ptr);
    }

    public void insertPayAccountRecord(String userId,Long money,Long payAccountId,String orderSn,int tradeStatus,int tradeType){
        PayAccountRecord par = new PayAccountRecord();
        par.setChannelType("提现");
        par.setMoney(money);
        par.setSerialNumber(orderSn);//订单号
        par.setPayAccountId(payAccountId);
        par.setTransferStatus((byte)tradeStatus);
        par.setTransferType((byte)tradeType);
        par.setTransferDate(new Date());
        par.setAddOperator(userId);
        par.setAddTime(new Date());
        par.setUpdateOperator(userId);
        par.setUpdateTime(new Date());
        par.setUserId(userId);
        payAccountRecordMapper.insert(par);
    }
}
