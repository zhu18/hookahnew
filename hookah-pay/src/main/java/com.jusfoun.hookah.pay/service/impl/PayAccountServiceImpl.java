package com.jusfoun.hookah.pay.service.impl;


import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.PayAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * dengxu
 */

@Service
public class PayAccountServiceImpl extends GenericServiceImpl<PayAccount, Integer> implements
		PayAccountService {

	@Resource
	private PayAccountMapper payAccountMapper;

	@Resource
	public void setDao(PayAccountMapper payAccountMapper) {
		super.setDao(payAccountMapper);
	}

	@Transactional
	public int operatorByType(MoneyInOutBo moneyInOutBo) {

		Map<String, Object> map = new HashMap<>();
		map.put("type", moneyInOutBo.getOperatorType());
		map.put("id", moneyInOutBo.getPayAccountID());
		map.put("changeMoney", moneyInOutBo.getMoney());
		int n = payAccountMapper.OperatorByType(map);
		if(n != 1){
			logger.info("用户[userId]->" + moneyInOutBo.getUserId() + "===>"
					+ (moneyInOutBo.getOperatorType() == 1 ? "入金" : "出金") + "操作失败" + LocalDateTime.now());
			throw new RuntimeException();
		}
		return n;
	}
}
