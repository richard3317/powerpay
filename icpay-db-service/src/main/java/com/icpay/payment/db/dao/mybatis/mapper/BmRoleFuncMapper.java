package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BmRoleFunc;
import com.icpay.payment.db.dao.mybatis.model.BmRoleFuncExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BmRoleFuncMapper {
    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    long countByExample(BmRoleFuncExample example);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int deleteByExample(BmRoleFuncExample example);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer roleFuncId);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int insert(BmRoleFunc record);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int insertSelective(BmRoleFunc record);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    List<BmRoleFunc> selectByPage(BmRoleFuncExample example);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    List<BmRoleFunc> selectByExample(BmRoleFuncExample example);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    BmRoleFunc selectByPrimaryKey(Integer roleFuncId);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BmRoleFunc record, @Param("example") BmRoleFuncExample example);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BmRoleFunc record, @Param("example") BmRoleFuncExample example);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BmRoleFunc record);

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BmRoleFunc record);
}