package com.icpay.payment.db.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntObligatedFlowMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntObligatedFlow;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntObligatedFlowExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntObligatedFlowExample.Criteria;
import com.icpay.payment.db.service.IChnlMchntObligatedFlowService;

@Service("chnlMchntObligatedFlowService")
public class ChnlMchntObligatedFlowService extends BaseService implements IChnlMchntObligatedFlowService {
	private static final Logger logger = Logger.getLogger(ChnlMchntObligatedFlowService.class);
	
	/**
	 * 分页查询
	 */
	@Override
	public Pager<ChnlMchntObligatedFlow> selectByPage(int pageNum, int pageSize,
			Map<String, String> qryParamMap) {
		ChnlMchntObligatedFlowExample example = this.getQryExample(qryParamMap);
		ChnlMchntObligatedFlowMapper mapper = getMapper();
		Pager<ChnlMchntObligatedFlow> pager = buildPager(pageNum, pageSize, qryParamMap);
		
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		
		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}
	
	/**
	 * 根据主键查询取现信息
	 */
	@Override
	public ChnlMchntObligatedFlow selectByPrimaryKey(String seqId) {
		return this.getMapper().selectByPrimaryKey(Long.valueOf(seqId));
	}
	
	/**
	 * 新增
	 */
	@Override
	public void add(ChnlMchntObligatedFlow chnlMchntObligatedFlow) {
		getMapper().insert(chnlMchntObligatedFlow);
	}
	
	private ChnlMchntObligatedFlowMapper getMapper() {
		return this.getMapper(ChnlMchntObligatedFlowMapper.class);
	}

	protected ChnlMchntObligatedFlowExample buildQryExample(Map<String, String> qryParamMap) {
		ChnlMchntObligatedFlowExample example = new ChnlMchntObligatedFlowExample();
		String orderBy = "rec_upd_ts desc";
		
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			// 查询条件：渠道
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			
			// 查询条件: 商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			//更新时间
			String startDate = StringUtil.trim(qryParamMap.get("startDate"));
			String startTime = StringUtil.trim(qryParamMap.get("startTime"));
			String endDate = StringUtil.trim(qryParamMap.get("endDate"));
			String endTime = StringUtil.trim(qryParamMap.get("endTime"));
			
			//timeQryMethod: UpdTS,CrtTS,OrdDT
			String timeQryMethod= StringUtil.trim(qryParamMap.get("timeQryMethod")); 
			if (Utils.isEmpty(timeQryMethod))
				timeQryMethod = "UpdTS";
			
			//更新时间
			String recUpdTs = StringUtil.trim(qryParamMap.get("recUpdTs"));
			
			// 查询条件: 操作时间
			if ((!StringUtil.isBlank(startDate)) || (!StringUtil.isBlank(endDate))) {
				if (StringUtil.isBlank(startDate)) startDate = endDate;
				if (StringUtil.isBlank(endDate)) endDate = startDate; //Converter.dateToString(tomorrow);
				
				if (StringUtil.isBlank(startTime)) startTime = "000000";
				if (StringUtil.isBlank(endTime)) endTime = "235959";
				
				startTime = StringUtils.left(startTime+"000000", 6);
				endTime = StringUtils.left(endTime+"000000", 6);
				
				Date date1=null;
				Date date2=null;
				try {
					if ("CrtTS".equals(timeQryMethod) || "OrdDT".equals(timeQryMethod)) {
						date1=DateUtils.parseDate(startDate+startTime, new String[]{"yyyyMMddHHmmss"});
						date2=DateUtils.parseDate(endDate+endTime+".999", new String[]{"yyyyMMddHHmmss.SSS"});
						c.andRecCrtTsGreaterThanOrEqualTo(date1);
						c.andRecCrtTsLessThan(date2);
						orderBy = "rec_crt_ts desc";
					}
					else {
						date1=DateUtils.parseDate(startDate+startTime, new String[]{"yyyyMMddHHmmss"});
						date2=DateUtils.parseDate(endDate+endTime+".999", new String[]{"yyyyMMddHHmmss.SSS"});
						c.andRecUpdTsGreaterThanOrEqualTo(date1);
						c.andRecUpdTsLessThan(date2);
						orderBy = "rec_upd_ts desc";
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if (!StringUtil.isBlank(recUpdTs)) {
				Date date1=null;
				Date date2=null;
				try {
					date1=DateUtils.parseDate(recUpdTs+"000000", new String[]{"yyyyMMddHHmmss"});
					date2=DateUtils.parseDate(recUpdTs+"235959.999", new String[]{"yyyyMMddHHmmss.SSS"});
					c.andRecUpdTsGreaterThanOrEqualTo(date1);
					c.andRecUpdTsLessThanOrEqualTo(date2);
					orderBy = "rec_upd_ts desc";
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 排序字段
		example.setOrderByClause(orderBy);
		return example;
	}
}
