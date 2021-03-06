package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SiteInfoExample {
    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public SiteInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_site_info
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
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_site_info
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_site_info
    
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

        public Criteria andSiteIdIsNull() {
            addCriterion("`site_id` is null");
            return (Criteria) this;
        }

        public Criteria andSiteIdIsNotNull() {
            addCriterion("`site_id` is not null");
            return (Criteria) this;
        }

        public Criteria andSiteIdEqualTo(String value) {
            addCriterion("`site_id` =", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotEqualTo(String value) {
            addCriterion("`site_id` <>", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThan(String value) {
            addCriterion("`site_id` >", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdGreaterThanOrEqualTo(String value) {
            addCriterion("`site_id` >=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThan(String value) {
            addCriterion("`site_id` <", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLessThanOrEqualTo(String value) {
            addCriterion("`site_id` <=", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdLike(String value) {
            addCriterion("`site_id` like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotLike(String value) {
            addCriterion("`site_id` not like", value, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdIn(List<String> values) {
            addCriterion("`site_id` in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotIn(List<String> values) {
            addCriterion("`site_id` not in", values, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdBetween(String value1, String value2) {
            addCriterion("`site_id` between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteIdNotBetween(String value1, String value2) {
            addCriterion("`site_id` not between", value1, value2, "siteId");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNull() {
            addCriterion("`site_name` is null");
            return (Criteria) this;
        }

        public Criteria andSiteNameIsNotNull() {
            addCriterion("`site_name` is not null");
            return (Criteria) this;
        }

        public Criteria andSiteNameEqualTo(String value) {
            addCriterion("`site_name` =", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotEqualTo(String value) {
            addCriterion("`site_name` <>", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThan(String value) {
            addCriterion("`site_name` >", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("`site_name` >=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThan(String value) {
            addCriterion("`site_name` <", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLessThanOrEqualTo(String value) {
            addCriterion("`site_name` <=", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameLike(String value) {
            addCriterion("`site_name` like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotLike(String value) {
            addCriterion("`site_name` not like", value, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameIn(List<String> values) {
            addCriterion("`site_name` in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotIn(List<String> values) {
            addCriterion("`site_name` not in", values, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameBetween(String value1, String value2) {
            addCriterion("`site_name` between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andSiteNameNotBetween(String value1, String value2) {
            addCriterion("`site_name` not between", value1, value2, "siteName");
            return (Criteria) this;
        }

        public Criteria andMerDomainIsNull() {
            addCriterion("`mer_domain` is null");
            return (Criteria) this;
        }

        public Criteria andMerDomainIsNotNull() {
            addCriterion("`mer_domain` is not null");
            return (Criteria) this;
        }

        public Criteria andMerDomainEqualTo(String value) {
            addCriterion("`mer_domain` =", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainNotEqualTo(String value) {
            addCriterion("`mer_domain` <>", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainGreaterThan(String value) {
            addCriterion("`mer_domain` >", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainGreaterThanOrEqualTo(String value) {
            addCriterion("`mer_domain` >=", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainLessThan(String value) {
            addCriterion("`mer_domain` <", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainLessThanOrEqualTo(String value) {
            addCriterion("`mer_domain` <=", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainLike(String value) {
            addCriterion("`mer_domain` like", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainNotLike(String value) {
            addCriterion("`mer_domain` not like", value, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainIn(List<String> values) {
            addCriterion("`mer_domain` in", values, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainNotIn(List<String> values) {
            addCriterion("`mer_domain` not in", values, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainBetween(String value1, String value2) {
            addCriterion("`mer_domain` between", value1, value2, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerDomainNotBetween(String value1, String value2) {
            addCriterion("`mer_domain` not between", value1, value2, "merDomain");
            return (Criteria) this;
        }

        public Criteria andMerParamsIsNull() {
            addCriterion("`mer_params` is null");
            return (Criteria) this;
        }

        public Criteria andMerParamsIsNotNull() {
            addCriterion("`mer_params` is not null");
            return (Criteria) this;
        }

        public Criteria andMerParamsEqualTo(String value) {
            addCriterion("`mer_params` =", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsNotEqualTo(String value) {
            addCriterion("`mer_params` <>", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsGreaterThan(String value) {
            addCriterion("`mer_params` >", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsGreaterThanOrEqualTo(String value) {
            addCriterion("`mer_params` >=", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsLessThan(String value) {
            addCriterion("`mer_params` <", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsLessThanOrEqualTo(String value) {
            addCriterion("`mer_params` <=", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsLike(String value) {
            addCriterion("`mer_params` like", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsNotLike(String value) {
            addCriterion("`mer_params` not like", value, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsIn(List<String> values) {
            addCriterion("`mer_params` in", values, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsNotIn(List<String> values) {
            addCriterion("`mer_params` not in", values, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsBetween(String value1, String value2) {
            addCriterion("`mer_params` between", value1, value2, "merParams");
            return (Criteria) this;
        }

        public Criteria andMerParamsNotBetween(String value1, String value2) {
            addCriterion("`mer_params` not between", value1, value2, "merParams");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainIsNull() {
            addCriterion("`gateway_domain` is null");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainIsNotNull() {
            addCriterion("`gateway_domain` is not null");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainEqualTo(String value) {
            addCriterion("`gateway_domain` =", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainNotEqualTo(String value) {
            addCriterion("`gateway_domain` <>", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainGreaterThan(String value) {
            addCriterion("`gateway_domain` >", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainGreaterThanOrEqualTo(String value) {
            addCriterion("`gateway_domain` >=", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainLessThan(String value) {
            addCriterion("`gateway_domain` <", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainLessThanOrEqualTo(String value) {
            addCriterion("`gateway_domain` <=", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainLike(String value) {
            addCriterion("`gateway_domain` like", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainNotLike(String value) {
            addCriterion("`gateway_domain` not like", value, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainIn(List<String> values) {
            addCriterion("`gateway_domain` in", values, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainNotIn(List<String> values) {
            addCriterion("`gateway_domain` not in", values, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainBetween(String value1, String value2) {
            addCriterion("`gateway_domain` between", value1, value2, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayDomainNotBetween(String value1, String value2) {
            addCriterion("`gateway_domain` not between", value1, value2, "gatewayDomain");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsIsNull() {
            addCriterion("`gateway_params` is null");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsIsNotNull() {
            addCriterion("`gateway_params` is not null");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsEqualTo(String value) {
            addCriterion("`gateway_params` =", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsNotEqualTo(String value) {
            addCriterion("`gateway_params` <>", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsGreaterThan(String value) {
            addCriterion("`gateway_params` >", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsGreaterThanOrEqualTo(String value) {
            addCriterion("`gateway_params` >=", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsLessThan(String value) {
            addCriterion("`gateway_params` <", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsLessThanOrEqualTo(String value) {
            addCriterion("`gateway_params` <=", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsLike(String value) {
            addCriterion("`gateway_params` like", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsNotLike(String value) {
            addCriterion("`gateway_params` not like", value, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsIn(List<String> values) {
            addCriterion("`gateway_params` in", values, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsNotIn(List<String> values) {
            addCriterion("`gateway_params` not in", values, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsBetween(String value1, String value2) {
            addCriterion("`gateway_params` between", value1, value2, "gatewayParams");
            return (Criteria) this;
        }

        public Criteria andGatewayParamsNotBetween(String value1, String value2) {
            addCriterion("`gateway_params` not between", value1, value2, "gatewayParams");
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
     * Database table : tbl_site_info
    
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
     * Database table : tbl_site_info
    
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