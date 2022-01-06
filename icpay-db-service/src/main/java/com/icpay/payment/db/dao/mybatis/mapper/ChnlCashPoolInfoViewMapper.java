package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoView;
import com.icpay.payment.db.dao.mybatis.model.ChnlCashPoolInfoViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChnlCashPoolInfoViewMapper {
    /**
     * Database table : view_chnl_cash_pool_info_view
     *
     * @mbg.generated
     */
    long countByExample(ChnlCashPoolInfoViewExample example);

    /**
     * Database table : view_chnl_cash_pool_info_view
     *
     * @mbg.generated
     */
    List<ChnlCashPoolInfoView> selectByPage(ChnlCashPoolInfoViewExample example);

    /**
     * Database table : view_chnl_cash_pool_info_view
     *
     * @mbg.generated
     */
    List<ChnlCashPoolInfoView> selectByExample(ChnlCashPoolInfoViewExample example);
    
    /**
   	 * 通道余额加总
   	 * @param qryParamMap
   	 * @return
   	 */
   	public String selectSummaryByChnl(ChnlCashPoolInfoViewExample example);
}