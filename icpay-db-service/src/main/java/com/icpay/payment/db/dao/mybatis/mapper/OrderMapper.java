package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.Order;
import com.icpay.payment.db.dao.mybatis.model.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    long countByExample(OrderExample example);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int deleteByExample(OrderExample example);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String orderSeqId);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int insert(Order record);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int insertSelective(Order record);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    List<Order> selectByPage(OrderExample example);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    List<Order> selectByExample(OrderExample example);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    Order selectByPrimaryKey(String orderSeqId);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * Database table : tbl_order01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Order record);
}