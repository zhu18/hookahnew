package com.jusfoun.hookah.crowd.service.impl;


import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.PayAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

//		入账到可用余额
//		1：在线充值（入金），
//		5：手工充值,
//		7：线下充值，
//		8：提现冲账
//		3001：销售（货款）收入
//		3007：交易交收手续费-收入

//		扣款可用余额
//		2：在线提现（出金），
//		6：手工扣款，
//		4001：销售（货款）支出

//		入账到冻结账户
//		6003：冻结划入-收益账户

//		扣款冻结账户到可用余额
//		6004：释放划出-收益账户

		// 账户操作
		Map<String, Object> map = new HashMap<>();

		// todo 加钱 这几种类型操作 balance&use_balance 同时增加
		if(operatorType.equals(HookahConstants.TradeType.OnlineRecharge.code) ||
				operatorType.equals(HookahConstants.TradeType.ManualRecharge.code) ||
				operatorType.equals(HookahConstants.TradeType.CashREverse.code) ||
				operatorType.equals(HookahConstants.TradeType.SalesIn.code) ||
				operatorType.equals(HookahConstants.TradeType.ChargeIn.code) ||
				operatorType.equals(HookahConstants.TradeType.OfflineRecharge.code)){
			map.put("type", "plus");
		}else if( // todo 提现 申请之后先冻结待提现的金额 use_balance扣除金额 frozen_balance增加金额
				operatorType.equals(HookahConstants.TradeType.CashFreeza.code)
				){
			map.put("type", "cash");
		}else if( // todo 减钱 这几种类型操作 balance&use_balance 同时扣款
				operatorType.equals(HookahConstants.TradeType.SalesOut.code) ||
						operatorType.equals(HookahConstants.TradeType.ManualDebit.code)
				){
			map.put("type", "sub");
		}else if(operatorType.equals(HookahConstants.TradeType.FreezaIn.code)){
			// todo 冻结转入 balance&frozen_balance增加金额
			map.put("type", "FreezaIn");
		}else if(
				operatorType.equals(HookahConstants.TradeType.releaseDraw.code) ||
						operatorType.equals(HookahConstants.TradeType.CashRelease.code)
				){
			// todo 释放划出 frozen_balance扣除金额 use_balance增加金额
			map.put("type", "releaseDraw");
		}else if( // todo 清算或者提现审核  balance&frozen_balance扣除金额
				operatorType.equals(HookahConstants.TradeType.SettleCut.code) ||
						operatorType.equals(HookahConstants.TradeType.OnlineCash.code)
				){
			map.put("type", "SettleCut");
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
