package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AgentFuncInfo;

public interface IAgentFuncInfoService {
	
public List<AgentFuncInfo> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询
	 * @param pager
	 * @param qryParamMap
	 */
	public Pager<AgentFuncInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 * @param funcCd
	 * @return
	 */
	public AgentFuncInfo selectByPrimaryKey(String funcCd);
	
	/**
	 * 新增
	 * @param funcInfo
	 * @return
	 */
	public void add(AgentFuncInfo funcInfo);
	
	/**
	 * 修改
	 * @param funcInfo
	 * @return
	 */
	public void update(AgentFuncInfo funcInfo);
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(String funcCd);
	
	/**
	 * 获取所有信息
	 * @return
	 */
	public List<AgentFuncInfo> getAllAgentFuncInfo();	
}
