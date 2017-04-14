package com.jusfoun.hookah.console.server.service.impl;


import com.jusfoun.hookah.core.dao.AccNoTokenMapper;
import com.jusfoun.hookah.core.domain.AccNoToken;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.rpc.api.AccNoTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AccNoTokenServiceImpl extends GenericServiceImpl<AccNoToken,String> implements
		AccNoTokenService {
	@Resource
	private AccNoTokenMapper mapper;

	@Resource
	public void setDao(AccNoTokenMapper mapper) {
		super.setDao(mapper);
	}

	@Transactional(readOnly=false)
	@Override
	public void updateTokenByOrderSn(String orderSn, String token) {
		AccNoToken accNoToken = new AccNoToken();
		accNoToken.setOrderSn(orderSn);
		accNoToken.setToken(token);
		//更新订单号对应的token
		mapper.updateTokenAndStatusByOrderSn(accNoToken);
		//更新卡号对应的token
		AccNoToken result = mapper.findByOrderSn(orderSn);
		accNoToken.setAccNo(result.getAccNo());
		mapper.updateTokenByAccNo(accNoToken);
	}

	@Override
	public List<AccNoToken> findAccNoList(String userId) {
		AccNoToken accNoToken = new AccNoToken();
		accNoToken.setUserId(userId);
		accNoToken.setStatus(1);
		List<AccNoToken> list = mapper.select(accNoToken);
		for(AccNoToken acc : list){
			acc.setToken(null);
		}
		return list;
	}

}
