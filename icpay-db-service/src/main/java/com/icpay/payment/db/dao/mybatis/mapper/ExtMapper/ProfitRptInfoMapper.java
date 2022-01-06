package com.icpay.payment.db.dao.mybatis.mapper.ExtMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.icpay.payment.db.dao.mybatis.model.TransProfitReport;

public interface ProfitRptInfoMapper {
   /**
    * 查询每日财报    
    * @param map
    * @return
    */
    List<TransProfitReport> selectProfitRpt(@Param("map") Map<String,String> map);
}