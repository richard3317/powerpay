package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.AgentMchntTxn;
import com.icpay.payment.db.dao.mybatis.model.AgentMchntTxnExample;
import com.icpay.payment.db.dao.mybatis.model.modelExt.AgentMchntTxnSummary;
public interface AgentMchntTxnExtMapper {
    /**
     * Database table : view_agent_mchnt_txn_01
     *
     * @mbg.generated
     */
    long countByExample(@Param("example")  AgentMchntTxnExample example, @Param("mon") String mon);

    /**
     * Database table : view_agent_mchnt_txn_01
     *
     * @mbg.generated
     */
    List<AgentMchntTxn> selectByPage(@Param("example") AgentMchntTxnExample example, @Param("mon") String mon);

    /**
     * Database table : view_agent_mchnt_txn_01
     *
     * @mbg.generated
     */
    List<AgentMchntTxn> selectByExample(@Param("example") AgentMchntTxnExample example, @Param("mon") String mon);
    
    /**
     * 获取符合条件的加总值
     * @param example
     * @param mon
     * @return
     */
    AgentMchntTxnSummary selectSummary(@Param("example") AgentMchntTxnExample example, @Param("mon") String mon);
    
}