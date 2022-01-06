package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RiskListItemMer;

public interface IRiskListItemMerService {
	
	
	/**
	 * 根据主键查询
	 */
	public RiskListItemMer selectByPrimaryKey(Long id);
	
	/**
	 * 修改
	 */
	public int update(RiskListItemMer riskListItemMer);
	
	/**
	 * 删除	
	 */
	public int delete(Long Id);
	
	
	public List<RiskListItemMer> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询名单组配置信息
	 */
	public Pager<RiskListItemMer> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	

	/**
	 * 新增名单组配置信息
	 * @return 
	 */
	public int add(RiskListItemMer riskListItemMer);
	
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
	public boolean validRiskItem(String groupId, String channel, String mchntCd, String item, int validType);
	
}
