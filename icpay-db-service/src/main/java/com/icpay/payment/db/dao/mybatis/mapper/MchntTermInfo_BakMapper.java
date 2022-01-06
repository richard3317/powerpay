package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MchntTermInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfoExample;
import com.icpay.payment.db.dao.mybatis.model.MchntTermInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@Deprecated
public interface MchntTermInfo_BakMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int countByExample(MchntTermInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int deleteByExample(MchntTermInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int deleteByPrimaryKey(MchntTermInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int insert(MchntTermInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int insertSelective(MchntTermInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    List<MchntTermInfo> selectByExample(MchntTermInfoExample example);
    
    List<MchntTermInfo> selectByPage(MchntTermInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    MchntTermInfo selectByPrimaryKey(MchntTermInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int updateByExampleSelective(@Param("record") MchntTermInfo record, @Param("example") MchntTermInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int updateByExample(@Param("record") MchntTermInfo record, @Param("example") MchntTermInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int updateByPrimaryKeySelective(MchntTermInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_mchnt_term_info
     *
     * @mbggenerated Thu Apr 02 21:31:54 CST 2015
     */
    int updateByPrimaryKey(MchntTermInfo record);
}