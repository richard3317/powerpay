package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MerParamsListv;
import com.icpay.payment.db.dao.mybatis.model.MerParamsListvExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerParamsListvMapper {
    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    long countByExample(MerParamsListvExample example);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int deleteByExample(MerParamsListvExample example);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int insert(MerParamsListv record);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int insertSelective(MerParamsListv record);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    List<MerParamsListv> selectByPage(MerParamsListvExample example);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    List<MerParamsListv> selectByExample(MerParamsListvExample example);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    MerParamsListv selectByPrimaryKey(Long id);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MerParamsListv record, @Param("example") MerParamsListvExample example);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MerParamsListv record, @Param("example") MerParamsListvExample example);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MerParamsListv record);

    /**
     * Database table : tbl_mer_params_listv
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MerParamsListv record);
}