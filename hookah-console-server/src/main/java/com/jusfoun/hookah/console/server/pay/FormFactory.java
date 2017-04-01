package com.jusfoun.hookah.console.server.pay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormFactory {

	public static String buildForm(Map<String, String> params, String gateWay, String method) {
		List<String> keys = new ArrayList<String>(params.keySet());
        StringBuffer html = new StringBuffer();
        html.append("<form id=\"jsfpayform\" name=\"jsfpayform\" action=\"" + gateWay + "\" method=\""+method+"\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) params.get(name);
            html.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        //submit按钮控件请不要含有name属性
        html.append("<input type=\"submit\" value=\"确定\" style=\"display:none;\"></form>");
        html.append("<script>document.forms['jsfpayform'].submit();</script>");
        return html.toString();
	}
	
	public static Map<String, String> paramFilter(Map<String, String> map) {
		Map<String, String> result = new HashMap<String, String>();
		if (map == null || map.size() <= 0) {
			return result;
		}
		for (String key : map.keySet()) {
			String value = map.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")//alipay sign
					|| key.equalsIgnoreCase("sign_type")//alipay signType
					|| key.equalsIgnoreCase("signMsg")//99bill sign
					|| key.equalsIgnoreCase("signType")//99bill signType
					|| key.equalsIgnoreCase("signature")//unionpay sign
					|| key.equalsIgnoreCase("signMethod")) {//unionpay signType
				continue;
			}
			result.put(key, value);
		}
		return result;
	}
	
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}
}
