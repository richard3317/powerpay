package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ProfitResFile;
import com.icpay.payment.db.dao.mybatis.model.ProfitResFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfitResFileMapper {
    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    long countByExample(ProfitResFileExample example);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int deleteByExample(ProfitResFileExample example);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int insert(ProfitResFile record);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int insertSelective(ProfitResFile record);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    List<ProfitResFile> selectByPage(ProfitResFileExample example);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    List<ProfitResFile> selectByExample(ProfitResFileExample example);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    ProfitResFile selectByPrimaryKey(Integer seqId);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ProfitResFile record, @Param("example") ProfitResFileExample example);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ProfitResFile record, @Param("example") ProfitResFileExample example);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProfitResFile record);

    /**
     * Database table : tbl_profit_res_file
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProfitResFile record);
}