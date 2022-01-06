package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.DataDic;
import com.icpay.payment.db.dao.mybatis.model.DataDicKey;

public interface IDataDicService {

public List<DataDic> select(Map<String, String> qryParamMap);
	
	/**
	 * 分页查询数据字典信息
	 * @param pager
	 * @param qryParamMap
	 */
	public Pager<DataDic> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	/**
	 * 根据主键获取数据字典对象
	 * @param dataTp
	 * @param dataKey
	 * @return
	 */
	public DataDic selectByPrimaryKey(DataDicKey key);

	/**
	 * 新增数据字典对象
	 * @param dataDic
	 * @return
	 */
	public void add(DataDic dataDic);
	
	/**
	 * 更新数据字典对象
	 * @param dataDic
	 * @return
	 */
	public void update(DataDic dataDic);
	
	/**
	 * 根据主键删除数据字典信息
	 * @param dataDicKey
	 * @return
	 */
	public void delete(DataDicKey key);
	
	/**
	 * 获取所有的MIM数据字典信息
	 * @return
	 */
	public List<DataDic> getAllDataDic();
}
