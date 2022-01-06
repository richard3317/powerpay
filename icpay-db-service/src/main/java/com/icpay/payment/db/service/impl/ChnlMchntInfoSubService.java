package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntInfoSubMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.ChnlMchntInfoSubExtMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.CustomExtMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMapping;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMappingExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoMappingKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSub;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoSubKey;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IChnlMchntInfoSubService;

@Service("chnlMchntInfoSubService")
public class ChnlMchntInfoSubService extends BaseService implements IChnlMchntInfoSubService {
	@Override
	public Pager<ChnlMchntInfoSub> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlMchntInfoSubExample example = new ChnlMchntInfoSubExample();
		example.setOrderByClause("rec_upd_ts desc");
		ChnlMchntInfoSubMapper mapper = getMapper();
		Pager<ChnlMchntInfoSub> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	
	/**
	 * 两表联查
	 */
	@Override
	public Pager<ChnlInfoMapping> selectChnlInfoMappingByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlInfoMappingExample example = new ChnlInfoMappingExample();
		example.setOrderByClause("rec_upd_ts desc");
		ChnlMchntInfoSubExtMapper mapper = this.getMapper(ChnlMchntInfoSubExtMapper.class);;
		Pager<ChnlInfoMapping> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExampleByMapping(example));
		pager.setResultList(mapper.selectByPageByMapping(example));
		return pager;
	}
	/**
	 * 获取全部通道信息
	 * @return
	 */
	@Override
	public List<ChnlMchntInfoSub> getAllChnlInfo() {
		ChnlMchntInfoSubExample example = new ChnlMchntInfoSubExample();
		example.setOrderByClause("chnl_id asc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public ChnlMchntInfoSub selectByPrimaryKey(ChnlMchntInfoSubKey key) {
		return getMapper().selectByPrimaryKey(key);
	}
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntInfoSub record) {
		getMapper().insert(record);
	}
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntInfoSub record) {
		
		getMapper().updateByPrimaryKey(record);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(ChnlMchntInfoSubKey key) {
		
		getMapper().deleteByPrimaryKey(key);
	}
	
	private ChnlMchntInfoSubMapper getMapper() {
		return this.getMapper(ChnlMchntInfoSubMapper.class);
	}



	
	public ChnlInfoMapping selectMciAndMcisByPrimarykey(ChnlInfoMappingKey key) {
		
		return this.getMapper(CustomExtMapper.class).selectMciAndMcisByPrimarykey(key);
	}


	@Override
	public List<ChnlMchntInfoSub> queryAllNoIntTransCd(ChnlMchntInfo key) {
		return this.getMapper(CustomExtMapper.class).queryAllNoIntTransCd(key);
	}


	@Override
	public List<ChnlMchntInfoSub> select( String chnlId, String chnlMchntCd) {
		AssertUtil.strIsBlank(chnlId, "chnlId is blank.");
		ChnlMchntInfoSubExample example = new ChnlMchntInfoSubExample();
		Criteria c = example.createCriteria();
		c.andChnlIdEqualTo(chnlId);
		c.andChnlMchntCdEqualTo(chnlMchntCd);
		return this.getMapper().selectByExample(example);
	}




}
