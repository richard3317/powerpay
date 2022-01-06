package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelegramMerchatidExample {
    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public TelegramMerchatidExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_telegram_merchatid
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
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_telegram_merchatid
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_telegram_merchatid
     * @author USER
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

        public Criteria andChatIdIsNull() {
            addCriterion("`chat_id` is null");
            return (Criteria) this;
        }

        public Criteria andChatIdIsNotNull() {
            addCriterion("`chat_id` is not null");
            return (Criteria) this;
        }

        public Criteria andChatIdEqualTo(String value) {
            addCriterion("`chat_id` =", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotEqualTo(String value) {
            addCriterion("`chat_id` <>", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdGreaterThan(String value) {
            addCriterion("`chat_id` >", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdGreaterThanOrEqualTo(String value) {
            addCriterion("`chat_id` >=", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLessThan(String value) {
            addCriterion("`chat_id` <", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLessThanOrEqualTo(String value) {
            addCriterion("`chat_id` <=", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLike(String value) {
            addCriterion("`chat_id` like", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotLike(String value) {
            addCriterion("`chat_id` not like", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdIn(List<String> values) {
            addCriterion("`chat_id` in", values, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotIn(List<String> values) {
            addCriterion("`chat_id` not in", values, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdBetween(String value1, String value2) {
            addCriterion("`chat_id` between", value1, value2, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotBetween(String value1, String value2) {
            addCriterion("`chat_id` not between", value1, value2, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNull() {
            addCriterion("`chat_type` is null");
            return (Criteria) this;
        }

        public Criteria andChatTypeIsNotNull() {
            addCriterion("`chat_type` is not null");
            return (Criteria) this;
        }

        public Criteria andChatTypeEqualTo(String value) {
            addCriterion("`chat_type` =", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotEqualTo(String value) {
            addCriterion("`chat_type` <>", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThan(String value) {
            addCriterion("`chat_type` >", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`chat_type` >=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThan(String value) {
            addCriterion("`chat_type` <", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLessThanOrEqualTo(String value) {
            addCriterion("`chat_type` <=", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeLike(String value) {
            addCriterion("`chat_type` like", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotLike(String value) {
            addCriterion("`chat_type` not like", value, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeIn(List<String> values) {
            addCriterion("`chat_type` in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotIn(List<String> values) {
            addCriterion("`chat_type` not in", values, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeBetween(String value1, String value2) {
            addCriterion("`chat_type` between", value1, value2, "chatType");
            return (Criteria) this;
        }

        public Criteria andChatTypeNotBetween(String value1, String value2) {
            addCriterion("`chat_type` not between", value1, value2, "chatType");
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
     * Database table : tbl_telegram_merchatid
     * @author USER
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
     * Database table : tbl_telegram_merchatid
     * @author USER
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