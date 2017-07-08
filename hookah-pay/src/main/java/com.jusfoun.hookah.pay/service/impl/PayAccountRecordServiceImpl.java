package com.jusfoun.hookah.pay.service.impl;


import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixConstants;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.apex.fix.AxCallFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.core.dao.PayAccountRecordMapper;
import com.jusfoun.hookah.core.domain.PayAccount;
import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.pay.util.ChannelType;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.pay.util.PayUtil;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * dengxu
 */

@Service
public class PayAccountRecordServiceImpl extends GenericServiceImpl<PayAccountRecord, Integer> implements
		PayAccountRecordService {

	@Resource
	FixClientUtil client;

	private IFixClient fixClient = client.createClientSSL();

	@Resource
	private PayAccountRecordMapper payAccountRecordMapper;

	@Resource
	public void setDao(PayAccountRecordMapper payAccountRecordMapper) {
		super.setDao(payAccountRecordMapper);
	}

	@Resource
	PayBankCardService payBankCardService;

	@Resource
	PayTradeRecordService payTradeRecordService;

	@Resource
	MgMoneyInOutLogService mgMoneyInOutLogService;

	@Resource
	PayAccountService payAccountService;

	@Transactional
	public void entryAndExitPayments(MoneyInOutBo moneyInOutBo) {

		try {
			// 获取农行k宝密码
			String kbaoRS = "";

			// 获取交易市场申请号
			String serialNum = PayUtil.createChannelSerial(ChannelType.QDABC);

			// 如果是提现 就先去扣客户帐
			if(moneyInOutBo.getOperatorType() == PayConstants.TradeType.OnlineCash.code){
				logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---提现操作先去扣客户账-->操作时间：" + LocalDateTime.now());
				payAccountService.operatorByType(moneyInOutBo.getPayAccountID(), moneyInOutBo.getOperatorType(), moneyInOutBo.getMoney());
			}

			// 添加外部流水记录 处理中状态
			PayAccountRecord payAccountRecord = initPayAccountRecord(moneyInOutBo, serialNum);
			logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---添加payAccountRecord记录-->操作时间：" + LocalDateTime.now());

			// 添加内部流水记录
			PayTradeRecord payTradeRecord = payTradeRecordService.initPayTradeRecord(moneyInOutBo);
			logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---添加payTradeRecord记录-->操作时间：" + LocalDateTime.now());

			// 根据当前用户信息获取银行卡信息
			List<Condition> filters = new ArrayList();
			filters.add(Condition.eq("userId", moneyInOutBo.getUserId()));
			filters.add(Condition.eq("payAccountId", moneyInOutBo.getPayAccountID()));
			PayBankCard payBankCard = payBankCardService.selectOne(filters);
			logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---获取银行卡信息-->操作时间：" + LocalDateTime.now());
			if(payBankCard == null){
				logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---获取银行卡信息失败-->操作时间：" + LocalDateTime.now());
				throw new RuntimeException();
			}

			// 拼装报文 发送请求 根据请求返回结果 进行后续处理
			Map<String, String> paramMap = new HashMap<String, String>();
			/**
			 * 				FID_JYS 交易市场*
			 *              FID_BZ  币种*
			 *              FID_ZZJE    转账金额*
			 *              FID_SQRQ    申请日期
			 *              FID_SQSJ    申请时间
			 *              FID_SQH 	申请号*
			 *              FID_YHZH    银行账户*
			 *              FID_YHDM    银行代码*
			 *              FID_YHMM    银行密码
			 *              FID_CZZD    操作站点
			 *              FID_BZXX    备注信息
			 *              FID_ZJZH    资金账户*
			 *              FID_WDH  	网点号
			 *              FID_CS1 	预留参数1 如果需要签名信息，对应签名信息
			 *              FID_CS2 	预留参数2 签名流水号，如果是农行，此处必填，主要用来验证加签信息
			 *              FID_CS3 	预留参数3 账单号，如果是农行，此处必填，主要用来验证加签信息
			 *              FID_CS4 	预留参数4
			 */
			paramMap.put("FID_JYS", PayConstants.FID_JYS);
			paramMap.put("FID_BZ", PayConstants.QD_BZ);
			paramMap.put("FID_ZZJE", PayUtil.moneyConver(moneyInOutBo.getMoney() / 100));
			paramMap.put("FID_SQRQ", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
			paramMap.put("FID_SQSJ", LocalTime.now().withNano(0).toString());
			paramMap.put("FID_SQH", serialNum);
			paramMap.put("FID_YHZH", payBankCard.getCardCode());
			paramMap.put("FID_YHDM", PayConstants.BankCode.NY02.code);
			paramMap.put("FID_CZZD", "");   								//操作站点
			paramMap.put("FID_ZJZH", moneyInOutBo.getUserId());
			paramMap.put("FID_CS1", kbaoRS);								// k宝返回
			paramMap.put("FID_CS2", PayConstants.QDABC_PREFIX + kbaoRS);	// BD + k宝返回

			// 待发送报文信息先存到Mongon
			mgMoneyInOutLogService.initMgMoneyInOutLog(moneyInOutBo, paramMap, payAccountRecord.getId());
			logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---待发送报文信息存到MgMoneyInOutLog数据库-->操作时间：" + LocalDateTime.now());

			AxCallFunc callFunc = new AxCallFunc() {
				public boolean onReply(JFixSess jFixSess, JFixComm jFixComm) {
					try{

						// 返回报文信息先存到Mongon
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("FID_CODE", jFixSess.getItem(FixConstants.FID_CODE));
						resultMap.put("FID_MESSAGE", jFixSess.getItem(FixConstants.FID_MESSAGE));
						resultMap.put("FID_SQBH", jFixSess.getItem(FixConstants.FID_SQBH));
						resultMap.put("FID_CLJG", jFixSess.getItem(FixConstants.FID_CLJG));
						resultMap.put("FID_JGSM", jFixSess.getItem(FixConstants.FID_JGSM));
						mgMoneyInOutLogService.updateMgMoneyInOutLog(moneyInOutBo, resultMap, payAccountRecord.getId());
						logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---返回报文信息存到MgMoneyInOutLog数据库-->操作时间：" + LocalDateTime.now());

						/**
						 * 异步调用返回：
						 *     		FID_CODE	返回码
						 * 	        FID_MESSAGE	返回信息
						 * 	        FID_SQBH	申请编号
						 * 	        FID_CLJG	处理结果  -111失败  111成功  0 申请中(存疑)
						 * 	        FID_JGSM	结果说明
						 */
						payAccountRecord.setTransferCode(jFixSess.getItem(FixConstants.FID_CODE));
						payAccountRecord.setTransferMessage(jFixSess.getItem(FixConstants.FID_MESSAGE));
						if(jFixSess.getItem(FixConstants.FID_SQBH) != null || jFixSess.getItem(FixConstants.FID_SQBH) != ""){
							payAccountRecord.setSqbhNumber(jFixSess.getItem(FixConstants.FID_SQBH));
						}

						payAccountRecord.setTransferResult(jFixSess.getItem(FixConstants.FID_CLJG));
						payAccountRecord.setTransferDesc(jFixSess.getItem(FixConstants.FID_JGSM));

						if(jFixSess.getCount() > 0){
							jFixSess.go(0);
							if (jFixSess.getCode() > 0){
								String FID_CID =  jFixSess.getItem(FixConstants.FID_CID);
								String FID_MID =  jFixSess.getItem(FixConstants.FID_MID);
								String msg =  jFixSess.getItem(FixConstants.FID_MESSAGE);
								String FID_CODE =  jFixSess.getItem(FixConstants.FID_CODE);
								msg = msg != null ? msg : "处理成功";

								//todo 成功后处理  账户加钱 修改内部流水状态成功 修改外部流水状态成功
								if(Integer.parseInt(FID_CODE) >= 0){ // >=0 成功 <0 失败

									payAccountService.operatorByType(moneyInOutBo.getPayAccountID(), moneyInOutBo.getOperatorType(), moneyInOutBo.getMoney());

									payTradeRecord.setTradeStatus(PayConstants.TransferStatus.success.getCode());
								}
								payAccountRecord.setTransferStatus(PayConstants.TransferStatus.success.code);
							}else{
								String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
								//todo 失败后处理 修改内部流水状态为失败 修改外部流水状态失败 如果是提现需要冲账
								payTradeRecord.setTradeStatus(PayConstants.TransferStatus.fail.code);
								payAccountRecord.setTransferStatus(PayConstants.TransferStatus.fail.code);


								if(moneyInOutBo.getOperatorType() == PayConstants.TradeType.OnlineCash.code){
									logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---提现失败冲正客户账-->操作时间：" + LocalDateTime.now());
									payAccountService.operatorByType(moneyInOutBo.getPayAccountID(), PayConstants.TradeType.CashREverse.code, moneyInOutBo.getMoney());
								}
							}
						}else{
							String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
							//todo 失败后处理 修改内部流水状态失败 修改外部流水状态失败
							payTradeRecord.setTradeStatus(PayConstants.TransferStatus.fail.code);
							payAccountRecord.setTransferStatus(PayConstants.TransferStatus.fail.code);
						}
						payTradeRecord.setUpdateOperator("SYSTEM");
						payTradeRecord.setUpdateTime(new Date());
						int n = payTradeRecordService.updateByIdSelective(payTradeRecord);
						logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---业务处理--->payTradeRecord修改" + (n > 0 ? "成功" : "失败") + "-->操作时间：" + LocalDateTime.now());

						payAccountRecord.setUpdateOperator("SYSTEM");
						payAccountRecord.setUpdateTime(new Date());
						int j = payAccountRecordMapper.updateByPrimaryKeySelective(payAccountRecord);
						logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---业务处理--->payAccountRecord修改" + (j > 0 ? "成功" : "失败") + "-->操作时间：" + LocalDateTime.now());

					}catch (Exception e){
						e.printStackTrace();
						//todo 异常后处理
						return false;
					}
					return true;
				}
			};

			ResultBean<Map<String, String>> resultBean;
			if(moneyInOutBo.getOperatorType() == PayConstants.TradeType.OnlineRecharge.code){
				resultBean = fixClient.sendMoneyIn(paramMap, callFunc);
			}else if(moneyInOutBo.getOperatorType() == PayConstants.TradeType.OnlineCash.code){
				resultBean = fixClient.sendMoneyOut(paramMap, callFunc);
			}else{
				logger.error("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---出入金可操作类型错误-----{}", moneyInOutBo.getOperatorType());
				return;
			}
			logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---发送报文-->操作时间：" + LocalDateTime.now());
			if(resultBean.isSuccess()){
				//发送成功
				//todo 发送成功处理
				String successMsg = "[fix error]" + String.format("[%s]%s",resultBean.getCode() , resultBean.getMsg());
				payAccountRecord.setSendMsg(successMsg);
				payAccountRecord.setUpdateOperator("SYSTEM");
				payAccountRecord.setUpdateTime(new Date());
				int m = payAccountRecordMapper.updateByPrimaryKeySelective(payAccountRecord);
				if(m != 1){
					logger.error("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---报文发送成功，payAccountRecordMapper修改失败-->操作时间" + LocalDateTime.now());
				}
				logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---报文发送成功-->操作时间：" + LocalDateTime.now());

			} else{
				//发送失败
				String errorMsg = "[fix error]" + String.format("[%s]%s",resultBean.getCode() , resultBean.getMsg());
				//todo 发送失败处理

				payTradeRecord.setUpdateOperator("SYSTEM");
				payTradeRecord.setUpdateTime(new Date());
				payTradeRecord.setTradeStatus(PayConstants.TransferStatus.fail.code);
				int y = payTradeRecordService.updateByIdSelective(payTradeRecord);
				if(y != 1){
					logger.error("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---报文发送失败，payTradeRecordService修改失败-->操作时间" + LocalDateTime.now());
				}

				payAccountRecord.setSendMsg(errorMsg);
				payAccountRecord.setTransferStatus(PayConstants.TransferStatus.fail.code);
				payAccountRecord.setUpdateOperator("SYSTEM");
				payAccountRecord.setUpdateTime(new Date());
				int m = payAccountRecordMapper.updateByPrimaryKeySelective(payAccountRecord);
				if(m != 1){
					logger.error("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---报文发送失败，payAccountRecordMapper修改失败-->操作时间" + LocalDateTime.now());
				}
				logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---报文发送失败-->操作时间：" + LocalDateTime.now());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private PayAccountRecord initPayAccountRecord(MoneyInOutBo moneyInOutBo, String serialNum) {
		PayAccountRecord payAccountRecord = new PayAccountRecord();
		payAccountRecord.setPayAccountId(moneyInOutBo.getPayAccountID());	//当前操作用户的payaccountID
		payAccountRecord.setUserId(moneyInOutBo.getUserId());		//当前操作用户的userID
		payAccountRecord.setTransferDate(new Date());
		payAccountRecord.setMoney(moneyInOutBo.getMoney());		//当前用户的入金出金的金额
		if(moneyInOutBo.getOperatorType() == 1){
			payAccountRecord.setTransferType(PayConstants.TradeType.OnlineRecharge.code);	//根据操作类型  入金
		}else{
			payAccountRecord.setTransferType(PayConstants.TradeType.OnlineCash.code);	//根据操作类型  出金
		}
		payAccountRecord.setTransferStatus(PayConstants.TransferStatus.handing.code);
		payAccountRecord.setSerialNumber(serialNum);
		payAccountRecord.setChannelType(ChannelType.QDABC);
		payAccountRecord.setAddTime(new Date());
		//		payAccountRecord.setAddOperator();	//userID用户的username
		int n = payAccountRecordMapper.insertAndGetId(payAccountRecord);
		if(n != 1){
			logger.info("当前用户【PayAccountID】---<" +moneyInOutBo.getPayAccountID()+ ">---payAccountRecord初始化插入" + (n > 0 ? "成功" : "失败") + "-->操作时间：" + LocalDateTime.now());
			throw new RuntimeException();
		}
		System.out.println(n);
		return payAccountRecord;
	}

	public static void main(String[] args){
		System.out.println(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
		System.out.println(LocalTime.now().withNano(0));

		// 获取当天日期
		LocalDate today = LocalDate.now();

		// 获取当天日期的后一天
		LocalDate day1 = today.plusDays(1);

		// 获取当天日期的前一天
		LocalDate day_1 = today.minusDays(1);

		// 获取当天日期的前2天
		LocalDate day_2 = today.minusDays(2);

		// 获取当天日期的前3天
		LocalDate day_3 = today.minusDays(3);

		System.out.println(day1);
		System.out.println(day_1);
		System.out.println(day_2);
		System.out.println(day_3);
	}
}
