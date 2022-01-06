package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RoutInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.RoutInfo;
import com.icpay.payment.db.dao.mybatis.model.RoutInfoExample;
import com.icpay.payment.db.dao.mybatis.model.RoutInfoKey;
import com.icpay.payment.db.dao.mybatis.model.RoutInfoExample.Criteria;
import com.icpay.payment.db.service.IRoutInfoService;

@Service("routInfoService")
public class RoutInfoService extends BaseService implements IRoutInfoService {
	
	@Override
	public Pager<RoutInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		RoutInfoExample example = this.getQryExample(qryParamMap);
		RoutInfoMapper mapper = getMapper();
		Pager<RoutInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	@Override
	public RoutInfo selectByPrimaryKey(RoutInfoKey key) {
		validateKey(key);
		return getMapper().selectByPrimaryKey(key);
	}
	
	/**
	 * 新增
	 */
	public void add(RoutInfo routInfo) {
		AssertUtil.argIsNull(routInfo, "routInfo is null.");
		
		validateKey(routInfo);
		
		Date now = new Date();
		routInfo.setRecCrtTs(now);
		routInfo.setRecUpdTs(now);
		this.getMapper().insert(routInfo);
	}
	
	/**
	 * 修改
	 */
	public void update(RoutInfo routInfo) {
		AssertUtil.objIsNull(routInfo, "routInfo is null.");
		RoutInfoMapper mapper = this.getMapper();
		RoutInfo dbEntity = mapper.selectByPrimaryKey(routInfo);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "待更新的路由信息不存在");

		// 更新数据库字段
		dbEntity.setChnlIds(routInfo.getChnlIds());
		dbEntity.setLastOperId(routInfo.getLastOperId());
		dbEntity.setRecUpdTs(new Date());
		mapper.updateByPrimaryKey(dbEntity);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(RoutInfoKey key) {
		RoutInfoMapper mapper = this.getMapper();
		RoutInfo entity = mapper.selectByPrimaryKey(key);
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "待删除的路由信息不存在");

		mapper.deleteByPrimaryKey(key);
	}
	
	private RoutInfoMapper getMapper() {
		return this.getMapper(RoutInfoMapper.class);
	}

	private void validateKey(RoutInfoKey key) {
		AssertUtil.objIsNull(key, "key is null.");
		AssertUtil.strIsBlank(key.getMchntCd(), "mchntCd is blank.");
		AssertUtil.strIsBlank(key.getIntTransCd(), "intTransCd is blank.");
		AssertUtil.strIsBlank(key.getPayType(), "payType is blank.");
		AssertUtil.strIsBlank(key.getTermSnRegex(), "termSnRegex is blank.");
	}
	
	@Override
	protected RoutInfoExample buildQryExample(Map<String, String> qryParamMap) {
		RoutInfoExample example = new RoutInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			String intTransCd = qryParamMap.get("intTransCd");
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			String termSnRegex = qryParamMap.get("termSnRegex");
			if (!StringUtil.isBlank(termSnRegex)) {
				c.andTermSnRegexLike("%" + termSnRegex + "%");
			}
		}
		// 按照模块代码、排序序号、功能代码排序
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
}
