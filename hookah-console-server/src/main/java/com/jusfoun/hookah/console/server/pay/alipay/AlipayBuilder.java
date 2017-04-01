package com.jusfoun.hookah.console.server.pay.alipay;

import java.util.HashMap;
import java.util.Map;

import com.jusfoun.hookah.console.server.pay.FormFactory;
import com.jusfoun.hookah.console.server.pay.alipay.httpclient.HttpProtocolHandler;
import com.jusfoun.hookah.console.server.pay.alipay.httpclient.HttpRequest;
import com.jusfoun.hookah.console.server.pay.alipay.httpclient.HttpResponse;
import com.jusfoun.hookah.console.server.pay.alipay.httpclient.HttpResultType;
import com.jusfoun.hookah.core.domain.vo.PayVo;
import org.apache.commons.httpclient.NameValuePair;


public class AlipayBuilder {

    public static String build(PayVo payVo) {
        Map<String, String> map = new HashMap<String, String>();
        //基本信息
        map.put("service", AlipayConfig.service);
        map.put("partner", AlipayConfig.partner);
        map.put("seller_id", AlipayConfig.seller_id);
        map.put("_input_charset", AlipayConfig.input_charset);
        map.put("payment_type", AlipayConfig.payment_type);
        map.put("notify_url", AlipayConfig.notify_url);
        map.put("return_url", AlipayConfig.return_url);
        map.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        map.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        //订单信息
        map.put("out_trade_no", payVo.getOrderSn());//订单号
        map.put("subject", payVo.getOrderTitle());//String(256),商品名称/商品的标题/交易标题/订单标题/订单关键字等
        map.put("total_fee", payVo.getTotalFee().toString());//该笔订单的资金总额,单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
        map.put("body", payVo.getOrderInfo());//String(1000),对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。

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
        if(AlipayConfig.sign_type.equals("MD5") ) {
            mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        return mysign;
    }

    public static String buildRequest(String strParaFileName, String strFilePath,Map<String, String> sParaTemp) throws Exception {
        //待请求参数数组
        Map<String, String> sPara = FormFactory.paramFilter(sParaTemp);
        String mysign = buildRequestMysign(sPara);
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(AlipayConfig.input_charset);
        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(AlipayConfig.alipayGateway+"_input_charset="+AlipayConfig.input_charset);
        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
        if (response == null) {
            return null;
        }
        String strResult = response.getStringResult();
        return strResult;
    }

    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }
}
