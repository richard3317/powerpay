package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.OffPayBank;
import com.icpay.payment.db.dao.mybatis.model.OffPayBankExample;
import com.icpay.payment.db.dao.mybatis.model.OffPayBankKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OffPayBankMapper {
    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    long countByExample(OffPayBankExample example);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int deleteByExample(OffPayBankExample example);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(OffPayBankKey key);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int insert(OffPayBank record);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int insertSelective(OffPayBank record);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    List<OffPayBank> selectByPage(OffPayBankExample example);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    List<OffPayBank> selectByExample(OffPayBankExample example);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    OffPayBank selectByPrimaryKey(OffPayBankKey key);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") OffPayBank record, @Param("example") OffPayBankExample example);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") OffPayBank record, @Param("example") OffPayBankExample example);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(OffPayBank record);

    /**
     * Database table : tbl_off_pay_bank
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OffPayBank record);
    
    public OffPayBank select(String mchntCd);
}