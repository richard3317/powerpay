package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.RiskListItemMerMapper;
import com.icpay.payment.db.dao.mybatis.mapper.RiskListItemMerMapper;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMerExample;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMerExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMerKey;
import com.icpay.payment.db.service.IRiskListItemMerService;



@Service("riskListItemMerService")
public class RiskListItemMerService extends BaseService implements IRiskListItemMerService {

	private static final Logger logger = Logger.getLogger(RiskListItemMerService.class);
	
	@Override
	public List<RiskListItemMer> select(Map<String, String> qryParamMap) {
		RiskListItemMerExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<RiskListItemMer> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询名单组配置信息开始");
		RiskListItemMerExample example = this.getQryExample(qryParamMap);
		RiskListItemMerMapper mapper = getMapper();
		Pager<RiskListItemMer> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询名单组配置信息完成");
		return pager;
	}

	@Override
	public RiskListItemMer selectByPrimaryKey(Long id) {
		return getMapper().selectByPrimaryKey(id);
	}

	/**
	 * 新增
	 */
	@Override
	public int add(RiskListItemMer riskListItemMer) {
		int r= this.getMapper().insertSelective(riskListItemMer);
		logger.info("新增名单组配置信息完成");
		return r;
	}
	
	/**
	 * 删除
	 */
	@Override
	public int delete(Long Id) {
		return getMapper().deleteByPrimaryKey(Id);
	}
	
	@Override
	protected RiskListItemMerExample buildQryExample(Map<String, String> qryParamMap) {
		RiskListItemMerExample example = new RiskListItemMerExample();
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
	
	private RiskListItemMerMapper getMapper() {
		return this.getMapper(RiskListItemMerMapper.class);
	}

	@Override
	public int update(RiskListItemMer record) {
		 return this.getMapper().updateByPrimaryKey(record);
	}
	
	/**
	 * 检查风险项目
	 * @param groupId
	 * @param channel
	 * @param mchntCd
	 * @param item
	 * @param validType 
	 * 	0=完全比对，空列表不通过，
	 * 	1=完全比对，空列表通过，
	 * 	2=完全比对，空列表不通过，ALL表示全部允许
	 * @return
	 */
	public boolean validRiskItem(String groupId, String channel, String mchntCd, String item, int validType) {
		boolean enableALL = (2==validType);
		Map<String, String> qryParamMap = new HashMap<String, String>();
		qryParamMap.put("chnlId", channel);
		qryParamMap.put("groupId", groupId);
		qryParamMap.put("mchntCd", mchntCd);
		qryParamMap.put("item", item);
		qryParamMap.put("enableALL", ""+enableALL);
		
		RiskListItemMerExample example = this.getQryExample(qryParamMap);
		long count = getMapper().countByExample(example);
		if (count>0) return true; // 可能包含item 或 ALL(当 2==validType)
		if (validType==1) {
			qryParamMap.remove("item");
			example = this.getQryExample(qryParamMap);
			count = getMapper().countByExample(example);
			return count==0;
		}
		return false;
	}
}

