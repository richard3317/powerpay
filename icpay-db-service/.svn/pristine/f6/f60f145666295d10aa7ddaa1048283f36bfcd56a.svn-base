package com.icpay.payment.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.RegionInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.dao.mybatis.model.RegionInfoExample;
import com.icpay.payment.db.service.IRegionInfoService;

@Service("regionInfoService")
public class RegionInfoService extends BaseService implements IRegionInfoService {

	@Override
	public List<RegionInfo> selectAll() {
		RegionInfoExample example = new RegionInfoExample();
		example.setOrderByClause("region_en_nm asc");
		return this.getMapper(RegionInfoMapper.class).selectByExample(example);
	}

}
