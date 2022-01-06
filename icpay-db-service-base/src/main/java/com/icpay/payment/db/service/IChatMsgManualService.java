package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;
import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.ChatMsgManual;
import com.icpay.payment.db.dao.mybatis.model.ChatMsgManualKey;

public interface IChatMsgManualService {

	Pager<ChatMsgManual> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);

    List<ChatMsgManual> select(Map<String, String> qryParamMap);

    long count(Map<String, String> qryParamMap);
    
    int add(ChatMsgManual record);
    
    int update(ChatMsgManual record);

    int delete(ChatMsgManualKey key);

    ChatMsgManual selectByPrimaryKey(ChatMsgManualKey key);

}
