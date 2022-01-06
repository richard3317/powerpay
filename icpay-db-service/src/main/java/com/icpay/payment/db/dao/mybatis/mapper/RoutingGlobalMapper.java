package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RoutingGlobal;
import com.icpay.payment.db.dao.mybatis.model.RoutingGlobalExample;
import com.icpay.payment.db.dao.mybatis.model.RoutingGlobalKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoutingGlobalMapper {
    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    long countByExample(RoutingGlobalExample example);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int deleteByExample(RoutingGlobalExample example);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RoutingGlobalKey key);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int insert(RoutingGlobal record);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int insertSelective(RoutingGlobal record);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    List<RoutingGlobal> selectByPage(RoutingGlobalExample example);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    List<RoutingGlobal> selectByExample(RoutingGlobalExample example);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    RoutingGlobal selectByPrimaryKey(RoutingGlobalKey key);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RoutingGlobal record, @Param("example") RoutingGlobalExample example);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RoutingGlobal record, @Param("example") RoutingGlobalExample example);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RoutingGlobal record);

    /**
     * Database table : tbl_routing_global
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RoutingGlobal record);
}