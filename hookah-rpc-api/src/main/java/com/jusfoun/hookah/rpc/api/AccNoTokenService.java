package com.jusfoun.hookah.rpc.api;


import com.jusfoun.hookah.core.domain.AccNoToken;
import com.jusfoun.hookah.core.generic.GenericService;

import java.util.List;

public interface AccNoTokenService extends GenericService<AccNoToken,String> {

	void updateTokenByOrderSn(String orderSn, String token);

	List<AccNoToken> findAccNoList(String userId);

}
