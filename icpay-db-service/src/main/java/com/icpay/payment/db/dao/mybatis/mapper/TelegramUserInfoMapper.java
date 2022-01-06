package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.TelegramUserInfo;
import com.icpay.payment.db.dao.mybatis.model.TelegramUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TelegramUserInfoMapper {
    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    long countByExample(TelegramUserInfoExample example);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    int deleteByExample(TelegramUserInfoExample example);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    int insert(TelegramUserInfo record);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    int insertSelective(TelegramUserInfo record);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    List<TelegramUserInfo> selectByPage(TelegramUserInfoExample example);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    List<TelegramUserInfo> selectByExample(TelegramUserInfoExample example);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TelegramUserInfo record, @Param("example") TelegramUserInfoExample example);

    /**
     * Database table : tbl_telegram_user_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TelegramUserInfo record, @Param("example") TelegramUserInfoExample example);
}