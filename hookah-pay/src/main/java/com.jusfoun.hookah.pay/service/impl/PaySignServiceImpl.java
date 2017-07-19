package com.jusfoun.hookah.pay.service.impl;

//import com.apex.etm.qss.client.IFixClient;
//import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.jusfoun.hookah.core.dao.PaySignMapper;
import com.jusfoun.hookah.core.domain.PaySign;
import com.jusfoun.hookah.core.domain.mongo.MgPaySign;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.FileUtils;
import com.jusfoun.hookah.pay.util.DateUtil;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.MgPaySignService;
import com.jusfoun.hookah.rpc.api.PaySignService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lt on 2017/7/5.
 */
@Service
public class PaySignServiceImpl extends GenericServiceImpl<PaySign, String> implements PaySignService {

	@Resource
	FixClientUtil fixClientUtil;

//	private IFixClient fixClient = fixClientUtil.createClientSSL();

	@Resource
	MgPaySignService mgPaySignService;

	@Resource
	private PaySignMapper mapper;

	@Resource
	public void setDao(PaySignMapper mapper) {
		super.setDao(mapper);
	}

	@Transactional
	@Override
//	@Scheduled(cron = "0 50 17 * * ?")//每天08:05执行定时任务
	public void sendMarketLogin(){
//		PaySign paySign = new PaySign();
//		String taskDate = DateUtil.getCurrentDate("YYYYMMDD");
//		Map<String, String> paramMap = new HashMap<String,String>();
//		paramMap.put("FID_YWRQ",taskDate);//业务日期
//		paramMap.put("FID_JYS",PayConstants.FID_JYS);//交易所代码
//
//		paySign.setAddTime(new Date());
//		paySign.setSignFlag(PayConstants.Sign.SIGN_IN.getCode());
//		paySign.setTradeMarket(PayConstants.FID_JYS);
//		paySign.setTaskDate(new Date());
//		paySign.setUpdateTime(new Date());
//
//		try {
//			ResultBean<Map<String, String>> resultBean = this.fixClient.sendMarketLogin(paramMap);
//			paySign.setResultCode(resultBean.getCode());
//			paySign.setResultMsg(resultBean.getMsg());
//			mapper.insertAndGetId(paySign);
//			MgPaySign mgPaySign = new MgPaySign();
//			BeanUtils.copyProperties(paySign,mgPaySign);
//			mgPaySignService.insert(mgPaySign);
//		}catch (Exception e){
//			logger.info(e.getMessage());
//			e.printStackTrace();
//		}
//		Asser.assertTrue(resultBean.isSuccess());
	}

	@Transactional
	@Override
//	@Scheduled(cron = "0 55 19 * * ?")//每天七点55执行定时任务
	public void sendMarketLogout(){
//		PaySign paySign = new PaySign();
//		String taskDate = DateUtil.getCurrentDate("YYYYMMDD");
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("FID_YWRQ",taskDate);//业务日期
//		paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易所代码
//
//		paySign.setAddTime(new Date());
//		paySign.setSignFlag(PayConstants.Sign.SIGN_OUT.getCode());
//		paySign.setTradeMarket(PayConstants.FID_JYS);
//		paySign.setTaskDate(new Date());
//		paySign.setUpdateTime(new Date());
//
//		try {
//			ResultBean<Map<String, String>> resultBean = this.fixClient.sendMarketLogout(paramMap);
//			paySign.setResultCode(resultBean.getCode());
//			paySign.setResultMsg(resultBean.getMsg());
//			mapper.insert(paySign);
//		}catch (Exception e){
//			logger.info(e.getMessage());
//		}

	}

}