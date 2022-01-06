package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.Holidays;
import com.icpay.payment.db.dao.mybatis.model.HolidaysExample;
import com.icpay.payment.db.dao.mybatis.model.HolidaysKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HolidaysMapper {
    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    long countByExample(HolidaysExample example);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int deleteByExample(HolidaysExample example);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(HolidaysKey key);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int insert(Holidays record);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int insertSelective(Holidays record);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    List<Holidays> selectByPage(HolidaysExample example);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    List<Holidays> selectByExample(HolidaysExample example);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    Holidays selectByPrimaryKey(HolidaysKey key);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Holidays record, @Param("example") HolidaysExample example);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Holidays record, @Param("example") HolidaysExample example);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Holidays record);

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Holidays record);
}