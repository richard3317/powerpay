package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.AcctChkResultMapper;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResult;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultExample;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultExample.Criteria;
import com.icpay.payment.db.service.IAcctChkResultService;

@Service("acctChkResultService")
public class AcctChkResultService extends BaseService implements IAcctChkResultService {

	@Override
	public Pager<AcctChkResult> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		AcctChkResultExample example = this.getQryExample(qryParamMap);
		AcctChkResultMapper mapper = getMapper();
		Pager<AcctChkResult> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public AcctChkResult selectByPrimaryKey(int seqId) {
		return this.getMapper().selectByPrimaryKey(seqId);
	}

	/**
	 * 添加备注
	 */
	@Override
	public void cmt(int seqId, String comments) {
		AcctChkResultMapper mapper = getMapper();
		AcctChkResult entity = mapper.selectByPrimaryKey(seqId);
		i18ObjIsNull(entity, this.getClass().getSimpleName(), "未找到记录: %s",String.valueOf(seqId));
		entity.setComments(comments);
		entity.setRecUpdTs(new Date());
		mapper.updateByPrimaryKey(entity);
	}
	
	private AcctChkResultMapper getMapper() {
		return this.getMapper(AcctChkResultMapper.class);
	}
	
	@Override
	protected AcctChkResultExample buildQryExample(Map<String, String> qryParamMap) {
		AcctChkResultExample example = new AcctChkResultExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件: 对账日期
			String checkDt = StringUtil.trim(qryParamMap.get("checkDt"));
			if (!StringUtil.isBlank(checkDt)) {
				c.andCheckDtEqualTo(checkDt);
			}
			
			// 查询条件: 交易流水号
			String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
			if (!StringUtil.isBlank(transSeqId)) {
				c.andTransSeqIdEqualTo(transSeqId);
			}
			
			// 查询条件: 商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}

			// 查询条件：订单号
			String orderId = StringUtil.trim(qryParamMap.get("orderId"));
			if (!StringUtil.isBlank(orderId)) {
				c.andOrderIdEqualTo(orderId);
			}
			
			// 查询条件：渠道
			String transChnl = StringUtil.trim(qryParamMap.get("transChnl"));
			if (!StringUtil.isBlank(transChnl)) {
				c.andTransChnlEqualTo(transChnl);
			}
			
			// 查询条件：对账结果
			String checkResult = StringUtil.trim(qryParamMap.get("checkResult"));
			if (!StringUtil.isBlank(checkResult)) {
				c.andCheckResultEqualTo(checkResult);
			}
			
			// 查询条件：交易日期
			String extTransDt = StringUtil.trimStr(qryParamMap.get("extTransDt"));
			if (!StringUtil.isBlank(extTransDt)) {
				c.andExtTransDtEqualTo(extTransDt);
			}
			
			// 查询条件： 交易类型
			String intTransCd = StringUtil.trimStr(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
		
			// 查询条件： 终端号
			String termSn = StringUtil.trimStr(qryParamMap.get("termSn"));
			if (!StringUtil.isBlank(termSn)) {
				c.andTermSnEqualTo(termSn);
			}
			
			// 查询条件： 备注
			String comments = StringUtil.trimStr(qryParamMap.get("comments"));
			if (!StringUtil.isBlank(comments)) {
				c.andCommentsLike("%" + comments + "%");
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts asc");
		return example;
	}
}
