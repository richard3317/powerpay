package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlInfoExample;
import com.icpay.payment.db.service.IChnlInfoService;

@Service("chnlInfoService")
public class ChnlInfoService extends BaseService implements IChnlInfoService {
	
	@Override
	public Pager<ChnlInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlInfoExample example = new ChnlInfoExample();
		example.setOrderByClause("rec_upd_ts desc");
		ChnlInfoMapper mapper = getMapper();
		Pager<ChnlInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	/**
	 * 获取全部通道信息
	 * @return
	 */
	@Override
	public List<ChnlInfo> getAllChnlInfo() {
		ChnlInfoExample example = new ChnlInfoExample();
		example.setOrderByClause("chnl_id asc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public ChnlInfo selectByPrimaryKey(String chnlId) {
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "渠道编号为空");
		return getMapper().selectByPrimaryKey(chnlId);
	}
	
	/**
	 * 新增
	 */
	public void add(ChnlInfo chnlInfo) {
		i18ArgIsNull(chnlInfo, this.getClass().getSimpleName(), "待保存的渠道信息对象为null");

		String chnlId = chnlInfo.getChnlId();
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "渠道码为空");

		ChnlInfo m = this.selectByPrimaryKey(chnlId);
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "渠道信息已存在: %s", chnlId);

		Date now = new Date();
		chnlInfo.setRecCrtTs(now);
		chnlInfo.setRecUpdTs(now);
		
		getMapper().insert(chnlInfo);
	}
	
	/**
	 * 修改
	 */
	public void update(ChnlInfo chnlInfo) {
		i18ArgIsNull(chnlInfo, this.getClass().getSimpleName(), "待更新的渠道信息对象为null");

		String chnlId = chnlInfo.getChnlId();
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "功能码不能为空");

		ChnlInfo dbEntity = this.selectByPrimaryKey(chnlId);
		i18ObjIsNull(dbEntity, this.getClass().getSimpleName(), "渠道信息信息不存在： %s", chnlId);

		// 更新数据库字段
		BeanUtil.cloneEntity(chnlInfo, dbEntity, new String[] {
				"chnlDesc", "transType", "feeLevel", "state",
				"transLimit", "dayTransLimit", "operateConditions", "lastOperId"});
		dbEntity.setRecUpdTs(new Date());
		
		getMapper().updateByPrimaryKey(dbEntity);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(String chnlId) {
		i18ArgIsBlank(chnlId, this.getClass().getSimpleName(), "渠道编号不能为空");
		ChnlInfo m = this.selectByPrimaryKey(chnlId);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的渠道信息信息不存在： %s", chnlId);
		
		getMapper().deleteByPrimaryKey(chnlId);
	}
	
	private ChnlInfoMapper getMapper() {
		return this.getMapper(ChnlInfoMapper.class);
	}

}
