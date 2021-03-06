package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RoutingPriority;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriorityExample;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriorityKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.RoutingPriorityExt;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoutingPriorityMapper {
    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    long countByExample(RoutingPriorityExample example);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int deleteByExample(RoutingPriorityExample example);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RoutingPriorityKey key);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int insert(RoutingPriority record);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int insertSelective(RoutingPriority record);
    
    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int insertSelectiveExt(RoutingPriorityExt record);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    List<RoutingPriority> selectByPage(RoutingPriorityExample example);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    List<RoutingPriority> selectByExample(RoutingPriorityExample example);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    RoutingPriority selectByPrimaryKey(RoutingPriorityKey key);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RoutingPriority record, @Param("example") RoutingPriorityExample example);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RoutingPriority record, @Param("example") RoutingPriorityExample example);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RoutingPriority record);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RoutingPriority record);
}