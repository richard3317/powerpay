package com.icpay.payment.db.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.db.dao.mybatis.mapper.PageConfigMapper;
import com.icpay.payment.db.dao.mybatis.model.PageConfig;
import com.icpay.payment.db.dao.mybatis.model.PageConfigExample;
import com.icpay.payment.db.service.IPageConfigService;

@Service("pageConfigService")
public class PageConfigService extends BaseService implements IPageConfigService {

	@Override
	public List<PageConfig> selectAll() {
		PageConfigExample example = new PageConfigExample();
		example.setOrderByClause("entity_nm asc, display_idx asc");
		return this.getMapper(PageConfigMapper.class).selectByExample(example);
	}
}
