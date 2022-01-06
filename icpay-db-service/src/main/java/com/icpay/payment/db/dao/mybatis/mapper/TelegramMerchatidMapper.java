package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatid;
import com.icpay.payment.db.dao.mybatis.model.TelegramMerchatidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TelegramMerchatidMapper {
    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    long countByExample(TelegramMerchatidExample example);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int deleteByExample(TelegramMerchatidExample example);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String mchntCd);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int insert(TelegramMerchatid record);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int insertSelective(TelegramMerchatid record);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    List<TelegramMerchatid> selectByPage(TelegramMerchatidExample example);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    List<TelegramMerchatid> selectByExample(TelegramMerchatidExample example);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    TelegramMerchatid selectByPrimaryKey(String mchntCd);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TelegramMerchatid record, @Param("example") TelegramMerchatidExample example);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TelegramMerchatid record, @Param("example") TelegramMerchatidExample example);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TelegramMerchatid record);

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TelegramMerchatid record);
}