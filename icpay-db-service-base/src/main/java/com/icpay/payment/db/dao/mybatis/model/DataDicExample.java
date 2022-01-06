package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class DataDicExample {
    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public DataDicExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_data_dic
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
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_data_dic
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_data_dic
    
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

        public Criteria andDataCatalogIsNull() {
            addCriterion("`data_catalog` is null");
            return (Criteria) this;
        }

        public Criteria andDataCatalogIsNotNull() {
            addCriterion("`data_catalog` is not null");
            return (Criteria) this;
        }

        public Criteria andDataCatalogEqualTo(String value) {
            addCriterion("`data_catalog` =", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogNotEqualTo(String value) {
            addCriterion("`data_catalog` <>", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogGreaterThan(String value) {
            addCriterion("`data_catalog` >", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogGreaterThanOrEqualTo(String value) {
            addCriterion("`data_catalog` >=", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogLessThan(String value) {
            addCriterion("`data_catalog` <", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogLessThanOrEqualTo(String value) {
            addCriterion("`data_catalog` <=", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogLike(String value) {
            addCriterion("`data_catalog` like", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogNotLike(String value) {
            addCriterion("`data_catalog` not like", value, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogIn(List<String> values) {
            addCriterion("`data_catalog` in", values, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogNotIn(List<String> values) {
            addCriterion("`data_catalog` not in", values, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogBetween(String value1, String value2) {
            addCriterion("`data_catalog` between", value1, value2, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataCatalogNotBetween(String value1, String value2) {
            addCriterion("`data_catalog` not between", value1, value2, "dataCatalog");
            return (Criteria) this;
        }

        public Criteria andDataTpIsNull() {
            addCriterion("`data_tp` is null");
            return (Criteria) this;
        }

        public Criteria andDataTpIsNotNull() {
            addCriterion("`data_tp` is not null");
            return (Criteria) this;
        }

        public Criteria andDataTpEqualTo(String value) {
            addCriterion("`data_tp` =", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpNotEqualTo(String value) {
            addCriterion("`data_tp` <>", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpGreaterThan(String value) {
            addCriterion("`data_tp` >", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpGreaterThanOrEqualTo(String value) {
            addCriterion("`data_tp` >=", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpLessThan(String value) {
            addCriterion("`data_tp` <", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpLessThanOrEqualTo(String value) {
            addCriterion("`data_tp` <=", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpLike(String value) {
            addCriterion("`data_tp` like", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpNotLike(String value) {
            addCriterion("`data_tp` not like", value, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpIn(List<String> values) {
            addCriterion("`data_tp` in", values, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpNotIn(List<String> values) {
            addCriterion("`data_tp` not in", values, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpBetween(String value1, String value2) {
            addCriterion("`data_tp` between", value1, value2, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataTpNotBetween(String value1, String value2) {
            addCriterion("`data_tp` not between", value1, value2, "dataTp");
            return (Criteria) this;
        }

        public Criteria andDataKeyIsNull() {
            addCriterion("`data_key` is null");
            return (Criteria) this;
        }

        public Criteria andDataKeyIsNotNull() {
            addCriterion("`data_key` is not null");
            return (Criteria) this;
        }

        public Criteria andDataKeyEqualTo(String value) {
            addCriterion("`data_key` =", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyNotEqualTo(String value) {
            addCriterion("`data_key` <>", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyGreaterThan(String value) {
            addCriterion("`data_key` >", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyGreaterThanOrEqualTo(String value) {
            addCriterion("`data_key` >=", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyLessThan(String value) {
            addCriterion("`data_key` <", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyLessThanOrEqualTo(String value) {
            addCriterion("`data_key` <=", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyLike(String value) {
            addCriterion("`data_key` like", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyNotLike(String value) {
            addCriterion("`data_key` not like", value, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyIn(List<String> values) {
            addCriterion("`data_key` in", values, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyNotIn(List<String> values) {
            addCriterion("`data_key` not in", values, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyBetween(String value1, String value2) {
            addCriterion("`data_key` between", value1, value2, "dataKey");
            return (Criteria) this;
        }

        public Criteria andDataKeyNotBetween(String value1, String value2) {
            addCriterion("`data_key` not between", value1, value2, "dataKey");
            return (Criteria) this;
        }

        public Criteria andLangIsNull() {
            addCriterion("`lang` is null");
            return (Criteria) this;
        }

        public Criteria andLangIsNotNull() {
            addCriterion("`lang` is not null");
            return (Criteria) this;
        }

        public Criteria andLangEqualTo(String value) {
            addCriterion("`lang` =", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotEqualTo(String value) {
            addCriterion("`lang` <>", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangGreaterThan(String value) {
            addCriterion("`lang` >", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangGreaterThanOrEqualTo(String value) {
            addCriterion("`lang` >=", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangLessThan(String value) {
            addCriterion("`lang` <", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangLessThanOrEqualTo(String value) {
            addCriterion("`lang` <=", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangLike(String value) {
            addCriterion("`lang` like", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotLike(String value) {
            addCriterion("`lang` not like", value, "lang");
            return (Criteria) this;
        }

        public Criteria andLangIn(List<String> values) {
            addCriterion("`lang` in", values, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotIn(List<String> values) {
            addCriterion("`lang` not in", values, "lang");
            return (Criteria) this;
        }

        public Criteria andLangBetween(String value1, String value2) {
            addCriterion("`lang` between", value1, value2, "lang");
            return (Criteria) this;
        }

        public Criteria andLangNotBetween(String value1, String value2) {
            addCriterion("`lang` not between", value1, value2, "lang");
            return (Criteria) this;
        }

        public Criteria andDataValIsNull() {
            addCriterion("`data_val` is null");
            return (Criteria) this;
        }

        public Criteria andDataValIsNotNull() {
            addCriterion("`data_val` is not null");
            return (Criteria) this;
        }

        public Criteria andDataValEqualTo(String value) {
            addCriterion("`data_val` =", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValNotEqualTo(String value) {
            addCriterion("`data_val` <>", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValGreaterThan(String value) {
            addCriterion("`data_val` >", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValGreaterThanOrEqualTo(String value) {
            addCriterion("`data_val` >=", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValLessThan(String value) {
            addCriterion("`data_val` <", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValLessThanOrEqualTo(String value) {
            addCriterion("`data_val` <=", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValLike(String value) {
            addCriterion("`data_val` like", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValNotLike(String value) {
            addCriterion("`data_val` not like", value, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValIn(List<String> values) {
            addCriterion("`data_val` in", values, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValNotIn(List<String> values) {
            addCriterion("`data_val` not in", values, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValBetween(String value1, String value2) {
            addCriterion("`data_val` between", value1, value2, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataValNotBetween(String value1, String value2) {
            addCriterion("`data_val` not between", value1, value2, "dataVal");
            return (Criteria) this;
        }

        public Criteria andDataIdxIsNull() {
            addCriterion("`data_idx` is null");
            return (Criteria) this;
        }

        public Criteria andDataIdxIsNotNull() {
            addCriterion("`data_idx` is not null");
            return (Criteria) this;
        }

        public Criteria andDataIdxEqualTo(Integer value) {
            addCriterion("`data_idx` =", value, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxNotEqualTo(Integer value) {
            addCriterion("`data_idx` <>", value, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxGreaterThan(Integer value) {
            addCriterion("`data_idx` >", value, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxGreaterThanOrEqualTo(Integer value) {
            addCriterion("`data_idx` >=", value, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxLessThan(Integer value) {
            addCriterion("`data_idx` <", value, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxLessThanOrEqualTo(Integer value) {
            addCriterion("`data_idx` <=", value, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxIn(List<Integer> values) {
            addCriterion("`data_idx` in", values, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxNotIn(List<Integer> values) {
            addCriterion("`data_idx` not in", values, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxBetween(Integer value1, Integer value2) {
            addCriterion("`data_idx` between", value1, value2, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andDataIdxNotBetween(Integer value1, Integer value2) {
            addCriterion("`data_idx` not between", value1, value2, "dataIdx");
            return (Criteria) this;
        }

        public Criteria andCommentsIsNull() {
            addCriterion("`comments` is null");
            return (Criteria) this;
        }

        public Criteria andCommentsIsNotNull() {
            addCriterion("`comments` is not null");
            return (Criteria) this;
        }

        public Criteria andCommentsEqualTo(String value) {
            addCriterion("`comments` =", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotEqualTo(String value) {
            addCriterion("`comments` <>", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsGreaterThan(String value) {
            addCriterion("`comments` >", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsGreaterThanOrEqualTo(String value) {
            addCriterion("`comments` >=", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLessThan(String value) {
            addCriterion("`comments` <", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLessThanOrEqualTo(String value) {
            addCriterion("`comments` <=", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsLike(String value) {
            addCriterion("`comments` like", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotLike(String value) {
            addCriterion("`comments` not like", value, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsIn(List<String> values) {
            addCriterion("`comments` in", values, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotIn(List<String> values) {
            addCriterion("`comments` not in", values, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsBetween(String value1, String value2) {
            addCriterion("`comments` between", value1, value2, "comments");
            return (Criteria) this;
        }

        public Criteria andCommentsNotBetween(String value1, String value2) {
            addCriterion("`comments` not between", value1, value2, "comments");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_data_dic
    
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
     * Database table : tbl_data_dic
    
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