package com.icpay.payment.db.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.common.utils.AssertUtil;
import com.icpay.payment.common.utils.StringUtil;
import com.icpay.payment.common.utils.Utils;
import com.icpay.payment.db.dao.mybatis.mapper.ChatBindingMapper;
import com.icpay.payment.db.dao.mybatis.model.ChatBinding;
import com.icpay.payment.db.dao.mybatis.model.ChatBindingExample;
import com.icpay.payment.db.dao.mybatis.model.ChatBindingExample.Criteria;
import com.icpay.payment.db.dao.mybatis.model.ChatBindingKey;
import com.icpay.payment.db.service.IChatBindingService;

@Service("chatBindingService")
public class ChatBindingService extends BaseService implements IChatBindingService {

	@Override
	protected ChatBindingExample buildQryExample(Map<String, String> qryParamMap) {
		ChatBindingExample example = new ChatBindingExample();
		if (qryParamMap != null && !qryParamMap.isEmpty()) {
			Criteria c = example.createCriteria();

			String category = qryParamMap.get("category");
			if (!StringUtil.isBlank(category)) {
				c.andCategoryEqualTo(category);
			}

			String mchntCd = qryParamMap.get("mchntCd");
			if (!StringUtil.isBlank(mchntCd)) {
				c.andMchntCdEqualTo(mchntCd);
			}

			String chatId = qryParamMap.get("chatId");
			if (!StringUtil.isBlank(chatId)) {
				c.andChatIdEqualTo(chatId);
			}

			String state = qryParamMap.get("state");
			if (!StringUtil.isBlank(state)) {
				c.andStateEqualTo(state);
			}

			String tags = qryParamMap.get("tags");
			if (!StringUtil.isBlank(tags)) {
				c.andTagsEqualTo(tags);
			}
//			if (!StringUtil.isBlank(tags)) { //增加多標籤查詢支援
//				c.andCategoryIn(Utils.strSplitToList(tags, ";", true));
//			}
		}
		// 排序字段
		example.setOrderByClause("category, mchnt_cd, chat_id");
		return example;
	}

	@Override
	public Pager<ChatBinding> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap) {
		ChatBindingExample example = this.getQryExample(qryParamMap);
		ChatBindingMapper mapper = getMapper();
		Pager<ChatBinding> pager = buildPager(pageNum, pageSize, qryParamMap);

		example.setStartNum(pager.getStartNum());
		example.setPageSize(pageSize);

		pager.setTotal(mapper.countByExample(example));
		pager.setResultList(mapper.selectByPage(example));
		return pager;
	}

	@Override
	public List<ChatBinding> select(Map<String, String> qryParamMap) {
		ChatBindingExample example = this.getQryExample(qryParamMap);
		return this.getMapper().selectByExample(example);
	}

	@Override
	public ChatBinding selectByPrimaryKey(ChatBindingKey key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	@Override
	public long count(Map<String, String> qryParamMap) {
		ChatBindingExample example = this.getQryExample(qryParamMap);
		return this.getMapper().countByExample(example);
	}

	@Override
	public int add(ChatBinding record) {
		ChatBindingKey key = new ChatBindingKey();
		key.setCategory(record.getCategory());
		key.setChatId(record.getMchntCd());
		key.setMchntCd(record.getChatId());
		AssertUtil.argIsNull(key, "telegram-Bind待新增的用户信息对象为null");

		ChatBindingMapper mapper = this.getMapper();
		ChatBinding u = mapper.selectByPrimaryKey(key);
		AssertUtil.objIsNotNull(u, "telegram-Bind待新增的用户信息已存在");

		return mapper.insertSelective(record);
	}

	@Override
	public int update(ChatBinding record) {
		return this.getMapper().updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(ChatBindingKey key) {
		return this.getMapper().deleteByPrimaryKey(key);
	}

	protected ChatBindingMapper getMapper() {
		return super.getMapper(ChatBindingMapper.class);
	}
}
