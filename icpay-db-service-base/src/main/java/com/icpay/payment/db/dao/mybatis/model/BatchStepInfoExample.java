package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class BatchStepInfoExample {
    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public BatchStepInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_batch_step_info
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
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_batch_step_info
    
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

        public Criteria andJobNoIsNull() {
            addCriterion("`job_no` is null");
            return (Criteria) this;
        }

        public Criteria andJobNoIsNotNull() {
            addCriterion("`job_no` is not null");
            return (Criteria) this;
        }

        public Criteria andJobNoEqualTo(Integer value) {
            addCriterion("`job_no` =", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotEqualTo(Integer value) {
            addCriterion("`job_no` <>", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoGreaterThan(Integer value) {
            addCriterion("`job_no` >", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("`job_no` >=", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoLessThan(Integer value) {
            addCriterion("`job_no` <", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoLessThanOrEqualTo(Integer value) {
            addCriterion("`job_no` <=", value, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoIn(List<Integer> values) {
            addCriterion("`job_no` in", values, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotIn(List<Integer> values) {
            addCriterion("`job_no` not in", values, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoBetween(Integer value1, Integer value2) {
            addCriterion("`job_no` between", value1, value2, "jobNo");
            return (Criteria) this;
        }

        public Criteria andJobNoNotBetween(Integer value1, Integer value2) {
            addCriterion("`job_no` not between", value1, value2, "jobNo");
            return (Criteria) this;
        }

        public Criteria andStepNoIsNull() {
            addCriterion("`step_no` is null");
            return (Criteria) this;
        }

        public Criteria andStepNoIsNotNull() {
            addCriterion("`step_no` is not null");
            return (Criteria) this;
        }

        public Criteria andStepNoEqualTo(Integer value) {
            addCriterion("`step_no` =", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoNotEqualTo(Integer value) {
            addCriterion("`step_no` <>", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoGreaterThan(Integer value) {
            addCriterion("`step_no` >", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("`step_no` >=", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoLessThan(Integer value) {
            addCriterion("`step_no` <", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoLessThanOrEqualTo(Integer value) {
            addCriterion("`step_no` <=", value, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoIn(List<Integer> values) {
            addCriterion("`step_no` in", values, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoNotIn(List<Integer> values) {
            addCriterion("`step_no` not in", values, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoBetween(Integer value1, Integer value2) {
            addCriterion("`step_no` between", value1, value2, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepNoNotBetween(Integer value1, Integer value2) {
            addCriterion("`step_no` not between", value1, value2, "stepNo");
            return (Criteria) this;
        }

        public Criteria andStepDescIsNull() {
            addCriterion("`step_desc` is null");
            return (Criteria) this;
        }

        public Criteria andStepDescIsNotNull() {
            addCriterion("`step_desc` is not null");
            return (Criteria) this;
        }

        public Criteria andStepDescEqualTo(String value) {
            addCriterion("`step_desc` =", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescNotEqualTo(String value) {
            addCriterion("`step_desc` <>", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescGreaterThan(String value) {
            addCriterion("`step_desc` >", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescGreaterThanOrEqualTo(String value) {
            addCriterion("`step_desc` >=", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescLessThan(String value) {
            addCriterion("`step_desc` <", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescLessThanOrEqualTo(String value) {
            addCriterion("`step_desc` <=", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescLike(String value) {
            addCriterion("`step_desc` like", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescNotLike(String value) {
            addCriterion("`step_desc` not like", value, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescIn(List<String> values) {
            addCriterion("`step_desc` in", values, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescNotIn(List<String> values) {
            addCriterion("`step_desc` not in", values, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescBetween(String value1, String value2) {
            addCriterion("`step_desc` between", value1, value2, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepDescNotBetween(String value1, String value2) {
            addCriterion("`step_desc` not between", value1, value2, "stepDesc");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameIsNull() {
            addCriterion("`step_bean_name` is null");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameIsNotNull() {
            addCriterion("`step_bean_name` is not null");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameEqualTo(String value) {
            addCriterion("`step_bean_name` =", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameNotEqualTo(String value) {
            addCriterion("`step_bean_name` <>", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameGreaterThan(String value) {
            addCriterion("`step_bean_name` >", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameGreaterThanOrEqualTo(String value) {
            addCriterion("`step_bean_name` >=", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameLessThan(String value) {
            addCriterion("`step_bean_name` <", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameLessThanOrEqualTo(String value) {
            addCriterion("`step_bean_name` <=", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameLike(String value) {
            addCriterion("`step_bean_name` like", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameNotLike(String value) {
            addCriterion("`step_bean_name` not like", value, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameIn(List<String> values) {
            addCriterion("`step_bean_name` in", values, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameNotIn(List<String> values) {
            addCriterion("`step_bean_name` not in", values, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameBetween(String value1, String value2) {
            addCriterion("`step_bean_name` between", value1, value2, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepBeanNameNotBetween(String value1, String value2) {
            addCriterion("`step_bean_name` not between", value1, value2, "stepBeanName");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodIsNull() {
            addCriterion("`step_run_period` is null");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodIsNotNull() {
            addCriterion("`step_run_period` is not null");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodEqualTo(String value) {
            addCriterion("`step_run_period` =", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodNotEqualTo(String value) {
            addCriterion("`step_run_period` <>", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodGreaterThan(String value) {
            addCriterion("`step_run_period` >", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("`step_run_period` >=", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodLessThan(String value) {
            addCriterion("`step_run_period` <", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodLessThanOrEqualTo(String value) {
            addCriterion("`step_run_period` <=", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodLike(String value) {
            addCriterion("`step_run_period` like", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodNotLike(String value) {
            addCriterion("`step_run_period` not like", value, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodIn(List<String> values) {
            addCriterion("`step_run_period` in", values, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodNotIn(List<String> values) {
            addCriterion("`step_run_period` not in", values, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodBetween(String value1, String value2) {
            addCriterion("`step_run_period` between", value1, value2, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepRunPeriodNotBetween(String value1, String value2) {
            addCriterion("`step_run_period` not between", value1, value2, "stepRunPeriod");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsIsNull() {
            addCriterion("`step_ctrl_flgs` is null");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsIsNotNull() {
            addCriterion("`step_ctrl_flgs` is not null");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsEqualTo(String value) {
            addCriterion("`step_ctrl_flgs` =", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsNotEqualTo(String value) {
            addCriterion("`step_ctrl_flgs` <>", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsGreaterThan(String value) {
            addCriterion("`step_ctrl_flgs` >", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsGreaterThanOrEqualTo(String value) {
            addCriterion("`step_ctrl_flgs` >=", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsLessThan(String value) {
            addCriterion("`step_ctrl_flgs` <", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsLessThanOrEqualTo(String value) {
            addCriterion("`step_ctrl_flgs` <=", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsLike(String value) {
            addCriterion("`step_ctrl_flgs` like", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsNotLike(String value) {
            addCriterion("`step_ctrl_flgs` not like", value, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsIn(List<String> values) {
            addCriterion("`step_ctrl_flgs` in", values, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsNotIn(List<String> values) {
            addCriterion("`step_ctrl_flgs` not in", values, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsBetween(String value1, String value2) {
            addCriterion("`step_ctrl_flgs` between", value1, value2, "stepCtrlFlgs");
            return (Criteria) this;
        }

        public Criteria andStepCtrlFlgsNotBetween(String value1, String value2) {
            addCriterion("`step_ctrl_flgs` not between", value1, value2, "stepCtrlFlgs");
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
     * Database table : tbl_batch_step_info
    
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
     * Database table : tbl_batch_step_info
    
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