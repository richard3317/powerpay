package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.Tracelog;
import com.icpay.payment.db.dao.mybatis.model.TracelogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TracelogMapper {
    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    long countByExample(TracelogExample example);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int deleteByExample(TracelogExample example);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int insert(Tracelog record);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int insertSelective(Tracelog record);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    List<Tracelog> selectByPage(TracelogExample example);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    List<Tracelog> selectByExample(TracelogExample example);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    Tracelog selectByPrimaryKey(Long logId);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Tracelog record, @Param("example") TracelogExample example);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Tracelog record, @Param("example") TracelogExample example);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Tracelog record);

    /**
     * Database table : tbl_tracelog_01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Tracelog record);
}