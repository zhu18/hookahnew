package com.jusfoun.hookah.core.dao;


import com.github.pagehelper.Page;
import com.jusfoun.hookah.core.dao.provider.PayCoreProvider;
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.vo.PayCoreVo;
import com.jusfoun.hookah.core.domain.vo.RechargeVo;
import com.jusfoun.hookah.core.generic.GenericDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface PayCoreMapper extends GenericDao<PayCore> {

	/*@Update("update pay_core set pay_status=#{payStatus},trade_no=#{tradeNo} where order_sn=#{orderSn}")*/
	void updatePayStatusAndTradeNo(PayCore pay);

	/*@Select("select * from pay_core where order_sn = #{orderSn}")*/
	List<PayCore> getPayCoreByOrderSn(String orderSn);
	
	/*@SelectProvider(type=PayCoreProvider.class, method="findByVo")*/
	List<PayCore> findByVo(PayCoreVo payCoreVo);
	
	/*@Select("select sum(amount) from pay_core where  ((pay_mode=1 and income_flag=1) or (pay_mode=2 and income_flag=0)) AND pay_status=2 AND user_id=#{userId} ")
	*/String findTotalConsume(String userId);
	
	/*@Select("select * from pay_core where  pay_mode=2 and income_flag=1 AND pay_status=2 AND user_id=#{userId}  order by pay_date desc")
	*/List<PayCore> select3(PayCore pay);

	/*@Update("update pay_core set have_checked=1 where order_sn=#{orderSn}")
	*/void updateCheckedStatus(PayCore pay);

	/*@SelectProvider(type=PayCoreProvider.class, method="selectRechargeList")*/
	Page<PayCore> selectRechargeList(RechargeVo vo);
}
