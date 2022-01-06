package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AcctChkTransLog;
import com.icpay.payment.db.dao.mybatis.model.AcctChkTransLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AcctChkTransLogMapper {
    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    long countByExample(AcctChkTransLogExample example);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int deleteByExample(AcctChkTransLogExample example);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String transSeqId);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int insert(AcctChkTransLog record);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int insertSelective(AcctChkTransLog record);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    List<AcctChkTransLog> selectByPage(AcctChkTransLogExample example);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    List<AcctChkTransLog> selectByExample(AcctChkTransLogExample example);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    AcctChkTransLog selectByPrimaryKey(String transSeqId);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AcctChkTransLog record, @Param("example") AcctChkTransLogExample example);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AcctChkTransLog record, @Param("example") AcctChkTransLogExample example);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AcctChkTransLog record);

    /**
     * Database table : tbl_acct_chk_trans_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AcctChkTransLog record);
}