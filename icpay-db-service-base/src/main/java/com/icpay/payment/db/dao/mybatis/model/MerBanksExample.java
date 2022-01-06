package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class MerBanksExample {
    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public MerBanksExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : view_mer_banks
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
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : view_mer_banks
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : view_mer_banks
     * @author user
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

        public Criteria andIntTransCdIsNull() {
            addCriterion("`int_trans_cd` is null");
            return (Criteria) this;
        }

        public Criteria andIntTransCdIsNotNull() {
            addCriterion("`int_trans_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andIntTransCdEqualTo(String value) {
            addCriterion("`int_trans_cd` =", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdNotEqualTo(String value) {
            addCriterion("`int_trans_cd` <>", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdGreaterThan(String value) {
            addCriterion("`int_trans_cd` >", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdGreaterThanOrEqualTo(String value) {
            addCriterion("`int_trans_cd` >=", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdLessThan(String value) {
            addCriterion("`int_trans_cd` <", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdLessThanOrEqualTo(String value) {
            addCriterion("`int_trans_cd` <=", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdLike(String value) {
            addCriterion("`int_trans_cd` like", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdNotLike(String value) {
            addCriterion("`int_trans_cd` not like", value, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdIn(List<String> values) {
            addCriterion("`int_trans_cd` in", values, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdNotIn(List<String> values) {
            addCriterion("`int_trans_cd` not in", values, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdBetween(String value1, String value2) {
            addCriterion("`int_trans_cd` between", value1, value2, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andIntTransCdNotBetween(String value1, String value2) {
            addCriterion("`int_trans_cd` not between", value1, value2, "intTransCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdIsNull() {
            addCriterion("`curr_cd` is null");
            return (Criteria) this;
        }

        public Criteria andCurrCdIsNotNull() {
            addCriterion("`curr_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andCurrCdEqualTo(String value) {
            addCriterion("`curr_cd` =", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdNotEqualTo(String value) {
            addCriterion("`curr_cd` <>", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdGreaterThan(String value) {
            addCriterion("`curr_cd` >", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdGreaterThanOrEqualTo(String value) {
            addCriterion("`curr_cd` >=", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdLessThan(String value) {
            addCriterion("`curr_cd` <", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdLessThanOrEqualTo(String value) {
            addCriterion("`curr_cd` <=", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdLike(String value) {
            addCriterion("`curr_cd` like", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdNotLike(String value) {
            addCriterion("`curr_cd` not like", value, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdIn(List<String> values) {
            addCriterion("`curr_cd` in", values, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdNotIn(List<String> values) {
            addCriterion("`curr_cd` not in", values, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdBetween(String value1, String value2) {
            addCriterion("`curr_cd` between", value1, value2, "currCd");
            return (Criteria) this;
        }

        public Criteria andCurrCdNotBetween(String value1, String value2) {
            addCriterion("`curr_cd` not between", value1, value2, "currCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdIsNull() {
            addCriterion("`mchnt_cd` is null");
            return (Criteria) this;
        }

        public Criteria andMchntCdIsNotNull() {
            addCriterion("`mchnt_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andMchntCdEqualTo(String value) {
            addCriterion("`mchnt_cd` =", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdNotEqualTo(String value) {
            addCriterion("`mchnt_cd` <>", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdGreaterThan(String value) {
            addCriterion("`mchnt_cd` >", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdGreaterThanOrEqualTo(String value) {
            addCriterion("`mchnt_cd` >=", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdLessThan(String value) {
            addCriterion("`mchnt_cd` <", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdLessThanOrEqualTo(String value) {
            addCriterion("`mchnt_cd` <=", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdLike(String value) {
            addCriterion("`mchnt_cd` like", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdNotLike(String value) {
            addCriterion("`mchnt_cd` not like", value, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdIn(List<String> values) {
            addCriterion("`mchnt_cd` in", values, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdNotIn(List<String> values) {
            addCriterion("`mchnt_cd` not in", values, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdBetween(String value1, String value2) {
            addCriterion("`mchnt_cd` between", value1, value2, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andMchntCdNotBetween(String value1, String value2) {
            addCriterion("`mchnt_cd` not between", value1, value2, "mchntCd");
            return (Criteria) this;
        }

        public Criteria andBankNumIsNull() {
            addCriterion("`bank_num` is null");
            return (Criteria) this;
        }

        public Criteria andBankNumIsNotNull() {
            addCriterion("`bank_num` is not null");
            return (Criteria) this;
        }

        public Criteria andBankNumEqualTo(String value) {
            addCriterion("`bank_num` =", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotEqualTo(String value) {
            addCriterion("`bank_num` <>", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumGreaterThan(String value) {
            addCriterion("`bank_num` >", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumGreaterThanOrEqualTo(String value) {
            addCriterion("`bank_num` >=", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumLessThan(String value) {
            addCriterion("`bank_num` <", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumLessThanOrEqualTo(String value) {
            addCriterion("`bank_num` <=", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumLike(String value) {
            addCriterion("`bank_num` like", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotLike(String value) {
            addCriterion("`bank_num` not like", value, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumIn(List<String> values) {
            addCriterion("`bank_num` in", values, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotIn(List<String> values) {
            addCriterion("`bank_num` not in", values, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumBetween(String value1, String value2) {
            addCriterion("`bank_num` between", value1, value2, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNumNotBetween(String value1, String value2) {
            addCriterion("`bank_num` not between", value1, value2, "bankNum");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("`bank_name` is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("`bank_name` is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("`bank_name` =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("`bank_name` <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("`bank_name` >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("`bank_name` >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("`bank_name` <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("`bank_name` <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("`bank_name` like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("`bank_name` not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("`bank_name` in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("`bank_name` not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("`bank_name` between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("`bank_name` not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andSortSeqIsNull() {
            addCriterion("`sort_seq` is null");
            return (Criteria) this;
        }

        public Criteria andSortSeqIsNotNull() {
            addCriterion("`sort_seq` is not null");
            return (Criteria) this;
        }

        public Criteria andSortSeqEqualTo(Integer value) {
            addCriterion("`sort_seq` =", value, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqNotEqualTo(Integer value) {
            addCriterion("`sort_seq` <>", value, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqGreaterThan(Integer value) {
            addCriterion("`sort_seq` >", value, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("`sort_seq` >=", value, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqLessThan(Integer value) {
            addCriterion("`sort_seq` <", value, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqLessThanOrEqualTo(Integer value) {
            addCriterion("`sort_seq` <=", value, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqIn(List<Integer> values) {
            addCriterion("`sort_seq` in", values, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqNotIn(List<Integer> values) {
            addCriterion("`sort_seq` not in", values, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqBetween(Integer value1, Integer value2) {
            addCriterion("`sort_seq` between", value1, value2, "sortSeq");
            return (Criteria) this;
        }

        public Criteria andSortSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("`sort_seq` not between", value1, value2, "sortSeq");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : view_mer_banks
     * @author user
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
     * Database table : view_mer_banks
     * @author user
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