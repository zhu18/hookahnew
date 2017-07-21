package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.WithdrawRecordMapper;
import com.jusfoun.hookah.core.domain.WithdrawRecord;
import com.jusfoun.hookah.core.domain.vo.WithdrawVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.WithdrawRecordService;
import org.springframework.stereotype.Service;

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
    public void setDao(WithdrawRecordMapper withdrawRecordMapper) {
        super.setDao(withdrawRecordMapper);
    }

    @Override
    public int insertRecord(WithdrawRecord withdrawRecord) {
        return withdrawRecordMapper.insertAndGetId(withdrawRecord);
    }

    @Override
    public ReturnData applyWithdraw(WithdrawRecord withdrawRecord) {

        ReturnData returnData = new ReturnData<>();
        if(!StringUtils.isNotBlank(withdrawRecord.getPayPwd())){
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("请输入交易密码！");
            return returnData;
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

        withdrawRecord.setMoney(withdrawRecord.getMoney() * 100);
        withdrawRecord.setSerialNo(DateUtils.getCurrentTimeFormat(new Date()) + Thread.currentThread().getId());
        withdrawRecord.setAddTime(new Date());
        withdrawRecord.setCheckStatus(HookahConstants.WithdrawStatus.waitCheck.code);
        int n = insertRecord(withdrawRecord);
        if(n == 1){
            returnData.setCode(ExceptionConst.Success);
            returnData.setMessage("提现申请成功，等待系统审核！");
        }else{
            returnData.setCode(ExceptionConst.Failed);
            returnData.setMessage("提现申请失败，请重新申请或联系管理员！");
        }
        return returnData;
    }

    @Override
    public List<WithdrawVo> getListForPage(String startDate, String endDate, String checkStatus, String orgName) {
        return withdrawRecordMapper.getListForPage(startDate, endDate, checkStatus, orgName);
    }
}
