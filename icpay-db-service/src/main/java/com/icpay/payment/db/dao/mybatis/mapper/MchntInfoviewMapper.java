package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.MchntInfoview;
import com.icpay.payment.db.dao.mybatis.model.MchntInfoviewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MchntInfoviewMapper {
    /**
     * Database table : view_mchnt_infoview
     *
     * @mbg.generated
     */
    long countByExample(MchntInfoviewExample example);

    /**
     * Database table : view_mchnt_infoview
     *
     * @mbg.generated
     */
    List<MchntInfoview> selectByPage(MchntInfoviewExample example);

    /**
     * Database table : view_mchnt_infoview
     *
     * @mbg.generated
     */
    List<MchntInfoview> selectByExample(MchntInfoviewExample example);
}