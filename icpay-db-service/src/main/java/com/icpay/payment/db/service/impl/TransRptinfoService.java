package com.icpay.payment.db.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TransRptInfoMapper;
import com.icpay.payment.db.dao.mybatis.model.TransRptInfo;
import com.icpay.payment.db.dao.mybatis.model.TransRptInfoExample;
import com.icpay.payment.db.dao.mybatis.model.TransRptInfoExample.Criteria;
import com.icpay.payment.db.service.ITransRptInfoService;

@Service("transRptinfoService")
public class TransRptinfoService extends BaseService implements ITransRptInfoService {

	@Override
	public List<TransRptInfo> select(Map<String, String> qryParamMap) {

		TransRptInfoExample qryExample = this.getQryExample(qryParamMap);
		return getMapper(TransRptInfoMapper.class).selectByExample(qryExample);
	}

	@Override
	public Pager<TransRptInfo> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		TransRptInfoExample example = this.getQryExample(qryParamMap);
		TransRptInfoMapper mapper = getMapper();
		Pager<TransRptInfo> pager = buildPager(pageNum, pageSize, qryParamMap);

		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	private TransRptInfoMapper getMapper() {
		return this.getMapper(TransRptInfoMapper.class);
	}

	@SuppressWarnings("unused")
	@Override
	protected TransRptInfoExample buildQryExample(Map<String, String> qryParamMap) {
		TransRptInfoExample example = new TransRptInfoExample();
		Criteria c = example.createCriteria();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			// 商户号
			String mchntCd = StringUtil.trimStr(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				// 商户号等于
				c.andMchntCdEqualTo(mchntCd);
			}
			// 商户名称
			String mchntCnNm = StringUtil.trimStr(qryParamMap.get("mchntCnNm"));
			if (!StringUtil.isBlank(mchntCnNm)) {
				// 商户名称等于
				c.andMchntCnNmEqualTo(mchntCnNm);
			}
			// 交易类型
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}

			// 交易状态
			String txnState = StringUtil.trim(qryParamMap.get("txnState"));
			if (!StringUtil.isBlank(txnState)) {
				c.andTxnStateEqualTo(txnState);
			}

			// 查询条件:交易金额 得区间#
			// 最小值
			String transAtMin = StringUtil.trim(qryParamMap.get("transAtMin"));
			// 最大值
			String transAtMax = StringUtil.trim(qryParamMap.get("transAtMax"));
			if ((!StringUtil.isBlank(transAtMin)) && !"".equals(transAtMin)) {
				// 交易金额大于或等于
				c.andTransAtGreaterThanOrEqualTo(Long.valueOf(transAtMin));
			}

			if ((!StringUtil.isBlank(transAtMax)) && !"".equals(transAtMax)) {
				// 交易金额小于或等于
				c.andTransAtLessThanOrEqualTo(Long.valueOf(transAtMax));
			}

			// 交易日期：将字符串转换成long类型 进行时间段的查询
			String endDate = StringUtil.trimStr(qryParamMap.get("endDate"));

			Calendar calendar = Calendar.getInstance();
			String startDate = StringUtil.trimStr(qryParamMap.get("startDate"));
			if ((endDate == null && startDate == null) || endDate.equals("")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
				endDate = df.format(calendar.getTime().getTime()).replace("-", "");
			} else if (endDate == null) {
				endDate = null;
			} else if (endDate.equals("qry")) {
				endDate = null;
			} else {
				endDate += " 00";
			}
			if ((startDate == null && endDate == null) || "".equals(startDate)) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH");
				startDate = df.format(calendar.getTime().getTime() - (1000 * 60 * 60) * 1).replace("-", "");
			} else if (startDate == null) {
				startDate = null;
			} else if (startDate.equals("qry")) {
				startDate = null;
			} else {
				startDate += " 00";
			}
		// System.out.println("开始时间为：" + startDate + "；结束时间为：" + endDate);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH");
			if ((!StringUtil.isBlank(endDate)) || (!StringUtil.isBlank(startDate))) {
				try {
					Date date = simpleDateFormat.parse(startDate);
					Date date2 = simpleDateFormat.parse(endDate);
					// 创建时间大于或等于
					c.andRecCrtTsGreaterThanOrEqualTo(date);
					// 创建时间小于或得等于
					c.andRecCrtTsLessThanOrEqualTo(date2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return example;
	}

}
