package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.TelegramMerchatidMapper;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatid;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatidExample;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatidExample.Criteria;
import com.icpay.payment.db.service.ITelegramMerchatidService;

@Service("telegramMerchatidService")
public class TelegramMerchatidService extends BaseService implements ITelegramMerchatidService {
	
	
	
	/**
	 * 新增
	 * @param usrInfo
	 * @return 
	 */
	@Transactional
	public int add(TelegramMerchatid userInfo) {
		AssertUtil.argIsNull(userInfo, "telegram-Bind待新增的用户信息对象为null");
		TelegramMerchatidMapper mapper = this.getMapper();
		TelegramMerchatid u = mapper.selectByPrimaryKey(userInfo.getMchntCd());
		AssertUtil.objIsNotNull(u, "telegram-Bind待新增的用户信息已存在");
		int ok =  mapper.insert(userInfo);
		return ok;
		
	}
	private TelegramMerchatidMapper getMapper() {
		return this.getMapper(TelegramMerchatidMapper.class);
	}
		
	@Override
	public List<TelegramMerchatid> select(Map<String, String> qryParamMap) {
		TelegramMerchatidExample example = this.buildQryExample(qryParamMap);
		return getMapper().selectByExample(example);
	}
	@Override
	protected TelegramMerchatidExample buildQryExample(Map<String, String> qryParamMap) {
		TelegramMerchatidExample example = new TelegramMerchatidExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c=example.createCriteria();
			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdLike("%" + mchntCd + "%");
			}
			
			String chatId = qryParamMap.get("chatId");
			if (!StringUtil.isBlank(chatId)) {
				c.andChatIdEqualTo(chatId);
			}
			
			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
				c.andStateLike("%" + state + "%");
			}
			
		}
		// 排序字段
		example.setOrderByClause("mchnt_cd");
		return example;
	}

}
