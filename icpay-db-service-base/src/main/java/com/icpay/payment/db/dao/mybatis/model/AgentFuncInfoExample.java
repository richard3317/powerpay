package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentFuncInfoExample {
    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public AgentFuncInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_agent_func_info
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
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_agent_func_info
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_agent_func_info
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

        public Criteria andFuncCdIsNull() {
            addCriterion("`func_cd` is null");
            return (Criteria) this;
        }

        public Criteria andFuncCdIsNotNull() {
            addCriterion("`func_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andFuncCdEqualTo(String value) {
            addCriterion("`func_cd` =", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdNotEqualTo(String value) {
            addCriterion("`func_cd` <>", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdGreaterThan(String value) {
            addCriterion("`func_cd` >", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdGreaterThanOrEqualTo(String value) {
            addCriterion("`func_cd` >=", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdLessThan(String value) {
            addCriterion("`func_cd` <", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdLessThanOrEqualTo(String value) {
            addCriterion("`func_cd` <=", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdLike(String value) {
            addCriterion("`func_cd` like", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdNotLike(String value) {
            addCriterion("`func_cd` not like", value, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdIn(List<String> values) {
            addCriterion("`func_cd` in", values, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdNotIn(List<String> values) {
            addCriterion("`func_cd` not in", values, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdBetween(String value1, String value2) {
            addCriterion("`func_cd` between", value1, value2, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncCdNotBetween(String value1, String value2) {
            addCriterion("`func_cd` not between", value1, value2, "funcCd");
            return (Criteria) this;
        }

        public Criteria andFuncNmIsNull() {
            addCriterion("`func_nm` is null");
            return (Criteria) this;
        }

        public Criteria andFuncNmIsNotNull() {
            addCriterion("`func_nm` is not null");
            return (Criteria) this;
        }

        public Criteria andFuncNmEqualTo(String value) {
            addCriterion("`func_nm` =", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmNotEqualTo(String value) {
            addCriterion("`func_nm` <>", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmGreaterThan(String value) {
            addCriterion("`func_nm` >", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmGreaterThanOrEqualTo(String value) {
            addCriterion("`func_nm` >=", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmLessThan(String value) {
            addCriterion("`func_nm` <", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmLessThanOrEqualTo(String value) {
            addCriterion("`func_nm` <=", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmLike(String value) {
            addCriterion("`func_nm` like", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmNotLike(String value) {
            addCriterion("`func_nm` not like", value, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmIn(List<String> values) {
            addCriterion("`func_nm` in", values, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmNotIn(List<String> values) {
            addCriterion("`func_nm` not in", values, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmBetween(String value1, String value2) {
            addCriterion("`func_nm` between", value1, value2, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncNmNotBetween(String value1, String value2) {
            addCriterion("`func_nm` not between", value1, value2, "funcNm");
            return (Criteria) this;
        }

        public Criteria andFuncTpIsNull() {
            addCriterion("`func_tp` is null");
            return (Criteria) this;
        }

        public Criteria andFuncTpIsNotNull() {
            addCriterion("`func_tp` is not null");
            return (Criteria) this;
        }

        public Criteria andFuncTpEqualTo(String value) {
            addCriterion("`func_tp` =", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpNotEqualTo(String value) {
            addCriterion("`func_tp` <>", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpGreaterThan(String value) {
            addCriterion("`func_tp` >", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpGreaterThanOrEqualTo(String value) {
            addCriterion("`func_tp` >=", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpLessThan(String value) {
            addCriterion("`func_tp` <", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpLessThanOrEqualTo(String value) {
            addCriterion("`func_tp` <=", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpLike(String value) {
            addCriterion("`func_tp` like", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpNotLike(String value) {
            addCriterion("`func_tp` not like", value, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpIn(List<String> values) {
            addCriterion("`func_tp` in", values, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpNotIn(List<String> values) {
            addCriterion("`func_tp` not in", values, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpBetween(String value1, String value2) {
            addCriterion("`func_tp` between", value1, value2, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncTpNotBetween(String value1, String value2) {
            addCriterion("`func_tp` not between", value1, value2, "funcTp");
            return (Criteria) this;
        }

        public Criteria andFuncHrefIsNull() {
            addCriterion("`func_href` is null");
            return (Criteria) this;
        }

        public Criteria andFuncHrefIsNotNull() {
            addCriterion("`func_href` is not null");
            return (Criteria) this;
        }

        public Criteria andFuncHrefEqualTo(String value) {
            addCriterion("`func_href` =", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefNotEqualTo(String value) {
            addCriterion("`func_href` <>", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefGreaterThan(String value) {
            addCriterion("`func_href` >", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefGreaterThanOrEqualTo(String value) {
            addCriterion("`func_href` >=", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefLessThan(String value) {
            addCriterion("`func_href` <", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefLessThanOrEqualTo(String value) {
            addCriterion("`func_href` <=", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefLike(String value) {
            addCriterion("`func_href` like", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefNotLike(String value) {
            addCriterion("`func_href` not like", value, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefIn(List<String> values) {
            addCriterion("`func_href` in", values, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefNotIn(List<String> values) {
            addCriterion("`func_href` not in", values, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefBetween(String value1, String value2) {
            addCriterion("`func_href` between", value1, value2, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncHrefNotBetween(String value1, String value2) {
            addCriterion("`func_href` not between", value1, value2, "funcHref");
            return (Criteria) this;
        }

        public Criteria andFuncIdxIsNull() {
            addCriterion("`func_idx` is null");
            return (Criteria) this;
        }

        public Criteria andFuncIdxIsNotNull() {
            addCriterion("`func_idx` is not null");
            return (Criteria) this;
        }

        public Criteria andFuncIdxEqualTo(Integer value) {
            addCriterion("`func_idx` =", value, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxNotEqualTo(Integer value) {
            addCriterion("`func_idx` <>", value, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxGreaterThan(Integer value) {
            addCriterion("`func_idx` >", value, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxGreaterThanOrEqualTo(Integer value) {
            addCriterion("`func_idx` >=", value, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxLessThan(Integer value) {
            addCriterion("`func_idx` <", value, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxLessThanOrEqualTo(Integer value) {
            addCriterion("`func_idx` <=", value, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxIn(List<Integer> values) {
            addCriterion("`func_idx` in", values, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxNotIn(List<Integer> values) {
            addCriterion("`func_idx` not in", values, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxBetween(Integer value1, Integer value2) {
            addCriterion("`func_idx` between", value1, value2, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andFuncIdxNotBetween(Integer value1, Integer value2) {
            addCriterion("`func_idx` not between", value1, value2, "funcIdx");
            return (Criteria) this;
        }

        public Criteria andParentCdIsNull() {
            addCriterion("`parent_cd` is null");
            return (Criteria) this;
        }

        public Criteria andParentCdIsNotNull() {
            addCriterion("`parent_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andParentCdEqualTo(String value) {
            addCriterion("`parent_cd` =", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdNotEqualTo(String value) {
            addCriterion("`parent_cd` <>", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdGreaterThan(String value) {
            addCriterion("`parent_cd` >", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdGreaterThanOrEqualTo(String value) {
            addCriterion("`parent_cd` >=", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdLessThan(String value) {
            addCriterion("`parent_cd` <", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdLessThanOrEqualTo(String value) {
            addCriterion("`parent_cd` <=", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdLike(String value) {
            addCriterion("`parent_cd` like", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdNotLike(String value) {
            addCriterion("`parent_cd` not like", value, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdIn(List<String> values) {
            addCriterion("`parent_cd` in", values, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdNotIn(List<String> values) {
            addCriterion("`parent_cd` not in", values, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdBetween(String value1, String value2) {
            addCriterion("`parent_cd` between", value1, value2, "parentCd");
            return (Criteria) this;
        }

        public Criteria andParentCdNotBetween(String value1, String value2) {
            addCriterion("`parent_cd` not between", value1, value2, "parentCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdIsNull() {
            addCriterion("`module_cd` is null");
            return (Criteria) this;
        }

        public Criteria andModuleCdIsNotNull() {
            addCriterion("`module_cd` is not null");
            return (Criteria) this;
        }

        public Criteria andModuleCdEqualTo(String value) {
            addCriterion("`module_cd` =", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdNotEqualTo(String value) {
            addCriterion("`module_cd` <>", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdGreaterThan(String value) {
            addCriterion("`module_cd` >", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdGreaterThanOrEqualTo(String value) {
            addCriterion("`module_cd` >=", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdLessThan(String value) {
            addCriterion("`module_cd` <", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdLessThanOrEqualTo(String value) {
            addCriterion("`module_cd` <=", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdLike(String value) {
            addCriterion("`module_cd` like", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdNotLike(String value) {
            addCriterion("`module_cd` not like", value, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdIn(List<String> values) {
            addCriterion("`module_cd` in", values, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdNotIn(List<String> values) {
            addCriterion("`module_cd` not in", values, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdBetween(String value1, String value2) {
            addCriterion("`module_cd` between", value1, value2, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andModuleCdNotBetween(String value1, String value2) {
            addCriterion("`module_cd` not between", value1, value2, "moduleCd");
            return (Criteria) this;
        }

        public Criteria andFuncStIsNull() {
            addCriterion("`func_st` is null");
            return (Criteria) this;
        }

        public Criteria andFuncStIsNotNull() {
            addCriterion("`func_st` is not null");
            return (Criteria) this;
        }

        public Criteria andFuncStEqualTo(String value) {
            addCriterion("`func_st` =", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStNotEqualTo(String value) {
            addCriterion("`func_st` <>", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStGreaterThan(String value) {
            addCriterion("`func_st` >", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStGreaterThanOrEqualTo(String value) {
            addCriterion("`func_st` >=", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStLessThan(String value) {
            addCriterion("`func_st` <", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStLessThanOrEqualTo(String value) {
            addCriterion("`func_st` <=", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStLike(String value) {
            addCriterion("`func_st` like", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStNotLike(String value) {
            addCriterion("`func_st` not like", value, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStIn(List<String> values) {
            addCriterion("`func_st` in", values, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStNotIn(List<String> values) {
            addCriterion("`func_st` not in", values, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStBetween(String value1, String value2) {
            addCriterion("`func_st` between", value1, value2, "funcSt");
            return (Criteria) this;
        }

        public Criteria andFuncStNotBetween(String value1, String value2) {
            addCriterion("`func_st` not between", value1, value2, "funcSt");
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
     * Database table : tbl_agent_func_info
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
     * Database table : tbl_agent_func_info
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