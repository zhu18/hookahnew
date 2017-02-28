package com.jusfoun.hookah.core.common;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8493018985714409268L;

	private List<T> list;
	
	//总条数
	private long totalItems;
	
	private int totalPage;
	
	private int pageSize;
	
	private int currentPage;
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
 
	

	/**
	 * @return the totalItems
	 */
	public long getTotalItems() {
		return totalItems;
	}

	/**
	 * @param totalItems the totalItems to set
	 */
	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		if (totalItems < 0) {
            return -1;
        }
        int pages = (int)totalItems / pageSize;
        totalPage = totalItems % pageSize > 0 ? ++pages : pages;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
