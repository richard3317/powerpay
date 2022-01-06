package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMap;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapKey;

public interface IBankChnlMapService {

	Pager<BankChnlMap> selectByPage(int pageNum, int pageSize, Map<String, String> qryParamMap);

    List<BankChnlMap> select(Map<String, String> qryParamMap);

    long count(Map<String, String> qryParamMap);
    
    int add(BankChnlMap record);
    
    int update(BankChnlMap record);

    int delete(BankChnlMapKey key);

	BankChnlMap selectByPrimaryKey(BankChnlMapKey key);

	int batchUpdate(Map<String, String> qryParamMap, BankChnlMap bankChnlMap);

}
