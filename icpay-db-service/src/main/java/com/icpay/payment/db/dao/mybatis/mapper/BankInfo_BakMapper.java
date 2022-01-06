package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BankInfo;
import com.icpay.payment.db.dao.mybatis.model.BankInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@Deprecated
public interface BankInfo_BakMapper {
    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    long countByExample(BankInfoExample example);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int deleteByExample(BankInfoExample example);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String bankCd);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int insert(BankInfo record);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int insertSelective(BankInfo record);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    List<BankInfo> selectByPage(BankInfoExample example);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    List<BankInfo> selectByExample(BankInfoExample example);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    BankInfo selectByPrimaryKey(String bankCd);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BankInfo record, @Param("example") BankInfoExample example);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BankInfo record, @Param("example") BankInfoExample example);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BankInfo record);

    /**
     * Database table : tbl_bank_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BankInfo record);
    
    //以下手动新增
    
    /**
     * 获取Distinct银行名称
     * @return
     */
    List<String> selectBankNameLst();
}