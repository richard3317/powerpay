package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.EventLog;
import com.icpay.payment.db.dao.mybatis.model.EventLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EventLogMapper {
    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    long countByExample(EventLogExample example);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int deleteByExample(EventLogExample example);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int insert(EventLog record);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int insertSelective(EventLog record);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    List<EventLog> selectByPage(EventLogExample example);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    List<EventLog> selectByExample(EventLogExample example);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    EventLog selectByPrimaryKey(Long logId);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") EventLog record, @Param("example") EventLogExample example);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") EventLog record, @Param("example") EventLogExample example);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(EventLog record);

    /**
     * Database table : tbl_event_log_01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(EventLog record);
}