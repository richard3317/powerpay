package com.icpay.payment.db.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.icpay.payment.common.constants.Constant;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.BeanUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ChnlMchntAccountFlowExtMapper;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlowExample;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlowExample.Criteria;
import com.icpay.payment.db.service.IChnlMchntAccountFlowService;

@Service("chnlMchntAccountFlowService")
public class ChnlMchntAccountFlowService extends BaseService implements IChnlMchntAccountFlowService {

	private static final Logger logger = Logger.getLogger(ChnlMchntAccountFlowService.class);

	@Override
	public List<ChnlMchntAccountFlow> select(String mon, Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		ChnlMchntAccountFlowExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectByExample(example, mon);
	}

	@Override
	public Pager<ChnlMchntAccountFlow> selectByPage(int pageNum, int pageSize, String mon,
			Map<String, String> qryParamMap) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		logger.info("分页查询账户调整开始");
		ChnlMchntAccountFlowExample example = this.getQryExample(qryParamMap);
		ChnlMchntAccountFlowExtMapper mapper = getMapper();
		Pager<ChnlMchntAccountFlow> pager = buildPager(pageNum, pageSize, qryParamMap);

		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);
		long total = mapper.countByExample(example, mon);
		pager.setTotal(new Long(total).intValue());
		pager.setResultList(mapper.selectByPage(example, mon));

		logger.info("分页查询账户调整完成");
		return pager;
	}

	@Override
	public ChnlMchntAccountFlow selectByPrimaryKey(String seqId, String mon) {
		// 为了防止SQL注入，对MON进行格式校验
		AssertUtil.notMonStr(mon);
		return this.getMapper().selectByPrimaryKey(Long.valueOf(seqId), mon);
	}

	@Override
	protected ChnlMchntAccountFlowExample buildQryExample(Map<String, String> qryParamMap) {
		Date now = new Date();
		Date yesterday = new Date(now.getTime()-86400000L);
		Date tomorrow = new Date(now.getTime()-86400000L);

		ChnlMchntAccountFlowExample example = new ChnlMchntAccountFlowExample();
		String orderBy = "rec_upd_ts desc";
		
		Criteria c = example.createCriteria();
		c.andOperateTpNotEqualTo(Constant.OPERTYPE._90); //忽略重置归零的内容
		
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			
			String note= StringUtil.trim(qryParamMap.get("note"));
			if (!StringUtil.isBlank(note)) {
				c.andNoteLike("%"+note+"%");
			}
			
			// 查询条件:操作员
			String lastOperId = StringUtil.trim(qryParamMap.get("lastOperId"));
			if (!StringUtil.isBlank(lastOperId)) {
				c.andLastOperIdLike("%"+lastOperId+"%");
			}
			
			// 查询条件:交易流水号
			String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
			if (!StringUtil.isBlank(transSeqId)) {
				c.andTransSeqIdEqualTo(transSeqId);
			}
			// 查询条件：渠道号
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// 查询条件: 操作类型
			String operateTp = StringUtil.trim(qryParamMap.get("operateTp"));
			if (!StringUtil.isBlank(operateTp)) {
				c.andOperateTpEqualTo(operateTp);
			}
			//operateTpCat
			String operateTpCat = StringUtil.trim(qryParamMap.get("operateTpCat"));
			if (!StringUtil.isBlank(operateTpCat)) {
				String[] cats =  operateTpCat.split(";");
				List<String> opList = Arrays.asList(cats);
				c.andOperateTpIn(opList);
			}

			// 查询条件:商户号
			String mchntCd = StringUtil.trim(qryParamMap.get("mchntCd"));
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}
			
			//多个商户号
		   String mchntCdArr = StringUtil.trim(qryParamMap.get("mchntCdArr"));
		   if (!StringUtil.isBlank(mchntCdArr)) {
			   c.andMchntCdIn(Utils.strSplitToList(mchntCdArr, ",", true));
		   }

			// 查询条件:交易金额
			String transAt = StringUtil.trim(qryParamMap.get("transAt"));
			if (!StringUtil.isBlank(transAt)) {
				// 转换成分
				//BigDecimal at = new BigDecimal(transAt).multiply(new BigDecimal(100));
				BigDecimal at = new BigDecimal(transAt);
				c.andTransAtEqualTo(at.longValue());
			}

			// 查询条件:操作手续费
			String transFee = StringUtil.trim(qryParamMap.get("transFee"));
			if (!StringUtil.isBlank(transFee)) {
				// 转换成分
				BigDecimal at = new BigDecimal(transFee).multiply(new BigDecimal(100));
				c.andTransFeeEqualTo(at.longValue());
			}

			// 查询条件:清算币别
			String currCd = StringUtil.trim(qryParamMap.get("currCd"));
			if (!StringUtil.isBlank(currCd)) {
				c.andCurrCdEqualTo(currCd);
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
			String transDt = StringUtil.trim(qryParamMap.get("transDt"));
			
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
					if ("OrdDT".equals(timeQryMethod)) {
						c.andTransDtGreaterThanOrEqualTo(startDate);
						c.andTransDtLessThanOrEqualTo(endDate);
						c.andTransTmGreaterThanOrEqualTo(startTime);
						c.andTransTmLessThanOrEqualTo(endTime);
						orderBy = "trans_dt desc, trans_tm desc";
					}
					else if ("CrtTS".equals(timeQryMethod)) {
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
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (!StringUtil.isBlank(transDt)) {
				c.andTransDtEqualTo(transDt);
			}

		}
		// 排序字段
		example.setOrderByClause(orderBy);
		return example;
	}

	private ChnlMchntAccountFlowExtMapper getMapper() {
		return this.getMapper(ChnlMchntAccountFlowExtMapper.class);
	}

	/**
	 * 更新
	 */
	@Override
	public void update(ChnlMchntAccountFlow chnlMchntAccountFlow) {
		AssertUtil.objIsNull(chnlMchntAccountFlow, "待修改的记录为null");
		
		Date date = chnlMchntAccountFlow.getRecUpdTs();
		//設定日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(date);
		
		ChnlMchntAccountFlow dbEntity = this.selectByPrimaryKey(String.valueOf(chnlMchntAccountFlow.getSeqId()), dateString.substring(5,7));
		AssertUtil.objIsNull(dbEntity, "该商户账户流水不存在");
		
		// 更新数据库字段
		BeanUtil.cloneEntity(chnlMchntAccountFlow, dbEntity, new String[] {
				"note", "lastOperId"
		});
		dbEntity.setRecUpdTs(new Date());
		
		// 保存至数据库
		getMapper().updateByPrimaryKey(dbEntity, dateString.substring(5,7));
		
	}
	
	/**
	 * 加總
	 */
	@Override
	public ChnlMchntAccountFlow selectSummary(String mon, Map<String, String> qryParamMap) {
		AssertUtil.notMonStr(mon);
		ChnlMchntAccountFlowExample example = this.buildQryExample(qryParamMap);
		return this.getMapper().selectSummary(example, mon);
	}

}
