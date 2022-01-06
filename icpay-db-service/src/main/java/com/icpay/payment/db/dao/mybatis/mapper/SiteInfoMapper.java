package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.SiteInfo;
import com.icpay.payment.db.dao.mybatis.model.SiteInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteInfoMapper {
    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    long countByExample(SiteInfoExample example);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int deleteByExample(SiteInfoExample example);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String siteId);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int insert(SiteInfo record);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int insertSelective(SiteInfo record);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    List<SiteInfo> selectByPage(SiteInfoExample example);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    List<SiteInfo> selectByExample(SiteInfoExample example);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    SiteInfo selectByPrimaryKey(String siteId);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SiteInfo record, @Param("example") SiteInfoExample example);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SiteInfo record, @Param("example") SiteInfoExample example);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SiteInfo record);

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SiteInfo record);
}