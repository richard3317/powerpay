package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RiskListMassItemMapper;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItem;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItemExample;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItemExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.RiskListMassItemKey;
import com.icpay.payment.db.service.IRiskListMassItemService;

@Service("riskListMassItemService")
public class RiskListMassItemServiceImpl extends BaseService implements IRiskListMassItemService {
	
	@Override
	protected RiskListMassItemExample buildQryExample(Map<String, String> qryParamMap) {
		RiskListMassItemExample example = new RiskListMassItemExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件:名单组ID
			String groupId = StringUtil.trim(qryParamMap.get("groupId"));
			if (!StringUtil.isBlank(groupId)) {
				c.andGroupIdEqualTo(Integer.valueOf(groupId));
			}
			// 查询条件:渠道id
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件:商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			// 查询条件:名单项
			String item = StringUtil.trim(qryParamMap.get("item")); //用来检验风险项目
			String itemFilter = StringUtil.trim(qryParamMap.get("itemFilter")); //用来维护管理风险项目
			if (!StringUtil.isBlank(item)) {
				String enableALL=StringUtil.trim(qryParamMap.get("enableALL"));
				if ("false".equalsIgnoreCase(enableALL)) {
					c.andItemEqualTo(item);
				}
				else {
					List<String> items=new ArrayList<>();
					items.add("ALL");
					items.add(item);
					c.andItemIn(items);
				}
			}
			else if (!StringUtil.isBlank(itemFilter)) {
				c.andItemLike("%"+itemFilter+"%");
			}
		}
		// 排序字段
		String orderBy=StringUtil.trim(qryParamMap.get("orderBy")); 
		if (StringUtil.isBlank(orderBy))
			orderBy="rec_upd_ts desc";
		example.setOrderByClause(orderBy);
		
		return example;
	}	

	private RiskListMassItemMapper getMapper() {
		return this.getMapper(RiskListMassItemMapper.class);
	}
	
	@Override
	public RiskListMassItem selectByPrimaryKey(RiskListMassItemKey key) {
		return getMapper().selectByPrimaryKey(key);
	}

	@Override
	public int add(RiskListMassItem record) {
		return getMapper().insertSelective(record);
	}

	@Override
	public int update(RiskListMassItem record) {
		return getMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(RiskListMassItemKey key) {
		return getMapper().deleteByPrimaryKey(key);
	}

	@Override
	public List<RiskListMassItem> select(Map<String, String> qryParamMap) {
		RiskListMassItemExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<RiskListMassItem> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		info("分页查询名单组配置信息开始");
		RiskListMassItemExample example = this.getQryExample(qryParamMap);
		RiskListMassItemMapper mapper = getMapper();
		Pager<RiskListMassItem> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		info("分页查询名单组配置信息完成");
		return pager;

	}

	@Override
	public boolean validRiskItem(String groupId, String channel, String mchntCd, String item, int validType) {
		boolean enableALL = (2==validType);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("chnlId", channel);
		qryParamMap.put("groupId", groupId);
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("item", item);
		qryParamMap.put("enableALL", ""+enableALL);
		
		RiskListMassItemExample example = this.getQryExample(qryParamMap);
		long count = getMapper().countByExample(example);
		if (count>0) return true; // 可能包含item 或 ALL(当 2==validType)
		if (validType==1) {
			qryParamMap.remove("item");
			example = this.getQryExample(qryParamMap);
			count = getMapper().countByExample(example);
			return count==0L;
		}
		return false;
	}

}
