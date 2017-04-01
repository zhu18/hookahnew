package com.jusfoun.hookah.console.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.util.StringUtil;
import com.jusfoun.hookah.console.server.pay.alipay.AlipayBuilder;
import com.jusfoun.hookah.console.server.pay.alipay.AlipayNotify;
import com.jusfoun.hookah.console.server.pay.unionpay.UnionpayBuilder;
import com.jusfoun.hookah.console.server.pay.unionpay.UnionpayConfig;
import com.jusfoun.hookah.console.server.pay.unionpay.sdk.AcpService;
import com.jusfoun.hookah.console.server.pay.wallet.WalletBuilder;
import com.jusfoun.hookah.core.common.Pagination;
import com.jusfoun.hookah.core.dao.AccNoTokenMapper;
import com.jusfoun.hookah.core.dao.PayCoreMapper;
import com.jusfoun.hookah.core.domain.AccNoToken;
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.vo.PayCoreVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.domain.vo.RechargeVo;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.PayCoreService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

@Service
public class PayCoreServiceImpl extends GenericServiceImpl<PayCore, String> implements PayCoreService {

	//@Value("${walletId}")
	private String walletId;
	//@Value("${rechargeUrl}")
	private String rechargeUrl;
//	@Value("${alipayFeeRate}")
	private String alipayFeeRate;
	//@Value("${getBalanceUrl}")
	private String getBalanceUrl;
	//@Value("${findUserIdByPhoneOrEmail}")
	private String findUserIdByPhoneOrEmail;
//	@Value("${usrDetailUrl}")
	private String usrDetailUrl;
	@Autowired
	private PayCoreMapper mapper;
	
	@Autowired
	private OrderInfoService orderService;
	@Autowired
	private AccNoTokenMapper accNoTokenMapper;
	
	


	@SuppressWarnings("rawtypes")
	public List select2(PayCore t){
		return mapper.select3(t);
	}
	@Override
	public PayCore findPayCoreByOrderId(Integer orderId) {
		List<PayCore> pays = mapper.getPayCoreByOrderId(orderId);
		return (pays==null || pays.size()==0) ? null : pays.get(0);
	}

	@Override
	public String buildRequestParams(PayVo payVo) {
		if(payVo.getPayId().intValue()==1){
			return AlipayBuilder.build(payVo);
		}else if(payVo.getPayId().intValue()==Integer.parseInt(walletId)){
			return WalletBuilder.build(payVo);
		}else if(payVo.getPayId().intValue()==3){
			return UnionpayBuilder.build(payVo);
		}
		return null;
	}

	@Transactional(readOnly=false)
	@Override
	public void updatePayCore(PayCore pay) throws Exception {
		//更新订单状态
		orderService.updatePayStatus(pay.getOrderSn(), pay.getPayStatus());
		//更新记账状态、交易号
		mapper.updatePayStatusAndTradeNo(pay);
	}

	@Override
	public PayCore findPayCoreByOrderSn(String orderSn) {
		List<PayCore> pays = mapper.getPayCoreByOrderSn(orderSn);
		return (pays == null || pays.size()==0) ? null : pays.get(0);
	}

	@Transactional(readOnly=false)
	@Override
	public String doPay(Integer orderId, String userId) throws Exception {
		// 检查
		PayCore paied = findPayCoreByOrderId(orderId);
		if (paied != null 
				&& 
				PayCore.PayStatus.success.getValue() == paied.getPayStatus().intValue())
			// (PayStatus.success.getValue() == paied.getPayStatus().intValue()
			// || PayStatus.paying.getValue() ==
			// paied.getPayStatus().intValue()))
			return null;

		//暂时屏蔽的一行
		/*PayVo payVo = orderService.getPayParam(orderId);*/
		PayVo payVo = new PayVo();

		if (null == payVo || payVo.getPayId().intValue() == 0)
			throw new RuntimeException("订单 [id : " + orderId + "] 信息有误");
		if (payVo.getTotalFee().doubleValue() < 0)
			throw new RuntimeException("订单金额不合法");
		payVo.setUserId(userId);
		String html = buildRequestParams(payVo);
		// 记账
		if (StringUtils.isNotEmpty(html))
			enterAccount(orderId, payVo);
		return html;
	}

	private void enterAccount(Integer orderId, PayVo payVo) throws Exception {
		PayCore pay = new PayCore();
		pay.setOrderId(orderId);
		pay.setOrderSn(payVo.getOrderSn());
		pay.setUserId(payVo.getUserId());
		pay.setAmount(payVo.getTotalFee());//金额
		pay.setPayDate(new Date());
//		pay.setPayStatus(PayStatus.paying.getValue());
		pay.setPayStatus(PayCore.PayStatus.unpay.getValue());
		pay.setPayMode(payVo.getPayId().toString());
		if(payVo.getPayId().intValue()==Integer.parseInt(walletId))
			pay.setIncomeFlag(PayCore.OUTCOME);//钱包支付，记作出账
		else
			pay.setIncomeFlag(PayCore.INCOME);//第三方支付，记作进账
		BigDecimal fee = sumFee(payVo.getTotalFee());
		if(payVo.getPayId().intValue()==1 && fee!=null){//是支付宝而且手续费不为空
			PayCore payFee = new PayCore();
			payFee.setOrderId(orderId);
			payFee.setOrderSn(payVo.getOrderSn());
			payFee.setUserId(payVo.getUserId());
			payFee.setAmount(fee);//金额
			payFee.setPayDate(new Date());
//			payFee.setPayStatus(PayStatus.paying.getValue());
			payFee.setPayStatus(PayCore.PayStatus.unpay.getValue());
			payFee.setPayMode(payVo.getPayId().toString());
			payFee.setIncomeFlag(PayCore.OUTCOME);
			mapper.insert(payFee);
			pay.setFee(fee);
		}
		mapper.insert(pay);
		//更新订单状态：支付中
//		orderService.updatePayStatus(pay.getOrderSn(), PayStatus.paying.getValue());		
	}

	@Override
	public Pagination<PayCore> findPageByVo(PayCoreVo payCoreVo, int pageNo, int pageSize) {
		Page<PayCore> page = PageHelper.startPage(pageNo, pageSize, getOrderBy(null));
        List<PayCore> payCoreList= mapper.findByVo(payCoreVo);
        Pagination<PayCore> p = new Pagination<PayCore>();
        p.setTotalItems(page.getTotal());
        p.setList(payCoreList);
        return p;		
	}

	@Override
	public List<PayCore> findListByVo(PayCoreVo payCoreVo) {
		return mapper.findByVo(payCoreVo);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	@Override
	public void recharge(String account, String money) {
		HttpClientUtil client = new HttpClientUtil();
		Map<String, String> result = client.GetMethod(rechargeUrl+"?user="+account+"&money="+money);
		String resultCode = result.get("resultCode");
		if(!"200".equals(resultCode))
			throw new RuntimeException("充值失败");
		Map<String,String> map = (Map<String,String>) JSONObject.parse(result.get("result"));
		//记账
		PayCore pay = new PayCore();
		pay.setOrderId(null);
		pay.setOrderSn(null);
		pay.setUserId(map.get("data"));
		pay.setAmount(new BigDecimal(money));//金额
		pay.setPayDate(new Date());
		pay.setPayStatus(PayCore.PayStatus.success.getValue());
		pay.setPayMode(walletId);
		pay.setIncomeFlag(PayCore.INCOME);//钱包充值，记作进账
		mapper.insert(pay);
	}
	private BigDecimal sumFee(BigDecimal price){
		BigDecimal fee = price.multiply(new BigDecimal(alipayFeeRate)).setScale(2, RoundingMode.HALF_UP);
		if(fee.doubleValue()>0){
			return fee;
		}else{
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> findUsrAccount(String userId) {
		Map<String, String> returnMap = new HashMap<String, String>();
		//查询用户钱包余额
		HttpClientUtil client = new HttpClientUtil();
		Map<String, String> result = null;
		for(int t=0;t<5;t++){
			try{
				result = client.GetMethod(getBalanceUrl+"?userid="+userId);
				String resultCode = result.get("resultCode");
				if(!"200".equals(resultCode))
					throw new RuntimeException("获取用户账户信息失败");
				else
					break;
			}catch(Exception e){
				continue;
			}
		}
		Gson gson = new Gson();
		Map data = gson.fromJson(result.get("result"), Map.class);
		String b = (String) data.get("data");
		returnMap.put("balance", StringUtils.isEmpty(b) ? "0" : b);
		//查询消费总额
		String tc = mapper.findTotalConsume(userId);
		returnMap.put("totalConcume", StringUtils.isEmpty(tc) ? "0" : tc);
		return returnMap;
	}

	@Override
	public void updateCheckStatus(PayCore pay) {
		mapper.updateCheckedStatus(pay);
	}

	private Page<PayCore> selectRechargeList(RechargeVo t) {
		return mapper.selectRechargeList(t);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Pagination<PayCore> findRechargeList(RechargeVo vo, Integer pageNum,
												Integer pageSize) {
		if(StringUtil.isNotEmpty(vo.getUser())){
			HttpClientUtil httpclient = new HttpClientUtil();
			Map<String, String> result = httpclient.GetMethod(findUserIdByPhoneOrEmail+vo.getUser());
			if("null".equals(result.get("result")))
				return new Pagination<PayCore>();
			Gson gson = new Gson();
			Map user = gson.fromJson(result.get("result"), Map.class);
			vo.setUser(String.valueOf(user.get("id")));
		}
		PageHelper.startPage(pageNum, pageSize, getOrderBy(null));
        Page<PayCore> page= (Page<PayCore>)selectRechargeList(vo);
        Pagination<PayCore> p = new Pagination<PayCore>();
        p.setTotalItems(page.getTotal());
        //设置用户名
        HttpClientUtil httpclient2 = new HttpClientUtil();
        for(PayCore pc : page){
    		Map<String, String> result = httpclient2.GetMethod(usrDetailUrl + "?userid=" + pc.getUserId());
    		Gson gson = new Gson();
    		Map map = gson.fromJson(result.get("result"), Map.class);
    		Map u= (Map) map.get("data");
    		pc.setUserId((String) u.get("username"));
        }
        p.setList(page);
        return p;
	}

	@Override
	public boolean verifyAlipay(Map<String, String> request) {
		return AlipayNotify.verify(request);
	}

	@Transactional(readOnly=false)
	@Override
	public String openUnionpay(Integer orderId, String accNo, String userId) throws Exception {
		//根据orderId查询order信息
		//暂时屏蔽的一行
		/*PayVo payVo = orderService.getPayParam(orderId);*/
		PayVo payVo = new PayVo();

		if (null == payVo || payVo.getPayId().intValue() == 0)
			throw new RuntimeException("订单 [id : " + orderId + "] 信息有误");
		if (payVo.getTotalFee().doubleValue() < 0)
			throw new RuntimeException("订单金额不合法");
		//保存用户、订单号和卡号
		AccNoToken record = new AccNoToken(userId, accNo);
		if(CollectionUtils.isEmpty(accNoTokenMapper.select(record))){
			record.setOrderSn(payVo.getOrderSn());
			accNoTokenMapper.insert(record);
		}
		else{
			record.setOrderSn(payVo.getOrderSn());
			accNoTokenMapper.updateOrderSnByUserIdAndAccNo(record);
		}
		//组装请求html
		String html = UnionpayBuilder.buildOpenCard(payVo,accNo);
		// 记账
		if (StringUtils.isNotEmpty(html)){
			payVo.setUserId(userId);
			enterAccount(orderId, payVo);
		}
		return html;
	}

	@Override
	public boolean verifyUnionpay(Map<String, String> requestParams) {
		return AcpService.validate(requestParams, UnionpayConfig.encoding);
	}

	@Override
	public String getTokenByOrderSn(String orderSn) {
		return UnionpayBuilder.queryTokenByOrderSn(orderSn);
	}

	@Override
	public String unionpaySendSMS(String orderSn, String accNo, BigDecimal amount, String userId) {
		AccNoToken accNoToken = new AccNoToken(userId, accNo);
		accNoToken.setStatus(1);
		AccNoToken one = accNoTokenMapper.selectOne(accNoToken);
		//支付订单
		one.setOrderSn(orderSn);
		return UnionpayBuilder.sendSMS(one, amount);
	}

	@Transactional(readOnly=false)
	@Override
	public String unionpayConsume(PayVo payVo, Integer orderId, String userId, String accNo, String smsCode) throws Exception {
		// 记账
		payVo.setUserId(userId);
		enterAccount(orderId, payVo);
		//发送消费请求
		AccNoToken accNoToken = new AccNoToken(userId, accNo);
		accNoToken.setStatus(1);
		AccNoToken one = accNoTokenMapper.selectOne(accNoToken);
		String res = UnionpayBuilder.consume(payVo, one.getToken(), smsCode);
		if("ok".equals(res)){
			//更新支付状态
			PayCore paied = findPayCoreByOrderSn(payVo.getOrderSn());
			paied.setPayStatus(PayCore.PayStatus.success.getValue());
			updatePayCore(paied);
		}
		return res;
	}

	@Transactional(readOnly=false)
	@Override
	public String deleteAccno(String orderSn, String accNo, String userId) {
		AccNoToken accNoToken = new AccNoToken(userId, accNo);
		accNoToken.setStatus(1);
		AccNoToken selectOne = accNoTokenMapper.selectOne(accNoToken);
		String status = UnionpayBuilder.deleteAccno(orderSn, selectOne.getToken());
		if("ok".equals(status)){
			accNoTokenMapper.updateTokenByUserIdAndAccNo(accNoToken);
			return "ok";
		}else{
			return "fail";
		}
	}
}