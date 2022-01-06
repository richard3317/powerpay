package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TransProofMapper;
import com.icpay.payment.db.dao.mybatis.model.OffPayBank;
import com.icpay.payment.db.dao.mybatis.model.OffPayBankExample;
import com.icpay.payment.db.dao.mybatis.model.TransProof;
import com.icpay.payment.db.dao.mybatis.model.TransProofExample;
import com.icpay.payment.db.dao.mybatis.model.TxnLogView;
import com.icpay.payment.db.dao.mybatis.model.TxnLogViewExample;
import com.icpay.payment.db.service.ITransProofService;

@Service("transProofService")
public class TransProofService extends BaseService implements ITransProofService {
	
	@Override
	public List<TransProof> selectByMap(Map<String, String> qryParamMap) {
		TransProofExample example = new TransProofExample();
		example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByPage(example);
	}
	
	@Override
	protected TransProofExample buildQryExample(Map<String, String> qryParamMap) {
		TransProofExample example = new TransProofExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			com.icpay.payment.db.dao.mybatis.model.TransProofExample.Criteria c = example.createCriteria();
			// 查询条件：文件ID
			String fileId = StringUtil.trim(qryParamMap.get("fileId"));
			if (!StringUtil.isBlank(fileId)) {
				c.andFileIdEqualTo(fileId);
			}
			// 查询条件：交易流水号
			String txnId = StringUtil.trim(qryParamMap.get("txnId"));
			if (!StringUtil.isBlank(txnId)) {
				c.andTxnIdEqualTo(txnId);
			}
		}
		// 排序字段
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}
	
	@Override
	public TransProof selectByPrimaryKey(String txnId) {
		TransProofExample example = new TransProofExample();
		example.createCriteria().andTxnIdEqualTo(txnId);
		List<TransProof> list= getMapper().selectByExample(example);
		if ((list==null)||(list.size()==0))
			return null;
		return list.get(0);
	}
	
	/**
	 * 新增
	 */
	public void add(TransProof transProof) {
		i18ArgIsNull(transProof, this.getClass().getSimpleName(), "待保存的订单凭证对象为null");

		Date now = new Date();
		transProof.setRecCrtTs(now);
		transProof.setRecUpdTs(now);
		getMapper().insert(transProof);
	}
	
	private TransProofMapper getMapper() {
		return this.getMapper(TransProofMapper.class);
	}

}
