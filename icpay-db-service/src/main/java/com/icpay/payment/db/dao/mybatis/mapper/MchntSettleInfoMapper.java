package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MchntSettleInfo;
import com.icpay.payment.db.dao.mybatis.model.MchntSettleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MchntSettleInfoMapper {
    /**
     * Database table : view_mchnt_settle_info
     *
     * @mbg.generated
     */
    long countByExample(MchntSettleInfoExample example);

    /**
     * Database table : view_mchnt_settle_info
     *
     * @mbg.generated
     */
    List<MchntSettleInfo> selectByPage(MchntSettleInfoExample example);

    /**
     * Database table : view_mchnt_settle_info
     *
     * @mbg.generated
     */
    List<MchntSettleInfo> selectByExample(MchntSettleInfoExample example);
}