package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BankChnlMap;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapExample;
import com.icpay.payment.db.dao.mybatis.model.BankChnlMapKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankChnlMapMapper {
    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    long countByExample(BankChnlMapExample example);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int deleteByExample(BankChnlMapExample example);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(BankChnlMapKey key);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int insert(BankChnlMap record);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int insertSelective(BankChnlMap record);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    List<BankChnlMap> selectByPage(BankChnlMapExample example);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    List<BankChnlMap> selectByExample(BankChnlMapExample example);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    BankChnlMap selectByPrimaryKey(BankChnlMapKey key);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BankChnlMap record, @Param("example") BankChnlMapExample example);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BankChnlMap record, @Param("example") BankChnlMapExample example);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BankChnlMap record);

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BankChnlMap record);
}