package com.jusfoun.hookah.pay.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.vo.PayTradeRecordVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.core.utils.JsonUtils;
import com.jusfoun.hookah.rpc.api.PayTradeRecordService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * dengxu
 */

@Service
public class PayTradeRecordServiceImpl extends GenericServiceImpl<PayTradeRecord, Integer> implements
		PayTradeRecordService {


	@Resource
	private PayTradeRecordMapper payTradeRecordMapper;

	@Resource
	UserService userService;

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


	@Override
	public Pagination<PayTradeRecordVo> getFlowListInPage(Integer pageNum, Integer pageSize, List<Condition> filters, List<OrderBy> orderBys) {
		PageHelper.startPage(pageNum, pageSize);
        Page<PayTradeRecord> list =  (Page<PayTradeRecord>) super.selectList(filters,orderBys);
        Page<PayTradeRecordVo> page = new Page<PayTradeRecordVo>(pageNum,pageSize);
		for(PayTradeRecord pay : list){
			PayTradeRecordVo payTradeRecordVo = new PayTradeRecordVo();
			this.copyProperties(pay, payTradeRecordVo, null);
			User user = userService.selectById(payTradeRecordVo.getUserId());
			if(payTradeRecordVo.getTradeType().equals(3007) ||
					payTradeRecordVo.getTradeType().equals(6003) ||
					payTradeRecordVo.getTradeType().equals(6004)){
				payTradeRecordVo.setAccountParty("交易中心账户资金");
			}else{
				if(user != null){
					payTradeRecordVo.setAccountParty(user.getUserName() + "账户资金");
				}else{
					payTradeRecordVo.setAccountParty("会员账户资金");
				}
			}
			page.add(payTradeRecordVo);
		}
        Pagination<PayTradeRecordVo> pagination = new Pagination<PayTradeRecordVo>();
        pagination.setTotalItems(list.getTotal());
        pagination.setPageSize(pageSize);
        pagination.setCurrentPage(pageNum);
        pagination.setList(page);
        logger.info(JsonUtils.toJson(pagination));
		return pagination;
	}
}
