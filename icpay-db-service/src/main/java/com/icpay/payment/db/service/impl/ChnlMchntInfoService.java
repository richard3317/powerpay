package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import com.icpay.payment.db.service.IChnlMchntInfoService;

@Service("chnlMchntInfoService")
public class ChnlMchntInfoService extends BaseService implements IChnlMchntInfoService {
	private static final Logger logger = Logger.getLogger(ChnlMchntInfoService.class);
	@Override
	public Pager<ChnlMchntInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlMchntInfoExample example = this.getQryExample(qryParamMap);
		example.setOrderByClause("rec_upd_ts desc");
		ChnlMchntInfoMapper mapper = getMapper();
		Pager<ChnlMchntInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	/**
	 * 获取全部通道信息
	 * @return
	 */
	@Override
	public List<ChnlMchntInfo> getAllChnlInfo() {
		ChnlMchntInfoExample example = new ChnlMchntInfoExample();
		//example.setOrderByClause("chnl_id asc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public ChnlMchntInfo selectByPrimaryKey(ChnlMchntInfoKey key) {
		return getMapper().selectByPrimaryKey(key);
	}
	
	/**
	 * 新增
	 */
	public void add(ChnlMchntInfo record) {
		getMapper().insert(record);
	}
	
	/**
	 * 修改
	 */
	public void update(ChnlMchntInfo record) {
		
		getMapper().updateByPrimaryKey(record);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(ChnlMchntInfoKey key) {
		getMapper().deleteByPrimaryKey(key);
	}
	
	private ChnlMchntInfoMapper getMapper() {
		return this.getMapper(ChnlMchntInfoMapper.class);
	}

	@Override
	protected ChnlMchntInfoExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlMchntInfoExample example = new ChnlMchntInfoExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			// 查询条件: 商户号
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			
			// 查询条件：渠道
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件：对账结果
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// 查询条件：商户名称
			String chnlMchntDesc = StringUtil.trim(qryParamMap.get("chnlMchntDesc"));
			if (!StringUtil.isBlank(chnlMchntDesc)) {
				c.andChnlMchntDescLike("%" + chnlMchntDesc + "%");
			}
			
//			// 查询条件：清算周期
//			String settlePeriod = StringUtil.trim(qryParamMap.get("settlePeriod"));
//			if (!StringUtil.isBlank(settlePeriod)) {
//				c.andSettlePeriodEqualTo(settlePeriod);
//			}
			
			// 查询关联条件: 商户号
			if(qryParamMap.containsKey("chnlMchntCdAssoc")) {
				String chnlMchntCdAssoc = StringUtil.trim(qryParamMap.get("chnlMchntCdAssoc"));
				if (!StringUtil.isBlank(chnlMchntCdAssoc)) {
					//String[] mchnts =  chnlMchntCdAssoc.split(";");
					//List<String> chnmmnList = Arrays.asList(mchnts);
					//c.andChnlMchntCdIn(chnmmnList);
					c.andChnlMchntCdLike(chnlMchntCdAssoc +"%");
				}
			}
		}
		return example;
	}
}
