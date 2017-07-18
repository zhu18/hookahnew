package com.jusfoun.hookah.oauth2server.service.impl;


import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * dengxu
 */

@Service
public class PayAccountServiceImpl extends GenericServiceImpl<PayAccount, Long> implements
		PayAccountService {

	@Resource
	private PayAccountMapper payAccountMapper;

	@Resource
	public void setDao(PayAccountMapper payAccountMapper) {
		super.setDao(payAccountMapper);
	}

	@Transactional
	public int operatorByType(Long payAccountId, Integer operatorType, Long money) {
		return 1;
	}

	@Transactional
	public void operatorByType(Long payAccountId, String userId, Integer operatorType, Long money, String orderSn, String operatorId ) {

	}

	@Transactional
	public int operatorByType(MoneyInOutBo moneyInOutBo, Long id) {
		return 1;
	}

	/**
	 *  用户注册时，同时在pay_account表插入记录
	 * @param userId
	 * @param userName
	 */
	public void insertPayAccountByUserIdAndName(String userId, String userName) {

		List<Condition> filters = new ArrayList<>();
		if(StringUtils.isNotBlank(userId)){
			filters.add(Condition.eq("userId", userId));
		}
		PayAccount payAccount = super.selectOne(filters);

		if (payAccount == null) {
			PayAccount pa = new PayAccount();
			pa.setUserId(userId);
			pa.setUserName(userName);
			pa.setBalance(0l);
			pa.setUseBalance(0l);
			pa.setFrozenBalance(0l);
			pa.setPayPassword("00000000");
			pa.setAccountFlag((byte) 1);
			pa.setMerchantId("");
			pa.setSyncFlag((byte) 0);
			pa.setAddTime(new Date());
			pa.setAddOperator("system");
			payAccountMapper.insertAndGetId(pa);
		}
	}

	/**
	 *  重置支付密码
	 * @param id 账户表即pay_account表主键
	 * @param payPassword   支付密码
	 */
	public void resetPayPassword(Long id, String payPassword) {
		Map<String, Object> map = new HashMap<>();
		PayAccount pa = new PayAccount();
		map.put("id", id);
		map.put("payPassword", payPassword);
		payAccountMapper.resetPayPassword(map);
	}

	@Override
	public void payByBalance(OrderInfo orderInfo) throws Exception {

	}

	@Override
	public String payByAli(OrderInfo orderInfo){
		return new String();
	}

	public ReturnData userRecharge(Map<String,Object> params){ return new ReturnData();};

	public void saveRechargeResult(Map<String,String> params){};
}
