package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.RegionInfo;
import com.icpay.payment.db.dao.mybatis.model.RegionInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionInfoMapper {
    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    long countByExample(RegionInfoExample example);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int deleteByExample(RegionInfoExample example);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String regionCd);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int insert(RegionInfo record);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int insertSelective(RegionInfo record);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    List<RegionInfo> selectByPage(RegionInfoExample example);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    List<RegionInfo> selectByExample(RegionInfoExample example);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    RegionInfo selectByPrimaryKey(String regionCd);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RegionInfo record, @Param("example") RegionInfoExample example);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RegionInfo record, @Param("example") RegionInfoExample example);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RegionInfo record);

    /**
     * Database table : tbl_region_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RegionInfo record);
}