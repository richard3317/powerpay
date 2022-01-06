package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AcctChkResultTotal;
import com.icpay.payment.db.dao.mybatis.model.AcctChkResultTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AcctChkResultTotalMapper {
    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    long countByExample(AcctChkResultTotalExample example);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int deleteByExample(AcctChkResultTotalExample example);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int insert(AcctChkResultTotal record);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int insertSelective(AcctChkResultTotal record);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    List<AcctChkResultTotal> selectByPage(AcctChkResultTotalExample example);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    List<AcctChkResultTotal> selectByExample(AcctChkResultTotalExample example);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    AcctChkResultTotal selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") AcctChkResultTotal record, @Param("example") AcctChkResultTotalExample example);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") AcctChkResultTotal record, @Param("example") AcctChkResultTotalExample example);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AcctChkResultTotal record);

    /**
     * Database table : tbl_acct_chk_result_total
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AcctChkResultTotal record);
}