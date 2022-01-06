package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.SysSettleProfitBankMapper;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBankExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBank;
import com.icpay.payment.db.dao.mybatis.model.SysSettleProfitBankExample;
import com.icpay.payment.db.service.ISysSettleProfitBankService;

@Service("sysSettleProfitBank")
public class SysSettleProfitBankService extends BaseService implements ISysSettleProfitBankService {

	@Override
	public int deleteByPrimaryKey(String accountNo) {
		return dao().deleteByPrimaryKey(accountNo);
	}
	
	@Override
	public int insert(SysSettleProfitBank record) {
		return dao().insert(record);
	}

	@Override
	public int insertSelective(SysSettleProfitBank record) {
		return dao().insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(SysSettleProfitBank record) {
		return dao().updateByPrimaryKeySelective(record);
	}

	@Override
	public List<SysSettleProfitBank> selectByExample(Map<String,String> qryParamMap) {
		SysSettleProfitBankExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public Pager<SysSettleProfitBank> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap) {
		SysSettleProfitBankExample example = this.getQryExample(qryParamMap);
		Pager<SysSettleProfitBank> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(dao().countByExample(example));
		pager.setResultList(dao().selectByPage(example));
		return pager;
	}

	@Override
	public SysSettleProfitBank selectByPrimaryKey(String accountNo) {
		return dao().selectByPrimaryKey(accountNo);
	}

	private SysSettleProfitBankMapper dao() {
		return this.getMapper(SysSettleProfitBankMapper.class);
	}	
	
	@Override
	protected SysSettleProfitBankExample buildQryExample(Map<String, String> qryParamMap) {
		SysSettleProfitBankExample example = new SysSettleProfitBankExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:银行帐号
			String accountNo = StringUtil.trim(qryParamMap.get("accountNo"));
			if (!StringUtil.isBlank(accountNo)) {
				c.andAccountNoLike("%" + accountNo + "%");//模糊查询
			}
			// 查询条件:帐号户名
			String accountName = StringUtil.trim(qryParamMap.get("accountName"));
			if (!StringUtil.isBlank(accountName)) {
				c.andAccountNameEqualTo(accountName);
			}
			// 查询条件:银行联行号
			String accountBankCode = StringUtil.trim(qryParamMap.get("accountBankCode"));
			if (!StringUtil.isBlank(accountBankCode)) {
				c.andAccountBankCodeEqualTo(accountBankCode);
			}
			// 查询条件:银行名称
			String accountBankName = StringUtil.trim(qryParamMap.get("accountBankName"));
			if (!StringUtil.isBlank(accountBankName)) {
				c.andAccountBankNameEqualTo(accountBankName);
			}
			// 查询条件:状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

}
