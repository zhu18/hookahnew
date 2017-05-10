package com.jusfoun.hookah.console.server.pay.unionpay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jusfoun.hookah.console.server.pay.FormFactory;
import com.jusfoun.hookah.console.server.pay.unionpay.sdk.AcpService;
import com.jusfoun.hookah.console.server.pay.unionpay.sdk.CertUtil;
import com.jusfoun.hookah.console.server.pay.unionpay.sdk.SDKConfig;
import com.jusfoun.hookah.core.domain.AccNoToken;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class UnionpayBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(UnionpayBuilder.class);

	public static String build(PayVo payVo) {
		Map<String, String> map = new HashMap<String, String>();
		//基本信息
		map.put("version", UnionpayConfig.version);		//版本号，全渠道默认值
		map.put("encoding", UnionpayConfig.encoding);		//字符集编码，可以使用UTF-8,GBK两种方式
		map.put("signMethod", UnionpayConfig.signMethod);	//签名方法，只支持 01：RSA方式证书加密
		map.put("txnType", UnionpayConfig.txnType);		//交易类型 ，01：消费
		map.put("txnSubType", UnionpayConfig.txnSubType);		//交易子类型， 01：自助消费
		map.put("bizType", UnionpayConfig.bizType);		//业务类型，B2C网关支付，手机wap支付
		map.put("channelType", UnionpayConfig.channelType);		//渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
		//商户信息
		map.put("merId", UnionpayConfig.merId);			//商户号码
		map.put("accessType", UnionpayConfig.accessType);		//接入类型
		map.put("currencyCode", UnionpayConfig.currencyCode);		//交易币种
		//订单信息
		map.put("orderId", payVo.getOrderSn());	//商户订单号
		map.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));	//订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		map.put("txnAmt", String.valueOf(payVo.getTotalFee().multiply(new BigDecimal(100)).longValue()));	//交易金额，单位分，不要带小数点
		//交易通知
		map.put("frontUrl", UnionpayConfig.frontUrl);
		map.put("backUrl", UnionpayConfig.backUrl);
		
		Map<String, String> params = FormFactory.paramFilter(map);
//		String mysign = buildRequestMysign(params);
//        params.put("signature", mysign);
//		return FormFactory.buildForm(params,UnionpayConfig.frontTransUrl);
		return FormFactory.buildForm(params, SDKConfig.getConfig().getFrontRequestUrl(),"post");
	}

	/**银联侧开通卡并支付
	 * @param payVo
	 * @param accNo
	 * @return
	 */
	public static String buildOpenCard(PayVo payVo, String accNo) {
		Map<String, String> contentData = new HashMap<String, String>();
		buildBasicInfo(contentData);
		contentData.put("txnType", UnionpayConfig.txnType);	//交易类型 01-消费
		contentData.put("txnSubType", UnionpayConfig.txnSubType);	//交易子类型 01-消费
		contentData.put("bizType", "000301");	//业务类型  000301
		
		contentData.put("orderId", payVo.getOrderSn());	//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("currencyCode", UnionpayConfig.currencyCode);	//交易币种（境内商户一般是156 人民币）
		/*contentData.put("txnAmt", String.valueOf(payVo.getTotalFee().multiply(new BigDecimal(100)).longValue()));	*///交易金额，单位分，不要带小数点
		contentData.put("txnAmt", String.valueOf(payVo.getTotalFee().longValue()));
		contentData.put("accType", "01");	//账号类型
		
		String accNo1 = AcpService.encryptData(accNo, "UTF-8");	//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo1);
		contentData.put("encryptCertId",AcpService.getEncryptCertId());	//加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		
		contentData.put("frontUrl", UnionpayConfig.openCardFrontUrl);
		contentData.put("backUrl", UnionpayConfig.openCardBackUrl);
		Map<String, String> reqData = AcpService.sign(contentData,UnionpayConfig.encoding);	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();	//获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, reqData,UnionpayConfig.encoding);	//生成自动跳转的Html表单
		return html;
	}

	/**根据订单号查询token
	 * @param orderSn
	 * @return
	 */
	public static String queryTokenByOrderSn(String orderSn) {
		Map<String, String> contentData = new HashMap<String, String>();
		buildBasicInfo(contentData);
		contentData.put("txnType", "79");	//交易类型 01-消费
		contentData.put("txnSubType", "05");	//交易子类型 01-消费
		contentData.put("bizType", "000902");	//业务类型 认证支付2.0
		
		/***商户接入参数***/
		contentData.put("orderId", orderSn);	//商户订单号	
		contentData.put("tokenPayData", "{trId="+UnionpayConfig.trId+"&tokenType=01}");

		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,UnionpayConfig.encoding);	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();	//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,UnionpayConfig.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, UnionpayConfig.encoding)){
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//成功
					String tokenPayData = rspData.get("tokenPayData") ;
					String first = tokenPayData.split("&")[0];
					String token = first.substring(first.indexOf("=")+1);
					return token;
				}
			}
		}
		return null;
	}
	
	/**交易时发送短信验证码（银联侧发送）
	 * @param one
	 * @param amount 
	 * @return
	 */
	public static String sendSMS(AccNoToken one, BigDecimal amount) {
		Map<String, String> contentData = new HashMap<String, String>();
		buildBasicInfo(contentData);
		contentData.put("txnType", "77");                              //交易类型 11-代收
		contentData.put("txnSubType", "02");                           //交易子类型 02-消费短信
		contentData.put("bizType", "000902");                          //业务类型 认证支付2.0
		
		contentData.put("orderId", one.getOrderSn());             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("currencyCode", "156");						   //交易币种（境内商户一般是156 人民币）
		contentData.put("txnAmt", String.valueOf(amount.multiply(new BigDecimal(100)).longValue()));							   //交易金额，单位分，不要带小数点
		contentData.put("accType", "01");                              //账号类型
		
		contentData.put("encryptCertId", CertUtil.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		//消费短信：token号（从前台开通的后台通知中获取或者后台开通的返回报文中获取）
		contentData.put("tokenPayData", "{token="+one.getToken()+"&trId="+UnionpayConfig.trId+"}");
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,UnionpayConfig.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,UnionpayConfig.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		logger.info(rspData.entrySet().toString());
		
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, UnionpayConfig.encoding)){
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					return "ok";
				}
			}
		}
		return "fail";
	}
	
	private static void buildBasicInfo(Map<String, String> contentData){
		contentData.put("version", UnionpayConfig.version);	//版本号
		contentData.put("encoding", UnionpayConfig.encoding);	//字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", UnionpayConfig.signMethod);	//签名方法 目前只支持01-RSA方式证书加密
		contentData.put("channelType", UnionpayConfig.channelType);	//渠道类型07-PC
		
		contentData.put("merId", UnionpayConfig.merId);	//商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", UnionpayConfig.accessType);	//接入类型，商户接入固定填0，不需修改	
		contentData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));	//订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
	}

	public static String consume(PayVo payVo, String token, String smsCode) {
		Map<String, String> contentData = new HashMap<String, String>();
		buildBasicInfo(contentData);
		contentData.put("txnType", "01");		//交易类型 01-消费
		contentData.put("txnSubType", "01");		//交易子类型 01-消费
		contentData.put("bizType", "000902");		//业务类型 认证支付2.0
		/***商户接入参数***/
		contentData.put("orderId", payVo.getOrderSn());		//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("currencyCode", "156");		//交易币种（境内商户一般是156 人民币）
		contentData.put("txnAmt", String.valueOf(payVo.getTotalFee().multiply(new BigDecimal(100)).longValue()));	//交易金额，单位分，不要带小数点
		contentData.put("accType", "01");		//账号类型
		contentData.put("backUrl", UnionpayConfig.consumeBackUrl);
		contentData.put("tokenPayData", "{token="+token+"&trId="+UnionpayConfig.trId+"}");
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		customerInfoMap.put("smsCode", smsCode);			    	//短信验证码
		//customerInfoMap不送pin的话 该方法可以不送 卡号
		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,UnionpayConfig.encoding);
		contentData.put("customerInfo", customerInfoStr);
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,UnionpayConfig.encoding);	//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();	//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,UnionpayConfig.encoding);	//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		logger.info(rspData.entrySet().toString());
		/**对应答码的处理**/
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, UnionpayConfig.encoding)){
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//主动查询交易状态
					String res = queryConsumeStatus(payVo, contentData.get("txnTime"));
					if("ok".equals(res))
						return "ok";
				}
			}
		}
		return "fail";
	}

	private static String queryConsumeStatus(PayVo payVo, String txnTime) {
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", UnionpayConfig.version);                 //版本号
		data.put("encoding", UnionpayConfig.encoding);               //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", "01");                          //签名方法 目前只支持01-RSA方式证书加密
		data.put("txnType", "00");                             //交易类型 00-默认
		data.put("txnSubType", "00");                          //交易子类型  默认00
		data.put("bizType", "000902");                         //业务类型
		
		/***商户接入参数***/
		data.put("merId", UnionpayConfig.merId);                  			   //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改
		
		/***要调通交易以下字段必须修改***/
		data.put("orderId", payVo.getOrderSn());                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
		data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		
		Map<String, String> reqData = AcpService.sign(data,UnionpayConfig.encoding);           //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getSingleQueryUrl();						          //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
		
		//循环6次查询，每次时间间隔2N秒发起,即间隔1，2，4，8，16，32S查询（查询到03 04 05继续查询，否则终止查询）
		for(int t =0; t<6; t++){
			Map<String, String> rspData = AcpService.post(reqData,url,UnionpayConfig.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				if(AcpService.validate(rspData, UnionpayConfig.encoding)){
					if(("00").equals(rspData.get("respCode"))){//如果查询交易成功
						String origRespCode = rspData.get("origRespCode");
						if(("00").equals(origRespCode)){
							//交易成功，更新商户订单状态
							return "ok";
						}else if(("03").equals(origRespCode)||
								("04").equals(origRespCode)||
								("05").equals(origRespCode)){
							//订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
							continue;
						}else{
							//其他应答码为交易失败
							logger.error("["+payVo.getOrderSn()+"]交易失败: 返回报文--"+rspData.entrySet().toString());
						}
					}else if(("34").equals(rspData.get("respCode"))){
						//订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
						continue;
					}else{//查询交易本身失败，如应答码10/11检查查询报文是否正确
						//TODO
						logger.error("["+payVo.getOrderSn()+"]交易失败: 返回报文--"+rspData.entrySet().toString());
					}
				}else{
					//TODO 检查验证签名失败的原因
					logger.error("["+payVo.getOrderSn()+"]签名失败: 返回报文--"+rspData.entrySet().toString());
				}
			}else{
				//未返回正确的http状态
				logger.error("["+payVo.getOrderSn()+"]查询交易状态失败");
			}
			try {
				//等待2的t次方秒后再次查询
				Thread.sleep((int)Math.pow(2, t)*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return "fail";
	}
	

	/**解除绑定
	 * @param orderSn
	 * @param token
	 * @return
	 */
	public static String deleteAccno(String orderSn, String token) {
		Map<String, String> contentData = new HashMap<String, String>();
		buildBasicInfo(contentData);
		contentData.put("txnType", "74");                              //交易类型 74-解除标记
		contentData.put("txnSubType", "01");                           //交易子类型 01-解除标记
		contentData.put("bizType", "000902");                          //业务类型 token支付
		/***商户接入参数***/
		contentData.put("orderId", orderSn);	//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("tokenPayData", "{token="+token+"&trId="+UnionpayConfig.trId+"}");
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,UnionpayConfig.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   			//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,UnionpayConfig.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, UnionpayConfig.encoding)){
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					return "ok";
				}
			}
		}
		return "fail";
	}

}
