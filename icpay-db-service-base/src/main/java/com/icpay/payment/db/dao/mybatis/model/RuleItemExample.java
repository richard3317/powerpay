package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RuleItemExample {
    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public RuleItemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_rule_item
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
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_rule_item
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_rule_item
    
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

        public Criteria andRuleIidIsNull() {
            addCriterion("`rule_iid` is null");
            return (Criteria) this;
        }

        public Criteria andRuleIidIsNotNull() {
            addCriterion("`rule_iid` is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIidEqualTo(Long value) {
            addCriterion("`rule_iid` =", value, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidNotEqualTo(Long value) {
            addCriterion("`rule_iid` <>", value, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidGreaterThan(Long value) {
            addCriterion("`rule_iid` >", value, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidGreaterThanOrEqualTo(Long value) {
            addCriterion("`rule_iid` >=", value, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidLessThan(Long value) {
            addCriterion("`rule_iid` <", value, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidLessThanOrEqualTo(Long value) {
            addCriterion("`rule_iid` <=", value, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidIn(List<Long> values) {
            addCriterion("`rule_iid` in", values, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidNotIn(List<Long> values) {
            addCriterion("`rule_iid` not in", values, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidBetween(Long value1, Long value2) {
            addCriterion("`rule_iid` between", value1, value2, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andRuleIidNotBetween(Long value1, Long value2) {
            addCriterion("`rule_iid` not between", value1, value2, "ruleIid");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("`category` is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("`category` is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("`category` =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("`category` <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("`category` >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("`category` >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("`category` <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("`category` <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("`category` like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("`category` not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("`category` in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("`category` not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("`category` between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("`category` not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNull() {
            addCriterion("`rule_id` is null");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNotNull() {
            addCriterion("`rule_id` is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIdEqualTo(String value) {
            addCriterion("`rule_id` =", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotEqualTo(String value) {
            addCriterion("`rule_id` <>", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThan(String value) {
            addCriterion("`rule_id` >", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThanOrEqualTo(String value) {
            addCriterion("`rule_id` >=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThan(String value) {
            addCriterion("`rule_id` <", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThanOrEqualTo(String value) {
            addCriterion("`rule_id` <=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLike(String value) {
            addCriterion("`rule_id` like", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotLike(String value) {
            addCriterion("`rule_id` not like", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIn(List<String> values) {
            addCriterion("`rule_id` in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotIn(List<String> values) {
            addCriterion("`rule_id` not in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdBetween(String value1, String value2) {
            addCriterion("`rule_id` between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotBetween(String value1, String value2) {
            addCriterion("`rule_id` not between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("`sort_order` is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("`sort_order` is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Integer value) {
            addCriterion("`sort_order` =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Integer value) {
            addCriterion("`sort_order` <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Integer value) {
            addCriterion("`sort_order` >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("`sort_order` >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Integer value) {
            addCriterion("`sort_order` <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Integer value) {
            addCriterion("`sort_order` <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Integer> values) {
            addCriterion("`sort_order` in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Integer> values) {
            addCriterion("`sort_order` not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Integer value1, Integer value2) {
            addCriterion("`sort_order` between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("`sort_order` not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNull() {
            addCriterion("`class_name` is null");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNotNull() {
            addCriterion("`class_name` is not null");
            return (Criteria) this;
        }

        public Criteria andClassNameEqualTo(String value) {
            addCriterion("`class_name` =", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotEqualTo(String value) {
            addCriterion("`class_name` <>", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThan(String value) {
            addCriterion("`class_name` >", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("`class_name` >=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThan(String value) {
            addCriterion("`class_name` <", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThanOrEqualTo(String value) {
            addCriterion("`class_name` <=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLike(String value) {
            addCriterion("`class_name` like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotLike(String value) {
            addCriterion("`class_name` not like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameIn(List<String> values) {
            addCriterion("`class_name` in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotIn(List<String> values) {
            addCriterion("`class_name` not in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameBetween(String value1, String value2) {
            addCriterion("`class_name` between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotBetween(String value1, String value2) {
            addCriterion("`class_name` not between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andConfigIsNull() {
            addCriterion("`config` is null");
            return (Criteria) this;
        }

        public Criteria andConfigIsNotNull() {
            addCriterion("`config` is not null");
            return (Criteria) this;
        }

        public Criteria andConfigEqualTo(String value) {
            addCriterion("`config` =", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotEqualTo(String value) {
            addCriterion("`config` <>", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigGreaterThan(String value) {
            addCriterion("`config` >", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigGreaterThanOrEqualTo(String value) {
            addCriterion("`config` >=", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigLessThan(String value) {
            addCriterion("`config` <", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigLessThanOrEqualTo(String value) {
            addCriterion("`config` <=", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigLike(String value) {
            addCriterion("`config` like", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotLike(String value) {
            addCriterion("`config` not like", value, "config");
            return (Criteria) this;
        }

        public Criteria andConfigIn(List<String> values) {
            addCriterion("`config` in", values, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotIn(List<String> values) {
            addCriterion("`config` not in", values, "config");
            return (Criteria) this;
        }

        public Criteria andConfigBetween(String value1, String value2) {
            addCriterion("`config` between", value1, value2, "config");
            return (Criteria) this;
        }

        public Criteria andConfigNotBetween(String value1, String value2) {
            addCriterion("`config` not between", value1, value2, "config");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("`desc` like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("`desc` not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andMemoIsNull() {
            addCriterion("`memo` is null");
            return (Criteria) this;
        }

        public Criteria andMemoIsNotNull() {
            addCriterion("`memo` is not null");
            return (Criteria) this;
        }

        public Criteria andMemoEqualTo(String value) {
            addCriterion("`memo` =", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotEqualTo(String value) {
            addCriterion("`memo` <>", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThan(String value) {
            addCriterion("`memo` >", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoGreaterThanOrEqualTo(String value) {
            addCriterion("`memo` >=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThan(String value) {
            addCriterion("`memo` <", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLessThanOrEqualTo(String value) {
            addCriterion("`memo` <=", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoLike(String value) {
            addCriterion("`memo` like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotLike(String value) {
            addCriterion("`memo` not like", value, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoIn(List<String> values) {
            addCriterion("`memo` in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotIn(List<String> values) {
            addCriterion("`memo` not in", values, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoBetween(String value1, String value2) {
            addCriterion("`memo` between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andMemoNotBetween(String value1, String value2) {
            addCriterion("`memo` not between", value1, value2, "memo");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsIsNull() {
            addCriterion("`rec_crt_ts` is null");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsIsNotNull() {
            addCriterion("`rec_crt_ts` is not null");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsEqualTo(Date value) {
            addCriterion("`rec_crt_ts` =", value, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsNotEqualTo(Date value) {
            addCriterion("`rec_crt_ts` <>", value, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsGreaterThan(Date value) {
            addCriterion("`rec_crt_ts` >", value, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsGreaterThanOrEqualTo(Date value) {
            addCriterion("`rec_crt_ts` >=", value, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsLessThan(Date value) {
            addCriterion("`rec_crt_ts` <", value, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsLessThanOrEqualTo(Date value) {
            addCriterion("`rec_crt_ts` <=", value, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsIn(List<Date> values) {
            addCriterion("`rec_crt_ts` in", values, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsNotIn(List<Date> values) {
            addCriterion("`rec_crt_ts` not in", values, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsBetween(Date value1, Date value2) {
            addCriterion("`rec_crt_ts` between", value1, value2, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecCrtTsNotBetween(Date value1, Date value2) {
            addCriterion("`rec_crt_ts` not between", value1, value2, "recCrtTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsIsNull() {
            addCriterion("`rec_upd_ts` is null");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsIsNotNull() {
            addCriterion("`rec_upd_ts` is not null");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsEqualTo(Date value) {
            addCriterion("`rec_upd_ts` =", value, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsNotEqualTo(Date value) {
            addCriterion("`rec_upd_ts` <>", value, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsGreaterThan(Date value) {
            addCriterion("`rec_upd_ts` >", value, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsGreaterThanOrEqualTo(Date value) {
            addCriterion("`rec_upd_ts` >=", value, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsLessThan(Date value) {
            addCriterion("`rec_upd_ts` <", value, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsLessThanOrEqualTo(Date value) {
            addCriterion("`rec_upd_ts` <=", value, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsIn(List<Date> values) {
            addCriterion("`rec_upd_ts` in", values, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsNotIn(List<Date> values) {
            addCriterion("`rec_upd_ts` not in", values, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsBetween(Date value1, Date value2) {
            addCriterion("`rec_upd_ts` between", value1, value2, "recUpdTs");
            return (Criteria) this;
        }

        public Criteria andRecUpdTsNotBetween(Date value1, Date value2) {
            addCriterion("`rec_upd_ts` not between", value1, value2, "recUpdTs");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_rule_item
    
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
     * Database table : tbl_rule_item
    
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