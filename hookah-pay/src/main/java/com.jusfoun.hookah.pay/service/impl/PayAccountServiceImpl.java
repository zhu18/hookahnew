package com.jusfoun.hookah.pay.service.impl;


import com.jusfoun.hookah.core.constants.HookahConstants;
import com.jusfoun.hookah.core.constants.PayConstants;
import com.jusfoun.hookah.core.constants.RabbitmqQueue;
import com.jusfoun.hookah.core.dao.PayAccountMapper;
import com.jusfoun.hookah.core.dao.PayAccountRecordMapper;
import com.jusfoun.hookah.core.dao.PayTradeRecordMapper;
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.ExceptionConst;
import com.jusfoun.hookah.core.utils.ReturnData;
import com.jusfoun.hookah.core.utils.StrUtil;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.pay.util.AlipayNotify;
import com.jusfoun.hookah.pay.util.ChannelType;
import com.jusfoun.hookah.pay.util.PayConfiguration;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

	@Resource
	PayAccountRecordService payAccountRecordService;

	@Resource
	MqSenderService mqSenderService;

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
	public boolean insertPayAccountByUserIdAndName(String userId, String userName) {

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
			pa.setAccountFlag((byte) 1);
			pa.setMerchantId("");
			pa.setSyncFlag((byte) 0);
			pa.setAddTime(new Date());
			pa.setAddOperator("system");
			if(null != insert(pa))
				return true;
			else
				return false;
		}else {
			return false;
		}
	}

    /**
     *  设置支付密码
     * @param userId
     * @param payPassword   MD5密文支付密码
     */
    public boolean resetPayPassword(String userId, String payPassword) {
        List<Condition> filters = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            filters.add(Condition.eq("userId", userId));
        }
        //验证userId是否正确
        PayAccount payAccount = super.selectOne(filters);
        if (payAccount != null ) {
            payAccount.setPayPassword(payPassword);
            if(updateById(payAccount)>0)
                return true;
            else
                return  false;
        }else{
            return false;
        }
    }

    /**
     *  验证支付密码
     * @param payPassword
     * @param userId
     * @return
     */
	public boolean verifyPassword(String payPassword, String userId){
		List<Condition> filters = new ArrayList();
		if (StringUtils.isNotBlank(userId)) {
			filters.add(Condition.eq("userId", userId));
		}
		PayAccount payAccount = super.selectOne(filters);
		if (null != payAccount && payAccount.getPayPassword().equals(payPassword))
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public void payByBalance(OrderInfo orderInfo) throws Exception {
		//先看流水存不存在
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn",orderInfo.getOrderSn()));
		List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
		if (payTradeRecords==null || payTradeRecords.size() == 0){
			//插内部消费流水
			PayTradeRecord payTradeRecord = new PayTradeRecord();
			List<Condition> filters = new ArrayList<>();
			filters.add(Condition.eq("userId",orderInfo.getUserId()));
			PayAccount payAccount = super.selectOne(filters);
			payTradeRecord.setPayAccountId(payAccount.getId());
			payTradeRecord.setUserId(orderInfo.getUserId());
			payTradeRecord.setMoney(orderInfo.getOrderAmount());
			payTradeRecord.setTradeType(PayConstants.TradeType.SalesOut.getCode());
			payTradeRecord.setTradeStatus(PayConstants.TransferStatus.handing.getCode());
			payTradeRecord.setAddTime(new Date());
			payTradeRecord.setOrderSn(orderInfo.getOrderSn());
			payTradeRecord.setAddOperator(orderInfo.getUserId());
			payTradeRecord.setTransferDate(new Date());
			payTradeRecordService.insertAndGetId(payTradeRecord);
			//插交易中心冻结收入流水，
			PayTradeRecord payTradeRecord1 = new PayTradeRecord();
			payTradeRecord1.setPayAccountId(HookahConstants.TRADECENTERACCOUNT);
			payTradeRecord1.setUserId(orderInfo.getUserId());
			payTradeRecord1.setMoney(orderInfo.getOrderAmount());
			payTradeRecord1.setTradeType(HookahConstants.TradeType.FreezaIn.getCode());
			payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.handing.getCode());
			payTradeRecord1.setAddTime(new Date());
			payTradeRecord1.setOrderSn(orderInfo.getOrderSn());
			payTradeRecord1.setAddOperator(orderInfo.getUserId());
			payTradeRecord1.setTransferDate(new Date());
			payTradeRecordService.insertAndGetId(payTradeRecord1);
		}

		//调余额支付
		doPayByBalance(orderInfo);

	}

	public void doPayByBalance(OrderInfo orderinfo) throws Exception {
		//用户减去余额，交易中心收入冻结金额
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("userId", orderinfo.getUserId()));
		PayAccount payAccount = super.selectOne(filter);
		operatorByType(payAccount.getId(), HookahConstants.TradeType.SalesOut.getCode(), orderinfo.getOrderAmount());
		operatorByType(HookahConstants.TRADECENTERACCOUNT,HookahConstants.TradeType.FreezaIn.getCode(),
				orderinfo.getOrderAmount());

		List<Condition> filters = new ArrayList<>();
		filters.add(Condition.eq("orderSn",orderinfo.getOrderSn()));
		List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filters);
		for(PayTradeRecord payTradeRecord:payTradeRecords){
			payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.success.getCode());
			payTradeRecordService.updateByIdSelective(payTradeRecord);
		}

		//修改订单支付状态
		orderInfoService.updatePayStatus(orderinfo.getOrderSn(), OrderInfo.PAYSTATUS_PAYED,0);

		// 支付成功 查询订单 获取订单中商品插入到待清算记录
		mqSenderService.sendDirect(RabbitmqQueue.WAIT_SETTLE_ORDERS, orderinfo.getOrderSn());
	}

	@Override
	@Transactional
	public String payByAli(OrderInfo orderInfo) {
		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn",orderInfo.getOrderSn()));
		List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
		if (payTradeRecords==null || payTradeRecords.size() == 0){
			//插内部消费流水
			PayTradeRecord payTradeRecord = new PayTradeRecord();
			List<Condition> filters = new ArrayList<>();
			filters.add(Condition.eq("userId",orderInfo.getUserId()));
			PayAccount payAccount = super.selectOne(filters);
			payTradeRecord.setPayAccountId(payAccount.getId());
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
			payTrade.setPayAccountId(payAccount.getId());
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
			payAccountRecord.setPayAccountId(payAccount.getId());
			payAccountRecord.setUserId(orderInfo.getUserId());
			Date date = new Date();
			payAccountRecord.setTransferDate(date);
			payAccountRecord.setMoney(orderInfo.getOrderAmount());//订单资金总额
			payAccountRecord.setSerialNumber(orderInfo.getOrderSn());//订单号
			payAccountRecord.setTransferType(PayConstants.TransferType.MONEY_IN.getCode());
			payAccountRecord.setTransferStatus(HookahConstants.TransferStatus.handing.getCode());
			payAccountRecord.setAddTime(date);
			payAccountRecord.setAddOperator(orderInfo.getUserId());
			payAccountRecordMapper.insertAndGetId(payAccountRecord);
		}
		//调支付宝接口
		String html = alipayService.doPay(orderInfo.getUserId(), orderInfo.getOrderId(),
				PayConfiguration.ALIPAY_NOTIFY_URL, PayConfiguration.ALIPAY_RETURN_URL);
		return html;
	}

	@Override
	@Transactional
	public boolean aliPay(String orderSn, String tradeStatus, Map<String,String> param) throws Exception {

		List<Condition> filter = new ArrayList<>();
		filter.add(Condition.eq("orderSn", orderSn));
		OrderInfo orderInfo = orderInfoService.selectOne(filter);
		if (AlipayNotify.verify(param)){
			if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
				//交易成功,插交易中心冻结收入流水，更新交易中心虚拟账户金额
				PayTradeRecord payTradeRecord = new PayTradeRecord();
				payTradeRecord.setPayAccountId(HookahConstants.TRADECENTERACCOUNT);
				payTradeRecord.setUserId(orderInfo.getUserId());
				payTradeRecord.setMoney(orderInfo.getOrderAmount());
				payTradeRecord.setTradeType(HookahConstants.TradeType.FreezaIn.getCode());
				payTradeRecord.setTradeStatus(HookahConstants.TransferStatus.handing.getCode());
				payTradeRecord.setAddTime(new Date());
				payTradeRecord.setOrderSn(orderSn);
				payTradeRecord.setAddOperator(orderInfo.getUserId());
				payTradeRecord.setTransferDate(new Date());
				payTradeRecordService.insertAndGetId(payTradeRecord);
				//更新交易中心虚拟账户金额
				operatorByType(HookahConstants.TRADECENTERACCOUNT,HookahConstants.TradeType.FreezaIn.getCode(),
						orderInfo.getOrderAmount());

				//修改内部流水的状态和外部充值状态
				List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
				for (PayTradeRecord payTradeRecord1 : payTradeRecords){
					payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.success.getCode());
					payTradeRecordService.updateByIdSelective(payTradeRecord1);
				}
				List<Condition> filters = new ArrayList<>();
				filters.add(Condition.eq("serialNumber",orderSn));
				PayAccountRecord payAccountRecord = payAccountRecordService.selectOne(filters);
				payAccountRecord.setTransferStatus(HookahConstants.TransferStatus.success.getCode());
				payAccountRecordService.updateByIdSelective(payAccountRecord);

				//更新订单状态
				orderInfoService.updatePayStatus(orderSn,orderInfo.PAYSTATUS_PAYED,1);

				// 支付成功 查询订单 获取订单中商品插入到待清算记录
				mqSenderService.sendDirect(RabbitmqQueue.WAIT_SETTLE_ORDERS, orderSn);
				
				return true;
			}else{
				//交易失败
				List<PayTradeRecord> payTradeRecords = payTradeRecordService.selectList(filter);
				for (PayTradeRecord payTradeRecord1 : payTradeRecords){
					payTradeRecord1.setTradeStatus(HookahConstants.TransferStatus.fail.getCode());
				}
				return false;
			}
		}else{
			return false;
		}
	}

	public ReturnData userRecharge(Map<String,String> params){
		ReturnData returnData = new ReturnData();

		String userId = params.get("userId");
		if(StrUtil.isBlank(userId)){
			returnData.setCode(ExceptionConst.Failed);
			returnData.setMessage("充值失败：用户ID不能为空！");
			return returnData;
		}
		if(StrUtil.isBlank(params.get("money"))){
			returnData.setCode(ExceptionConst.Failed);
			returnData.setMessage("充值失败：充值金额不能为空！");
			return returnData;
		}

		Double moneyObj=Double.parseDouble(params.get("money"));
		try {
			if( moneyObj<= 0) {
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
			Long money = Math.round(moneyObj*100);
			if (payAccount == null){
				returnData.setCode(ExceptionConst.Failed);
				returnData.setMessage("充值失败：账户不存在！");
				return returnData;
			}

			String html = alipayService.doCharge(userId,payAccount.getId(),money.toString(),
					PayConfiguration.RECHARGE_NOTIFY_URL,PayConfiguration.RECHARGE_RETURN_URL);
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

	public void updatePayAccountMoney(Long payAccountId,Long money,String userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("payAccountId",payAccountId);
		params.put("money",money);
		params.put("userId",userId);
		payAccountMapper.updatePayAccountMoney(params);
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
				if (money<=0){
					returnData.setCode(ExceptionConst.Failed);
					returnData.setMessage("充值失败！充值金额必须大于0元！");
					return returnData;
				}
				//更新账户金额
				updatePayAccountMoney(payAccount.getId(),money,userId);
			}
			//更新记录表
			payTradeRecordMapper.updatePayTradeRecordStatusByOrderSn(params);
			payAccountRecordMapper.updatePayAccountRecordStatusByOrderSn(params);

			returnData.setCode(ExceptionConst.Success);
			returnData.setMessage("操作成功！");
			return returnData;
		}catch (Exception e){
			returnData.setCode(ExceptionConst.Error);
			returnData.setMessage(e.toString());
			e.printStackTrace();
		}
		return returnData;
	}

    /**
     * 查询虚拟账户信息
     * @param userId
     * @return
     */
    @Override
    public PayAccount findPayAccountByUserId(String userId) {
        List<Condition> filters = null;
        if (StringUtils.isNotBlank(userId)) {
            filters=new ArrayList();
            filters.add(Condition.eq("userId", userId));
            PayAccount payAccount=super.selectOne(filters);
			//不提供交易密码
            payAccount.setPayPassword("");
            return payAccount;
        }else{
            return null;
        }
    }
	/**
	 * 修改交易密码
	 * @param oldPayPassWord
	 * @param newPayPassWord
	 * @param userId
	 * @return
	 */
	@Override
	public boolean updatePayPassWordByUserId(String oldPayPassWord, String newPayPassWord, String userId) {
		if(StringUtils.isNotBlank(userId) && !oldPayPassWord.equals(newPayPassWord)){
			List<Condition> filters = new ArrayList();
			filters.add(Condition.eq("userId", userId));
			PayAccount payAccount=super.selectOne(filters);
			//判断UserId是否正确及原始交易密码是否正确
			if(null == payAccount || !oldPayPassWord.equals(payAccount.getPayPassword()))
				return false;
			payAccount.setPayPassword(newPayPassWord);
			if(updateById(payAccount)>0)
				return true;
			else
				return false;
		}
		return false;
	}

}
