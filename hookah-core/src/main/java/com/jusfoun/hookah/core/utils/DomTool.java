package com.jusfoun.hookah.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;



public class DomTool {
	static final SAXReader reader = new SAXReader();
	public static boolean assertStr(Document dom, String xpath, String value){
		if(dom==null || xpath==null || value==null)
			return false;
		Element node = (Element) dom.selectSingleNode(xpath);
		if(!value.equals(node.getStringValue()))
			return false;
		return true;
	}
	
	public static Document parseDom(String source){
		if(StringUtils.isEmpty(source))
			return null;
		try {
			return DocumentHelper.parseText(source);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List selectNodes(Document dom, String xpath){
		if(dom==null || xpath==null)
			return null;
		return dom.selectNodes(xpath);
	}
	public static void main(String[] args) throws Exception {
		Document dom = reader.read(new File("D:\\01Work\\shop\\alipay\\account2.xml"));
//		System.out.println(assertConten(dom, "/alipay/is_success", "T"));
		List list = DomTool.selectNodes(dom, "/alipay/response/account_page_query_result/account_log_list/AccountQueryAccountLogVO");
		for(int t=0;t<list.size();t++){
			Element node = (Element) list.get(t);
			Element orderSn = node.element("merchant_out_order_no");
			Element income = node.element("income");
			System.out.println(orderSn.getTextTrim()+",\t"+income.getTextTrim());
		}
	}
}
