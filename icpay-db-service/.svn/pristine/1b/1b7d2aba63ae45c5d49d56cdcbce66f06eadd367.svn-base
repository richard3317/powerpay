package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.BankNums;
import com.icpay.payment.db.dao.mybatis.model.BankNumsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
@Deprecated
public interface BankNums_BakMapper {
    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    long countByExample(BankNumsExample example);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int deleteByExample(BankNumsExample example);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String bankNum);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int insert(BankNums record);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int insertSelective(BankNums record);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    List<BankNums> selectByPage(BankNumsExample example);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    List<BankNums> selectByExample(BankNumsExample example);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    BankNums selectByPrimaryKey(String bankNum);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BankNums record, @Param("example") BankNumsExample example);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BankNums record, @Param("example") BankNumsExample example);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BankNums record);

    /**
     * Database table : tbl_bank_nums
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BankNums record);
    
    /**
     *  透过在代付路由上所有的渠道, 找出各渠道所匹配到联行号之集合
	*/
	List<BankNums> selectByChnls(List<String> uniqueChnlsId);
}