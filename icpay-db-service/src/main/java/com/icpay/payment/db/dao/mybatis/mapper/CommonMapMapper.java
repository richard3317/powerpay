package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.CommonMap;
import com.icpay.payment.db.dao.mybatis.model.CommonMapExample;
import com.icpay.payment.db.dao.mybatis.model.CommonMapKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonMapMapper {
    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    long countByExample(CommonMapExample example);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int deleteByExample(CommonMapExample example);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(CommonMapKey key);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int insert(CommonMap record);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int insertSelective(CommonMap record);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    List<CommonMap> selectByPage(CommonMapExample example);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    List<CommonMap> selectByExample(CommonMapExample example);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    CommonMap selectByPrimaryKey(CommonMapKey key);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CommonMap record, @Param("example") CommonMapExample example);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CommonMap record, @Param("example") CommonMapExample example);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CommonMap record);

    /**
     * Database table : tbl_common_map
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CommonMap record);
}