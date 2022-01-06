package com.icpay.payment.db.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TransLogExample;
@Deprecated
public interface TransLog_BakMapper {
	
	int countByExample(@Param("example") TransLogExample example, @Param("mon") String mon);

    List<TransLog> selectByExample(@Param("example") TransLogExample example, @Param("mon") String mon);
    
    List<TransLog> selectByPage(@Param("example") TransLogExample example, @Param("mon") String mon);

    TransLog selectByPrimaryKey(@Param("transSeqId") String transSeqId, @Param("mon") String mon);
    
    TransLog countTranAt(@Param("example") TransLogExample example, @Param("mon") String mon);
    
    int updateTxnTags(@Param("transSeqId")String transSeqId, @Param("mon") String mon, @Param("tags") String tags);

    int updateTxnState(@Param("example") TransLog log, @Param("mon") String mon);
    
    int updateState(@Param("example") TransLog log, @Param("mon") String mon);
}
