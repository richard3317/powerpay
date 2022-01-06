package com.icpay.payment.db.dao.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AgentProfitResultByChnlExample {
    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public AgentProfitResultByChnlExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : view_agent_profit_result_by_chnl
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : view_agent_profit_result_by_chnl
    
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("`settle_date` is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("`settle_date` is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(String value) {
            addCriterion("`settle_date` =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(String value) {
            addCriterion("`settle_date` <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(String value) {
            addCriterion("`settle_date` >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(String value) {
            addCriterion("`settle_date` >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(String value) {
            addCriterion("`settle_date` <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(String value) {
            addCriterion("`settle_date` <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLike(String value) {
            addCriterion("`settle_date` like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotLike(String value) {
            addCriterion("`settle_date` not like", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<String> values) {
            addCriterion("`settle_date` in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<String> values) {
            addCriterion("`settle_date` not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(String value1, String value2) {
            addCriterion("`settle_date` between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(String value1, String value2) {
            addCriterion("`settle_date` not between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andAgentCdIsNull() {
            addCriterion("`agent_cd` is null");
            return (Criteria) this;
        }

        public Criteria andAgentCdIsNotNull() {
            addCriterion("`agent_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andAgentCdEqualTo(String value) {
            addCriterion("`agent_cd` =", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdNotEqualTo(String value) {
            addCriterion("`agent_cd` <>", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdGreaterThan(String value) {
            addCriterion("`agent_cd` >", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdGreaterThanOrEqualTo(String value) {
            addCriterion("`agent_cd` >=", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdLessThan(String value) {
            addCriterion("`agent_cd` <", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdLessThanOrEqualTo(String value) {
            addCriterion("`agent_cd` <=", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdLike(String value) {
            addCriterion("`agent_cd` like", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdNotLike(String value) {
            addCriterion("`agent_cd` not like", value, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdIn(List<String> values) {
            addCriterion("`agent_cd` in", values, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdNotIn(List<String> values) {
            addCriterion("`agent_cd` not in", values, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdBetween(String value1, String value2) {
            addCriterion("`agent_cd` between", value1, value2, "agentCd");
            return (Criteria) this;
        }

        public Criteria andAgentCdNotBetween(String value1, String value2) {
            addCriterion("`agent_cd` not between", value1, value2, "agentCd");
            return (Criteria) this;
        }

        public Criteria andChnlIdIsNull() {
            addCriterion("`chnl_id` is null");
            return (Criteria) this;
        }

        public Criteria andChnlIdIsNotNull() {
            addCriterion("`chnl_id` is not null");
            return (Criteria) this;
        }

        public Criteria andChnlIdEqualTo(String value) {
            addCriterion("`chnl_id` =", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdNotEqualTo(String value) {
            addCriterion("`chnl_id` <>", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdGreaterThan(String value) {
            addCriterion("`chnl_id` >", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdGreaterThanOrEqualTo(String value) {
            addCriterion("`chnl_id` >=", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdLessThan(String value) {
            addCriterion("`chnl_id` <", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdLessThanOrEqualTo(String value) {
            addCriterion("`chnl_id` <=", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdLike(String value) {
            addCriterion("`chnl_id` like", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdNotLike(String value) {
            addCriterion("`chnl_id` not like", value, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdIn(List<String> values) {
            addCriterion("`chnl_id` in", values, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdNotIn(List<String> values) {
            addCriterion("`chnl_id` not in", values, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdBetween(String value1, String value2) {
            addCriterion("`chnl_id` between", value1, value2, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlIdNotBetween(String value1, String value2) {
            addCriterion("`chnl_id` not between", value1, value2, "chnlId");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdIsNull() {
            addCriterion("`chnl_mchnt_cd` is null");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdIsNotNull() {
            addCriterion("`chnl_mchnt_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdEqualTo(String value) {
            addCriterion("`chnl_mchnt_cd` =", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdNotEqualTo(String value) {
            addCriterion("`chnl_mchnt_cd` <>", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdGreaterThan(String value) {
            addCriterion("`chnl_mchnt_cd` >", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdGreaterThanOrEqualTo(String value) {
            addCriterion("`chnl_mchnt_cd` >=", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdLessThan(String value) {
            addCriterion("`chnl_mchnt_cd` <", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdLessThanOrEqualTo(String value) {
            addCriterion("`chnl_mchnt_cd` <=", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdLike(String value) {
            addCriterion("`chnl_mchnt_cd` like", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdNotLike(String value) {
            addCriterion("`chnl_mchnt_cd` not like", value, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdIn(List<String> values) {
            addCriterion("`chnl_mchnt_cd` in", values, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdNotIn(List<String> values) {
            addCriterion("`chnl_mchnt_cd` not in", values, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdBetween(String value1, String value2) {
            addCriterion("`chnl_mchnt_cd` between", value1, value2, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andChnlMchntCdNotBetween(String value1, String value2) {
            addCriterion("`chnl_mchnt_cd` not between", value1, value2, "chnlMchntCd");
            return (Criteria) this;
        }

        public Criteria andAgentProfitIsNull() {
            addCriterion("`agent_profit` is null");
            return (Criteria) this;
        }

        public Criteria andAgentProfitIsNotNull() {
            addCriterion("`agent_profit` is not null");
            return (Criteria) this;
        }

        public Criteria andAgentProfitEqualTo(BigDecimal value) {
            addCriterion("`agent_profit` =", value, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitNotEqualTo(BigDecimal value) {
            addCriterion("`agent_profit` <>", value, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitGreaterThan(BigDecimal value) {
            addCriterion("`agent_profit` >", value, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("`agent_profit` >=", value, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitLessThan(BigDecimal value) {
            addCriterion("`agent_profit` <", value, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("`agent_profit` <=", value, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitIn(List<BigDecimal> values) {
            addCriterion("`agent_profit` in", values, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitNotIn(List<BigDecimal> values) {
            addCriterion("`agent_profit` not in", values, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`agent_profit` between", value1, value2, "agentProfit");
            return (Criteria) this;
        }

        public Criteria andAgentProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("`agent_profit` not between", value1, value2, "agentProfit");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : view_agent_profit_result_by_chnl
    
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : view_agent_profit_result_by_chnl
    
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}