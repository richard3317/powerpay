package com.icpay.payment.db.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.db.dao.mybatis.mapper.ChatMsgManualMapper;
import com.icpay.payment.db.dao.mybatis.model.ChatMsgManual;
import com.icpay.payment.db.dao.mybatis.model.ChatMsgManualExample;
import com.icpay.payment.db.dao.mybatis.model.ChatMsgManualExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChatMsgManualKey;
import com.icpay.payment.db.service.IChatMsgManualService;

@Service("chatMsgManualService")
public class ChatMsgManualService extends BaseService implements IChatMsgManualService {

	@Override
	protected ChatMsgManualExample buildQryExample(Map<String, String> qryParamMap) {
		ChatMsgManualExample example = new ChatMsgManualExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			String catelog = qryParamMap.get("catelog");
			if (!StringUtil.isBlank(catelog)) {
				c.andCatelogEqualTo(catelog);
			}

			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
//				c.andMchntCdEqualTo(mchntCd);
			}

			String chatId = qryParamMap.get("chatId");
			if (!StringUtil.isBlank(chatId)) {
//				c.andChatIdEqualTo(chatId);
			}

			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}

			String tags = qryParamMap.get("tags");
//			if (!StringUtil.isBlank(tags)) {
//				c.andTagsEqualTo(tags);
//			}
			if (!StringUtil.isBlank(tags)) { //增加多標籤查詢支援
//				c.andCategoryIn(Utils.strSplitToList(tags, ";", true));
			}
		}
		// 排序字段
		example.setOrderByClause("category, mchnt_cd, chat_id");
		return example;
	}

	@Override
	public Pager<ChatMsgManual> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		ChatMsgManualExample example = this.getQryExample(qryParamMap);
		ChatMsgManualMapper mapper = getMapper();
		Pager<ChatMsgManual> pager = buildPager(pageNum, pageSize, qryParamMap);

		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public List<ChatMsgManual> select(Map<String, String> qryParamMap) {
		ChatMsgManualExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public ChatMsgManual selectByPrimaryKey(ChatMsgManualKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public long count(Map<String, String> qryParamMap) {
		ChatMsgManualExample example = this.getQryExample(qryParamMap);
		return this.getMapper().countByExample(example);
	}

	@Override
	public int add(ChatMsgManual record) {
		ChatMsgManualKey key = new ChatMsgManualKey();
//		key.setCategory(record.getCategory());
//		key.setChatId(record.getMchntCd());
//		key.setMchntCd(record.getChatId());
		AssertUtil.argIsNull(key, "telegram-Bind待新增的用户信息对象为null");

		ChatMsgManualMapper mapper = this.getMapper();
		ChatMsgManual u = mapper.selectByPrimaryKey(key);
		AssertUtil.objIsNotNull(u, "telegram-Bind待新增的用户信息已存在");

		return mapper.insertSelective(record);
	}

	@Override
	public int update(ChatMsgManual record) {
		return this.getMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(ChatMsgManualKey key) {
		return this.getMapper().deleteByPrimaryKey(key);
	}

	protected ChatMsgManualMapper getMapper() {
		return super.getMapper(ChatMsgManualMapper.class);
	}


}
