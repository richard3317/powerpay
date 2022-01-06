package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfo;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfoExample;
import com.icpay.payment.db.dao.mybatis.model.VirtualTermInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VirtualTermInfoMapper {
    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    long countByExample(VirtualTermInfoExample example);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int deleteByExample(VirtualTermInfoExample example);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VirtualTermInfoKey key);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int insert(VirtualTermInfo record);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int insertSelective(VirtualTermInfo record);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    List<VirtualTermInfo> selectByPage(VirtualTermInfoExample example);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    List<VirtualTermInfo> selectByExample(VirtualTermInfoExample example);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    VirtualTermInfo selectByPrimaryKey(VirtualTermInfoKey key);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VirtualTermInfo record, @Param("example") VirtualTermInfoExample example);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VirtualTermInfo record, @Param("example") VirtualTermInfoExample example);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VirtualTermInfo record);

    /**
     * Database table : tbl_virtual_term_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VirtualTermInfo record);
}