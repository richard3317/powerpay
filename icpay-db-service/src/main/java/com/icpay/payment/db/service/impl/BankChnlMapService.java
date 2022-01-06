package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BankChnlMapMapper;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMap;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapExample;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapKey;
import com.icpay.payment.db.service.IBankChnlMapService;

@Service("bankChnlMapService")
public class BankChnlMapService extends BaseService implements IBankChnlMapService {
	
	@Override
	public int batchUpdate(Map<String, String> qryParamMap, BankChnlMap bankChnlMap) {
		BankChnlMapExample example = this.getQryExample(qryParamMap);
		BankChnlMapMapper mapper = getMapper();
		//List<TxnRouting>  list = mapper.selectByExample(example);
		//if (list==null) return 0;
		//if (list.size()==0) return 0;
		return mapper.updateByExampleSelective(bankChnlMap, example);
	}
	
	@Override
	protected BankChnlMapExample buildQryExample(Map<String, String> qryParamMap) {
		BankChnlMapExample example = new BankChnlMapExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			
			String intTransCd = qryParamMap.get("intTransCd");
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			
			String bankNum = qryParamMap.get("bankNum");
			if (!StringUtil.isBlank(bankNum)) {
				c.andBankNumEqualTo(bankNum);
			}
			
			String chnlId = qryParamMap.get("chnlId");
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			
			String chnlBankNum = qryParamMap.get("chnlBankNum");
			if (!StringUtil.isBlank(chnlBankNum)) {
				c.andChnlBankNumEqualTo(chnlBankNum);
			}
			
			String chnlBankName = qryParamMap.get("chnlBankName");
			if (!StringUtil.isBlank(chnlBankName)) {
				c.andChnlBankNameLike("%" + chnlBankName + "%");
			}
			
			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}
			
			// 查询条件：幣別
			String currCd = qryParamMap.get("currCd");
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}
			
		}
		// 排序字段
		example.setOrderByClause("int_trans_cd, chnl_id, sort_seq");
		return example;
	}	

	@Override
	public Pager<BankChnlMap> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		BankChnlMapExample example = this.getQryExample(qryParamMap);
		BankChnlMapMapper mapper = getMapper();
		Pager<BankChnlMap> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public List<BankChnlMap> select(Map<String, String> qryParamMap) {
		BankChnlMapExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public BankChnlMap selectByPrimaryKey(BankChnlMapKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public long count(Map<String, String> qryParamMap) {
		BankChnlMapExample example = this.getQryExample(qryParamMap);
		return this.getMapper().countByExample(example);
	}

	@Override
	public int add(BankChnlMap record) {
		return this.getMapper().insertSelective(record);
	}

	@Override
	public int update(BankChnlMap record) {
		return this.getMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(BankChnlMapKey key) {
		return this.getMapper().deleteByPrimaryKey(key);
	}
	
	protected BankChnlMapMapper getMapper() {
		return super.getMapper(BankChnlMapMapper.class);
	}
	


}
