package com.icpay.payment.db.dao.mybatis.model;

public class BaseExample {

	/**
     * 分页查询时的起始记录索引
     */
    protected Integer startNum;
    
    /**
     * 分页查询时每页记录条数
     */
    protected Integer endIdx;
	
	protected Integer pageSize;
	
	public Integer getStartNum() {
		return startNum == null ? 0 : startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndIdx() {
		return endIdx == null ? Integer.MAX_VALUE : endIdx;
	}

	public void setEndIdx(Integer endIdx) {
		this.endIdx = endIdx;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
