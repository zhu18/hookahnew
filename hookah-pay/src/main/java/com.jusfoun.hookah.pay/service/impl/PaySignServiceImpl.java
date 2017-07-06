package com.jusfoun.hookah.pay.service.impl;

import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.jusfoun.hookah.core.dao.PaySignMapper;
import com.jusfoun.hookah.core.domain.PaySign;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.PaySignService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lt on 2017/7/5.
 */
@Service
public class PaySignServiceImpl extends GenericServiceImpl<PaySign, String> implements PaySignService {

	/*@Resource
	private FixClientUtil client;*/

	private IFixClient fixClient =new FixClientUtil().createClient();

	@Resource
	private PaySignMapper mapper;

	@Resource
	public void setDao(PaySignMapper mapper) {
		super.setDao(mapper);
	}

	@Transactional
	@Override
//	@Scheduled(cron = "0 05 8 * * ?")//每天08:05执行定时任务
	public void sendMarketLogin(PaySign paySign){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String yerq = simpleDateFormat.format(new Date());
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("FID_YWRQ",yerq);//业务日期
		paramMap.put("FID_JYS",PayConstants.FID_JYS);//交易所代码

		paySign.setAddTime(new Date());
		paySign.setSignFlag(paySign.SIGN_IN);
		paySign.setTradeMarket(PayConstants.FID_JYS);
		paySign.setTaskDate(new Date());
		paySign.setUpdateTime(new Date());

		try {
			ResultBean<Map<String, String>> resultBean = this.fixClient.sendMarketLogin(paramMap);
			paySign.setResultCode(resultBean.getCode());
			paySign.setResultMsg(resultBean.getMsg());
			mapper.insert(paySign);
		}catch (Exception e){
			logger.info(e.getMessage());
		}
//		Asser.assertTrue(resultBean.isSuccess());
	}

	@Override
//	@Scheduled(cron = "0 55 19 * * ?")//每天七点55执行定时任务
	public void sendMarketLogout(PaySign paySign){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String yerq = formatter.format(new Date());
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("FID_YWRQ",yerq);//业务日期
		paramMap.put("FID_JYS", PayConstants.FID_JYS);//交易所代码

		paySign.setAddTime(new Date());
		paySign.setSignFlag(paySign.SIGN_OUT);
		paySign.setTradeMarket(PayConstants.FID_JYS);
		paySign.setTaskDate(new Date());
		paySign.setUpdateTime(new Date());

		try {
			ResultBean<Map<String, String>> resultBean = this.fixClient.sendMarketLogout(paramMap);
			paySign.setResultCode(resultBean.getCode());
			paySign.setResultMsg(resultBean.getMsg());
			mapper.insert(paySign);
		}catch (Exception e){
			logger.info(e.getMessage());
		}

	}

	/**
	 * 上传、发送 清算文件前的校验 719021
	 * FID_WJLX 参数 先做11 or 22 的检查 判断文件是否允许上传
	 * 文件上传成功后在做1 or 2 的确认
	 */
	public void sendFundFileCheck(){
		/**
		 * FID_YWRQ 业务日期
		 * FID_JYS 交易市场
		 * FID_WJLX 文件类型 （1资金日终    11撤销（检查）资金日终    2交易日终  22撤销（检查）交易日终）
		 *              先做11 or 22 的检查，处理成功后在做1 or 2 的确认
		 */
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("FID_YWRQ","");

		ResultBean<Map<String, String>> resultBean = this.fixClient.sendFundFileCheck(paramMap);
	}
}