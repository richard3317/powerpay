package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class TransStatExample {
    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public TransStatExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_trans_stat
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
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_trans_stat
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_trans_stat
    
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

        public Criteria andSeqIdIsNull() {
            addCriterion("`seq_id` is null");
            return (Criteria) this;
        }

        public Criteria andSeqIdIsNotNull() {
            addCriterion("`seq_id` is not null");
            return (Criteria) this;
        }

        public Criteria andSeqIdEqualTo(Integer value) {
            addCriterion("`seq_id` =", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotEqualTo(Integer value) {
            addCriterion("`seq_id` <>", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdGreaterThan(Integer value) {
            addCriterion("`seq_id` >", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("`seq_id` >=", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLessThan(Integer value) {
            addCriterion("`seq_id` <", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdLessThanOrEqualTo(Integer value) {
            addCriterion("`seq_id` <=", value, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdIn(List<Integer> values) {
            addCriterion("`seq_id` in", values, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotIn(List<Integer> values) {
            addCriterion("`seq_id` not in", values, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdBetween(Integer value1, Integer value2) {
            addCriterion("`seq_id` between", value1, value2, "seqId");
            return (Criteria) this;
        }

        public Criteria andSeqIdNotBetween(Integer value1, Integer value2) {
            addCriterion("`seq_id` not between", value1, value2, "seqId");
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

        public Criteria andTransChnlIsNull() {
            addCriterion("`trans_chnl` is null");
            return (Criteria) this;
        }

        public Criteria andTransChnlIsNotNull() {
            addCriterion("`trans_chnl` is not null");
            return (Criteria) this;
        }

        public Criteria andTransChnlEqualTo(String value) {
            addCriterion("`trans_chnl` =", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlNotEqualTo(String value) {
            addCriterion("`trans_chnl` <>", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlGreaterThan(String value) {
            addCriterion("`trans_chnl` >", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlGreaterThanOrEqualTo(String value) {
            addCriterion("`trans_chnl` >=", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlLessThan(String value) {
            addCriterion("`trans_chnl` <", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlLessThanOrEqualTo(String value) {
            addCriterion("`trans_chnl` <=", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlLike(String value) {
            addCriterion("`trans_chnl` like", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlNotLike(String value) {
            addCriterion("`trans_chnl` not like", value, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlIn(List<String> values) {
            addCriterion("`trans_chnl` in", values, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlNotIn(List<String> values) {
            addCriterion("`trans_chnl` not in", values, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlBetween(String value1, String value2) {
            addCriterion("`trans_chnl` between", value1, value2, "transChnl");
            return (Criteria) this;
        }

        public Criteria andTransChnlNotBetween(String value1, String value2) {
            addCriterion("`trans_chnl` not between", value1, value2, "transChnl");
            return (Criteria) this;
        }

        public Criteria andRspCdIsNull() {
            addCriterion("`rsp_cd` is null");
            return (Criteria) this;
        }

        public Criteria andRspCdIsNotNull() {
            addCriterion("`rsp_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andRspCdEqualTo(String value) {
            addCriterion("`rsp_cd` =", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdNotEqualTo(String value) {
            addCriterion("`rsp_cd` <>", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdGreaterThan(String value) {
            addCriterion("`rsp_cd` >", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdGreaterThanOrEqualTo(String value) {
            addCriterion("`rsp_cd` >=", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdLessThan(String value) {
            addCriterion("`rsp_cd` <", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdLessThanOrEqualTo(String value) {
            addCriterion("`rsp_cd` <=", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdLike(String value) {
            addCriterion("`rsp_cd` like", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdNotLike(String value) {
            addCriterion("`rsp_cd` not like", value, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdIn(List<String> values) {
            addCriterion("`rsp_cd` in", values, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdNotIn(List<String> values) {
            addCriterion("`rsp_cd` not in", values, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdBetween(String value1, String value2) {
            addCriterion("`rsp_cd` between", value1, value2, "rspCd");
            return (Criteria) this;
        }

        public Criteria andRspCdNotBetween(String value1, String value2) {
            addCriterion("`rsp_cd` not between", value1, value2, "rspCd");
            return (Criteria) this;
        }

        public Criteria andTermSnIsNull() {
            addCriterion("`term_sn` is null");
            return (Criteria) this;
        }

        public Criteria andTermSnIsNotNull() {
            addCriterion("`term_sn` is not null");
            return (Criteria) this;
        }

        public Criteria andTermSnEqualTo(String value) {
            addCriterion("`term_sn` =", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnNotEqualTo(String value) {
            addCriterion("`term_sn` <>", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnGreaterThan(String value) {
            addCriterion("`term_sn` >", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnGreaterThanOrEqualTo(String value) {
            addCriterion("`term_sn` >=", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnLessThan(String value) {
            addCriterion("`term_sn` <", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnLessThanOrEqualTo(String value) {
            addCriterion("`term_sn` <=", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnLike(String value) {
            addCriterion("`term_sn` like", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnNotLike(String value) {
            addCriterion("`term_sn` not like", value, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnIn(List<String> values) {
            addCriterion("`term_sn` in", values, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnNotIn(List<String> values) {
            addCriterion("`term_sn` not in", values, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnBetween(String value1, String value2) {
            addCriterion("`term_sn` between", value1, value2, "termSn");
            return (Criteria) this;
        }

        public Criteria andTermSnNotBetween(String value1, String value2) {
            addCriterion("`term_sn` not between", value1, value2, "termSn");
            return (Criteria) this;
        }

        public Criteria andStatDtIsNull() {
            addCriterion("`stat_dt` is null");
            return (Criteria) this;
        }

        public Criteria andStatDtIsNotNull() {
            addCriterion("`stat_dt` is not null");
            return (Criteria) this;
        }

        public Criteria andStatDtEqualTo(String value) {
            addCriterion("`stat_dt` =", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtNotEqualTo(String value) {
            addCriterion("`stat_dt` <>", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtGreaterThan(String value) {
            addCriterion("`stat_dt` >", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtGreaterThanOrEqualTo(String value) {
            addCriterion("`stat_dt` >=", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtLessThan(String value) {
            addCriterion("`stat_dt` <", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtLessThanOrEqualTo(String value) {
            addCriterion("`stat_dt` <=", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtLike(String value) {
            addCriterion("`stat_dt` like", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtNotLike(String value) {
            addCriterion("`stat_dt` not like", value, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtIn(List<String> values) {
            addCriterion("`stat_dt` in", values, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtNotIn(List<String> values) {
            addCriterion("`stat_dt` not in", values, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtBetween(String value1, String value2) {
            addCriterion("`stat_dt` between", value1, value2, "statDt");
            return (Criteria) this;
        }

        public Criteria andStatDtNotBetween(String value1, String value2) {
            addCriterion("`stat_dt` not between", value1, value2, "statDt");
            return (Criteria) this;
        }

        public Criteria andTransNumSumIsNull() {
            addCriterion("`trans_num_sum` is null");
            return (Criteria) this;
        }

        public Criteria andTransNumSumIsNotNull() {
            addCriterion("`trans_num_sum` is not null");
            return (Criteria) this;
        }

        public Criteria andTransNumSumEqualTo(Integer value) {
            addCriterion("`trans_num_sum` =", value, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumNotEqualTo(Integer value) {
            addCriterion("`trans_num_sum` <>", value, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumGreaterThan(Integer value) {
            addCriterion("`trans_num_sum` >", value, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("`trans_num_sum` >=", value, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumLessThan(Integer value) {
            addCriterion("`trans_num_sum` <", value, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumLessThanOrEqualTo(Integer value) {
            addCriterion("`trans_num_sum` <=", value, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumIn(List<Integer> values) {
            addCriterion("`trans_num_sum` in", values, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumNotIn(List<Integer> values) {
            addCriterion("`trans_num_sum` not in", values, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumBetween(Integer value1, Integer value2) {
            addCriterion("`trans_num_sum` between", value1, value2, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransNumSumNotBetween(Integer value1, Integer value2) {
            addCriterion("`trans_num_sum` not between", value1, value2, "transNumSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumIsNull() {
            addCriterion("`trans_at_sum` is null");
            return (Criteria) this;
        }

        public Criteria andTransAtSumIsNotNull() {
            addCriterion("`trans_at_sum` is not null");
            return (Criteria) this;
        }

        public Criteria andTransAtSumEqualTo(Long value) {
            addCriterion("`trans_at_sum` =", value, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumNotEqualTo(Long value) {
            addCriterion("`trans_at_sum` <>", value, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumGreaterThan(Long value) {
            addCriterion("`trans_at_sum` >", value, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumGreaterThanOrEqualTo(Long value) {
            addCriterion("`trans_at_sum` >=", value, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumLessThan(Long value) {
            addCriterion("`trans_at_sum` <", value, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumLessThanOrEqualTo(Long value) {
            addCriterion("`trans_at_sum` <=", value, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumIn(List<Long> values) {
            addCriterion("`trans_at_sum` in", values, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumNotIn(List<Long> values) {
            addCriterion("`trans_at_sum` not in", values, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumBetween(Long value1, Long value2) {
            addCriterion("`trans_at_sum` between", value1, value2, "transAtSum");
            return (Criteria) this;
        }

        public Criteria andTransAtSumNotBetween(Long value1, Long value2) {
            addCriterion("`trans_at_sum` not between", value1, value2, "transAtSum");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_trans_stat
    
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
     * Database table : tbl_trans_stat
    
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