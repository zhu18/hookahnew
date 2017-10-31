package com.jusfoun.hookah.crowd.service.impl;


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
import com.jusfoun.hookah.crowd.service.PayTradeRecordService;
import com.jusfoun.hookah.crowd.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@Override
	public int insertAndGetId(PayTradeRecord payTradeRecord) {
		return payTradeRecordMapper.insertAndGetId(payTradeRecord);
	}
}
