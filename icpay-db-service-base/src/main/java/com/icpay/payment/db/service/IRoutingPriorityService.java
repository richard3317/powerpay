package com.icpay.payment.db.service;

import java.util.List;
import java.util.Map;

import com.icpay.payment.common.entity.Pager;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriority;
import com.icpay.payment.db.dao.mybatis.model.RoutingPriorityKey;
import com.icpay.payment.db.dao.mybatis.model.modelExt.RoutingPriorityExt;

public interface IRoutingPriorityService {
	
    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(RoutingPriorityKey key);
    
    /**
     * 初始化优先路由
     * @param ridPrefix
     * @param routingList
     * @param deleteLast
     * @param optUser
     * @return
     */
    int initPriorityRoutings(String ridPrefix, List<Map<String,String>> routingList, boolean deleteLast, String optUser);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int insertSelective(RoutingPriority record);
    
    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int insertSelectiveExt(RoutingPriorityExt record);
    
    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RoutingPriority record);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    public Pager<RoutingPriority> selectByPage(int pageNum, int pageSize,Map<String,String> qryParamMap);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    List<RoutingPriority> selectByExample(Map<String,String> map);

    /**
     * Database table : tbl_routing_priority
     *
     * @mbg.generated
     */
    RoutingPriority selectByPrimaryKey(RoutingPriorityKey key);
}