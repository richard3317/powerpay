package com.icpay.payment.db.dao.mybatis.mapper;

import com.icpay.payment.db.dao.mybatis.model.AgentForChnlMer;
import com.icpay.payment.db.dao.mybatis.model.AgentForChnlMerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentForChnlMerMapper {
    /**
     * Database table : view_agent_for_chnl_mer
     *
     * @mbg.generated
     */
    long countByExample(AgentForChnlMerExample example);

    /**
     * Database table : view_agent_for_chnl_mer
     *
     * @mbg.generated
     */
    List<AgentForChnlMer> selectByPage(AgentForChnlMerExample example);

    /**
     * Database table : view_agent_for_chnl_mer
     *
     * @mbg.generated
     */
    List<AgentForChnlMer> selectByExample(AgentForChnlMerExample example);
}