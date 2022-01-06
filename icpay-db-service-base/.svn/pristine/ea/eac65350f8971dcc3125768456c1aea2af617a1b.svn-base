package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;

public interface IBmUserService {

	/**
	 * 分页查询
	 * @param pager
	 * @param qryParamMap
	 */
	public Pager<BmUserInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 * @param usrId
	 * @return
	 */
	public BmUserInfo selectByPrimaryKey(String usrId);
	
	/**
	 * 新增
	 * @param usrInfo
	 */
	public void add(BmUserInfo usrInfo);
	
	/**
	 * 修改用户信息
	 * @param usrInfo
	 * @param isUpdatePwd 是否修改密码
	 */
	public void update(BmUserInfo usrInfo, boolean isUpdatePwd);
	
	/**
	 * 修改用户登录
	 * @param usrInfo
	 * @param isUpdatePwd 是否修改密码
	 */
	public void updateLoginInfo(BmUserInfo usrInfo);
	
	/**
	 * 删除
	 * @param usrId
	 * @return
	 */
	public void delete(String usrId);
}
