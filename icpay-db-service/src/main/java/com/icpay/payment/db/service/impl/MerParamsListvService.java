package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerParamsListvMapper;
import com.icpay.payment.db.dao.mybatis.mapper.ExtMapper.MerParamsListvExtMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.MerParamsListv;
import com.icpay.payment.db.dao.mybatis.model.MerParamsListvExample;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IMerParamsListvService;

@Service("merParamsListvService")
public class MerParamsListvService extends BaseService implements IMerParamsListvService {
	private static final Logger logger = Logger.getLogger(MerParamsListvService.class);
	/*@Override
	public Pager<ChnlMchntInfo> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlMchntInfoExample example = new ChnlMchntInfoExample();
		example.setOrderByClause("rec_upd_ts desc");
		ChnlMchntInfoMapper mapper = getMapper();
		Pager<ChnlMchntInfo> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}*/
	
	/**
	 * 获取全部通道信息
	 * @return
	 */
	@Override
	public List<MerParamsListv> getAllMerParamsListv() {
		MerParamsListvExample example = new MerParamsListvExample();
		example.setOrderByClause("chnl_id asc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public MerParamsListv selectByPrimaryKey(Long id) {
		return getMapper().selectByPrimaryKey(id);
	}
	
	/**
	 * 新增
	 */
	public void add(MerParamsListv mp) {
		i18ArgIsNull(mp, this.getClass().getSimpleName(), "待保存的渠道信息对象为null");

		String chnlId = mp.getChnlId();
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "渠道码为空");
		MerParamsListv m = this.selectByPrimaryKey(mp.getId());
		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "渠道信息信息已存在: %s", chnlId);

		Date now = new Date();
		mp.setRecCrtTs(now);
		mp.setRecUpdTs(now);
		logger.info("新增ADD-------"+mp.getChnlId());
		getMapper().insert(mp);
	}
	
	/**
	 * 修改
	 */
	public void update(MerParamsListv mp) {
		
		// 更新数据库字段
//		BeanUtil.cloneEntity(chnlInfo, dbEntity, new String[] {
//				"chnlDesc", "transType", "feeLevel", "state",
//				"transLimit", "dayTransLimit", "lastOperId"});
//		dbEntity.setRecUpdTs(new Date());
//		
		getMapper().updateByPrimaryKey(mp);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(Long id) {
		
		getMapper().deleteByPrimaryKey(id);
	}
	
	private MerParamsListvMapper getMapper() {
		return this.getMapper(MerParamsListvMapper.class);
	}

	@Override
	public List<Long> getMerParamsListvKey(MerParamsListv mp) {
		i18StrIsBlank(mp.getChnlId(), this.getClass().getSimpleName(), "渠道编号为空");

		return this.getMapper(MerParamsListvExtMapper.class).getMerParamsListvKey(mp);
	}


}
