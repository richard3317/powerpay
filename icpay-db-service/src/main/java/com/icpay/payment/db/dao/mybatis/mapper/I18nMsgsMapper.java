package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.I18nMsgs;
import com.icpay.payment.db.dao.mybatis.model.I18nMsgsExample;
import com.icpay.payment.db.dao.mybatis.model.I18nMsgsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface I18nMsgsMapper {
    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    long countByExample(I18nMsgsExample example);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int deleteByExample(I18nMsgsExample example);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(I18nMsgsKey key);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int insert(I18nMsgs record);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int insertSelective(I18nMsgs record);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    List<I18nMsgs> selectByPage(I18nMsgsExample example);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    List<I18nMsgs> selectByExample(I18nMsgsExample example);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    I18nMsgs selectByPrimaryKey(I18nMsgsKey key);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") I18nMsgs record, @Param("example") I18nMsgsExample example);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") I18nMsgs record, @Param("example") I18nMsgsExample example);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(I18nMsgs record);

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(I18nMsgs record);
}