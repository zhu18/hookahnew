package com.jusfoun.hookah.pay.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.vo.PayTradeRecordVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
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

	@Override
	public int insertAndGetId(PayTradeRecord payTradeRecord) {
		return payTradeRecordMapper.insertAndGetId(payTradeRecord);
	}

	@Override
	public Pagination<PayTradeRecordVo> getListForPage(int pageNumberNew, int pageSizeNew, String startDate, String endDate, Integer tradeType, Integer tradeStatus) {

		Pagination<PayTradeRecordVo> pagination = new Pagination<>();
		PageHelper.startPage(pageNumberNew, pageSizeNew);
		Page<PayTradeRecordVo> page = (Page<PayTradeRecordVo>) payTradeRecordMapper.getListForPage(startDate, endDate, tradeType, tradeStatus);
		if(page != null && page.size() > 0){
			page.parallelStream().forEach(x -> {

				if(x.getTradeType().equals(3007) ||
						x.getTradeType().equals(6003) ||
						x.getTradeType().equals(6004)){
					x.setAccountParty("交易中心平台资金");
				}else{
					x.setAccountParty((x.getUserName() == null ? "会员" : x.getUserName()) + "平台资金");
				}
			});
		}

		pagination.setTotalItems(page.getTotal());
		pagination.setPageSize(pageSizeNew);
		pagination.setCurrentPage(pageNumberNew);
		pagination.setList(page);

		return pagination;
	}

	public int selectStatusByOrderSn(String orderSn){
		return payTradeRecordMapper.selectStatusByOrderSn(orderSn);
	}
}
