package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VaccFlowCatSubExample {
    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public VaccFlowCatSubExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
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
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_vacc_flow_cat_sub
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_vacc_flow_cat_sub
    
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

        public Criteria andBookIsNull() {
            addCriterion("`book` is null");
            return (Criteria) this;
        }

        public Criteria andBookIsNotNull() {
            addCriterion("`book` is not null");
            return (Criteria) this;
        }

        public Criteria andBookEqualTo(String value) {
            addCriterion("`book` =", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotEqualTo(String value) {
            addCriterion("`book` <>", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookGreaterThan(String value) {
            addCriterion("`book` >", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookGreaterThanOrEqualTo(String value) {
            addCriterion("`book` >=", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookLessThan(String value) {
            addCriterion("`book` <", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookLessThanOrEqualTo(String value) {
            addCriterion("`book` <=", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookLike(String value) {
            addCriterion("`book` like", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotLike(String value) {
            addCriterion("`book` not like", value, "book");
            return (Criteria) this;
        }

        public Criteria andBookIn(List<String> values) {
            addCriterion("`book` in", values, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotIn(List<String> values) {
            addCriterion("`book` not in", values, "book");
            return (Criteria) this;
        }

        public Criteria andBookBetween(String value1, String value2) {
            addCriterion("`book` between", value1, value2, "book");
            return (Criteria) this;
        }

        public Criteria andBookNotBetween(String value1, String value2) {
            addCriterion("`book` not between", value1, value2, "book");
            return (Criteria) this;
        }

        public Criteria andCatalogIsNull() {
            addCriterion("`catalog` is null");
            return (Criteria) this;
        }

        public Criteria andCatalogIsNotNull() {
            addCriterion("`catalog` is not null");
            return (Criteria) this;
        }

        public Criteria andCatalogEqualTo(String value) {
            addCriterion("`catalog` =", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotEqualTo(String value) {
            addCriterion("`catalog` <>", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogGreaterThan(String value) {
            addCriterion("`catalog` >", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogGreaterThanOrEqualTo(String value) {
            addCriterion("`catalog` >=", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLessThan(String value) {
            addCriterion("`catalog` <", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLessThanOrEqualTo(String value) {
            addCriterion("`catalog` <=", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogLike(String value) {
            addCriterion("`catalog` like", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotLike(String value) {
            addCriterion("`catalog` not like", value, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogIn(List<String> values) {
            addCriterion("`catalog` in", values, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotIn(List<String> values) {
            addCriterion("`catalog` not in", values, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogBetween(String value1, String value2) {
            addCriterion("`catalog` between", value1, value2, "catalog");
            return (Criteria) this;
        }

        public Criteria andCatalogNotBetween(String value1, String value2) {
            addCriterion("`catalog` not between", value1, value2, "catalog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogIsNull() {
            addCriterion("`sub_catlog` is null");
            return (Criteria) this;
        }

        public Criteria andSubCatlogIsNotNull() {
            addCriterion("`sub_catlog` is not null");
            return (Criteria) this;
        }

        public Criteria andSubCatlogEqualTo(String value) {
            addCriterion("`sub_catlog` =", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogNotEqualTo(String value) {
            addCriterion("`sub_catlog` <>", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogGreaterThan(String value) {
            addCriterion("`sub_catlog` >", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogGreaterThanOrEqualTo(String value) {
            addCriterion("`sub_catlog` >=", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogLessThan(String value) {
            addCriterion("`sub_catlog` <", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogLessThanOrEqualTo(String value) {
            addCriterion("`sub_catlog` <=", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogLike(String value) {
            addCriterion("`sub_catlog` like", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogNotLike(String value) {
            addCriterion("`sub_catlog` not like", value, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogIn(List<String> values) {
            addCriterion("`sub_catlog` in", values, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogNotIn(List<String> values) {
            addCriterion("`sub_catlog` not in", values, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogBetween(String value1, String value2) {
            addCriterion("`sub_catlog` between", value1, value2, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andSubCatlogNotBetween(String value1, String value2) {
            addCriterion("`sub_catlog` not between", value1, value2, "subCatlog");
            return (Criteria) this;
        }

        public Criteria andCatNameIsNull() {
            addCriterion("`cat_name` is null");
            return (Criteria) this;
        }

        public Criteria andCatNameIsNotNull() {
            addCriterion("`cat_name` is not null");
            return (Criteria) this;
        }

        public Criteria andCatNameEqualTo(String value) {
            addCriterion("`cat_name` =", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotEqualTo(String value) {
            addCriterion("`cat_name` <>", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameGreaterThan(String value) {
            addCriterion("`cat_name` >", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameGreaterThanOrEqualTo(String value) {
            addCriterion("`cat_name` >=", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameLessThan(String value) {
            addCriterion("`cat_name` <", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameLessThanOrEqualTo(String value) {
            addCriterion("`cat_name` <=", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameLike(String value) {
            addCriterion("`cat_name` like", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotLike(String value) {
            addCriterion("`cat_name` not like", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameIn(List<String> values) {
            addCriterion("`cat_name` in", values, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotIn(List<String> values) {
            addCriterion("`cat_name` not in", values, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameBetween(String value1, String value2) {
            addCriterion("`cat_name` between", value1, value2, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotBetween(String value1, String value2) {
            addCriterion("`cat_name` not between", value1, value2, "catName");
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
     * Database table : tbl_vacc_flow_cat_sub
    
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
     * Database table : tbl_vacc_flow_cat_sub
    
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