package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ExchangeRate;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRateExample;
import com.icpay.payment.db.dao.mybatis.model.ExchangeRateKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExchangeRateMapper {
    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    long countByExample(ExchangeRateExample example);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int deleteByExample(ExchangeRateExample example);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(ExchangeRateKey key);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int insert(ExchangeRate record);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int insertSelective(ExchangeRate record);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    List<ExchangeRate> selectByPage(ExchangeRateExample example);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    List<ExchangeRate> selectByExample(ExchangeRateExample example);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    ExchangeRate selectByPrimaryKey(ExchangeRateKey key);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ExchangeRate record, @Param("example") ExchangeRateExample example);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ExchangeRate record, @Param("example") ExchangeRateExample example);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ExchangeRate record);

    /**
     * Database table : tbl_exchange_rate
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ExchangeRate record);
}