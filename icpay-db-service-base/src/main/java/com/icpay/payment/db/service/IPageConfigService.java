package com.icpay.payment.db.service;

import java.util.List;

import com.icpay.payment.db.dao.mybatis.model.PageConfig;

public interface IPageConfigService {

	public List<PageConfig> selectAll();
	
}