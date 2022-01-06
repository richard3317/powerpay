package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TxnRoutingInfo;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TxnRoutingInfoMapper {
    /**
     * Database table : view_txn_routing_info
     *
     * @mbg.generated
     */
    long countByExample(TxnRoutingInfoExample example);

    /**
     * Database table : view_txn_routing_info
     *
     * @mbg.generated
     */
    List<TxnRoutingInfo> selectByPage(TxnRoutingInfoExample example);

    /**
     * Database table : view_txn_routing_info
     *
     * @mbg.generated
     */
    List<TxnRoutingInfo> selectByExample(TxnRoutingInfoExample example);
}