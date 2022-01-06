package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AcctChkResult;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AcctChkResultMapper {
    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    long countByExample(AcctChkResultExample example);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int deleteByExample(AcctChkResultExample example);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int insert(AcctChkResult record);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int insertSelective(AcctChkResult record);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    List<AcctChkResult> selectByPage(AcctChkResultExample example);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    List<AcctChkResult> selectByExample(AcctChkResultExample example);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    AcctChkResult selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AcctChkResult record, @Param("example") AcctChkResultExample example);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AcctChkResult record, @Param("example") AcctChkResultExample example);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AcctChkResult record);

    /**
     * Database table : tbl_acct_chk_result
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AcctChkResult record);
}