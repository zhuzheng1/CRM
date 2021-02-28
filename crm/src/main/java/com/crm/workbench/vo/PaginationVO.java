package com.crm.workbench.vo;

import java.util.List;

public class PaginationVO<T> {

	private List<T> dataList;
	private Integer total;
	
	
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "PaginationVO [dataList=" + dataList + ", total=" + total + "]";
	}
	
	
}
