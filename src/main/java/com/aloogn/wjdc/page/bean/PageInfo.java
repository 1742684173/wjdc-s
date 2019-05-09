package com.aloogn.wjdc.page.bean;

import java.util.List;

public class PageInfo<T> {

	private long currentPage;
	private long pageSize;
	private long totalPage;//总页数
	private long totalCount;//总记录数
	private List<T> list;
	
	public PageInfo() {
		
	}
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	
	public long getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
