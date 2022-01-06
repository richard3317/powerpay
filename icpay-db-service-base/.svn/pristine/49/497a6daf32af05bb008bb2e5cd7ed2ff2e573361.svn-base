package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;

public interface IBmRoleService {

	/**
	 * 分页查询
	 * @param pager
	 * @param qryParamMap
	 */
	public Pager<BmRoleInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 * @param roleId
	 * @return
	 */
	public BmRoleInfo selectByRoleId(int roleId);
	
	/**
	 * 新增角色信息
	 * @param roleInfo
	 */
	public void add(BmRoleInfo roleInfo);
	
	/**
	 * 修改角色信息
	 * @param roleInfo
	 */
	public void update(BmRoleInfo roleInfo);
	
	/**
	 * 删除角色信息
	 * @param funcCd
	 * @return
	 */
	public void delete(int roleId);
	
	/**
	 * 根据RoleId获取角色功能权限
	 * @param roleId
	 * @return
	 */
	public Set<String> selectRoleFunc(int roleId);
	
	/**
	 * 保存角色权限
	 * @param roleId
	 * @param funcSet
	 */
	public void saveRoleFunc(int roleId, Set<String> funcSet);
	
	/**
	 * 获取所有的角色信息
	 * @return
	 */
	public List<BmRoleInfo> getAllRoleInfo();
	
	public void insertRoleFunc(int roleId, Set<String> funcSet);
	
	public List<BmRoleInfo> queryRoleId(String roleNm);
	
}