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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	public int operatorByType(Long payAccountId, byte operatorType, Long money) {

		Map<String, Object> map = new HashMap<>();

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

		// 加钱
		if(operatorType == PayConstants.TradeType.OnlineRecharge.code ||
			operatorType == PayConstants.TradeType.ManualRecharge.code ||
			operatorType == PayConstants.TradeType.CashREverse.code ||
			operatorType == PayConstants.TradeType.SalesIn.code ||
			operatorType == PayConstants.TradeType.ChargeIn.code ||
			operatorType == PayConstants.TradeType.OfflineRecharge.code){
			map.put("type", "plus");
		}else if( // 减钱
				operatorType == PayConstants.TradeType.OnlineCash.code ||
				operatorType == PayConstants.TradeType.SalesOut.code ||
				operatorType == PayConstants.TradeType.ManualDebit.code
				){
			map.put("type", "sub");
		}else if(operatorType == PayConstants.TradeType.FreezaIn.code){
			map.put("type", "FreezaIn");
		}else if(operatorType == PayConstants.TradeType.releaseDraw.code){
			map.put("type", "releaseDraw");
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

	/**
	 *  用户注册时，同时在pay_account表插入记录
	 * @param userId
	 * @param userName
	 */
	public void insertPayAccountByUserIdAndName(String userId, String userName) {

		PayAccount payAccount = payAccountMapper.selectByPrimaryKey(userId);

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
}
