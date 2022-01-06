package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RptInfo;
import com.icpay.payment.db.dao.mybatis.model.RptInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RptInfoMapper {
    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    long countByExample(RptInfoExample example);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int deleteByExample(RptInfoExample example);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int insert(RptInfo record);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int insertSelective(RptInfo record);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    List<RptInfo> selectByPage(RptInfoExample example);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    List<RptInfo> selectByExample(RptInfoExample example);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    RptInfo selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RptInfo record, @Param("example") RptInfoExample example);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RptInfo record, @Param("example") RptInfoExample example);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RptInfo record);

    /**
     * Database table : tbl_rpt_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RptInfo record);
}