package com.jusfoun.hookah.console.server.service.impl;

import com.jusfoun.hookah.console.server.util.SensitiveWdFilter.WordFilter;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.common.redis.RedisOperate;
import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.UserMapper;
import com.jusfoun.hookah.core.domain.CashRecord;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.CommentVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.DateUtils;
import com.jusfoun.hookah.rpc.api.CashRecordService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    @Resource
    RedisOperate redisOperate;

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

    public Map<String, Object> getPUVCount(){
        Map<String, Object> map = new HashedMap();
        LocalDate today = LocalDate.now();
        map.put("pvdata", Integer.parseInt(redisOperate.get("pv:" + today.toString())));
        map.put("uvdata", Integer.parseInt(redisOperate.get("uv:" + today.toString())));
        return map;
    }

    @Override
    public Map<String, Object> getPUVCountByDate() {

        Map<String, Object> map = new HashedMap();
        int n = 4;
        List<Integer> listPv = new ArrayList<>(); //pv数据集合
        List<Integer> listUv = new ArrayList<>(); //uv数据集合
        List<String> listDate = new ArrayList<>(); //日期集合

        LocalDate today = LocalDate.now();
        listDate.add(today.toString().substring(6,10).replace("-","/"));
        listPv.add(Integer.parseInt(redisOperate.get("pv:" + today.toString()) == null ? "0" : redisOperate.get("pv:" + today.toString())));
        listUv.add(Integer.parseInt(redisOperate.get("uv:" + today.toString()) == null ? "0" : redisOperate.get("uv:" + today.toString())));
        for(int i = 1; i <= n; i++){
            listDate.add(today.minusDays(i).toString().substring(6,10).replace("-","/"));
            listPv.add(Integer.parseInt(redisOperate.get("pv:" + today.toString()) == null ? "0" : redisOperate.get("pv:" + today.toString())));
            listUv.add(Integer.parseInt(redisOperate.get("uv:" + today.toString()) == null ? "0" : redisOperate.get("uv:" + today.toString())));
        }
        map.put("x", listDate);
        map.put("listPv",listPv);
        map.put("listUv",listUv);
        return map;
    }

    @Override
    public String updatePayPassWord(String oldPayPassWord, String newPayPassWord, Integer safetyPayScore, String userId, Model model) {
        return null;
    }

    @Override
    public Pagination getUsersInPage(HashMap<String, Object> params) {
        int pageSize=(Integer) params.get("pageSize");
        int pageNumber=(Integer) params.get("pageNumber");
        int startIndex= (pageNumber-1)*pageSize;
        params.put("startIndex",startIndex);
        int total=userMapper.selectCountByCondition(params);
        List<User> list=userMapper.selectUsersByCondition(params);
        Pagination<User> pagination = new Pagination<User>();
        pagination.setTotalItems(total);
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNumber);
        pagination.setList(list);
        return pagination;
    }

}
