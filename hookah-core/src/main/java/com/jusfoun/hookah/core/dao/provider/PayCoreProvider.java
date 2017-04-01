/**
 * All rights Reserved, Designed By ZTE-ITS
 * Copyright:    Copyright(C) 2016-2020
 * Company       JUSFOUN GuoZhiFeng LTD.
 *
 * @author: 郭志峰
 * @version V1.0
 * Createdate:         2016年6月22日17:26:14
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2016年6月22日17:26:14       guozhifeng         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
package com.jusfoun.hookah.core.dao.provider;


import com.jusfoun.hookah.core.domain.vo.PayCoreVo;
import com.jusfoun.hookah.core.domain.vo.RechargeVo;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @ClassName: GoodsProvider
 * @Description:
 * @author Timothy guozhifengvip@163.com
 * @date 2016年6月15日 下午4:19:11
 *
 */
public class PayCoreProvider {
	final String base_colume = "id, user_id, order_id, order_sn, pay_mode, trade_no, pay_status, pay_date, fee, amount, income_flag";
	final String base_from = " from pay_core";

	public String findByVo(PayCoreVo vo){
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(base_colume).append(base_from);
		sb.append(" where have_checked=0");
		if(StringUtils.isNotEmpty(vo.getPayMode()))
			sb.append(" and pay_mode=#{payMode}");
		if(StringUtils.isNotEmpty(vo.getStartTime()))
			sb.append(" and pay_date>=#{startTime}");
		if(StringUtils.isNotEmpty(vo.getEndTime()))
			sb.append(" and pay_date<#{endTime}");
		if (vo.getPayStatus() != null)
			sb.append(" and pay_status=#{payStatus}");
		return sb.toString();
	}
	public String selectRechargeList(RechargeVo vo ) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select ").append(base_colume).append(base_from);
		sb.append(" where pay_mode=2 and income_flag=1");
		if(StringUtils.isNotEmpty(vo.getStartMoney()))
			sb.append(" and amount>=#{startMoney}");
		if(StringUtils.isNotEmpty(vo.getEndMoney()))
			sb.append(" and amount<=#{endMoney}");
		if(StringUtils.isNotEmpty(vo.getStartTime()))
			sb.append(" and pay_date>=#{startTime}");
		if(StringUtils.isNotEmpty(vo.getEndTime()))
			sb.append(" and pay_date<=#{endTime}");
		if(StringUtils.isNotEmpty(vo.getUser()))
			sb.append(" and user_id=#{user}");
		sb.append(" order by pay_date desc");
		return sb.toString();
	}
}
