package com.icpay.payment.db.dao.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransProofExample {
    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    protected Integer startNum;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    protected Integer pageSize;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public TransProofExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * Database table : tbl_trans_proof
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
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * Database table : tbl_trans_proof
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

        public Criteria andPlatformIdIsNull() {
            addCriterion("`platform_id` is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNotNull() {
            addCriterion("`platform_id` is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdEqualTo(String value) {
            addCriterion("`platform_id` =", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotEqualTo(String value) {
            addCriterion("`platform_id` <>", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThan(String value) {
            addCriterion("`platform_id` >", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThanOrEqualTo(String value) {
            addCriterion("`platform_id` >=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThan(String value) {
            addCriterion("`platform_id` <", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThanOrEqualTo(String value) {
            addCriterion("`platform_id` <=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLike(String value) {
            addCriterion("`platform_id` like", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotLike(String value) {
            addCriterion("`platform_id` not like", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIn(List<String> values) {
            addCriterion("`platform_id` in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotIn(List<String> values) {
            addCriterion("`platform_id` not in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdBetween(String value1, String value2) {
            addCriterion("`platform_id` between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotBetween(String value1, String value2) {
            addCriterion("`platform_id` not between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andTxnIdIsNull() {
            addCriterion("`txn_id` is null");
            return (Criteria) this;
        }

        public Criteria andTxnIdIsNotNull() {
            addCriterion("`txn_id` is not null");
            return (Criteria) this;
        }

        public Criteria andTxnIdEqualTo(String value) {
            addCriterion("`txn_id` =", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdNotEqualTo(String value) {
            addCriterion("`txn_id` <>", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdGreaterThan(String value) {
            addCriterion("`txn_id` >", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdGreaterThanOrEqualTo(String value) {
            addCriterion("`txn_id` >=", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdLessThan(String value) {
            addCriterion("`txn_id` <", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdLessThanOrEqualTo(String value) {
            addCriterion("`txn_id` <=", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdLike(String value) {
            addCriterion("`txn_id` like", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdNotLike(String value) {
            addCriterion("`txn_id` not like", value, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdIn(List<String> values) {
            addCriterion("`txn_id` in", values, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdNotIn(List<String> values) {
            addCriterion("`txn_id` not in", values, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdBetween(String value1, String value2) {
            addCriterion("`txn_id` between", value1, value2, "txnId");
            return (Criteria) this;
        }

        public Criteria andTxnIdNotBetween(String value1, String value2) {
            addCriterion("`txn_id` not between", value1, value2, "txnId");
            return (Criteria) this;
        }

        public Criteria andProofTypeIsNull() {
            addCriterion("`proof_type` is null");
            return (Criteria) this;
        }

        public Criteria andProofTypeIsNotNull() {
            addCriterion("`proof_type` is not null");
            return (Criteria) this;
        }

        public Criteria andProofTypeEqualTo(String value) {
            addCriterion("`proof_type` =", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeNotEqualTo(String value) {
            addCriterion("`proof_type` <>", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeGreaterThan(String value) {
            addCriterion("`proof_type` >", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`proof_type` >=", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeLessThan(String value) {
            addCriterion("`proof_type` <", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeLessThanOrEqualTo(String value) {
            addCriterion("`proof_type` <=", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeLike(String value) {
            addCriterion("`proof_type` like", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeNotLike(String value) {
            addCriterion("`proof_type` not like", value, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeIn(List<String> values) {
            addCriterion("`proof_type` in", values, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeNotIn(List<String> values) {
            addCriterion("`proof_type` not in", values, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeBetween(String value1, String value2) {
            addCriterion("`proof_type` between", value1, value2, "proofType");
            return (Criteria) this;
        }

        public Criteria andProofTypeNotBetween(String value1, String value2) {
            addCriterion("`proof_type` not between", value1, value2, "proofType");
            return (Criteria) this;
        }

        public Criteria andFileDescIsNull() {
            addCriterion("`file_desc` is null");
            return (Criteria) this;
        }

        public Criteria andFileDescIsNotNull() {
            addCriterion("`file_desc` is not null");
            return (Criteria) this;
        }

        public Criteria andFileDescEqualTo(String value) {
            addCriterion("`file_desc` =", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescNotEqualTo(String value) {
            addCriterion("`file_desc` <>", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescGreaterThan(String value) {
            addCriterion("`file_desc` >", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescGreaterThanOrEqualTo(String value) {
            addCriterion("`file_desc` >=", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescLessThan(String value) {
            addCriterion("`file_desc` <", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescLessThanOrEqualTo(String value) {
            addCriterion("`file_desc` <=", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescLike(String value) {
            addCriterion("`file_desc` like", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescNotLike(String value) {
            addCriterion("`file_desc` not like", value, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescIn(List<String> values) {
            addCriterion("`file_desc` in", values, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescNotIn(List<String> values) {
            addCriterion("`file_desc` not in", values, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescBetween(String value1, String value2) {
            addCriterion("`file_desc` between", value1, value2, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andFileDescNotBetween(String value1, String value2) {
            addCriterion("`file_desc` not between", value1, value2, "fileDesc");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNull() {
            addCriterion("`content_type` is null");
            return (Criteria) this;
        }

        public Criteria andContentTypeIsNotNull() {
            addCriterion("`content_type` is not null");
            return (Criteria) this;
        }

        public Criteria andContentTypeEqualTo(String value) {
            addCriterion("`content_type` =", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotEqualTo(String value) {
            addCriterion("`content_type` <>", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThan(String value) {
            addCriterion("`content_type` >", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeGreaterThanOrEqualTo(String value) {
            addCriterion("`content_type` >=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThan(String value) {
            addCriterion("`content_type` <", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLessThanOrEqualTo(String value) {
            addCriterion("`content_type` <=", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeLike(String value) {
            addCriterion("`content_type` like", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotLike(String value) {
            addCriterion("`content_type` not like", value, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeIn(List<String> values) {
            addCriterion("`content_type` in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotIn(List<String> values) {
            addCriterion("`content_type` not in", values, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeBetween(String value1, String value2) {
            addCriterion("`content_type` between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentTypeNotBetween(String value1, String value2) {
            addCriterion("`content_type` not between", value1, value2, "contentType");
            return (Criteria) this;
        }

        public Criteria andContentBase64IsNull() {
            addCriterion("`content_base64` is null");
            return (Criteria) this;
        }

        public Criteria andContentBase64IsNotNull() {
            addCriterion("`content_base64` is not null");
            return (Criteria) this;
        }

        public Criteria andContentBase64EqualTo(String value) {
            addCriterion("`content_base64` =", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64NotEqualTo(String value) {
            addCriterion("`content_base64` <>", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64GreaterThan(String value) {
            addCriterion("`content_base64` >", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64GreaterThanOrEqualTo(String value) {
            addCriterion("`content_base64` >=", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64LessThan(String value) {
            addCriterion("`content_base64` <", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64LessThanOrEqualTo(String value) {
            addCriterion("`content_base64` <=", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64Like(String value) {
            addCriterion("`content_base64` like", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64NotLike(String value) {
            addCriterion("`content_base64` not like", value, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64In(List<String> values) {
            addCriterion("`content_base64` in", values, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64NotIn(List<String> values) {
            addCriterion("`content_base64` not in", values, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64Between(String value1, String value2) {
            addCriterion("`content_base64` between", value1, value2, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andContentBase64NotBetween(String value1, String value2) {
            addCriterion("`content_base64` not between", value1, value2, "contentBase64");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("`file_id` is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("`file_id` is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("`file_id` =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("`file_id` <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("`file_id` >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("`file_id` >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("`file_id` <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("`file_id` <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("`file_id` like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("`file_id` not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("`file_id` in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("`file_id` not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("`file_id` between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("`file_id` not between", value1, value2, "fileId");
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
     * Database table : tbl_trans_proof
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
     * Database table : tbl_trans_proof
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