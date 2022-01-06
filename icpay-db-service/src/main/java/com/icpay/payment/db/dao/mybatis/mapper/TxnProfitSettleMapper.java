package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TxnProfitSettle;
import com.icpay.payment.db.dao.mybatis.model.TxnProfitSettleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TxnProfitSettleMapper {
    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    long countByExample(TxnProfitSettleExample example);

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    List<TxnProfitSettle> selectByPage(TxnProfitSettleExample example);

    /**
     * Database table : view_txn_profit_settle
     *
     * @mbg.generated
     */
    List<TxnProfitSettle> selectByExample(TxnProfitSettleExample example);
}