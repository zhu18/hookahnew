package com.jusfoun.hookah.core.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台响应信息
 */
public enum CodeEnum {

	SUCCESS("0000", "交易成功"),

	// 平台错误信息
	SYSTEM_ERROR("1000", "系统异常"),
	PATTERN_ERROR("1001", "入参格式错误"), 
	PAYMENT_ORDID_MORE("1002", "重复提交"),
	HANDING("1003","交易处理中..."),
	SIGN_ERROR("1004","加签异常"),

	//业务校验类异常
	BIZ_VALIDATE_ERROR("1100", "业务校验异常"),
	BIZ_ACC_NOT_FIND("1101", "平台账户不存在"),
	BIZ_BALANCE_LOW("1102", "平台账户可用余额不足"),
	BIZ_ACCOUNT_FREEZE("1103", "平台账户已冻结"),
	BIZ_ACCOUNT_STOP_PAY("1104", "平台账户已止付"),
	BIZ_ACCOUNT_CANCEL("1105", "平台账户已注销"),
	BIZ_BIND_CARD_ERROR("1106", "银行卡绑定异常或银行卡未绑定"),
	BIZ_ACCPAYPWD_ERROR("1107", "平台支付密码错误"),
	BIZ_ACCPAYPWD_NUM("1108", "平台支付密码输入次数超限"),
	BIZ_CHANNEL_NOT_SUPPERT("1109", "该渠道暂不支持该业务"),
	BIZ_CASHTYPE_NOT_SUPPERT("1110", "账务操作类型不存在"),
	BIZ_SALERINFO_NOT_FIND("1111", "收款信息不存在"),
	BIZ_STRIKESERIAL_NOT_FIND("1112", "被冲流水不存在"),
	BIZ_STRIKEAMT_ERROR("1113", "被冲流水交易金额与冲正金额不一致"),
	BIZ_INSERTDATA_ERROR("1114", "数据添加失败"),
	BIZ_MESSAGE_ERROR("1115", "报文数据信息不存在"),
	BIZ_CASHSTATUS("1116", "内部流水信息状态不成功"),   
	BIZ_QUERY_MISMATCH("1117", "查询的数据信息不匹配"),
	BIZ_QUERY_NOTFIND("1118", "数据不存在"),
	BIZ_DATA_SIGNATYRE_FAIL("1119", "数据签名失败"),
	BIZ_DATA_CHECKSIGNATYRE_FAIL("1120", "数据验签失败"),
	BIZ_PWD_OLD_SAME_NEW("1121", "新密码与原密码相同"),
	BIZ_SINGLE_LIMIT_VOER("1122", "单笔提现金额超出限额"),
	BIZ_DAY_TOTAL_LIMIT_VOER("1123", "当天提现总金额已达到限定额度"),
	BIZ_REPORT_ERROR_RS_NULL("1124", "通讯异常，返回信息不存在"), //未使用
	BIZ_REQUSET_IPORTOKEN_ERROR("1125", "非法用户"), 
	BIZ_ORDER_EXIST("1126", "订单已存在"), 
	BIZ_BEFORE_NOT_CHACK("1127", "前一天账务未核对"), 
	BIZ_SERIAL_NOT_SAME("1128", "代收流水的标识应该和代付流水的CashSerial不相同"), 
	BIZ_AMOUNT_NOT_SAME("1129", "订单金额不一致"), 
	BIZ_PRICE_ERROR("1129", "我司售价小于商家定价(亏本啦)"),
	BIZ_SALER_ERROR("1130", "获取商家信息失败(卖家信息不能为空)"),
	BIZ_BINDBANKCARD_ERROR("1131", "已绑定银行卡"),
	BIZ_NO_PERMISSIONS("1132", "该用户无此权限"),
	BIZ_QUERY_ERROR("1200", "查询失败或未查询到记录"),
	BIZ_DEAL_FAIL("1300", "业务处理异常"),

	//第三方处理响应
	THIRD_SUCCESS("2000","第三方交易处理成功"),
	THIRD_FAIL("2001","交易失败"),
	THIRD_HANDING("2002","第三方交易处理中"),
	THIRD_CARD_BALANCE_LOW("2010","银行卡可用余额不足"),
	THIRD_PWD_ERROR("2011","银行卡密码错误"),
	THIRD_PWD_NUM("2012","银行卡密码错误次数超限，请与发卡方联系"),
	THIRD_CARDSTATUS_ERROR("2013","银行卡状态异常"),
	THIRD_NOTSUPPORT_TRADE("2014","第三方不支持的交易"),
	THIRD_SYSTEM_ERROR("2015","第三方系统异常"),
	THIRD_PAYACCER_ERROR("2016","付款方账户信息有误"),
	THIRD_COLLECTACCEE_ERROR("2017","收款方账户信息有误"),
	THIRD_GET_CHECKACCOUNT_ERROR("2018","获取对账文件有误"),
	THIRD_ACCOUNTINFO_ERROR("2019","账号和户名不一致"),

	//对账差错处理
	HAND_ERROR_SUCCESS("3000","差错处理成功"),
	HAND_ERROR_FAIL("3001","差错处理失败"),
	HAND_ERROR_BALANCE_LOW("3002","账户余额不足"),
	HAND_ERROR_STATUS("3003","原交易流水状态与对应差错不匹配"),
	HAND_ERROR_HANDING("3004","该差错已在处理中"),
	HAND_ERROR_EXC("3005","差错处理异常"),
	HAND_ERROR_TYPE("3006","未找到指定差错类型处理流程，请人工核对"),
	
	UNKNOWN_ERROR("9999", "未知错误")
	;

	private final String code;

	private final String message;

	private CodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}

	public static CodeEnum getByCode(String code) {
		for (CodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	public List<CodeEnum> getAllEnum() {
		List<CodeEnum> list = new ArrayList<CodeEnum>();
		for (CodeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (CodeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
	
	public boolean equals(CodeEnum codeEnum){
		return this.getCode().equals(codeEnum.getCode());
	}
}
