package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class BatchInfoExample {
    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public BatchInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_batch_info
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
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_batch_info
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_batch_info
    
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

        public Criteria andBatchNoIsNull() {
            addCriterion("`batch_no` is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("`batch_no` is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(Integer value) {
            addCriterion("`batch_no` =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(Integer value) {
            addCriterion("`batch_no` <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(Integer value) {
            addCriterion("`batch_no` >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("`batch_no` >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(Integer value) {
            addCriterion("`batch_no` <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(Integer value) {
            addCriterion("`batch_no` <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<Integer> values) {
            addCriterion("`batch_no` in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<Integer> values) {
            addCriterion("`batch_no` not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(Integer value1, Integer value2) {
            addCriterion("`batch_no` between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(Integer value1, Integer value2) {
            addCriterion("`batch_no` not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchDescIsNull() {
            addCriterion("`batch_desc` is null");
            return (Criteria) this;
        }

        public Criteria andBatchDescIsNotNull() {
            addCriterion("`batch_desc` is not null");
            return (Criteria) this;
        }

        public Criteria andBatchDescEqualTo(String value) {
            addCriterion("`batch_desc` =", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescNotEqualTo(String value) {
            addCriterion("`batch_desc` <>", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescGreaterThan(String value) {
            addCriterion("`batch_desc` >", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescGreaterThanOrEqualTo(String value) {
            addCriterion("`batch_desc` >=", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescLessThan(String value) {
            addCriterion("`batch_desc` <", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescLessThanOrEqualTo(String value) {
            addCriterion("`batch_desc` <=", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescLike(String value) {
            addCriterion("`batch_desc` like", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescNotLike(String value) {
            addCriterion("`batch_desc` not like", value, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescIn(List<String> values) {
            addCriterion("`batch_desc` in", values, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescNotIn(List<String> values) {
            addCriterion("`batch_desc` not in", values, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescBetween(String value1, String value2) {
            addCriterion("`batch_desc` between", value1, value2, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andBatchDescNotBetween(String value1, String value2) {
            addCriterion("`batch_desc` not between", value1, value2, "batchDesc");
            return (Criteria) this;
        }

        public Criteria andRunCycleIsNull() {
            addCriterion("`run_cycle` is null");
            return (Criteria) this;
        }

        public Criteria andRunCycleIsNotNull() {
            addCriterion("`run_cycle` is not null");
            return (Criteria) this;
        }

        public Criteria andRunCycleEqualTo(String value) {
            addCriterion("`run_cycle` =", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleNotEqualTo(String value) {
            addCriterion("`run_cycle` <>", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleGreaterThan(String value) {
            addCriterion("`run_cycle` >", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleGreaterThanOrEqualTo(String value) {
            addCriterion("`run_cycle` >=", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleLessThan(String value) {
            addCriterion("`run_cycle` <", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleLessThanOrEqualTo(String value) {
            addCriterion("`run_cycle` <=", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleLike(String value) {
            addCriterion("`run_cycle` like", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleNotLike(String value) {
            addCriterion("`run_cycle` not like", value, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleIn(List<String> values) {
            addCriterion("`run_cycle` in", values, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleNotIn(List<String> values) {
            addCriterion("`run_cycle` not in", values, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleBetween(String value1, String value2) {
            addCriterion("`run_cycle` between", value1, value2, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunCycleNotBetween(String value1, String value2) {
            addCriterion("`run_cycle` not between", value1, value2, "runCycle");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigIsNull() {
            addCriterion("`run_tm_config` is null");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigIsNotNull() {
            addCriterion("`run_tm_config` is not null");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigEqualTo(String value) {
            addCriterion("`run_tm_config` =", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigNotEqualTo(String value) {
            addCriterion("`run_tm_config` <>", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigGreaterThan(String value) {
            addCriterion("`run_tm_config` >", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigGreaterThanOrEqualTo(String value) {
            addCriterion("`run_tm_config` >=", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigLessThan(String value) {
            addCriterion("`run_tm_config` <", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigLessThanOrEqualTo(String value) {
            addCriterion("`run_tm_config` <=", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigLike(String value) {
            addCriterion("`run_tm_config` like", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigNotLike(String value) {
            addCriterion("`run_tm_config` not like", value, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigIn(List<String> values) {
            addCriterion("`run_tm_config` in", values, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigNotIn(List<String> values) {
            addCriterion("`run_tm_config` not in", values, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigBetween(String value1, String value2) {
            addCriterion("`run_tm_config` between", value1, value2, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRunTmConfigNotBetween(String value1, String value2) {
            addCriterion("`run_tm_config` not between", value1, value2, "runTmConfig");
            return (Criteria) this;
        }

        public Criteria andRecStIsNull() {
            addCriterion("`rec_st` is null");
            return (Criteria) this;
        }

        public Criteria andRecStIsNotNull() {
            addCriterion("`rec_st` is not null");
            return (Criteria) this;
        }

        public Criteria andRecStEqualTo(String value) {
            addCriterion("`rec_st` =", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStNotEqualTo(String value) {
            addCriterion("`rec_st` <>", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStGreaterThan(String value) {
            addCriterion("`rec_st` >", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStGreaterThanOrEqualTo(String value) {
            addCriterion("`rec_st` >=", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStLessThan(String value) {
            addCriterion("`rec_st` <", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStLessThanOrEqualTo(String value) {
            addCriterion("`rec_st` <=", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStLike(String value) {
            addCriterion("`rec_st` like", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStNotLike(String value) {
            addCriterion("`rec_st` not like", value, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStIn(List<String> values) {
            addCriterion("`rec_st` in", values, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStNotIn(List<String> values) {
            addCriterion("`rec_st` not in", values, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStBetween(String value1, String value2) {
            addCriterion("`rec_st` between", value1, value2, "recSt");
            return (Criteria) this;
        }

        public Criteria andRecStNotBetween(String value1, String value2) {
            addCriterion("`rec_st` not between", value1, value2, "recSt");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_batch_info
    
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
     * Database table : tbl_batch_info
    
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