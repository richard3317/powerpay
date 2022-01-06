package com.icpay.payment.db.service;

import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResult;

public interface IAcctChkResultService {

	public Pager<AcctChkResult> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);
	
	public AcctChkResult selectByPrimaryKey(int seqId);
	
	public void cmt(int seqId, String comments);
}
