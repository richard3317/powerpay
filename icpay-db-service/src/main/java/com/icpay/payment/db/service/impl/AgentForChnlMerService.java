package com.icpay.payment.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.data.utils.TxnDataUtils;
import com.icpay.payment.db.dao.mybatis.mapper.AgentForChnlMerMapper;
import com.icpay.payment.db.dao.mybatis.model.AgentForChnlMer;
import com.icpay.payment.db.dao.mybatis.model.AgentForChnlMerExample;
import com.icpay.payment.db.dao.mybatis.model.MchntInfo;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentForChnlMerEx;
import com.icpay.payment.db.service.IAgentForChnlMerService;
import com.icpay.payment.db.dao.mybatis.mapper.MchntInfoMapper;

@Service("agentForChnlMerService")
public class AgentForChnlMerService  extends BaseService implements IAgentForChnlMerService {

	@Override
	public List<AgentForChnlMer> selectByAgent(String agentCd) {
		AgentForChnlMerExample example = new AgentForChnlMerExample();
		example.createCriteria().andAgentCdEqualTo(agentCd);
		//example.setOrderByClause("chnl_id,chnl_mchnt_cd");
		return getMapper().selectByExample(example);
	}

	@Override
	public List<AgentForChnlMerEx> selectByAgentEx(String agentCd) {
		List<AgentForChnlMer> list = selectByAgent(agentCd);
		if (list==null) return null;
		List<AgentForChnlMerEx> resList=new ArrayList<>();
		for(AgentForChnlMer item: list) {
			AgentForChnlMerEx rec = new AgentForChnlMerEx();
			rec.cloneFrom(item);
			String merId=TxnDataUtils.getAgentProfitMerIdByChnlMer(item.getChnlId(), item.getChnlMchntCd(), item.getAgentCd());
			rec.setFrontMchntCd(merId);
			rec.setFrontMchntDesc(queryMchntDesc(merId));
			resList.add(rec);
		}
		return resList;
	}
	
	protected String queryMchntDesc(String mchntCd) {
		MchntInfoMapper dao = this.getMapper(MchntInfoMapper.class);
		MchntInfo rec = dao.selectByPrimaryKey(mchntCd);
		if (rec==null) return null;
		return rec.getMchntCnNm();
	}
	
	private AgentForChnlMerMapper getMapper() {
		return this.getMapper(AgentForChnlMerMapper.class);
	}

}
