package com.jusfoun.hookah.console.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
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
import com.jusfoun.hookah.core.domain.OrderInfo;
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.User;
import com.jusfoun.hookah.core.domain.vo.PayCoreVo;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import com.jusfoun.hookah.core.domain.vo.RechargeVo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.HttpClientUtil;
import com.jusfoun.hookah.rpc.api.OrderInfoService;
import com.jusfoun.hookah.rpc.api.PayCoreService;
import com.jusfoun.hookah.rpc.api.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class PayCoreServiceImpl extends GenericServiceImpl<PayCore, String> implements PayCoreService {

	//支付方式‘钱包’的id
	//@Value("${walletId}")
	private String walletId ="1";

	//充值地址
	//@Value("${rechargeUrl}")
	private String rechargeUrl;
	//淘宝手续费费率
//	@Value("${alipayFeeRate}")
	private String alipayFeeRate ="0.0055";
	//账户余额
	//@Value("${getBalanceUrl}")
	private String getBalanceUrl;
	//根据手机号/邮箱查询用户ID
	//@Value("${findUserIdByPhoneOrEmail}")
	private String findUserIdByPhoneOrEmail;
	//账户信息地址
//	@Value("${usrDetailUrl}")
	private String usrDetailUrl;
	@Resource
	private PayCoreMapper mapper;

	@Resource
	private OrderInfoService orderService;
	@Resource
	private AccNoTokenMapper accNoTokenMapper;

	@Resource
	UserService userService;

	@Resource
	public void setDao(PayCoreMapper mapper) {
		super.setDao(mapper);
	}

	/**
	 *
	 * @param orderSn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public void doPayMoney(String orderSn, String userId) throws Exception{

		List<Condition> filters = new ArrayList();
		filters.add(Condition.eq("orderSn", orderSn));
		OrderInfo orderinfo  = orderService.selectOne(filters);
    	//修改订单支付状态
		orderinfo.setPayStatus(2);
		orderService.updateById(orderinfo);
        Long orderAmount = orderinfo.getOrderAmount();
        //减去余额
		User user =  userService.selectById(userId);
		Long oldMoneyBalance =  user.getMoneyBalance();
		Long newMoneyBalance = oldMoneyBalance - orderAmount;
		user.setMoneyBalance(newMoneyBalance);
		userService.updateByIdSelective(user);
	}

	@Override
	public PayCore findPayCoreByOrderId(String orderId) {
		List<Condition> filters = new ArrayList();
		filters.add(Condition.eq("orderId", orderId));
		List<PayCore> pays =this.selectList(filters);
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
		List<Condition> filters = new ArrayList();
		filters.add(Condition.eq("orderSn", orderSn));
		List<PayCore> pays =this.selectList(filters);
		return (pays == null || pays.size()==0) ? null : pays.get(0);
	}

	@Transactional(readOnly=false)
	@Override
	public String doPay(String orderId, String userId) throws Exception {
		// 检查
		PayCore paied = findPayCoreByOrderId(orderId);
		if (paied != null
				&&
				PayCore.PayStatus.success.getValue() == paied.getPayStatus().intValue())
			return null;

		/*PayVo payVo = new PayVo();
		payVo.setOrderSn("001");
		payVo.setPayId(1);
		payVo.setTotalFee(new BigDecimal("0.1"));
		payVo.setUserId("62cb01c71c4711e796c56a3b07101c5a");
		payVo.setOrderTitle("商品名称");*/



		PayVo payVo = orderService.getPayParam(orderId);
		if (null == payVo || payVo.getPayId().intValue() == 0)
			throw new RuntimeException("订单 [id : " + orderId + "] 信息有误");
		if (payVo.getTotalFee().doubleValue() < 0)
			throw new RuntimeException("订单金额不合法");
		payVo.setUserId(userId);

		BigDecimal b1 = payVo.getTotalFee();
		BigDecimal b2 = new BigDecimal(100);
		payVo.setTotalFee( b1.divide(b2,2,BigDecimal.ROUND_HALF_UP));
		BigDecimal fee = payVo.getTotalFee();
		String html = buildRequestParams(payVo);

		System.out.print(html  +"            ------------------------------------------    ") ;
		// 记账
		if (StringUtils.isNotEmpty(html))
			enterAccount(orderId, payVo);
		return html;
	}


	private void enterAccount(String orderId, PayVo payVo) throws Exception {
		PayCore pay = new PayCore();
		pay.setOrderId(orderId);
		pay.setOrderSn(payVo.getOrderSn());
		pay.setUserId(payVo.getUserId());
		System.out.print(payVo.getUserId() +"          ==============================");
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
	public String openUnionpay(String orderId, String accNo, String userId) throws Exception {
		//根据orderId查询order信息
		//暂时屏蔽的一行

		//PayVo payVo = orderService.getPayParam(orderId);

		PayVo payVo = new PayVo();
		payVo.setOrderSn("001");
		payVo.setPayId(1);
		payVo.setTotalFee(new BigDecimal("0.1"));
		payVo.setUserId("62cb01c71c4711e796c56a3b07101c5a");
		payVo.setOrderTitle("商品名称");

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
	public String unionpayConsume(PayVo payVo, String orderId, String userId, String accNo, String smsCode) throws Exception {
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