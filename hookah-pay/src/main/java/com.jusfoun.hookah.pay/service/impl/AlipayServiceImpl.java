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
import com.jusfoun.hookah.core.domain.PayCore;
import com.jusfoun.hookah.core.domain.PayTradeRecord;
import com.jusfoun.hookah.core.domain.bo.MoneyInOutBo;
import com.jusfoun.hookah.core.domain.vo.OrderInfoVo;
import com.jusfoun.hookah.core.exception.HookahException;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.generic.GenericServiceImpl;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.pay.util.*;
import com.jusfoun.hookah.rpc.api.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class AlipayServiceImpl extends GenericServiceImpl<PayAccountRecord, String> implements
        AlipayService {

    @Resource
    private MgOrderInfoService mgOrderInfoService;

    @Resource
    private PayAccountRecordMapper payAccountRecordMapper;

    @Resource
    public void setDao(PayAccountRecordMapper payAccountRecordMapper) {
        super.setDao(payAccountRecordMapper);
    }

    @Override
    public String doPay(String userId, String orderId, String notify_url, String return_url) {
        if (!StringUtils.isNotBlank(userId) || !StringUtils.isNotBlank(orderId))
            return null;
        //根据orderId查询orderInfo
        OrderInfoVo orderInfoVo = mgOrderInfoService.selectById(orderId);
        if (!orderInfoVo.getOrderStatus().equals(PayCore.PayStatus.unpay))
            return null;
        //构造html
        String html = buildRequestParams(userId, orderInfoVo, notify_url, return_url);
        //记账
        PayAccountRecord payAccountRecord = new PayAccountRecord();
        payAccountRecord.setPayAccountId(Long.valueOf(userId));
        payAccountRecord.setUserId(userId);
        Date date = new Date();
        payAccountRecord.setTransferDate(date);
        payAccountRecord.setMoney(orderInfoVo.getOrderAmount());//订单资金总额
        payAccountRecord.setSerialNumber(orderInfoVo.getOrderSn());//订单号
        payAccountRecord.setAddTime(date);
        payAccountRecord.setAddOperator(userId);
        insertRecord(payAccountRecord);
        return html;
    }

    @Override
    public boolean insertRecord(PayAccountRecord record) {
        int count = payAccountRecordMapper.insert(record);
        return count > 0;
    }

    @Override
    public boolean updateRecordStatus(PayAccountRecord record) {
        int count = payAccountRecordMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }

    private String buildRequestParams(String userId, OrderInfoVo payVo, String notify_url, String return_url) {
        Map<String, String> map = new HashMap<String, String>();
        //基本信息
        map.put("service", AlipayConfig.service);
        map.put("partner", AlipayConfig.partner);
        map.put("seller_id", AlipayConfig.seller_id);
        map.put("_input_charset", AlipayConfig.input_charset);
        map.put("payment_type", AlipayConfig.payment_type);
        map.put("notify_url", PayConfiguration.ALIPAY_NOTIFY_URL);
        map.put("return_url", PayConfiguration.ALIPAY_RETURN_URL);
        map.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        map.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        //订单信息
        map.put("out_trade_no", payVo.getOrderSn());//订单号
        map.put("subject", payVo.getOrderSn());//String(256),商品名称/商品的标题/交易标题/订单标题/订单关键字等
        map.put("total_fee", String.valueOf(payVo.getOrderAmount() / 100));//该笔订单的资金总额,单位为RMB-Yuan。精确到小数点后两位。
        map.put("body", payVo.getOrderSn());//String(1000),对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        map.put("extra_common_param", payVo.getAccount());//用户在系统中的账号（手机号或者邮箱）

        Map<String, String> params = FormFactory.paramFilter(map);
        String mysign = buildRequestMysign(params);
        params.put("sign", mysign);
        params.put("sign_type", AlipayConfig.sign_type);
        return FormFactory.buildForm(params, AlipayConfig.alipayGateway
                + "_input_charset=" + AlipayConfig.input_charset, "get");
    }

    private static String buildRequestMysign(Map<String, String> params) {
        String prestr = FormFactory.createLinkString(params); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if (AlipayConfig.sign_type.equals("MD5")) {
            mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return mysign;
    }

    public boolean verifyAlipay(Map<String, String> param) {
        return AlipayNotify.verify(param);
    }
}
