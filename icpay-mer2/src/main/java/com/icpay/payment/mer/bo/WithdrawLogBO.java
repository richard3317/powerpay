package com.icpay.payment.mer.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLog;
import com.icpay.payment.db.service.IWithdrawLogService;

@Component("withdrawLogBO")
public class WithdrawLogBO extends BaseBO {
	
	/**
	 * 获取取现信息
	 */
	public Pager<WithdrawLog> qryTrans(int pageNum, int pageSize, String mon, Map<String, String> qryParams) {
		IWithdrawLogService service = this.getDBService(IWithdrawLogService.class);
		return service.selectByPage(pageNum, pageSize, mon, qryParams);
	}
	
	public WithdrawLog getTransFlow(String orderSeqId, String mon) {
		IWithdrawLogService service = this.getDBService(IWithdrawLogService.class);
		return service.selectByPrimaryKey(orderSeqId, mon);
	}
	
	/**
	 * 获取取现信息
	 */
	public List<WithdrawLog> qryTransList(String mon, Map<String, String> qryParams) {
		IWithdrawLogService service = this.getDBService(IWithdrawLogService.class);
		return service.select(mon, qryParams);
	}
}
