package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.WithdrawRecordMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.*;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.WithdrawRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void setDao(WithdrawRecordMapper withdrawRecordMapper) {
        super.setDao(withdrawRecordMapper);
    }

    @Override
    public int insertRecord(WithdrawRecord withdrawRecord) {
        return withdrawRecordMapper.insertAndGetId(withdrawRecord);
    }

    @Transactional
    public ReturnData applyWithdraw(WithdrawRecord withdrawRecord) {

        if(!StringUtils.isNotBlank(withdrawRecord.getPayPwd())){
            return ReturnData.error("请输入交易密码！");
        }

        // todo 添加提现记录之后  冻结用户账户金额
        List<Condition> filters = new ArrayList<>();
        filters.add(Condition.eq("userId", withdrawRecord.getUserId()));
        PayAccount payAccount = payAccountService.selectOne(filters);
        if(payAccount == null){
            return ReturnData.error("未查询到账户信息！");
        }

        if(!StringUtils.isNotBlank(withdrawRecord.getPayPwd()) ||
                !withdrawRecord.getPayPwd().equals(payAccount.getPayPassword())
                ){
            return ReturnData.error("交易密码不匹配！");
        }

        if(withdrawRecord.getMoney() > payAccount.getUseBalance()){
            return ReturnData.error("可用余额不足！");
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

        withdrawRecord.setSerialNo(DateUtils.getCurrentTimeFormat(new Date()) + Thread.currentThread().getId());
        withdrawRecord.setAddTime(new Date());
        withdrawRecord.setCheckStatus(HookahConstants.WithdrawStatus.waitCheck.code);
        int n = insertRecord(withdrawRecord);
        if(n == 1){
            ReturnData.success("提现申请成功，等待系统审核！");
        }else{
            ReturnData.error("提现申请失败，系统繁忙或稍后再试！");
        }

        payAccountService.operatorByType(payAccount.getId(),
                payAccount.getUserId().toString(),
                HookahConstants.TradeType.CashFreeza.getCode(),
                withdrawRecord.getMoney(), OrderHelper.genOrderSn(),
                payAccount.getUserName());

        return ReturnData.success("审核成功，已扣除客户账！");
    }

    @Override
    public List<WithdrawVo> getListForPage(String startDate, String endDate, String checkStatus, String orgName) {
        return withdrawRecordMapper.getListForPage(startDate, endDate, checkStatus, orgName);
    }
}
