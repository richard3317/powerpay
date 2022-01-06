package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChnlMchntAccountFlowMapper {
    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    long countByExample(ChnlMchntAccountFlowExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int deleteByExample(ChnlMchntAccountFlowExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long seqId);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int insert(ChnlMchntAccountFlow record);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int insertSelective(ChnlMchntAccountFlow record);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    List<ChnlMchntAccountFlow> selectByPage(ChnlMchntAccountFlowExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    List<ChnlMchntAccountFlow> selectByExample(ChnlMchntAccountFlowExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    ChnlMchntAccountFlow selectByPrimaryKey(Long seqId);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ChnlMchntAccountFlow record, @Param("example") ChnlMchntAccountFlowExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ChnlMchntAccountFlow record, @Param("example") ChnlMchntAccountFlowExample example);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ChnlMchntAccountFlow record);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ChnlMchntAccountFlow record);
}