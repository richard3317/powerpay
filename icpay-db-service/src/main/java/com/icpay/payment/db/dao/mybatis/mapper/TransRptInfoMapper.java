package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TransRptInfo;
import com.icpay.payment.db.dao.mybatis.model.TransRptInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransRptInfoMapper {
    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    long countByExample(TransRptInfoExample example);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int deleteByExample(TransRptInfoExample example);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer rptId);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int insert(TransRptInfo record);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int insertSelective(TransRptInfo record);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    List<TransRptInfo> selectByPage(TransRptInfoExample example);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    List<TransRptInfo> selectByExample(TransRptInfoExample example);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    TransRptInfo selectByPrimaryKey(Integer rptId);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TransRptInfo record, @Param("example") TransRptInfoExample example);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TransRptInfo record, @Param("example") TransRptInfoExample example);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TransRptInfo record);

    /**
     * Database table : tbl_trans_rpt_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TransRptInfo record);
}