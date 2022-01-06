package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TermInfo;
import com.icpay.payment.db.dao.mybatis.model.TermInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TermInfoMapper {
    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    long countByExample(TermInfoExample example);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int deleteByExample(TermInfoExample example);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer termId);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int insert(TermInfo record);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int insertSelective(TermInfo record);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    List<TermInfo> selectByPage(TermInfoExample example);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    List<TermInfo> selectByExample(TermInfoExample example);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    TermInfo selectByPrimaryKey(Integer termId);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TermInfo record, @Param("example") TermInfoExample example);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TermInfo record, @Param("example") TermInfoExample example);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TermInfo record);

    /**
     * Database table : tbl_term_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TermInfo record);
}