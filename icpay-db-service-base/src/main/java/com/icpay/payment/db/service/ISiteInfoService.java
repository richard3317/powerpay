package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.db.dao.mybatis.model.SiteInfo;

public interface ISiteInfoService {
	
	/**
	 * 根据主键查询
	 */
	public SiteInfo selectByPrimaryKey(String siteId);

	/**
	 * 获取所有信息
	 * @return
	 */
	public List<SiteInfo> getAllSiteInfo();	

}
