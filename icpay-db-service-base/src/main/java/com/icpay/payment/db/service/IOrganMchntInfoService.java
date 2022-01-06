package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.OrganInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.OrganMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.ViewOrganMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.OrganMchntExtInfo;

public interface IOrganMchntInfoService {
	
	public Pager<ViewOrganMchntInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键查询
	 */
	public OrganMchntInfo selectByPrimaryKey(OrganMchntInfoKey key);
	
	public OrganInfo selectOrganInfoByOrganId(String organId);
	
	public OrganInfo selectOrganInfoByOrganDesc(String organDesc);
	
	/**
	 * 新增
	 */
	public void add(OrganMchntExtInfo info);
	
	/**
	 * 修改
	 */
	public void update(OrganMchntExtInfo info);
	
	/**
	 * 删除
	 */
	public void delete(OrganMchntExtInfo info);
	
	/**
	 * 根据organId 查询organ配置
	 * @param organId
	 * @return
	 */
	public List<OrganInfo> selectByOrganId(String organId);
	
	/**
	 * 查询所有机构信息
	 */
	public List<OrganInfo> select ();
	
	/**
	 * 根据organId ，查询所有的mchnt配置
	 * @param organId
	 * @return
	 */
	public List<OrganMchntInfo> selectMchntByOrganId(String organId);
	
	/**
	 * 根据mchntCd ，查询mchnt配置
	 * @param organId
	 * @return
	 */
	public OrganMchntInfo selectMchntByMchnt(String mchntCd);
	
	/**
	 *  修改机构商户信息
	 * @param info
	 */
	public void updateOrganMchnt(OrganMchntInfo info , Map<String,String> qryParamMap);
}
