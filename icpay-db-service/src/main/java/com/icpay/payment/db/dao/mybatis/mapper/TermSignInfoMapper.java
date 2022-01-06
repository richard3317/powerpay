package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TermSignInfo;
import com.icpay.payment.db.dao.mybatis.model.TermSignInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TermSignInfoMapper {
    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    long countByExample(TermSignInfoExample example);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    int deleteByExample(TermSignInfoExample example);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    int insert(TermSignInfo record);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    int insertSelective(TermSignInfo record);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    List<TermSignInfo> selectByPage(TermSignInfoExample example);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    List<TermSignInfo> selectByExample(TermSignInfoExample example);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TermSignInfo record, @Param("example") TermSignInfoExample example);

    /**
     * Database table : tbl_term_sign_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TermSignInfo record, @Param("example") TermSignInfoExample example);
}