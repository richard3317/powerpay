package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RiskTransLog;
import com.icpay.payment.db.dao.mybatis.model.RiskTransLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@Deprecated
public interface RiskTransLog_BakMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int countByExample(RiskTransLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int deleteByExample(RiskTransLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int deleteByPrimaryKey(String transSeqId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int insert(RiskTransLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int insertSelective(RiskTransLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    List<RiskTransLog> selectByExample(RiskTransLogExample example);
    
    List<RiskTransLog> selectByPage(RiskTransLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    RiskTransLog selectByPrimaryKey(String transSeqId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int updateByExampleSelective(@Param("record") RiskTransLog record, @Param("example") RiskTransLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int updateByExample(@Param("record") RiskTransLog record, @Param("example") RiskTransLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int updateByPrimaryKeySelective(RiskTransLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_risk_trans_log
     *
     * @mbggenerated Thu Mar 19 00:35:18 CST 2015
     */
    int updateByPrimaryKey(RiskTransLog record);
}