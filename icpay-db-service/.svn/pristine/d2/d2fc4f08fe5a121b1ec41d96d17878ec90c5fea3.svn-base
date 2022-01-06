package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.constants.RspCd;
import com.icpay.payment.common.constants.Constant.INT_STATE;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ExchangeRateMapper;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRate;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRateExample;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRateExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRateKey;
import com.icpay.payment.db.service.IExchangeRateService;

@Service("exchangeRateService")
public class ExchangeRateService extends BaseService implements IExchangeRateService {
	
	@Override
	protected ExchangeRateExample buildQryExample(Map<String, String> qryParamMap) {
		ExchangeRateExample example = new ExchangeRateExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			String cat = qryParamMap.get("cat");
			if (!StringUtil.isBlank(cat)) {
				c.andCatEqualTo(cat);
			}
			String intTransCd = qryParamMap.get("intTransCd");
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			String currency = qryParamMap.get("currency");
			if (!StringUtil.isBlank(currency)) {
				c.andCurrencyEqualTo(currency);
			}
			String currencyTarget = qryParamMap.get("currencyTarget");
			if (!StringUtil.isBlank(currencyTarget)) {
				c.andCurrencyTargetEqualTo(currencyTarget);
			}
			String valid = qryParamMap.get("valid");
			if (!StringUtil.isBlank(valid)) {
				c.andValidEqualTo(Integer.valueOf(valid));
			}

		}
//		// 排序字段
//		example.setOrderByClause("agent_cd,account_type,priority");
		return example;
	}	
	
	@Override
	public Pager<ExchangeRate> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		ExchangeRateExample example = this.buildQryExample(qryParamMap);
		ExchangeRateMapper mapper = getMapper();
		Pager<ExchangeRate> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public List<ExchangeRate> select(Map<String, String> qryParamMap) {
		ExchangeRateExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public ExchangeRate selectByPrimaryKey(ExchangeRateKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

//	@Override
//	public long count(Map<String, String> qryParamMap) {
//		BankChnlMapExample example = this.getQryExample(qryParamMap);
//		return this.getMapper().countByExample(example);
//	}
//
//	@Override
//	public int add(BankChnlMap record) {
//		return this.getMapper().insertSelective(record);
//	}
//
//	@Override
//	public int update(BankChnlMap record) {
//		return this.getMapper().updateByPrimaryKeySelective(record);
//	}
//
//	@Override
//	public int delete(BankChnlMapKey key) {
//		return this.getMapper().deleteByPrimaryKey(key);
//	}
	
	protected ExchangeRateMapper getMapper() {
		return super.getMapper(ExchangeRateMapper.class);
	}	
	
	
	@Override
	public ExchangeRate calcExchangeRate(String cat, String intTransCd, 
			BigDecimal txnAmt, BigDecimal txnUnit, String currency, String currencyTarget) {
		
		//查询汇率表
		ExchangeRate record = 
				getMapper().selectByPrimaryKey(new ExchangeRateKey(cat, intTransCd, currency, currencyTarget));
		
		this.checkQueryError(record, "查无匯率数据");
		
		if (! INT_STATE.YSE.equals(record.getValid())) 
			this.throwError(RspCd.Z_5005, "汇率已失效");
		
		
		BigDecimal amt = record.getAmount();
		BigDecimal unit = record.getUnit();
		BigDecimal amtTaget = record.getAmountTarget();
		BigDecimal unitTaget = record.getUnitTarget();
		this.checkParam(amt, "record.amount");
		this.checkParam(unit, "record.unit");
		this.checkParam(amtTaget, "record.amountTarget");
		this.checkParam(unitTaget, "record.unitTaget");
		
		//开始计算
		try {
			///////////////////////////////
			// 正规化数据表汇率记录
			
			//正规化来源金额(正规化为1.0单位)
			BigDecimal unitAmt = amt.multiply(unit);
			//正规化目标金额(正规化为1.0单位)
			BigDecimal unitAmtTarget = amtTaget.multiply(unitTaget);
			//计算正规化汇率 
			BigDecimal rate = unitAmtTarget.divide(unitAmt);
			
			//////////////////////////////////
			// 计算最后汇兑结果
			
			//正规化请求金额(正规化为1.0单位)
			BigDecimal reqUnitAmt = txnAmt.multiply(txnUnit);
			//计算汇兑结果
			BigDecimal respAmt = reqUnitAmt.multiply(rate);
			
			//回传结果
			ExchangeRate res = record; //new ExchangeRate(); //res.cloneFrom(record);
			res.setUnitTarget(new BigDecimal("1.0"));
			res.setAmountTarget(respAmt);
			res.setCurrencyTarget(currencyTarget);
			res.setAmount(txnAmt);
			res.setUnit(txnUnit);
			res.setCurrency(currency);
			return res;
		} catch (Exception err) {
			this.throwError("先擋著", "汇率计算错误", err);
		}
		return null;
	}	

}
