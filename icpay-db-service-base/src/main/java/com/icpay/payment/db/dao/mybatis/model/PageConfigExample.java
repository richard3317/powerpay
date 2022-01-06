package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class PageConfigExample {
    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public PageConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_page_config
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
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_page_config
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_page_config
    
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

        public Criteria andEntityNmIsNull() {
            addCriterion("`entity_nm` is null");
            return (Criteria) this;
        }

        public Criteria andEntityNmIsNotNull() {
            addCriterion("`entity_nm` is not null");
            return (Criteria) this;
        }

        public Criteria andEntityNmEqualTo(String value) {
            addCriterion("`entity_nm` =", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmNotEqualTo(String value) {
            addCriterion("`entity_nm` <>", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmGreaterThan(String value) {
            addCriterion("`entity_nm` >", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmGreaterThanOrEqualTo(String value) {
            addCriterion("`entity_nm` >=", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmLessThan(String value) {
            addCriterion("`entity_nm` <", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmLessThanOrEqualTo(String value) {
            addCriterion("`entity_nm` <=", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmLike(String value) {
            addCriterion("`entity_nm` like", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmNotLike(String value) {
            addCriterion("`entity_nm` not like", value, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmIn(List<String> values) {
            addCriterion("`entity_nm` in", values, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmNotIn(List<String> values) {
            addCriterion("`entity_nm` not in", values, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmBetween(String value1, String value2) {
            addCriterion("`entity_nm` between", value1, value2, "entityNm");
            return (Criteria) this;
        }

        public Criteria andEntityNmNotBetween(String value1, String value2) {
            addCriterion("`entity_nm` not between", value1, value2, "entityNm");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull() {
            addCriterion("`field` is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull() {
            addCriterion("`field` is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(String value) {
            addCriterion("`field` =", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(String value) {
            addCriterion("`field` <>", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(String value) {
            addCriterion("`field` >", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(String value) {
            addCriterion("`field` >=", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(String value) {
            addCriterion("`field` <", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(String value) {
            addCriterion("`field` <=", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldLike(String value) {
            addCriterion("`field` like", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(String value) {
            addCriterion("`field` not like", value, "field");
            return (Criteria) this;
        }

        public Criteria andFieldIn(List<String> values) {
            addCriterion("`field` in", values, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(List<String> values) {
            addCriterion("`field` not in", values, "field");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(String value1, String value2) {
            addCriterion("`field` between", value1, value2, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(String value1, String value2) {
            addCriterion("`field` not between", value1, value2, "field");
            return (Criteria) this;
        }

        public Criteria andFieldNmIsNull() {
            addCriterion("`field_nm` is null");
            return (Criteria) this;
        }

        public Criteria andFieldNmIsNotNull() {
            addCriterion("`field_nm` is not null");
            return (Criteria) this;
        }

        public Criteria andFieldNmEqualTo(String value) {
            addCriterion("`field_nm` =", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmNotEqualTo(String value) {
            addCriterion("`field_nm` <>", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmGreaterThan(String value) {
            addCriterion("`field_nm` >", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmGreaterThanOrEqualTo(String value) {
            addCriterion("`field_nm` >=", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmLessThan(String value) {
            addCriterion("`field_nm` <", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmLessThanOrEqualTo(String value) {
            addCriterion("`field_nm` <=", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmLike(String value) {
            addCriterion("`field_nm` like", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmNotLike(String value) {
            addCriterion("`field_nm` not like", value, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmIn(List<String> values) {
            addCriterion("`field_nm` in", values, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmNotIn(List<String> values) {
            addCriterion("`field_nm` not in", values, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmBetween(String value1, String value2) {
            addCriterion("`field_nm` between", value1, value2, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andFieldNmNotBetween(String value1, String value2) {
            addCriterion("`field_nm` not between", value1, value2, "fieldNm");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthIsNull() {
            addCriterion("`display_width` is null");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthIsNotNull() {
            addCriterion("`display_width` is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthEqualTo(Integer value) {
            addCriterion("`display_width` =", value, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthNotEqualTo(Integer value) {
            addCriterion("`display_width` <>", value, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthGreaterThan(Integer value) {
            addCriterion("`display_width` >", value, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("`display_width` >=", value, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthLessThan(Integer value) {
            addCriterion("`display_width` <", value, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthLessThanOrEqualTo(Integer value) {
            addCriterion("`display_width` <=", value, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthIn(List<Integer> values) {
            addCriterion("`display_width` in", values, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthNotIn(List<Integer> values) {
            addCriterion("`display_width` not in", values, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthBetween(Integer value1, Integer value2) {
            addCriterion("`display_width` between", value1, value2, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andDisplayWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("`display_width` not between", value1, value2, "displayWidth");
            return (Criteria) this;
        }

        public Criteria andAlignIsNull() {
            addCriterion("`align` is null");
            return (Criteria) this;
        }

        public Criteria andAlignIsNotNull() {
            addCriterion("`align` is not null");
            return (Criteria) this;
        }

        public Criteria andAlignEqualTo(String value) {
            addCriterion("`align` =", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignNotEqualTo(String value) {
            addCriterion("`align` <>", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignGreaterThan(String value) {
            addCriterion("`align` >", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignGreaterThanOrEqualTo(String value) {
            addCriterion("`align` >=", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignLessThan(String value) {
            addCriterion("`align` <", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignLessThanOrEqualTo(String value) {
            addCriterion("`align` <=", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignLike(String value) {
            addCriterion("`align` like", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignNotLike(String value) {
            addCriterion("`align` not like", value, "align");
            return (Criteria) this;
        }

        public Criteria andAlignIn(List<String> values) {
            addCriterion("`align` in", values, "align");
            return (Criteria) this;
        }

        public Criteria andAlignNotIn(List<String> values) {
            addCriterion("`align` not in", values, "align");
            return (Criteria) this;
        }

        public Criteria andAlignBetween(String value1, String value2) {
            addCriterion("`align` between", value1, value2, "align");
            return (Criteria) this;
        }

        public Criteria andAlignNotBetween(String value1, String value2) {
            addCriterion("`align` not between", value1, value2, "align");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigIsNull() {
            addCriterion("`display_config` is null");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigIsNotNull() {
            addCriterion("`display_config` is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigEqualTo(String value) {
            addCriterion("`display_config` =", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigNotEqualTo(String value) {
            addCriterion("`display_config` <>", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigGreaterThan(String value) {
            addCriterion("`display_config` >", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigGreaterThanOrEqualTo(String value) {
            addCriterion("`display_config` >=", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigLessThan(String value) {
            addCriterion("`display_config` <", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigLessThanOrEqualTo(String value) {
            addCriterion("`display_config` <=", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigLike(String value) {
            addCriterion("`display_config` like", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigNotLike(String value) {
            addCriterion("`display_config` not like", value, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigIn(List<String> values) {
            addCriterion("`display_config` in", values, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigNotIn(List<String> values) {
            addCriterion("`display_config` not in", values, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigBetween(String value1, String value2) {
            addCriterion("`display_config` between", value1, value2, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayConfigNotBetween(String value1, String value2) {
            addCriterion("`display_config` not between", value1, value2, "displayConfig");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxIsNull() {
            addCriterion("`display_idx` is null");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxIsNotNull() {
            addCriterion("`display_idx` is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxEqualTo(Integer value) {
            addCriterion("`display_idx` =", value, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxNotEqualTo(Integer value) {
            addCriterion("`display_idx` <>", value, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxGreaterThan(Integer value) {
            addCriterion("`display_idx` >", value, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxGreaterThanOrEqualTo(Integer value) {
            addCriterion("`display_idx` >=", value, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxLessThan(Integer value) {
            addCriterion("`display_idx` <", value, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxLessThanOrEqualTo(Integer value) {
            addCriterion("`display_idx` <=", value, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxIn(List<Integer> values) {
            addCriterion("`display_idx` in", values, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxNotIn(List<Integer> values) {
            addCriterion("`display_idx` not in", values, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxBetween(Integer value1, Integer value2) {
            addCriterion("`display_idx` between", value1, value2, "displayIdx");
            return (Criteria) this;
        }

        public Criteria andDisplayIdxNotBetween(Integer value1, Integer value2) {
            addCriterion("`display_idx` not between", value1, value2, "displayIdx");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_page_config
    
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
     * Database table : tbl_page_config
    
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