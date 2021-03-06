package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankChnlMapExample {
    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public BankChnlMapExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_bank_chnl_map
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
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_bank_chnl_map
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_bank_chnl_map
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

        public Criteria andChnlBankNumIsNull() {
            addCriterion("`chnl_bank_num` is null");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumIsNotNull() {
            addCriterion("`chnl_bank_num` is not null");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumEqualTo(String value) {
            addCriterion("`chnl_bank_num` =", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumNotEqualTo(String value) {
            addCriterion("`chnl_bank_num` <>", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumGreaterThan(String value) {
            addCriterion("`chnl_bank_num` >", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumGreaterThanOrEqualTo(String value) {
            addCriterion("`chnl_bank_num` >=", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumLessThan(String value) {
            addCriterion("`chnl_bank_num` <", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumLessThanOrEqualTo(String value) {
            addCriterion("`chnl_bank_num` <=", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumLike(String value) {
            addCriterion("`chnl_bank_num` like", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumNotLike(String value) {
            addCriterion("`chnl_bank_num` not like", value, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumIn(List<String> values) {
            addCriterion("`chnl_bank_num` in", values, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumNotIn(List<String> values) {
            addCriterion("`chnl_bank_num` not in", values, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumBetween(String value1, String value2) {
            addCriterion("`chnl_bank_num` between", value1, value2, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNumNotBetween(String value1, String value2) {
            addCriterion("`chnl_bank_num` not between", value1, value2, "chnlBankNum");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameIsNull() {
            addCriterion("`chnl_bank_name` is null");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameIsNotNull() {
            addCriterion("`chnl_bank_name` is not null");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameEqualTo(String value) {
            addCriterion("`chnl_bank_name` =", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameNotEqualTo(String value) {
            addCriterion("`chnl_bank_name` <>", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameGreaterThan(String value) {
            addCriterion("`chnl_bank_name` >", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("`chnl_bank_name` >=", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameLessThan(String value) {
            addCriterion("`chnl_bank_name` <", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameLessThanOrEqualTo(String value) {
            addCriterion("`chnl_bank_name` <=", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameLike(String value) {
            addCriterion("`chnl_bank_name` like", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameNotLike(String value) {
            addCriterion("`chnl_bank_name` not like", value, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameIn(List<String> values) {
            addCriterion("`chnl_bank_name` in", values, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameNotIn(List<String> values) {
            addCriterion("`chnl_bank_name` not in", values, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameBetween(String value1, String value2) {
            addCriterion("`chnl_bank_name` between", value1, value2, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andChnlBankNameNotBetween(String value1, String value2) {
            addCriterion("`chnl_bank_name` not between", value1, value2, "chnlBankName");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("`state` is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("`state` is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("`state` =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("`state` <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("`state` >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("`state` >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("`state` <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("`state` <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("`state` like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("`state` not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("`state` in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("`state` not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("`state` between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("`state` not between", value1, value2, "state");
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

        public Criteria andTagsIsNull() {
            addCriterion("`tags` is null");
            return (Criteria) this;
        }

        public Criteria andTagsIsNotNull() {
            addCriterion("`tags` is not null");
            return (Criteria) this;
        }

        public Criteria andTagsEqualTo(String value) {
            addCriterion("`tags` =", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotEqualTo(String value) {
            addCriterion("`tags` <>", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThan(String value) {
            addCriterion("`tags` >", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThanOrEqualTo(String value) {
            addCriterion("`tags` >=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThan(String value) {
            addCriterion("`tags` <", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThanOrEqualTo(String value) {
            addCriterion("`tags` <=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLike(String value) {
            addCriterion("`tags` like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotLike(String value) {
            addCriterion("`tags` not like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsIn(List<String> values) {
            addCriterion("`tags` in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotIn(List<String> values) {
            addCriterion("`tags` not in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsBetween(String value1, String value2) {
            addCriterion("`tags` between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotBetween(String value1, String value2) {
            addCriterion("`tags` not between", value1, value2, "tags");
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
     * Database table : tbl_bank_chnl_map
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
     * Database table : tbl_bank_chnl_map
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