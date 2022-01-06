package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BmOperLog;
import com.icpay.payment.db.dao.mybatis.model.BmOperLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BmOperLogMapper {
    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    long countByExample(BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int deleteByExample(BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer logId);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int insert(BmOperLog record);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int insertSelective(BmOperLog record);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    List<BmOperLog> selectByPageWithBLOBs(BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    List<BmOperLog> selectByExampleWithBLOBs(BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    List<BmOperLog> selectByPage(BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    List<BmOperLog> selectByExample(BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    BmOperLog selectByPrimaryKey(Integer logId);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BmOperLog record, @Param("example") BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") BmOperLog record, @Param("example") BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BmOperLog record, @Param("example") BmOperLogExample example);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BmOperLog record);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(BmOperLog record);

    /**
     * Database table : tbl_bm_oper_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BmOperLog record);
}