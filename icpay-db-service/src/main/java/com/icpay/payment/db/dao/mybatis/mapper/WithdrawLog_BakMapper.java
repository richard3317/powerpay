
package com.icpay.payment.db.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.WithdrawLog;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLogExample;

@Deprecated
public interface WithdrawLog_BakMapper {
    int countByExample(@Param("example") WithdrawLogExample example , @Param("mon") String mon);

    int deleteByExample(@Param("example") WithdrawLogExample example , @Param("mon") String mon);

    int deleteByPrimaryKey(@Param("orderSeqId") String orderSeqId , @Param("mon") String mon);

    int insert(@Param("record") WithdrawLog record , @Param("mon") String mon);

    int insertSelective(@Param("record") WithdrawLog record , @Param("mon") String mon);

    List<WithdrawLog> selectByExample(@Param("example") WithdrawLogExample example , @Param("mon") String mon);

    int updateByExampleSelective(@Param("record") WithdrawLog record, @Param("example") WithdrawLogExample example,@Param("mon") String mon);

    int updateByExample(@Param("record") WithdrawLog record, @Param("example") WithdrawLogExample example,@Param("mon") String mon);

    int updateByPrimaryKeySelective(@Param("record") WithdrawLog record , @Param("mon") String mon);

    int updateByPrimaryKey( @Param("record") WithdrawLog record , @Param("mon") String mon);

	WithdrawLog selectByPrimaryKey(@Param("orderSeqId") String orderSeqId, @Param("mon") String mon);
	
	List<WithdrawLog> selectByPage(@Param("example") WithdrawLogExample example, @Param("mon") String mon);
	
	List<WithdrawLog> selectWithdrawLogByPage(@Param("example") WithdrawLogExample example, @Param("mon") String mon);
	
}
