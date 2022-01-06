package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.DataDicMapper;
import com.icpay.payment.db.dao.mybatis.model.DataDic;
import com.icpay.payment.db.dao.mybatis.model.DataDicExample;
import com.icpay.payment.db.dao.mybatis.model.DataDicKey;
import com.icpay.payment.db.dao.mybatis.model.DataDicExample.Criteria;
import com.icpay.payment.db.service.IDataDicService;

@Service("dataDicService")
public class DataDicService extends BaseService implements IDataDicService {

	private static final Logger logger = Logger.getLogger(DataDicService.class);
	
	@Override
	public List<DataDic> select(Map<String, String> qryParamMap) {
		DataDicExample example = this.getQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}

	@Override
	public Pager<DataDic> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		logger.info("分页查询数据字典信息开始");
		DataDicExample example = this.getQryExample(qryParamMap);
		DataDicMapper mapper = getMapper();
		Pager<DataDic> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		
		logger.info("分页查询数据字典信息完成");
		return pager;
	}

	@Override
	public DataDic selectByPrimaryKey(DataDicKey key) {
		return getMapper().selectByPrimaryKey(key);
	}

	/**
	 * 新增
	 */
	@Override
	public void add(DataDic dataDic) {
		i18ObjIsNull(dataDic, this.getClass().getSimpleName(), "待新增的记录为null");
		AssertUtil.strIsBlank(dataDic.getDataCatalog(), "data catalog is blank");
		AssertUtil.strIsBlank(dataDic.getLang(), "lang is blank");
		AssertUtil.strIsBlank(dataDic.getDataTp(), "data tp is blank");
		AssertUtil.strIsBlank(dataDic.getDataKey(), "data key is blank");
		DataDicKey key = new DataDicKey();
		key.setDataCatalog(dataDic.getDataCatalog());
		key.setLang(dataDic.getLang());
		key.setDataTp(dataDic.getDataTp());
		key.setDataKey(dataDic.getDataKey());
		DataDic dbDd = this.selectByPrimaryKey(key);
		i18ObjIsNotNull(dbDd, this.getClass().getSimpleName(), "该数据字典信息已存在");

		this.getMapper().insert(dataDic);
	}
	
	/**
	 * 更新
	 */
	@Override
	public void update(DataDic dataDic) {
		i18ObjIsNull(dataDic, this.getClass().getSimpleName(), "待修改的记录为null");
	
		AssertUtil.strIsBlank(dataDic.getDataCatalog(), "data catalog is blank");
		AssertUtil.strIsBlank(dataDic.getLang(), "lang is blank");
		AssertUtil.strIsBlank(dataDic.getDataTp(), "datatp is blank");
		AssertUtil.strIsBlank(dataDic.getDataKey(), "datakey is blank");
		DataDicKey key = new DataDicKey();
		key.setDataCatalog(dataDic.getDataCatalog());
		key.setLang(dataDic.getLang());
		key.setDataTp(dataDic.getDataTp());
		key.setDataKey(dataDic.getDataKey());
		DataDic dbDd = this.selectByPrimaryKey(key);
		i18ObjIsNull(dbDd, this.getClass().getSimpleName(), "该数据字典信息不存在");

		dbDd.setDataIdx(dataDic.getDataIdx());
		dbDd.setDataVal(dataDic.getDataVal());
		dbDd.setComments(dataDic.getComments());
		
		getMapper().updateByPrimaryKey(dbDd);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(DataDicKey key) {
		
		DataDic dbDataDic = this.selectByPrimaryKey(key);
		i18ObjIsNull(dbDataDic, this.getClass().getSimpleName(), "待删除的记录不存在");
		getMapper().deleteByPrimaryKey(key);
	}
	
	/**
	 * 获取所有数据字典信息
	 * @return
	 */
	@Override
	public List<DataDic> getAllDataDic() {
		return this.select(null);
	}

	@Override
	protected DataDicExample buildQryExample(Map<String, String> qryParamMap) {
		DataDicExample example = new DataDicExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			if (qryParamMap.get("dataTp") != null) {
				String dataTp = qryParamMap.get("dataTp");
				if (!StringUtil.isBlank(dataTp)) {
					c.andDataTpEqualTo(StringUtil.trim(dataTp));
				}
			}
			if (qryParamMap.get("dataKey") != null) {
				String dataKey = qryParamMap.get("dataKey");				 
				if (!StringUtil.isBlank(dataKey)) {
					c.andDataKeyEqualTo(StringUtil.trim(dataKey));
				}
			}
			
			if (qryParamMap.get("dataVal") != null) {
				String dataVal = qryParamMap.get("dataVal");
				if (!StringUtil.isBlank(dataVal)) {
					c.andDataValEqualTo(StringUtil.trim(dataVal));
				}
			}
		}
		// 排序字段
		example.setOrderByClause("DATA_TP asc, DATA_KEY asc");
		return example;
	}
	
	private DataDicMapper getMapper() {
		return this.getMapper(DataDicMapper.class);
	}
}
