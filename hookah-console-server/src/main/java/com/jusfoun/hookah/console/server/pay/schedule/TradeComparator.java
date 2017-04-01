package com.jusfoun.hookah.console.server.pay.schedule;

import java.text.SimpleDateFormat;
import java.util.*;


import com.jusfoun.hookah.console.server.pay.alipay.AlipayBuilder;
import com.jusfoun.hookah.console.server.pay.alipay.AlipayConfig;
import com.jusfoun.hookah.core.dao.PayCoreMapper;
import com.jusfoun.hookah.core.domain.ErrorTrade;
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.vo.PayCoreVo;
import com.jusfoun.hookah.core.utils.DomTool;
import com.jusfoun.hookah.rpc.api.TradeComparatorService;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TradeComparator implements TradeComparatorService {
	public static final String accountLogVo ="/alipay/response/account_page_query_result/account_log_list/AccountQueryAccountLogVO";
	public static final String hasNextPage ="/alipay/response/account_page_query_result/has_next_page";
	public static final String isSuccess ="/alipay/is_success";
	public static final String TURE ="T";
	public static final String FALSE ="F";
	
	private String lastDay; 
	private String startTime;
	private String endTime;
	private TreeMap<String, TradeKey> shopTrades = new TreeMap<String, TradeKey>();
	private TreeMap<String, TradeKey> thirdPartyTrades = new TreeMap<String, TradeKey>();
	private TreeSet<ErrorTrade> errorTrades = new TreeSet<ErrorTrade>();
	private Set<String> successTrades = new HashSet<String>();
	
//	@Reference
//	private PayCoreService payCoreService;
	@Autowired
	private PayCoreMapper mapper;
	
	public TradeComparator() {
	}
	
	public TradeComparator(String startTime, String endTime) throws Exception {
		this.startTime = startTime;
		this.endTime = endTime;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date  now = format.parse(startTime);
		Calendar ca = Calendar.getInstance();
		ca.setTime(now);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		this.lastDay = format.format(new Date(ca.getTimeInMillis()));
	}
	public Map<String,Object> compare(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		shopTrades = getShopList();
		thirdPartyTrades = getThirdPartyList();
		if(shopTrades==null && thirdPartyTrades==null)
			return null;
		String key;
		TradeKey shopTrade,thirdPartyTrade;
		if(thirdPartyTrades==null || shopTrades.size()>thirdPartyTrades.size()){
			Iterator<String> it = shopTrades.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				if(thirdPartyTrades==null || !thirdPartyTrades.containsKey(key)){
					TradeKey trade = shopTrades.get(key);
					//短账
					errorTrades.add(new ErrorTrade(trade.getOrderSn(), ErrorTrade.ErrorType.more.getValue(), trade.getMoney(), null, trade.getIncomeFlag(), new Date()));
				}else{
					shopTrade = shopTrades.get(key);
					thirdPartyTrade = thirdPartyTrades.get(key);
					if(!shopTrade.getMoney().equals(thirdPartyTrade.getMoney()))
						//差额
						errorTrades.add(new ErrorTrade(shopTrade.getOrderSn(), ErrorTrade.ErrorType.diff.getValue(), shopTrade.getMoney(), thirdPartyTrade.getMoney(), shopTrade.getIncomeFlag(), new Date()));
					else
						successTrades.add(shopTrade.getOrderSn());
				}
			}
		}else{
			Iterator<String> it = thirdPartyTrades.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				if(shopTrades==null || !shopTrades.containsKey(key)){
					TradeKey trade = thirdPartyTrades.get(key);
					//长账
					errorTrades.add(new ErrorTrade(trade.getOrderSn(), ErrorTrade.ErrorType.less.getValue(), null, trade.getMoney(), trade.getIncomeFlag(), new Date()));
				}else{
					shopTrade = shopTrades.get(key);
					thirdPartyTrade = thirdPartyTrades.get(key);
					if(!shopTrade.getMoney().equals(thirdPartyTrade.getMoney()))
						//差额
						errorTrades.add(new ErrorTrade(shopTrade.getOrderSn(), ErrorTrade.ErrorType.diff.getValue(), shopTrade.getMoney(), thirdPartyTrade.getMoney(), thirdPartyTrade.getIncomeFlag(), new Date()));
					else
						successTrades.add(shopTrade.getOrderSn());
				}
			}
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("successTrades", successTrades.isEmpty() ? CollectionUtils.EMPTY_COLLECTION : successTrades);
		result.put("errorTrades", errorTrades.isEmpty() ? CollectionUtils.EMPTY_COLLECTION : errorTrades);
		return result;
	}
	@SuppressWarnings("rawtypes")
	private TreeMap<String, TradeKey> getThirdPartyList() {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "account.page.query");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("gmt_start_time", startTime);
		sParaTemp.put("gmt_end_time", endTime);
		int pageNo = 1;
		String result = null;
		while(true){
			sParaTemp.put("page_no", pageNo+++"");
			for(int t=0;t<5;t++){
				try {
					//尝试多次
					result = AlipayBuilder.buildRequest("", "", sParaTemp);
					if(result==null)
						continue;
					if(DomTool.assertStr(DomTool.parseDom(result), isSuccess, TURE))
						break;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			List alipayList = DomTool.selectNodes(DomTool.parseDom(result), accountLogVo);
			if(CollectionUtils.isEmpty(alipayList))
				return null;
			boolean incomeFlag;
			String incomeStr="";
			for(int t=0;t<alipayList.size();t++){
				Element node = (Element) alipayList.get(t);
				String orderSn = node.element("merchant_out_order_no").getTextTrim();
				String partnerId = node.element("partner_id").getTextTrim();
				//判断是不是九连环的交易
				if(StringUtils.isEmpty(orderSn) || StringUtils.isEmpty(partnerId) || !AlipayConfig.partner.equals(partnerId))
					continue;
				String income = node.element("income").getTextTrim();
				String outcome = node.element("outcome").getTextTrim();
				incomeFlag = Double.valueOf(income) != 0;
				incomeStr = incomeFlag ? "1" : "0";
//				if(thirdPartyTrades.containsKey(orderSn+incomeStr)){
//					errorTrades.add(new ErrorTrade(orderSn + incomeStr, ErrorType.tp_dupli.getValue(), null, incomeFlag ? income : outcome, incomeFlag, new Date()));
//					continue;
//				}
				thirdPartyTrades.put(orderSn + incomeStr, new TradeKey(orderSn, incomeFlag ? income : outcome, incomeFlag));
			}
			if(DomTool.assertStr(DomTool.parseDom(result), hasNextPage, FALSE))
				break;
		}
		return thirdPartyTrades;
	}
	private TreeMap<String, TradeKey> getShopList() {
		PayCoreVo payCoreVo = new PayCoreVo();
		payCoreVo.setPayMode("1");//支付宝
		payCoreVo.setPayStatus(PayCore.PayStatus.success.getValue());//成功的交易
		payCoreVo.setStartTime(lastDay);//延长开始时间到前一天
		payCoreVo.setEndTime(endTime);
//		List<PayCore> list = payCoreService.findListByVo(payCoreVo);
		List<PayCore> list = mapper.findByVo(payCoreVo);
		if(CollectionUtils.isEmpty(list))
			return null;
		String incomeStr = "";
		for(PayCore core : list){
			incomeStr = core.getIncomeFlag() ? "1" : "0";
//			if(shopTrades.containsKey(core.getOrderSn()+incomeStr)){
//				errorTrades.add(new ErrorTrade(core.getOrderSn(), ErrorType.s_dupli.getValue(), core.getAmount().toString(), null, core.getIncomeFlag(), new Date()));
//				continue;
//			}
			shopTrades.put(core.getOrderSn()+incomeStr, new TradeKey(core.getOrderSn(),core.getAmount().toString(), core.getIncomeFlag()));
		}
		return shopTrades;
	}
	
}