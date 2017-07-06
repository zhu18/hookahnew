package com.jusfoun.hookah.pay.service.impl;


import com.apex.etm.qss.client.IFixClient;
import com.apex.etm.qss.client.fixservice.FixConstants;
import com.apex.etm.qss.client.fixservice.bean.ResultBean;
import com.apex.fix.AxCallFunc;
import com.apex.fix.JFixComm;
import com.apex.fix.JFixSess;
import com.jusfoun.hookah.core.dao.PayAccountRecordMapper;
import com.jusfoun.hookah.core.domain.PayAccountRecord;
import com.jusfoun.hookah.core.domain.PayBankCard;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.generic.OrderBy;
import com.jusfoun.hookah.pay.util.ChannelType;
import com.jusfoun.hookah.pay.util.FixClientUtil;
import com.jusfoun.hookah.pay.util.PayConstants;
import com.jusfoun.hookah.pay.util.PayUtil;
import com.jusfoun.hookah.rpc.api.PayAccountRecordService;
import com.jusfoun.hookah.rpc.api.PayBankCardService;
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

//	@Resource
//	PayBankCardService payBankCardService;

	@Transactional
	public void entryAndExitPayments(MoneyInOutBo moneyInOutBo) {

		try {
			// 获取农行k宝密码
			String kbaoRS = "";

			// 获取交易市场申请号
			String serialNum = PayUtil.createChannelSerial(ChannelType.QDABC);

			// 添加记录 处理中状态
			PayAccountRecord payAccountRecord = new PayAccountRecord();
			payAccountRecord.setPayAccountId(moneyInOutBo.getPayAccountID());	//当前操作用户的payaccountID
			payAccountRecord.setUserId(moneyInOutBo.getUserId());		//当前操作用户的userID
			payAccountRecord.setTransferDate(new Date());
			payAccountRecord.setMoney(moneyInOutBo.getMoney());		//当前用户的入金出金的金额
			if(moneyInOutBo.getOperatorType() == 1){
				payAccountRecord.setTransferType(PayConstants.TransferType.MONEY_IN.code);	//根据操作类型  入金
			}else{
				payAccountRecord.setTransferType(PayConstants.TransferType.MONEY_OUT.code);	//根据操作类型  出金
			}
			payAccountRecord.setTransferStatus(PayConstants.TransferStatus.handing.code);
			payAccountRecord.setSerialNumber(serialNum);
			payAccountRecord.setChannelType(ChannelType.QDABC);
			payAccountRecord.setAddTime(new Date());
	//		payAccountRecord.setAddOperator();	//userID用户的username
			int n = payAccountRecordMapper.insertAndGetId(payAccountRecord);
			System.out.println(n);
			logger.info("payAccountRecord初始化插入" + (n > 0 ? "成功" : "失败") + "-->操作时间：" + LocalDateTime.now());

			// 根据当前用户信息获取银行卡信息
//			List<Condition> filters = new ArrayList();
//			filters.add(Condition.eq("userId", moneyInOutBo.getUserId()));
//			filters.add(Condition.eq("payAccountId", moneyInOutBo.getPayAccountID()));
//			PayBankCard payBankCard = payBankCardService.selectOne(filters);
//			if(payBankCard == null){
//				logger.info("获取银行卡信息失败-->操作时间：" + LocalDateTime.now());
//				throw new RuntimeException();
//			}

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
			paramMap.put("FID_SQRQ", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
			paramMap.put("FID_SQSJ", LocalTime.now().withNano(0).toString());
			paramMap.put("FID_SQH", serialNum);
//			paramMap.put("FID_YHZH", payBankCard.getCardCode());
			paramMap.put("FID_YHDM", PayConstants.BankCode.NYYH.code);
			paramMap.put("FID_CZZD", "");   //操作站点
			paramMap.put("FID_ZJZH", moneyInOutBo.userId);
			paramMap.put("FID_CS1", "");	// k宝返回
			paramMap.put("FID_CS2", "");	// BD + k宝返回

			AxCallFunc callFunc = new AxCallFunc() {
				public boolean onReply(JFixSess jFixSess, JFixComm jFixComm) {
					try{
						Map<String, Object> resultMap = new HashMap<String, Object>();
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

						// 返回报文信息先存到Mongon

						// 返回结果入字段

						if(jFixSess.getCount() > 0){
							jFixSess.go(0);
							if (jFixSess.getCode() > 0){
								String FID_CID =  jFixSess.getItem(FixConstants.FID_CID);
								String FID_MID =  jFixSess.getItem(FixConstants.FID_MID);
								String msg =  jFixSess.getItem(FixConstants.FID_MESSAGE);
								String FID_CODE =  jFixSess.getItem(FixConstants.FID_CODE);
								msg = msg != null ? msg : "处理成功";
								if(Integer.parseInt(FID_CODE) >= 0){ // >=0 成功 <0 失败

									// 账户加钱 加流水

								}

								//todo 成功后处理
								payAccountRecord.setTransferStatus(PayConstants.TransferStatus.success.code);


							}else{
								String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
								//todo 失败后处理
								payAccountRecord.setTransferStatus(PayConstants.TransferStatus.fail.code);
							}
						}else{
							String errorMsg = String.format("[fix async error][%s]%s",jFixSess.getItem(FixConstants.FID_CODE), jFixSess.getItem(FixConstants.FID_MESSAGE));
							//todo 失败后处理
							payAccountRecord.setTransferStatus(PayConstants.TransferStatus.fail.code);
						}
						payAccountRecord.setUpdateOperator("SYSTEM");
						payAccountRecord.setUpdateTime(new Date());
						int j = payAccountRecordMapper.updateByPrimaryKeySelective(payAccountRecord);
						logger.info("业务处理--->payAccountRecord修改" + (j > 0 ? "成功" : "失败") + "-->操作时间：" + LocalDateTime.now());

					}catch (Exception e){
						e.printStackTrace();
						//todo 异常后处理
						return false;
					}
					return true;
				}
			};

			// 待发送报文信息先存到Mongon

			ResultBean<Map<String, String>> resultBean;
			if(moneyInOutBo.getOperatorType() == 1){
				resultBean = fixClient.sendMoneyIn(paramMap, callFunc);
			}else{
				resultBean = fixClient.sendMoneyOut(paramMap, callFunc);
			}
			if(resultBean.isSuccess()){
				//发送成功
				//todo 发送成功处理
				String successMsg = "[fix error]" + String.format("[%s]%s",resultBean.getCode() , resultBean.getMsg());
				payAccountRecord.setSendMsg(successMsg);
			} else{
				//发送失败
				String errorMsg = "[fix error]" + String.format("[%s]%s",resultBean.getCode() , resultBean.getMsg());

				//todo 发送失败处理
				payAccountRecord.setSendMsg(errorMsg);
				payAccountRecord.setTransferStatus(PayConstants.TransferStatus.fail.code);
			}
			payAccountRecord.setUpdateOperator("SYSTEM");
			payAccountRecord.setUpdateTime(new Date());
			int m = payAccountRecordMapper.updateByPrimaryKeySelective(payAccountRecord);
			logger.info("发送处理--->payAccountRecord修改" + (m > 0 ? "成功" : "失败") + "-->操作时间：" + LocalDateTime.now());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		System.out.println(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
		System.out.println(LocalTime.now().withNano(0));
	}
}
