package com.jusfoun.hookah.core.dao;

import com.jusfoun.hookah.core.domain.AccNoToken;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccNoTokenMapper extends GenericDao<AccNoToken> {

	@Update("update acc_no_token set token=#{token}, status=1 where order_sn=#{orderSn}")
	void updateTokenAndStatusByOrderSn(AccNoToken accNoToken);

	@Update("update acc_no_token set order_sn=#{orderSn} where user_id=#{userId} and acc_no=#{accNo}")
	void updateOrderSnByUserIdAndAccNo(AccNoToken record);

	@Update("update acc_no_token set token=#{token} where acc_no=#{accNo}")
	void updateTokenByAccNo(AccNoToken accNoToken);

	@Select("select * from acc_no_token where order_sn=#{orderSn}")
	AccNoToken findByOrderSn(String orderSn);

	@Update("update acc_no_token set token=null, status=0 where user_id=#{userId} and acc_no=#{accNo}")
	void updateTokenByUserIdAndAccNo(AccNoToken accNoToken);

}