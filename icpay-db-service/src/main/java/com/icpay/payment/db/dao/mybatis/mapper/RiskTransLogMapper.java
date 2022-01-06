package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RiskTransLog;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RiskTransLogMapper {
    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    long countByExample(RiskTransLogExample example);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int deleteByExample(RiskTransLogExample example);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long logId);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int insert(RiskTransLog record);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int insertSelective(RiskTransLog record);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    List<RiskTransLog> selectByPage(RiskTransLogExample example);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    List<RiskTransLog> selectByExample(RiskTransLogExample example);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    RiskTransLog selectByPrimaryKey(Long logId);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RiskTransLog record, @Param("example") RiskTransLogExample example);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RiskTransLog record, @Param("example") RiskTransLogExample example);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RiskTransLog record);

    /**
     * Database table : tbl_risk_trans_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RiskTransLog record);
}