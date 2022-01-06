package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RiskListGroupExample {
    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public RiskListGroupExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_risk_list_group
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
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_risk_list_group
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_risk_list_group
    
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

        public Criteria andGroupIdIsNull() {
            addCriterion("`group_id` is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("`group_id` is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("`group_id` =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("`group_id` <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("`group_id` >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("`group_id` >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("`group_id` <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("`group_id` <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("`group_id` in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("`group_id` not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("`group_id` between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("`group_id` not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupNmIsNull() {
            addCriterion("`group_nm` is null");
            return (Criteria) this;
        }

        public Criteria andGroupNmIsNotNull() {
            addCriterion("`group_nm` is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNmEqualTo(String value) {
            addCriterion("`group_nm` =", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmNotEqualTo(String value) {
            addCriterion("`group_nm` <>", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmGreaterThan(String value) {
            addCriterion("`group_nm` >", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmGreaterThanOrEqualTo(String value) {
            addCriterion("`group_nm` >=", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmLessThan(String value) {
            addCriterion("`group_nm` <", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmLessThanOrEqualTo(String value) {
            addCriterion("`group_nm` <=", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmLike(String value) {
            addCriterion("`group_nm` like", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmNotLike(String value) {
            addCriterion("`group_nm` not like", value, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmIn(List<String> values) {
            addCriterion("`group_nm` in", values, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmNotIn(List<String> values) {
            addCriterion("`group_nm` not in", values, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmBetween(String value1, String value2) {
            addCriterion("`group_nm` between", value1, value2, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupNmNotBetween(String value1, String value2) {
            addCriterion("`group_nm` not between", value1, value2, "groupNm");
            return (Criteria) this;
        }

        public Criteria andGroupTpIsNull() {
            addCriterion("`group_tp` is null");
            return (Criteria) this;
        }

        public Criteria andGroupTpIsNotNull() {
            addCriterion("`group_tp` is not null");
            return (Criteria) this;
        }

        public Criteria andGroupTpEqualTo(String value) {
            addCriterion("`group_tp` =", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpNotEqualTo(String value) {
            addCriterion("`group_tp` <>", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpGreaterThan(String value) {
            addCriterion("`group_tp` >", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpGreaterThanOrEqualTo(String value) {
            addCriterion("`group_tp` >=", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpLessThan(String value) {
            addCriterion("`group_tp` <", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpLessThanOrEqualTo(String value) {
            addCriterion("`group_tp` <=", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpLike(String value) {
            addCriterion("`group_tp` like", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpNotLike(String value) {
            addCriterion("`group_tp` not like", value, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpIn(List<String> values) {
            addCriterion("`group_tp` in", values, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpNotIn(List<String> values) {
            addCriterion("`group_tp` not in", values, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpBetween(String value1, String value2) {
            addCriterion("`group_tp` between", value1, value2, "groupTp");
            return (Criteria) this;
        }

        public Criteria andGroupTpNotBetween(String value1, String value2) {
            addCriterion("`group_tp` not between", value1, value2, "groupTp");
            return (Criteria) this;
        }

        public Criteria andListTpIsNull() {
            addCriterion("`list_tp` is null");
            return (Criteria) this;
        }

        public Criteria andListTpIsNotNull() {
            addCriterion("`list_tp` is not null");
            return (Criteria) this;
        }

        public Criteria andListTpEqualTo(String value) {
            addCriterion("`list_tp` =", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpNotEqualTo(String value) {
            addCriterion("`list_tp` <>", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpGreaterThan(String value) {
            addCriterion("`list_tp` >", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpGreaterThanOrEqualTo(String value) {
            addCriterion("`list_tp` >=", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpLessThan(String value) {
            addCriterion("`list_tp` <", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpLessThanOrEqualTo(String value) {
            addCriterion("`list_tp` <=", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpLike(String value) {
            addCriterion("`list_tp` like", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpNotLike(String value) {
            addCriterion("`list_tp` not like", value, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpIn(List<String> values) {
            addCriterion("`list_tp` in", values, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpNotIn(List<String> values) {
            addCriterion("`list_tp` not in", values, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpBetween(String value1, String value2) {
            addCriterion("`list_tp` between", value1, value2, "listTp");
            return (Criteria) this;
        }

        public Criteria andListTpNotBetween(String value1, String value2) {
            addCriterion("`list_tp` not between", value1, value2, "listTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpIsNull() {
            addCriterion("`risk_tp` is null");
            return (Criteria) this;
        }

        public Criteria andRiskTpIsNotNull() {
            addCriterion("`risk_tp` is not null");
            return (Criteria) this;
        }

        public Criteria andRiskTpEqualTo(String value) {
            addCriterion("`risk_tp` =", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpNotEqualTo(String value) {
            addCriterion("`risk_tp` <>", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpGreaterThan(String value) {
            addCriterion("`risk_tp` >", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpGreaterThanOrEqualTo(String value) {
            addCriterion("`risk_tp` >=", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpLessThan(String value) {
            addCriterion("`risk_tp` <", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpLessThanOrEqualTo(String value) {
            addCriterion("`risk_tp` <=", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpLike(String value) {
            addCriterion("`risk_tp` like", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpNotLike(String value) {
            addCriterion("`risk_tp` not like", value, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpIn(List<String> values) {
            addCriterion("`risk_tp` in", values, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpNotIn(List<String> values) {
            addCriterion("`risk_tp` not in", values, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpBetween(String value1, String value2) {
            addCriterion("`risk_tp` between", value1, value2, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskTpNotBetween(String value1, String value2) {
            addCriterion("`risk_tp` not between", value1, value2, "riskTp");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIsNull() {
            addCriterion("`risk_level` is null");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIsNotNull() {
            addCriterion("`risk_level` is not null");
            return (Criteria) this;
        }

        public Criteria andRiskLevelEqualTo(Integer value) {
            addCriterion("`risk_level` =", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotEqualTo(Integer value) {
            addCriterion("`risk_level` <>", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelGreaterThan(Integer value) {
            addCriterion("`risk_level` >", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("`risk_level` >=", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLessThan(Integer value) {
            addCriterion("`risk_level` <", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelLessThanOrEqualTo(Integer value) {
            addCriterion("`risk_level` <=", value, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelIn(List<Integer> values) {
            addCriterion("`risk_level` in", values, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotIn(List<Integer> values) {
            addCriterion("`risk_level` not in", values, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelBetween(Integer value1, Integer value2) {
            addCriterion("`risk_level` between", value1, value2, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andRiskLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("`risk_level` not between", value1, value2, "riskLevel");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("`result` is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("`result` is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {
            addCriterion("`result` =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {
            addCriterion("`result` <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {
            addCriterion("`result` >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {
            addCriterion("`result` >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {
            addCriterion("`result` <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {
            addCriterion("`result` <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {
            addCriterion("`result` like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {
            addCriterion("`result` not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {
            addCriterion("`result` in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {
            addCriterion("`result` not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {
            addCriterion("`result` between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {
            addCriterion("`result` not between", value1, value2, "result");
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

        public Criteria andExpiredTmIsNull() {
            addCriterion("`expired_tm` is null");
            return (Criteria) this;
        }

        public Criteria andExpiredTmIsNotNull() {
            addCriterion("`expired_tm` is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredTmEqualTo(Date value) {
            addCriterion("`expired_tm` =", value, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmNotEqualTo(Date value) {
            addCriterion("`expired_tm` <>", value, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmGreaterThan(Date value) {
            addCriterion("`expired_tm` >", value, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmGreaterThanOrEqualTo(Date value) {
            addCriterion("`expired_tm` >=", value, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmLessThan(Date value) {
            addCriterion("`expired_tm` <", value, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmLessThanOrEqualTo(Date value) {
            addCriterion("`expired_tm` <=", value, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmIn(List<Date> values) {
            addCriterion("`expired_tm` in", values, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmNotIn(List<Date> values) {
            addCriterion("`expired_tm` not in", values, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmBetween(Date value1, Date value2) {
            addCriterion("`expired_tm` between", value1, value2, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andExpiredTmNotBetween(Date value1, Date value2) {
            addCriterion("`expired_tm` not between", value1, value2, "expiredTm");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("`comment` is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("`comment` is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("`comment` =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("`comment` <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("`comment` >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("`comment` >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("`comment` <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("`comment` <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("`comment` like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("`comment` not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("`comment` in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("`comment` not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("`comment` between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("`comment` not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andLastOperIdIsNull() {
            addCriterion("`last_oper_id` is null");
            return (Criteria) this;
        }

        public Criteria andLastOperIdIsNotNull() {
            addCriterion("`last_oper_id` is not null");
            return (Criteria) this;
        }

        public Criteria andLastOperIdEqualTo(String value) {
            addCriterion("`last_oper_id` =", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdNotEqualTo(String value) {
            addCriterion("`last_oper_id` <>", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdGreaterThan(String value) {
            addCriterion("`last_oper_id` >", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdGreaterThanOrEqualTo(String value) {
            addCriterion("`last_oper_id` >=", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdLessThan(String value) {
            addCriterion("`last_oper_id` <", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdLessThanOrEqualTo(String value) {
            addCriterion("`last_oper_id` <=", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdLike(String value) {
            addCriterion("`last_oper_id` like", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdNotLike(String value) {
            addCriterion("`last_oper_id` not like", value, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdIn(List<String> values) {
            addCriterion("`last_oper_id` in", values, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdNotIn(List<String> values) {
            addCriterion("`last_oper_id` not in", values, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdBetween(String value1, String value2) {
            addCriterion("`last_oper_id` between", value1, value2, "lastOperId");
            return (Criteria) this;
        }

        public Criteria andLastOperIdNotBetween(String value1, String value2) {
            addCriterion("`last_oper_id` not between", value1, value2, "lastOperId");
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
     * Database table : tbl_risk_list_group
    
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
     * Database table : tbl_risk_list_group
    
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