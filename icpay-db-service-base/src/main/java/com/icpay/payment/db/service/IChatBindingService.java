package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChatBinding;
import com.icpay.payment.db.dao.mybatis.model.ChatBindingKey;

public interface IChatBindingService {

	Pager<ChatBinding> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);

    List<ChatBinding> select(Map<String, String> qryParamMap);

    long count(Map<String, String> qryParamMap);
    
    int add(ChatBinding record);
    
    int update(ChatBinding record);

    int delete(ChatBindingKey key);

    ChatBinding selectByPrimaryKey(ChatBindingKey key);

}
