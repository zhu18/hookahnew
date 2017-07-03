package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.CashRecord;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.CashRecordService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author huang lei
 * @date 2017/2/28 下午4:37
 * @desc
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    public void setDao(UserMapper userMapper) {
        super.setDao(userMapper);
    }

    @Resource
    CashRecordService cashRecordService;

    public User insert(User user) {
        String encPassword = new Md5Hash(user.getPassword()).toString();
        user.setPassword(encPassword);
        User u = super.insert(user);
        return u;
    }

    @Transactional(rollbackFor = Exception.class)
    public int manualRecharge(String userId, long rechagerMoney, String operator) throws Exception{

        CashRecord cashRecord = new CashRecord();
        cashRecord.setCashDate(new Date());
        cashRecord.setCashAmount(rechagerMoney);
        cashRecord.setCashNumber(DateUtils.getCurrentTimeFormat(new Date()) + HookahConstants.CashType.ManualRecharge.getCode());
        cashRecord.setCashOperator(operator);
        cashRecord.setCashStatus(HookahConstants.CashStatus.handing.getCode());
        cashRecord.setUserId(userId);
        cashRecord.setCashType(HookahConstants.CashType.ManualRecharge.getCode());
        cashRecord.setAddTime(new Date());
//        cashRecord.setBeforeAmount();
//        cashRecord.setOrderId();
//        cashRecord.setCashNote();
        cashRecord = cashRecordService.insert(cashRecord);

        int n = userMapper.rechargeMoney(userId, rechagerMoney);
        if(n > 0){
            cashRecord.setCashStatus(HookahConstants.CashStatus.success.getCode());
        }else{
            cashRecord.setCashStatus(HookahConstants.CashStatus.fail.getCode());
        }
        int m = cashRecordService.updateByIdSelective(cashRecord);
        if(m < 1){
            throw new RuntimeException("操作失败");
        }
        return m;
    }

}
