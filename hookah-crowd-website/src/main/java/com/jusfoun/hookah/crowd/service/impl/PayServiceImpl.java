package com.jusfoun.hookah.crowd.service.impl;

import com.jusfoun.hookah.core.domain.zb.ZbTrusteeRecord;
import com.jusfoun.hookah.core.generic.Condition;
import com.jusfoun.hookah.core.utils.StringUtils;
import com.jusfoun.hookah.crowd.service.PayService;
import com.jusfoun.hookah.crowd.service.ZbTrusteeRecordService;
import com.jusfoun.hookah.crowd.util.AlipayConfig;
import com.jusfoun.hookah.crowd.util.AlipayNotify;
import com.jusfoun.hookah.crowd.util.FormFactory;
import com.jusfoun.hookah.crowd.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PayServiceImpl implements PayService {

    protected final static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Resource
    private ZbTrusteeRecordService zbTrusteeRecordService;

    @Override
    public String toPayByZFB(String requirementSn, String tradeSn, Long amount, String flagNum, String notify_url, String return_url) {
        return buildRequestParams(requirementSn, tradeSn, amount, flagNum, notify_url, return_url);
    }

    private String buildRequestParams(String requirementSn, String tradeSn, Long amount, String flagNum, String notify_url, String return_url) {
        Map<String, String> map = new HashMap<String, String>();
        //基本信息
        map.put("service", AlipayConfig.service);
        map.put("partner", AlipayConfig.partner);
        map.put("seller_id", AlipayConfig.seller_id);
        map.put("_input_charset", AlipayConfig.input_charset);
        map.put("payment_type", AlipayConfig.payment_type);
        map.put("notify_url", notify_url);
        map.put("return_url", return_url);
        map.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        map.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        //订单信息
        map.put("out_trade_no", tradeSn);//订单号
        map.put("subject", tradeSn);//String(256),商品名称/商品的标题/交易标题/订单标题/订单关键字等
        map.put("total_fee", String.valueOf((float)amount / 100));//该笔订单的资金总额,单位为RMB-Yuan。精确到小数点后两位。
        map.put("body", flagNum);//String(1000),对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        map.put("extra_common_param", requirementSn);//用户在系统中的账号（手机号或者邮箱）

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

    @Override
    public ModelAndView handleZFBRs(HttpServletRequest request) {

        logger.info("众包项目，支付宝同步回调处理中……");

        ModelAndView modelAndView = new ModelAndView();

        String tradeSn = "";

        try {

            //商户订单号
            tradeSn = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

            Map<String,String> param = getRequestParams(request);

            boolean flag = aliPayHandle(tradeSn, tradeStatus, param);
            if (flag){
                modelAndView.addObject("money", total_fee);
                modelAndView.setViewName("pay/success");
            }else {
                modelAndView.addObject("orderSn", tradeSn);
                modelAndView.setViewName("pay/fail");
            }

            logger.info("众包项目，支付宝同步回调处理中……");

            return modelAndView;

        }catch(Exception e){
           logger.error("众包项目，支付宝同步回调{}", e);
        }

        return modelAndView;
    }

    private boolean aliPayHandle(String tradeSn, String tradeStatus, Map<String, String> param) {

        if (AlipayNotify.verify(param)){
            if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){

                if(!StringUtils.isNotBlank(tradeSn)){
                    return false;
                }
                List<Condition> filters = new ArrayList<>();
                filters.add(Condition.eq("serialNo", tradeSn));
                ZbTrusteeRecord zbTrusteeRecord = zbTrusteeRecordService.selectOne(filters);
                if(zbTrusteeRecord == null){
                    return false;
                }
//                if(!zbTrusteeRecord.getStatus().equals(Short.parseShort("0"))){
//                    return false;
//                }

                zbTrusteeRecord.setStatus(Short.parseShort("1"));
                zbTrusteeRecordService.updateById(zbTrusteeRecord);

                logger.info("支付宝支付成功"+orderSn);
                return true;
            }else{

                return false;
            }
        }else{
            logger.error("支付宝回调验证失败"+orderSn);
            return false;
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
}
