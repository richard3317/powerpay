package com.icpay.payment.db.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.TxnRouting;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingExample;
import com.icpay.payment.db.dao.mybatis.model.TxnRoutingKey;
@Deprecated
public interface TxnRouting_BakMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int countByExample(TxnRoutingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int deleteByExample(TxnRoutingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int deleteByPrimaryKey(TxnRoutingKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int insert(TxnRouting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int insertSelective(TxnRouting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    List<TxnRouting> selectByExample(TxnRoutingExample example);
    List<TxnRouting> selectByPage(TxnRoutingExample example);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    TxnRouting selectByPrimaryKey(TxnRoutingKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int updateByExampleSelective(@Param("record") TxnRouting record, @Param("example") TxnRoutingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int updateByExample(@Param("record") TxnRouting record, @Param("example") TxnRoutingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int updateByPrimaryKeySelective(TxnRouting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_txn_routing
     *
     * @mbggenerated Mon Mar 26 16:02:20 CST 2018
     */
    int updateByPrimaryKey(TxnRouting record);
    
    /**
     *	 手动新增
     * 	在路由表上, 透过 交易类型5210, 有效状态, 商户号三者,去找符合的渠道编号
     */
    List<TxnRoutingKey> selectByMchntCd(String mchnt_cd);
}