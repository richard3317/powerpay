package com.icpay.payment.db.dao.mybatis.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlow;
import com.icpay.payment.db.dao.mybatis.model.ChnlMchntAccountFlowExample;

public interface ChnlMchntAccountFlowExtMapper {
    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    long countByExample(@Param("example") ChnlMchntAccountFlowExample example, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int deleteByExample(@Param("example") ChnlMchntAccountFlowExample example, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("seqId") Long seqId, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int insert(@Param("record") ChnlMchntAccountFlow record, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int insertSelective(@Param("record") ChnlMchntAccountFlow record, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    List<ChnlMchntAccountFlow> selectByExample(@Param("example") ChnlMchntAccountFlowExample example, @Param("mon")String mon);
    List<ChnlMchntAccountFlow> selectByPage(@Param("example") ChnlMchntAccountFlowExample example, @Param("mon") String mon);
    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    ChnlMchntAccountFlow selectByPrimaryKey(@Param("seqId") Long seqId, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ChnlMchntAccountFlow record, @Param("example") ChnlMchntAccountFlowExample example, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ChnlMchntAccountFlow record, @Param("example") ChnlMchntAccountFlowExample example, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(@Param("record") ChnlMchntAccountFlow record, @Param("mon")String mon);

    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(@Param("record") ChnlMchntAccountFlow record, @Param("mon")String mon);
    
    /**
     * Database table : tbl_chnl_mchnt_account_flow01
     *
     * @mbg.generated
     */
    ChnlMchntAccountFlow selectSummary(@Param("example") ChnlMchntAccountFlowExample example, @Param("mon")String mon);
    
}