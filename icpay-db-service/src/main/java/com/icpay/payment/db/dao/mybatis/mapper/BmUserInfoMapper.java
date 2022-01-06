package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BmUserInfo;
import com.icpay.payment.db.dao.mybatis.model.BmUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BmUserInfoMapper {
    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    long countByExample(BmUserInfoExample example);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int deleteByExample(BmUserInfoExample example);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String usrId);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int insert(BmUserInfo record);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int insertSelective(BmUserInfo record);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    List<BmUserInfo> selectByPage(BmUserInfoExample example);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    List<BmUserInfo> selectByExample(BmUserInfoExample example);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    BmUserInfo selectByPrimaryKey(String usrId);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BmUserInfo record, @Param("example") BmUserInfoExample example);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BmUserInfo record, @Param("example") BmUserInfoExample example);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BmUserInfo record);

    /**
     * Database table : tbl_bm_user_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BmUserInfo record);
}