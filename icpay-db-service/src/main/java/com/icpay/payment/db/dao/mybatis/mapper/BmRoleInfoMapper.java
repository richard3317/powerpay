package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BmRoleInfo;
import com.icpay.payment.db.dao.mybatis.model.BmRoleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BmRoleInfoMapper {
    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    long countByExample(BmRoleInfoExample example);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int deleteByExample(BmRoleInfoExample example);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer roleId);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int insert(BmRoleInfo record);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int insertSelective(BmRoleInfo record);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    List<BmRoleInfo> selectByPage(BmRoleInfoExample example);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    List<BmRoleInfo> selectByExample(BmRoleInfoExample example);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    BmRoleInfo selectByPrimaryKey(Integer roleId);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BmRoleInfo record, @Param("example") BmRoleInfoExample example);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BmRoleInfo record, @Param("example") BmRoleInfoExample example);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BmRoleInfo record);

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BmRoleInfo record);
}