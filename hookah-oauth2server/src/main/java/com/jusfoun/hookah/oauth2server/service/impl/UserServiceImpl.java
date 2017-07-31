package com.jusfoun.hookah.oauth2server.service.impl;

import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.CashRecord;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.CashRecordService;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * @author huang lei
 * @date 2017/3/22 上午10:29
 * @desc
 */
@Service("userService")
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    RedisOperate redisOperate;

    @Resource
    public void setDao(UserMapper userMapper) {
        super.setDao(userMapper);
    }

    @Resource
    CashRecordService cashRecordService;

    @Resource
    PayAccountService payAccountService;

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

    @Async
    public void setPVCountByDate() {
        String pvKey = "pv:"+ LocalDate.now().toString();
        redisOperate.incr(pvKey);
    }

    @Async
    public void setUVCountByDate() {
        String pvKey = "uv:"+ LocalDate.now().toString();
        redisOperate.incr(pvKey);
    }

    @Override
    public Map<String, Object> getPUVCountByDate() {

        Map<String, Object> map = new HashedMap();
        //int n = 4;
        //List<Integer> listPv = new ArrayList<>(); //pv数据集合
        //List<Integer> listUv = new ArrayList<>(); //uv数据集合
        //List<String> listDate = new ArrayList<>(); //日期集合
        LocalDate today = LocalDate.now();
        //listDate.add(today.toString());
        //listPv.add(Integer.parseInt(redisOperate.get("pv:" + today.toString())));
        //listUv.add(Integer.parseInt(redisOperate.get("uv:" + today.toString())));
        /*for(int i = 1; i <= n; i++){
            listDate.add(today.minusDays(i).toString());
            listPv.add(Integer.parseInt(redisOperate.get("pv:" + today.minusDays(i))));
            listUv.add(Integer.parseInt(redisOperate.get("uv:" + today.minusDays(i))));
        }*/
        //map.put("puvdate", listDate);
        map.put("pvdata", Integer.parseInt(redisOperate.get("pv:" + today.toString())));
        map.put("uvdata", Integer.parseInt(redisOperate.get("uv:" + today.toString())));
        return map;
    }

    @Override
    public String updatePayPassWord(String oldPayPassWord, String newPayPassWord, Integer safetyPayScore, String userId, Model model) {
        if (!StringUtils.isNotBlank(userId)) {
            model.addAttribute("title", "请重新登录");
            return "modify/updateLoginPwd";
        }
        if(StringUtils.stringsIsEmpty(oldPayPassWord,newPayPassWord)){
            model.addAttribute("error","原始交易密码与新交易密码不可为空");
            return  "modify/payPassword";
        }
        if(oldPayPassWord.equals(newPayPassWord)){
            model.addAttribute("error","原始交易密码不可与新交易密码一致");
            return "modify/payPassword";
        }

        if(payAccountService.updatePayPassWordByUserId(oldPayPassWord, newPayPassWord, userId)){
            User user=selectById(userId);
            //判断支付密码设置状态
            if(user.getPaymentPasswordStatus() != HookahConstants.PayPassWordStatus.isOK.getCode()){
                user.setPaymentPasswordStatus(HookahConstants.PayPassWordStatus.isOK.getCode());
            }
            //更新安全分值
            user.setSafetyPayScore(safetyPayScore);
            if(updateById(user) == 0)
                logger.error("更新用户信息失败");
            return "redirect:/modify/success?type=payPassword";
        }else{
            model.addAttribute("error","修改交易密码失败，请联系管理员。");
            return "modify/payPassword";
        }
    }
}
