package com.jusfoun.hookah.crowd.service.impl;


import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.crowd.service.PayAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayAccountServiceImpl extends GenericServiceImpl<PayAccount, Long> implements
		PayAccountService {

	@Resource
	private PayAccountMapper payAccountMapper;

	@Resource
	public void setDao(PayAccountMapper payAccountMapper) {
		super.setDao(payAccountMapper);
	}


}
