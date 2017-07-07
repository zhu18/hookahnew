package com.jusfoun.hookah.pay.service.impl;


import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.PayConstants;
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
	public int operatorByType(Long payAccountId, byte operatorType, Long money) {

		Map<String, Object> map = new HashMap<>();

		// 加钱
		if(operatorType == PayConstants.TradeType.IntoAccount.code ||
			operatorType == PayConstants.TradeType.OnlineRecharge.code ||
			operatorType == PayConstants.TradeType.ManualRecharge.code ||
			operatorType == PayConstants.TradeType.CashREverse.code ||
			operatorType == PayConstants.TradeType.OfflineRecharge.code){

			map.put("type", "plus");
		}else if( // 减钱
				operatorType == PayConstants.TradeType.Deduct.code ||
				operatorType == PayConstants.TradeType.OnlineCash.code ||
				operatorType == PayConstants.TradeType.ManualDebit.code
				){
			map.put("type", "sub");
		}

		map.put("id", payAccountId);
		map.put("changeMoney", money);
		int n = payAccountMapper.OperatorByType(map);
		if(n != 1){
			logger.info("用户[payAccountId]->" + payAccountId + "===>操作失败" + LocalDateTime.now());
			throw new RuntimeException();
		}
		return n;
	}
}
