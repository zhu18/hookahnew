package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;

import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.dao.PaySignMapper;
import com.jusfoun.hookah.core.domain.PaySign;
import com.jusfoun.hookah.core.domain.mongo.MgPaySign;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.DateUtil;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.rpc.api.MgPaySignService;
import com.jusfoun.hookah.rpc.api.PaySignService;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lt on 2017/7/5.
 */
@Service
public class PaySignServiceImpl extends GenericServiceImpl<PaySign, String> implements PaySignService {

	@Resource
	FixClientUtil fixClientUtil;

	private IFixClient fixClient = fixClientUtil.createClientSSL();

	@Resource
	MgPaySignService mgPaySignService;

	@Resource
	private PaySignMapper mapper;

	@Resource
	public void setDao(PaySignMapper mapper) {
		super.setDao(mapper);
	}

	public void sign(String signFlag){
		PaySign paySign = new PaySign();
		try {
			String taskDate = DateUtil.dateCurrentForYMD();
			Map<String, String> paramMap = new HashMap<String,String>();
			paramMap.put("FID_YWRQ",taskDate);//业务日期
			paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易所代码

			paySign.setAddTime(new Date());
			paySign.setSignFlag(signFlag);
			paySign.setTradeMarket(PayConstants.FID_JYS);
			paySign.setTaskDate(new Date());
			paySign.setUpdateTime(new Date());

			ResultBean<Map<String, String>> resultBean = null;
			if (signFlag.equals(PayConstants.Sign.SIGN_IN.getCode())) {
				resultBean = this.fixClient.sendMarketLogin(paramMap);
			}else {
				resultBean = this.fixClient.sendMarketLogout(paramMap);
			}
			if (resultBean.isSuccess()){
				paySign.setResultCode(resultBean.getObject().get("FID_CODE"));
				paySign.setResultMsg(resultBean.getObject().get("FID_MESSAGE"));
			}else {
				paySign.setResultCode(resultBean.getCode());
				paySign.setResultMsg(resultBean.getMsg());
			}
			mapper.insertAndGetId(paySign);
			MgPaySign mgPaySign = new MgPaySign();
			BeanUtils.copyProperties(paySign,mgPaySign);
			mgPaySignService.insert(mgPaySign);
		}catch (Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
//	@Scheduled(cron = "0 55 19 * * ?")
	public void sendMarketLogin(){
		sign(PayConstants.Sign.SIGN_IN.getCode());
	}

	@Transactional
	@Override
//	@Scheduled(cron = "0 28 17 * * ?")//每天七点55执行定时任务
	public void sendMarketLogout(){
		sign(PayConstants.Sign.SIGN_OUT.getCode());
	}

}