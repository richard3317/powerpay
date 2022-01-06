package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.DateUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.BalanceTransferResultMapper;
import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResult;
import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResultExample;
import com.icpay.payment.db.dao.mybatis.model.BalanceTransferResultExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.BatchInfo;
import com.icpay.payment.db.dao.mybatis.model.TransLogExample;
import com.icpay.payment.db.service.IBalanceTransferResultService;

@Service("BalanceTransferResultService")
public class BalanceTransferResultService extends BaseService implements IBalanceTransferResultService {

	/**
	 * 新增
	 */
	public void add(BalanceTransferResult transferInfo) {
		i18ArgIsNull(transferInfo, this.getClass().getSimpleName(), "待保存的渠道信息对象为null");

		String chnlId = transferInfo.getTransChnl();
		i18StrIsBlank(chnlId, this.getClass().getSimpleName(), "渠道码为空");
		i18StrIsBlank(transferInfo.getMchntCd(), this.getClass().getSimpleName(), "商户号为空");

		
//		BalanceTransferResult key = new BalanceTransferResult();
//		key.setTransChnl(chnlId);
//		key.setMchntCd(transferInfo.getMchntCd());
//		key.setTransferDt(transferInfo.getTransferDt());
//		
//		BalanceTransferResult m = getMapper().selectByPrimaryKey(key);
//		i18ObjIsNotNull(m, this.getClass().getSimpleName(), "信息已存在: %s, %s, %s", chnlId,transferInfo.getMchntCd(),transferInfo.getTransferDt());
		
		
		checkNotEmpty(transferInfo,"transferInfo",this.getClass().getSimpleName());
		checkNotEmpty(transferInfo.getTransChnl(),"transChnl",this.getClass().getSimpleName());
		checkNotEmpty(transferInfo.getMchntCd(),"mchntCd",this.getClass().getSimpleName());
		checkNotEmpty(transferInfo.getCurrCd(),"currCd",this.getClass().getSimpleName());
		checkNotEmpty(transferInfo.getTransferDt(),"transDt",this.getClass().getSimpleName());
		
		BalanceTransferResultExample ex= new BalanceTransferResultExample();
		
		ex.createCriteria()
			.andTransChnlEqualTo(transferInfo.getTransChnl())
			.andMchntCdEqualTo(transferInfo.getMchntCd())
			.andCurrCdEqualTo(transferInfo.getCurrCd())
			.andTransferDtEqualTo(transferInfo.getTransferDt())
			;
		
		List<BalanceTransferResult> qr = getMapper().selectByExample(ex);
		
		if ((qr!=null) && (qr.size()>0)) {
			BalanceTransferResult r = qr.get(0);
			i18ObjIsNotNull(r, this.getClass().getSimpleName(), "信息已存在: %s, %s, %s", chnlId,transferInfo.getMchntCd(),transferInfo.getTransferDt());
		}

		Date now = new Date();
		transferInfo.setRecCrtTs(now);
		transferInfo.setRecUpdTs(now);

		getMapper().insert(transferInfo);
	}

	/**
	 * 修改
	 */
	public void update(BalanceTransferResult transferInfo) {
		i18ArgIsNull(transferInfo.getTransChnl(), this.getClass().getSimpleName(), "待更新的渠道信息对象为null");
		i18ArgIsNull(transferInfo.getMchntCd(), this.getClass().getSimpleName(), "待更新的商户号信息对象为null");
		getMapper().updateByPrimaryKey(transferInfo);
	}

	
	private BalanceTransferResultMapper getMapper() {
		return this.getMapper(BalanceTransferResultMapper.class);
	}

	@Override
	protected BalanceTransferResultExample buildQryExample(Map<String, String> qryParamMap) {
		BalanceTransferResultExample example = new BalanceTransferResultExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// 查询条件：渠道
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andTransChnlEqualTo(chnlId);
			}

			// 查询条件：商户代码
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdLike("%" + mchntCd + "%");
			}

			// 查询条件：Currency
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
			}

			// 查询条件:状态
			String state = StringUtil.trim(qryParamMap.get("state"));
			if (!StringUtil.isBlank(state)) {
				c.andTransferResultEqualTo(state);
			}
			
			// 查询条件:日期
			String transferDt = StringUtil.trim(qryParamMap.get("transferDt"));
			if (!StringUtil.isBlank(transferDt)) {
				c.andTransferDtEqualTo(transferDt);
			}
		}
		return example;
	}

	@Override
	public long count(Map <String,String> qryParamMap) {
		BalanceTransferResultExample example = this.buildQryExample(qryParamMap);
		return getMapper().countByExample(example);
	}

}
