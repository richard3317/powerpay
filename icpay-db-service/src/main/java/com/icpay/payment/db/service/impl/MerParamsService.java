package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntInfoMapper;
import com.icpay.payment.db.dao.mybatis.mapper.MerParamsMapper;
//import com.icpay.payment.db.dao.mybatis.mapper.MerParams_BakMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfo;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntInfoKey;
import com.icpay.payment.db.dao.mybatis.model.MerParams;
import com.icpay.payment.db.dao.mybatis.model.MerParamsExample;
import com.icpay.payment.db.dao.mybatis.model.MerParamsKey;
import com.icpay.payment.db.service.IChnlMchntInfoService;
import com.icpay.payment.db.service.IMerParamsService;

@Service("merParamsService")
public class MerParamsService extends BaseService implements IMerParamsService {
	private static final Logger logger = Logger.getLogger(MerParamsService.class);
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
	public List<MerParams> getAllMerParams() {
		MerParamsExample example = new MerParamsExample();
		example.setOrderByClause("chnl_id asc");
		return this.getMapper().selectByExample(example);
	}
	
	@Override
	public MerParams selectByPrimaryKey(MerParamsKey mpk) {
		i18StrIsBlank(mpk.getChnlId(), this.getClass().getSimpleName(), "渠道编号为空");
		return getMapper().selectByPrimaryKey(mpk);
	}
	
	/**
	 * 新增
	 */
	public void add(MerParams mp) {
		i18ArgIsNull(mp, this.getClass().getSimpleName(), "待保存的渠道信息对象为null");
		String chnlId = mp.getChnlId();
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "渠道码为空");
		
		MerParams m = this.selectByPrimaryKey(mp);
		Date now = new Date();
		mp.setRecCrtTs(now);
		mp.setRecUpdTs(now);
		logger.info("新增ADD-------"+mp.getChnlId());
		getMapper().insert(mp);
	}
	
	/**
	 * 修改
	 */
	public void update(MerParams mp) {
		getMapper().updateByPrimaryKey(mp);
	}
	
	/**
	 * 删除
	 * @param funcCd
	 * @return
	 */
	public void delete(MerParamsKey mpk) {
		i18ArgIsBlank(mpk.getChnlId(), this.getClass().getSimpleName(), "渠道编号不能为空");
		MerParamsKey m = this.selectByPrimaryKey(mpk);
		i18ObjIsNull(m, this.getClass().getSimpleName(), "待删除的渠道信息信息不存在： %s", mpk.getChnlId());

		getMapper().deleteByPrimaryKey(mpk);
	}
	
	private MerParamsMapper getMapper() {
		return this.getMapper(MerParamsMapper.class);
	}

//	/**
//	 * 删除key中所有paramId字段
//	 */
//	@Override
//	public void deleteNoParamId(MerParamsKey mpk) {
//		i18ArgIsBlank(mpk.getChnlId(), this.getClass().getSimpleName(), "渠道编号不能为空");
//		getMapper().deleteByPrimaryKeyNoParamId(mpk);
//	}

	@Override
	protected MerParamsExample buildQryExample(Map<String, String> qryParamMap) {
		MerParamsExample example = new MerParamsExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.MerParamsExample.Criteria c = example.createCriteria();
			// 查询条件：param_cat 
			String paramCat = StringUtil.trim(qryParamMap.get("param_cat"));
			if (!StringUtil.isBlank(paramCat)) {
				c.andParamCatEqualTo(paramCat);
			}
			// 查询条件：param_id 
			String paramId = StringUtil.trim(qryParamMap.get("param_id"));
			if (!StringUtil.isBlank(paramId)) {
				c.andParamIdEqualTo(paramId);
			}
			
			// 查询条件：chnl_id
			String chnlId = StringUtil.trim(qryParamMap.get("chnl_id"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件：mchnt_cd
			String mchntCd = StringUtil.trim(qryParamMap.get("mchnt_cd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}		}
		// 排序字段
		example.setOrderByClause("rec_crt_ts desc");
		return example;
	}
	
	@Override
	public List<MerParams> select(Map<String, String> qryParamMap) {
		MerParamsExample example = this.getQryExample(qryParamMap);
		return this.getMapper(MerParamsMapper.class).selectByExample(example);	
	}


}
