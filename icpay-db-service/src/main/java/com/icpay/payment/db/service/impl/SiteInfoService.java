package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.SiteInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.SiteInfo;
import com.icpay.payment.db.dao.mybatis.model.SiteInfoExample;
import com.icpay.payment.db.service.ISiteInfoService;

@Service("siteInfoService")
public class SiteInfoService extends BaseService implements ISiteInfoService {
	private static final Logger logger = Logger.getLogger(SiteInfoService.class);
	
	@Override
	public List<SiteInfo> getAllSiteInfo() {
		SiteInfoExample example = new SiteInfoExample();
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public SiteInfo selectByPrimaryKey(String siteId) {
		return getMapper().selectByPrimaryKey(siteId);
	}
	
	private SiteInfoMapper getMapper() {
		return this.getMapper(SiteInfoMapper.class);
	}

}
