package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.TransLog;
import com.icpay.payment.db.dao.mybatis.model.TransLogExample;
import com.icpay.payment.db.dao.mybatis.model.WithdrawLogExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.TransLogMapping;
import com.icpay.payment.db.dao.mybatis.model.modelExt.WithdrawLogMapping;

/** 
* @author  作者 : wangyi
* @date 创建时间：2018年3月30日 上午12:52:15 
* @version 1.0 
* @parameter  
* @since  
* @return 
*/
//重構時，將所有原TransLogMapper手動修改的方法，全數寫入此CLASS
public interface TransLogExt2Mapper {
	
	int countByExample(@Param("example") TransLogExample example, @Param("mon") String mon);

    List<TransLog> selectByExample(@Param("example") TransLogExample example, @Param("mon") String mon);
    
    List<TransLog> selectByPage(@Param("example") TransLogExample example, @Param("mon") String mon);

    TransLog selectByPrimaryKey(@Param("transSeqId") String transSeqId, @Param("mon") String mon);
    
    TransLog countTranAt(@Param("example") TransLogExample example, @Param("mon") String mon);
    
    int updateTxnTags(@Param("transSeqId")String transSeqId, @Param("mon") String mon, @Param("tags") String tags);

    int updateTxnState(@Param("example") TransLog log, @Param("mon") String mon);
    
    int updateState(@Param("example") TransLog log, @Param("mon") String mon);
	
	/**
	 * 交易查询联查
	 */
	List<TransLogMapping> selectTransLogMappingByPage(@Param("example") TransLogExample example, @Param("mon") String mon);

	/**
	 * 分页查询取现信息
	 */
	public List<WithdrawLogMapping> selectWithdrawLogMappingByPage(@Param("example") WithdrawLogExample example, @Param("mon") String mon);
	
	/**
	 * 导出流水
	 * @param example
	 * @param mon
	 * @return
	 */
	List<TransLogMapping> selectTransLogMappingByExample(@Param("example") TransLogExample example, @Param("mon") String mon);

	/**
	 * 导出取现流水
	 * @param example
	 * @param mon
	 * @return
	 */
	List<WithdrawLogMapping> selectWithdrawLogMappingByExample(@Param("example") WithdrawLogExample example, @Param("mon") String mon);
	
	/**
	 * 取现流水详情
	 * @param orderSeqId
	 * @param mon
	 * @return
	 */
	
}
