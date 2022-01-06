package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.VaccFlowCatSub;
import com.icpay.payment.db.dao.mybatis.model.VaccFlowCatSubExample;
import com.icpay.payment.db.dao.mybatis.model.VaccFlowCatSubKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VaccFlowCatSubMapper {
    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    long countByExample(VaccFlowCatSubExample example);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int deleteByExample(VaccFlowCatSubExample example);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(VaccFlowCatSubKey key);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int insert(VaccFlowCatSub record);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int insertSelective(VaccFlowCatSub record);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    List<VaccFlowCatSub> selectByPage(VaccFlowCatSubExample example);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    List<VaccFlowCatSub> selectByExample(VaccFlowCatSubExample example);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    VaccFlowCatSub selectByPrimaryKey(VaccFlowCatSubKey key);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") VaccFlowCatSub record, @Param("example") VaccFlowCatSubExample example);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") VaccFlowCatSub record, @Param("example") VaccFlowCatSubExample example);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(VaccFlowCatSub record);

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(VaccFlowCatSub record);
}