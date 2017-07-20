package com.jusfoun.hookah.pay.service.impl;


import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.dao.PayAccountRecordMapper;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.domain.*;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.mongo.MgOrderGoods;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.pay.util.AlipayNotify;
import com.jusfoun.hookah.pay.util.ChannelType;
import com.jusfoun.hookah.pay.util.PayConfiguration;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PayAccountServiceImpl extends GenericServiceImpl<PayAccount, Long> implements
		PayAccountService {

	@Resource
	private PayAccountMapper payAccountMapper;

	@Resource
	public void setDao(PayAccountMapper payAccountMapper) {
		super.setDao(payAccountMapper);
	}

	@Resource
	PayTradeRecordService payTradeRecordService;

	@Resource
	AlipayService alipayService;

	@Resource
	OrderInfoService orderInfoService;

	@Resource
	PayTradeRecordMapper payTradeRecordMapper;

	@Resource
	PayAccountRecordMapper payAccountRecordMapper;

	private String notifyUrl="/payAccount/rechargeResultSync";

	private String returnUrl="/payAccount/rechargeResult";

	@Transactional
	public int operatorByType(Long payAccountId, Integer operatorType, Long money) {

//		入账到可用余额
//		1：在线充值（入金），
//		5：手工充值,
//		7：线下充值，
//		8：提现冲账
//		3001：销售（货款）收入
//		3007：交易交收手续费-收入

//		扣款可用余额
//		2：在线提现（出金），
//		6：手工扣款，
//		4001：销售（货款）支出

//		入账到冻结账户
//		6003：冻结划入-收益账户

//		扣款冻结账户到可用余额
//		6004：释放划出-收益账户

		// 账户操作
		Map<String, Object> map = new HashMap<>();

		// 加钱
		if(operatorType.equals(PayConstants.TradeType.OnlineRecharge.code) ||
				operatorType.equals(PayConstants.TradeType.ManualRecharge.code) ||
				operatorType.equals(PayConstants.TradeType.CashREverse.code) ||
				operatorType.equals(PayConstants.TradeType.SalesIn.code) ||
				operatorType.equals(PayConstants.TradeType.ChargeIn.code) ||
				operatorType.equals(PayConstants.TradeType.OfflineRecharge.code)){
			map.put("type", "plus");
		}else if( // 减钱
				operatorType.equals(PayConstants.TradeType.OnlineCash.code) ||
						operatorType.equals(PayConstants.TradeType.SalesOut.code) ||
						operatorType.equals(PayConstants.TradeType.ManualDebit.code)
				){
			map.put("type", "sub");
		}else if(operatorType.equals(PayConstants.TradeType.FreezaIn.code)){ // 冻结转入
			map.put("type", "FreezaIn");
		}else if(operatorType.equals(PayConstants.TradeType.releaseDraw.code)){ // 释放划出
			map.put("type", "releaseDraw");
		}else if(operatorType.equals(PayConstants.TradeType.SettleCut.code)){
			map.put("type", "SettleCut");
		}
		map.put("id", payAccountId);
		map.put("changeMoney", money);
		int n = payAccountMapper.OperatorByType(map);
		if(n != 1){
			logger.info("用户[payAccountId]->" + payAccountId + "===>操作失败" + LocalDateTime.now());
			throw new RuntimeException();
		}
		return n;
	}

	@Transactional
	public void operatorByType(Long payAccountId, String userId, Integer operatorType, Long money, String orderSn, String operatorId ) {

		// 先添加处理中流水
		PayTradeRecord payTradeRecord = new PayTradeRecord();
		payTradeRecord.setPayAccountId(payAccountId);
		payTradeRecord.setUserId(userId);
		payTradeRecord.setMoney(money);
		payTradeRecord.setTradeType(operatorType);
		payTradeRecord.setTradeStatus(PayConstants.TransferStatus.handing.getCode());
		payTradeRecord.setAddTime(new Date());
		payTradeRecord.setOrderSn(orderSn);
		payTradeRecord.setAddOperator(operatorId);
		payTradeRecord.setTransferDate(new Date());
		int nm = payTradeRecordService.insertAndGetId(payTradeRecord);
		if(nm != 1){
			throw new RuntimeException("添加内部流水失败，操作时间" + LocalDateTime.now());
		}

		// 账户操作
		Map<String, Object> map = new HashMap<>();

		// 加钱
		if(operatorType.equals(PayConstants.TradeType.OnlineRecharge.code) ||
				operatorType.equals(PayConstants.TradeType.ManualRecharge.code) ||
				operatorType.equals(PayConstants.TradeType.CashREverse.code) ||
				operatorType.equals(PayConstants.TradeType.SalesIn.code) ||
				operatorType.equals(PayConstants.TradeType.ChargeIn.code) ||
				operatorType.equals(PayConstants.TradeType.OfflineRecharge.code)){
			map.put("type", "plus");
		}else if( // 减钱
				operatorType.equals(PayConstants.TradeType.OnlineCash.code) ||
						operatorType.equals(PayConstants.TradeType.SalesOut.code) ||
						operatorType.equals(PayConstants.TradeType.ManualDebit.code)
				){
			map.put("type", "sub");
		}else if(operatorType.equals(PayConstants.TradeType.FreezaIn.code)){ // 冻结转入
			map.put("type", "FreezaIn");
		}else if(operatorType.equals(PayConstants.TradeType.releaseDraw.code)){ // 释放划出
			map.put("type", "releaseDraw");
		}else if(operatorType.equals(PayConstants.TradeType.SettleCut.code)){
			map.put("type", "SettleCut");
		}
		map.put("id", payAccountId);
		map.put("changeMoney", money);
		int mn = payAccountMapper.OperatorByType(map);
		if(mn != 1){
			throw new RuntimeException("账户金额操作失败，操作时间" + LocalDateTime.now());
		}

		payTradeRecord.setTradeStatus(PayConstants.TransferStatus.success.code);
		payTradeRecord.setUpdateTime(new Date());
		int m = payTradeRecordService.updateByIdSelective(payTradeRecord);
		if(m != 1){
			throw new RuntimeException("更新流水状态失败，操作时间" + LocalDateTime.now());
		}
	}

	@Transactional
	public int operatorByType(MoneyInOutBo moneyInOutBo, Long id) {

		// 添加内部流水记录
		PayTradeRecord payTradeRecord = payTradeRecordService.initPayTradeRecord(moneyInOutBo, id.toString());
		Integer operatorType = moneyInOutBo.getOperatorType();

		// 账户操作
		Map<String, Object> map = new HashMap<>();

		// 加钱
		if(operatorType.equals(PayConstants.TradeType.OnlineRecharge.code) ||
				operatorType.equals(PayConstants.TradeType.ManualRecharge.code) ||
				operatorType.equals(PayConstants.TradeType.CashREverse.code) ||
				operatorType.equals(PayConstants.TradeType.SalesIn.code) ||
				operatorType.equals(PayConstants.TradeType.ChargeIn.code) ||
				operatorType.equals(PayConstants.TradeType.OfflineRecharge.code)){
			map.put("type", "plus");
		}else if( // 减钱
				operatorType.equals(PayConstants.TradeType.OnlineCash.code) ||
						operatorType.equals(PayConstants.TradeType.SalesOut.code) ||
						operatorType.equals(PayConstants.TradeType.ManualDebit.code)
				){
			map.put("type", "sub");
		}else if(operatorType.equals(PayConstants.TradeType.FreezaIn.code)){ // 冻结转入
			map.put("type", "FreezaIn");
		}else if(operatorType.equals(PayConstants.TradeType.releaseDraw.code)){ // 释放划出
			map.put("type", "releaseDraw");
		}else if(operatorType.equals(PayConstants.TradeType.SettleCut.code)){
			map.put("type", "SettleCut");
		}
		map.put("id", moneyInOutBo.getPayAccountID());
		map.put("changeMoney", moneyInOutBo.getMoney());
		int n = payAccountMapper.OperatorByType(map);
		if(n != 1){
			logger.info("用户[payAccountId]->" + moneyInOutBo.getPayAccountID() + "===>操作失败" + LocalDateTime.now());
			throw new RuntimeException();
		}
		payTradeRecord.setTradeStatus(PayConstants.TransferStatus.success.code);
		payTradeRecord.setUpdateTime(new Date());
		int m = payTradeRecordService.updateByIdSelective(payTradeRecord);
		return m;
	}

	/**
	 *  用户注册时，同时在pay_account表插入记录
	 * @param userId
	 * @param userName
	 */
	public void insertPayAccountByUserIdAndName(String userId, String userName) {

		List<Condition> filters = new ArrayList<>();
		if(StringUtils.isNotBlank(userId)){
			filters.add(Condition.eq("userId", userId));
		}
		PayAccount payAccount = super.selectOne(filters);

		if (payAccount == null) {
			PayAccount pa = new PayAccount();
			pa.setUserId(userId);
			pa.setUserName(userName);
			pa.setBalance(0l);
			pa.setUseBalance(0l);
			pa.setFrozenBalance(0l);
			pa.setPayPassword("00000000");
			pa.setAccountFlag((byte) 1);
			pa.setMerchantId("");
			pa.setSyncFlag((byte) 0);
			pa.setAddTime(new Date());
			pa.setAddOperator("system");
			payAccountMapper.insertAndGetId(pa);
		}
	}

	/**
	 *  重置支付密码
	 * @param id 账户表即pay_account表主键
	 * @param payPassword   支付密码
	 */
	public void resetPayPassword(Long id, String payPassword) {
		Map<String, Object> map = new HashMap<>();
		PayAccount pa = new PayAccount();
		map.put("id", id);
		map.put("payPassword", payPassword);
		payAccountMapper.resetPayPassword(map);
	}

	//验证 支付密码是否正确
	public boolean verifyPassword(String payPassword) throws HookahException {
		String userId = this.getCurrentUser().getUserId();
		List<Condition> filters = new ArrayList();
		if (StringUtils.isNotBlank(userId)) {
			filters.add(Condition.eq("userId", userId));
		}
		PayAccount payAccount = super.selectOne(filters);
		if (StringUtils.isNotBlank(payAccount.getPayPassword())) {
			if (payAccount.getPayPassword().equals(payPassword)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public void payByBalance(OrderInfo orderInfo) throws Exception {
		//插内部消费流水
		//先看流水存不存在
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn",orderInfo.getOrderSn()));
		List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
		if (payTradeRecords==null){
			PayTradeRecord payTradeRecord = new PayTradeRecord();
			payTradeRecord.setUserId(orderInfo.getUserId());
			payTradeRecord.setMoney(orderInfo.getOrderAmount());
			payTradeRecord.setTradeType(PayConstants.TradeType.SalesOut.getCode());
			payTradeRecord.setTradeStatus(PayConstants.TransferStatus.handing.getCode());
			payTradeRecord.setAddTime(new Date());
			payTradeRecord.setOrderSn(orderInfo.getOrderSn());
			payTradeRecord.setAddOperator(orderInfo.getUserId());
			payTradeRecord.setTransferDate(new Date());
			payTradeRecordService.insertAndGetId(payTradeRecord);
		}

		//调余额支付
		doPayByBalance(orderInfo);

	}

	@Override
	@Transactional
	public String payByAli(OrderInfo orderInfo) {
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn",orderInfo.getOrderSn()));
		List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
		if (payTradeRecords==null){
			//插内部消费流水
			PayTradeRecord payTradeRecord = new PayTradeRecord();
			payTradeRecord.setUserId(orderInfo.getUserId());
			payTradeRecord.setMoney(orderInfo.getOrderAmount());
			payTradeRecord.setTradeType(PayConstants.TradeType.SalesOut.getCode());
			payTradeRecord.setTradeStatus(PayConstants.TransferStatus.handing.getCode());
			payTradeRecord.setAddTime(new Date());
			payTradeRecord.setOrderSn(orderInfo.getOrderSn());
			payTradeRecord.setAddOperator(orderInfo.getUserId());
			payTradeRecord.setTransferDate(new Date());
			payTradeRecordService.insertAndGetId(payTradeRecord);
			//插内部充值流水
			PayTradeRecord payTrade = new PayTradeRecord();
			payTrade.setUserId(orderInfo.getUserId());
			payTrade.setMoney(orderInfo.getOrderAmount());
			payTrade.setTradeType(PayConstants.TradeType.OnlineRecharge.getCode());
			payTrade.setTradeStatus(PayConstants.TransferStatus.handing.getCode());
			payTrade.setAddTime(new Date());
			payTrade.setOrderSn(orderInfo.getOrderSn());
			payTrade.setAddOperator(orderInfo.getUserId());
			payTrade.setTransferDate(new Date());
			payTradeRecordService.insertAndGetId(payTrade);
			//插外部充值流水
			PayAccountRecord payAccountRecord = new PayAccountRecord();
			payAccountRecord.setPayAccountId(Long.valueOf(orderInfo.getUserId()));
			payAccountRecord.setUserId(orderInfo.getUserId());
			Date date = new Date();
			payAccountRecord.setTransferDate(date);
			payAccountRecord.setMoney(orderInfo.getOrderAmount());//订单资金总额
			payAccountRecord.setSerialNumber(orderInfo.getOrderSn());//订单号
			payAccountRecord.setTransferType(HookahConstants.TransferStatus.handing.getCode());
			payAccountRecord.setAddTime(date);
			payAccountRecord.setAddOperator(orderInfo.getUserId());
			payAccountRecordMapper.insertAndGetId(payAccountRecord);
		}
		//调支付宝接口
		String html = alipayService.doPay(orderInfo.getUserId(), orderInfo.getOrderId(),
				PayConfiguration.ALIPAY_NOTIFY_URL, PayConfiguration.ALIPAY_RETURN_URL);
		return html;
	}

	public void doPayByBalance(OrderInfo orderinfo) throws Exception {
		Long orderAmount = orderinfo.getOrderAmount();
		//插交易中心冻结收入流水，
		PayTradeRecord payTradeRecord = new PayTradeRecord();
		payTradeRecord.setPayAccountId(HookahConstants.TRADECENTERACCOUNT);
		payTradeRecord.setMoney(orderAmount);
		payTradeRecord.setTradeType(HookahConstants.TradeType.FreezaIn.getCode());
		payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.handing.getCode());
		payTradeRecord.setAddTime(new Date());
		payTradeRecord.setOrderSn(orderinfo.getOrderSn());
		payTradeRecord.setAddOperator(orderinfo.getUserId());
		payTradeRecord.setTransferDate(new Date());
		payTradeRecordService.insertAndGetId(payTradeRecord);
		//用户减去余额，交易中心收入冻结金额
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("userId", orderinfo.getUserId()));
		PayAccount payAccount = super.selectOne(filter);
		operatorByType(payAccount.getId(), HookahConstants.TradeType.SalesOut.getCode(), orderAmount);
		int n = operatorByType(HookahConstants.TRADECENTERACCOUNT,HookahConstants.TradeType.FreezaIn.getCode(),
				orderAmount);

		List<Condition> filters = new ArrayList<>();
		filters.add(Condition.eq("orderSn",orderinfo.getOrderSn()));
		List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filters);
		for(PayTradeRecord payTradeRecord1:payTradeRecords){
			payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.success.getCode());
			payTradeRecordService.updateByIdSelective(payTradeRecord1);
		}

		// 支付成功 查询订单 获取订单中商品插入到待清算记录
		//修改订单支付状态
		orderInfoService.updatePayStatus(orderinfo.getOrderSn(), OrderInfo.PAYSTATUS_PAYED,0);
		orderInfoService.waitSettleRecordInsert(orderinfo.getOrderSn());
	}


	public void alipayRtn(HttpServletRequest request) throws Exception {
		//商户订单号
		String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn", orderSn));
		OrderInfo orderInfo = orderInfoService.selectOne(filter);
		if (AlipayNotify.verify(getRequestParams(request))) {
			if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
				//交易成功,插交易中心冻结收入流水，更新交易中心虚拟账户金额
				PayTradeRecord payTradeRecord = new PayTradeRecord();
				payTradeRecord.setUserId(orderInfo.getUserId());//交易中心Id
				payTradeRecord.setMoney(orderInfo.getOrderAmount());
				payTradeRecord.setTradeType(HookahConstants.TradeType.FreezaIn.getCode());
				payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.handing.getCode());
				payTradeRecord.setAddTime(new Date());
				payTradeRecord.setOrderSn(orderSn);
				payTradeRecord.setAddOperator(orderInfo.getUserId());
				payTradeRecord.setTransferDate(new Date());
				payTradeRecordService.insertAndGetId(payTradeRecord);


				//修改内部流水的状态和外部充值状态
				List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
				for (PayTradeRecord payTradeRecord1 : payTradeRecords) {
					payTradeRecord1.setTradeStatus((byte) 1);
				}

				//更新订单状态
//				orderInfoService.updatePayStatus(orderSn, 2);
			}
		}
	}

	public String alipayNtf(HttpServletRequest request) throws Exception {
		//商户订单号
		String orderSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn",orderSn));
		OrderInfo orderInfo = orderInfoService.selectOne(filter);
		if(AlipayNotify.verify(getRequestParams(request))){
			if(tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
				//交易成功,插交易中心冻结收入流水，更新交易中心虚拟账户金额
				PayTradeRecord payTradeRecord = new PayTradeRecord();
				payTradeRecord.setUserId(orderInfo.getUserId());//交易中心Id
				payTradeRecord.setMoney(orderInfo.getOrderAmount());
				payTradeRecord.setTradeType(6003);
				payTradeRecord.setTradeStatus((byte)0);
				payTradeRecord.setAddTime(new Date());
				payTradeRecord.setOrderSn(orderSn);
				payTradeRecord.setAddOperator(orderInfo.getUserId());
				payTradeRecord.setTransferDate(new Date());
				payTradeRecordService.insertAndGetId(payTradeRecord);
				//修改内部流水的状态和外部充值状态
				List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
				for (PayTradeRecord payTradeRecord1 : payTradeRecords){
					payTradeRecord1.setTradeStatus((byte)1);
				}
				//更新订单状态
//				orderInfoService.updatePayStatus(orderSn,2);
			}else {
				List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
				for (PayTradeRecord payTradeRecord1 : payTradeRecords){
					payTradeRecord1.setTradeStatus((byte)2);
				}
			}
//            payCoreService.updatePayCore(paied);
			return "success";
		}else {
			return "fail";
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<String,String> getRequestParams(HttpServletRequest request){
		//处理通知参数
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}

	public ReturnData userRecharge(Map<String,Object> params){
		ReturnData returnData = new ReturnData();

		String userId = "";
		if(Objects.isNull(params.get("userId"))){
			returnData.setCode(ExceptionConst.Failed);
			returnData.setMessage("充值失败：用户ID不能为空！");
			return returnData;
		}
		userId=(String) params.get("userId");
		Object moneyObj=params.get("money");
		if(Objects.isNull(moneyObj)){
			returnData.setCode(ExceptionConst.Failed);
			returnData.setMessage("充值失败：充值金额不能为空！");
			return returnData;
		}

		try {
			if((double)moneyObj <= 0) {
				returnData.setCode(ExceptionConst.Failed);
				returnData.setMessage("充值失败：充值金额必须大于0！");
				return returnData;
			}
			List<Condition> filters = new ArrayList();
			if(StringUtils.isNotBlank(userId)){
				filters.add(Condition.eq("userId", userId));
			}
			PayAccount payAccount = super.selectOne(filters);
			//将金额*100转为Long型
			Long money = Math.round((double)moneyObj*100);
			if (payAccount == null){
				returnData.setCode(ExceptionConst.Failed);
				returnData.setMessage("充值失败：账户不存在！");
				return returnData;
			}
			//insertPayTradeRecord( userId, money, payAccount.getId(), 0, 1);
			//insertPayAccountRecord( userId, money, payAccount.getId(), 0, 1);

			String html = alipayService.doCharge(userId,money.toString(),notifyUrl,returnUrl);
			returnData.setCode(ExceptionConst.Success);
			returnData.setMessage(html);
			return returnData;
		}catch (Exception e){
			returnData.setCode(ExceptionConst.Error);
			returnData.setMessage(e.toString());
			e.printStackTrace();
		}

		return returnData;
	}

	public void updatePayAccountMoney(Long payAccountId,Long money){
		Map<String,Long> params=new HashMap<String,Long>();
		params.put("payAccountId",payAccountId);
		params.put("money",money);
		payAccountMapper.updatePayAccountMoney(params);
	}

	public void insertPayTradeRecord(String userId,Long money,Long payAccountId,int tradeStatus,int tradeType){
		PayTradeRecord ptr=new PayTradeRecord();
		ptr.setMoney(Math.abs(money));
		ptr.setPayAccountId(payAccountId);
		ptr.setUserId(userId);
		ptr.setTradeStatus((byte)tradeStatus);
		ptr.setTradeType(tradeType);
		ptr.setAddOperator(userId);
		ptr.setAddTime(new Date());
		ptr.setUpdateOperator(userId);
		ptr.setUpdateTime(new Date());
		payTradeRecordMapper.insert(ptr);
	}

	public void insertPayAccountRecord(String userId,Long money,Long payAccountId,int tradeStatus,int tradeType){
		PayAccountRecord par = new PayAccountRecord();
		par.setChannelType(ChannelType.ZFB);
		par.setMoney(Math.abs(money));
		par.setPayAccountId(payAccountId);
		par.setTransferStatus((byte)tradeStatus);
		par.setTransferType((byte)tradeType);
		par.setTransferDate(new Date());
		par.setAddOperator(userId);
		par.setAddTime(new Date());
		par.setUpdateOperator(userId);
		par.setUpdateTime(new Date());
		par.setUserId(userId);
		payAccountRecordMapper.insert(par);
	}

	/**
	 * 更新账户表并插入记录表
	 * @param params
	 */
	public ReturnData saveRechargeResult(Map<String,String> params){
		ReturnData returnData = new ReturnData();
		String tradeStatus=params.get("tradeStatus");
		String totalFee=params.get("totalFee");
		String userId=params.get("userId");
		String tradeType=params.get("tradeType");
		Long money=0l;
		try{
			List<Condition> filters = new ArrayList();
			if(StringUtils.isNotBlank(userId)){
				filters.add(Condition.eq("userId", userId));
			}
			//查询出payAccountId
			PayAccount payAccount = super.selectOne(filters);
			if(tradeStatus.equals("1")){
				money = Math.round(Double.parseDouble(totalFee)*100);
				//更新账户金额
				updatePayAccountMoney(payAccount.getId(),money);
			}
			//插入记录表
			insertPayTradeRecord( userId, money, payAccount.getId(), new Byte(tradeStatus), new Byte(tradeType));
			insertPayAccountRecord( userId, money, payAccount.getId(), new Byte(tradeStatus), new Byte(tradeType));
			//payTradeRecordMapper.updateByPrimaryKeySelective();
			returnData.setCode(ExceptionConst.Success);
			returnData.setMessage("操作成功！");
			return returnData;
		}catch (Exception e){
			returnData.setCode(ExceptionConst.Error);
			returnData.setMessage(e.toString());
			e.printStackTrace();
		}
		return returnData;
	};

}
