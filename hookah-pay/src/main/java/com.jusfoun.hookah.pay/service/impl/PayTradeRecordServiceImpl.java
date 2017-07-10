package com.jusfoun.hookah.pay.service.impl;


import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.PayTradeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * dengxu
 */

@Service
public class PayTradeRecordServiceImpl extends GenericServiceImpl<PayTradeRecord, Integer> implements
		PayTradeRecordService {


	@Resource
	private PayTradeRecordMapper payTradeRecordMapper;

	@Resource
	public void setDao(PayTradeRecordMapper payTradeRecordMapper) {
		super.setDao(payTradeRecordMapper);
	}


	@Transactional
	public PayTradeRecord initPayTradeRecord(MoneyInOutBo moneyInOutBo, String payAccountRecordId) {

		PayTradeRecord payTradeRecord = new PayTradeRecord();
		payTradeRecord.setPayAccountId(moneyInOutBo.getPayAccountID());
		payTradeRecord.setUserId(moneyInOutBo.getUserId());
		payTradeRecord.setMoney(moneyInOutBo.getMoney());
		payTradeRecord.setTradeType(moneyInOutBo.getOperatorType());
		payTradeRecord.setTradeStatus(PayConstants.TransferStatus.handing.getCode());
		payTradeRecord.setAddTime(new Date());
		payTradeRecord.setOrderSn(payAccountRecordId);
		payTradeRecord.setAddOperator(moneyInOutBo.getUserId());
		payTradeRecord.setTransferDate(new Date());
		int n = payTradeRecordMapper.insertAndGetId(payTradeRecord);
		if(n != 1){
			logger.info("添加内部流水失败");
			throw new RuntimeException();
		}
		return payTradeRecord;
	}
}
