package com.jusfoun.hookah.core.utils;

import java.util.HashMap;
import java.util.Map;

public class PageMap {
	private Map<String, Object> res = new HashMap<String, Object>();
	
	public PageMap(Object object,int totalCount,int totalPages,String currentPage){
		res.put("dataList", object);
		res.put("totalCount", totalCount);//总记录数
		res.put("totalPages", totalPages);//总页数
		res.put("currentPage", currentPage);//总页数
	}
	
	public PageMap(Object object){
		res.put("dataList", object);
	}
	
	public Map<String, Object> getMap(){
		return res;
	}
}
