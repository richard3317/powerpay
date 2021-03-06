package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.constants.Constant.COMMON_STATE;
import com.icpay.payment.common.constants.Names.CHNL_MSG;
import com.icpay.payment.common.constants.Names.INTER_MSG;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.enums.SettleEnums;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.RoutingPriorityMapper;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriority;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriorityExample;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriorityExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriorityKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.RoutingPriorityExt;
import com.icpay.payment.db.service.IRoutingPriorityService;

@Service("routingPriority")
public class RoutingPriorityService extends BaseService implements IRoutingPriorityService {

	@Override
	public int deleteByPrimaryKey(RoutingPriorityKey key) {
		return dao().deleteByPrimaryKey(key);
	}
	
	@Override
	public int insertSelective(RoutingPriority record) {
		return dao().insertSelective(record);
	}
	
	@Override
	public int insertSelectiveExt(RoutingPriorityExt record) {
		return dao().insertSelectiveExt(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(RoutingPriority record) {
		return dao().updateByPrimaryKeySelective(record);
	}

	@Override
	public List<RoutingPriority> selectByExample(Map<String,String> qryParamMap) {
		RoutingPriorityExample example = this.getQryExample(qryParamMap);
		return dao().selectByExample(example);
	}
	
	@Override
	public Pager<RoutingPriority> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap) {
		RoutingPriorityExample example = this.getQryExample(qryParamMap);
		Pager<RoutingPriority> pager = buildPager(pageNum, pageSize, qryParamMap);
		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(dao().countByExample(example));
		pager.setResultList(dao().selectByPage(example));
		return pager;
	}

	@Override
	public RoutingPriority selectByPrimaryKey(RoutingPriorityKey key) {
		return dao().selectByPrimaryKey(key);
	}

	private RoutingPriorityMapper dao() {
		return this.getMapper(RoutingPriorityMapper.class);
	}	
	
	@Override
	protected RoutingPriorityExample buildQryExample(Map<String, String> qryParamMap) {
		RoutingPriorityExample example = new RoutingPriorityExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();
			// ????????????:?????????????????????
			String transSeqId = StringUtil.trim(qryParamMap.get("transSeqId"));
			if (!StringUtil.isBlank(transSeqId)) {
				c.andTransSeqIdLike("%" + transSeqId + "%");//????????????
			}
			// ????????????:????????????
			String intTransCd = StringUtil.trim(qryParamMap.get("intTransCd"));
			if (!StringUtil.isBlank(intTransCd)) {
				c.andIntTransCdEqualTo(intTransCd);
			}
			// ????????????:????????????
			String chnlId = StringUtil.trim(qryParamMap.get("chnlId"));
			if (!StringUtil.isBlank(chnlId)) {
				c.andChnlIdEqualTo(chnlId);
			}
			// ????????????:???????????????
			String chnlMchntCd = StringUtil.trim(qryParamMap.get("chnlMchntCd"));
			if (!StringUtil.isBlank(chnlMchntCd)) {
				c.andChnlMchntCdEqualTo(chnlMchntCd);
			}
			// ????????????:??????
			String status = StringUtil.trim(qryParamMap.get("status"));
			if (!StringUtil.isBlank(status)) {
				c.andStatusEqualTo(status);
			}
		}
		// ????????????
		example.setOrderByClause("rec_upd_ts desc");
		return example;
	}

	@Override
	public int initPriorityRoutings(String ridPrefix, List<Map<String,String>> routingList, boolean deleteLast, String optUser) {
		
		if (deleteLast) {
			//???????????????
			Map<String, String> qryParamMap = new HashMap<String, String>();
			qryParamMap.put("transSeqId", ridPrefix);
			RoutingPriorityExample example = this.getQryExample(qryParamMap);
			List<RoutingPriority> routingPriority = dao().selectByExample(example);
			if (routingPriority != null) {
				dao().deleteByExample(example);
				info("[withdraw] ?????????????????????");
			}
		}

		if (routingList != null) {
			//??????????????????tbl_routing_priority
			for (int i = 0; i < routingList.size(); i++) {
				Map<String,String> routingMap = routingList.get(i);
				RoutingPriorityExt rp = new RoutingPriorityExt();
				rp.setTransSeqId(routingMap.get(INTER_MSG.routingId));
				rp.setIntTransCd(SettleEnums.SettleTxnType._5210.getCode());
				rp.setChnlId(routingMap.get(CHNL_MSG.channel));
				rp.setChnlMchntCd(routingMap.get(CHNL_MSG.chnlMerId));
				rp.setPriority(10);
				rp.setWeight(100);
				rp.setStatus(COMMON_STATE.YSE);
				rp.setLastOperId(optUser);
				rp.setRecCrtTs(new Date());
				rp.setRecUpdTs(new Date());
				dao().insertSelectiveExt(rp);
				info("[withdraw] ????????????: ??????ID:%s, ????????????:%s, ????????????:%s, ???????????????:%s, ????????????:%s, ??????:%s, ??????:%s", routingMap.get(INTER_MSG.routingId), SettleEnums.SettleTxnType._5210.getCode(), routingMap.get(CHNL_MSG.channel), routingMap.get(CHNL_MSG.chnlMerId), 10, 100, COMMON_STATE.YSE);
			}
		}
		return 0;
	}

}
