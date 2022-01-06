package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_trans_proof
 * @author USER
 *
 * @mbg.generated
 */
public class TransProof implements Serializable {
    /**
     * 记录序号
     * Database column : tbl_trans_proof.seq_id
     *
     * @mbg.generated
     */
    private Integer seqId;

    /**
     * 分站ID
     * Database column : tbl_trans_proof.platform_id
     *
     * @mbg.generated
     */
    private String platformId;

    /**
     * 交易流水号
     * Database column : tbl_trans_proof.txn_id
     *
     * @mbg.generated
     */
    private String txnId;

    /**
     * 凭证类型
     * Database column : tbl_trans_proof.proof_type
     *
     * @mbg.generated
     */
    private String proofType;

    /**
     * 文件说明
     * Database column : tbl_trans_proof.file_desc
     *
     * @mbg.generated
     */
    private String fileDesc;

    /**
     * 文件格式：如：jpg，png等
     * Database column : tbl_trans_proof.content_type
     *
     * @mbg.generated
     */
    private String contentType;

    /**
     * 文件内容base64
     * Database column : tbl_trans_proof.content_base64
     *
     * @mbg.generated
     */
    private String contentBase64;

    /**
     * 文件ID
     * Database column : tbl_trans_proof.file_id
     *
     * @mbg.generated
     */
    private String fileId;

    /**
     * 备注
     * Database column : tbl_trans_proof.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * 创建时间
     * Database column : tbl_trans_proof.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * 更新时间
     * Database column : tbl_trans_proof.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public TransProof(Integer seqId, String platformId, String txnId, String proofType, String fileDesc, String contentType, String contentBase64, String fileId, String comment, Date recCrtTs, Date recUpdTs) {
        this.seqId = seqId;
        this.platformId = platformId;
        this.txnId = txnId;
        this.proofType = proofType;
        this.fileDesc = fileDesc;
        this.contentType = contentType;
        this.contentBase64 = contentBase64;
        this.fileId = fileId;
        this.comment = comment;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    public TransProof() {
        super();
    }

    /**
     * 记录序号
     * @return seq_id 记录序号
     *
     * @mbg.generated
     */
    public Integer getSeqId() {
        return seqId;
    }

    /**
     * 记录序号
     * @param seqId 记录序号
     *
     * @mbg.generated
     */
    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    /**
     * 分站ID
     * @return platform_id 分站ID
     *
     * @mbg.generated
     */
    public String getPlatformId() {
        return platformId;
    }

    /**
     * 分站ID
     * @param platformId 分站ID
     *
     * @mbg.generated
     */
    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    /**
     * 交易流水号
     * @return txn_id 交易流水号
     *
     * @mbg.generated
     */
    public String getTxnId() {
        return txnId;
    }

    /**
     * 交易流水号
     * @param txnId 交易流水号
     *
     * @mbg.generated
     */
    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    /**
     * 凭证类型
     * @return proof_type 凭证类型
     *
     * @mbg.generated
     */
    public String getProofType() {
        return proofType;
    }

    /**
     * 凭证类型
     * @param proofType 凭证类型
     *
     * @mbg.generated
     */
    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    /**
     * 文件说明
     * @return file_desc 文件说明
     *
     * @mbg.generated
     */
    public String getFileDesc() {
        return fileDesc;
    }

    /**
     * 文件说明
     * @param fileDesc 文件说明
     *
     * @mbg.generated
     */
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * 文件格式：如：jpg，png等
     * @return content_type 文件格式：如：jpg，png等
     *
     * @mbg.generated
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 文件格式：如：jpg，png等
     * @param contentType 文件格式：如：jpg，png等
     *
     * @mbg.generated
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 文件内容base64
     * @return content_base64 文件内容base64
     *
     * @mbg.generated
     */
    public String getContentBase64() {
        return contentBase64;
    }

    /**
     * 文件内容base64
     * @param contentBase64 文件内容base64
     *
     * @mbg.generated
     */
    public void setContentBase64(String contentBase64) {
        this.contentBase64 = contentBase64;
    }

    /**
     * 文件ID
     * @return file_id 文件ID
     *
     * @mbg.generated
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 文件ID
     * @param fileId 文件ID
     *
     * @mbg.generated
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 备注
     * @return comment 备注
     *
     * @mbg.generated
     */
    public String getComment() {
        return comment;
    }

    /**
     * 备注
     * @param comment 备注
     *
     * @mbg.generated
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 创建时间
     * @return rec_crt_ts 创建时间
     *
     * @mbg.generated
     */
    public Date getRecCrtTs() {
        return recCrtTs;
    }

    /**
     * 创建时间
     * @param recCrtTs 创建时间
     *
     * @mbg.generated
     */
    public void setRecCrtTs(Date recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    /**
     * 更新时间
     * @return rec_upd_ts 更新时间
     *
     * @mbg.generated
     */
    public Date getRecUpdTs() {
        return recUpdTs;
    }

    /**
     * 更新时间
     * @param recUpdTs 更新时间
     *
     * @mbg.generated
     */
    public void setRecUpdTs(Date recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TransProof other = (TransProof) that;
        return (this.getSeqId() == null ? other.getSeqId() == null : this.getSeqId().equals(other.getSeqId()))
            && (this.getPlatformId() == null ? other.getPlatformId() == null : this.getPlatformId().equals(other.getPlatformId()))
            && (this.getTxnId() == null ? other.getTxnId() == null : this.getTxnId().equals(other.getTxnId()))
            && (this.getProofType() == null ? other.getProofType() == null : this.getProofType().equals(other.getProofType()))
            && (this.getFileDesc() == null ? other.getFileDesc() == null : this.getFileDesc().equals(other.getFileDesc()))
            && (this.getContentType() == null ? other.getContentType() == null : this.getContentType().equals(other.getContentType()))
            && (this.getContentBase64() == null ? other.getContentBase64() == null : this.getContentBase64().equals(other.getContentBase64()))
            && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()))
            && (this.getComment() == null ? other.getComment() == null : this.getComment().equals(other.getComment()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqId() == null) ? 0 : getSeqId().hashCode());
        result = prime * result + ((getPlatformId() == null) ? 0 : getPlatformId().hashCode());
        result = prime * result + ((getTxnId() == null) ? 0 : getTxnId().hashCode());
        result = prime * result + ((getProofType() == null) ? 0 : getProofType().hashCode());
        result = prime * result + ((getFileDesc() == null) ? 0 : getFileDesc().hashCode());
        result = prime * result + ((getContentType() == null) ? 0 : getContentType().hashCode());
        result = prime * result + ((getContentBase64() == null) ? 0 : getContentBase64().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getComment() == null) ? 0 : getComment().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_trans_proof
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", seqId=").append(seqId);
        sb.append(", platformId=").append(platformId);
        sb.append(", txnId=").append(txnId);
        sb.append(", proofType=").append(proofType);
        sb.append(", fileDesc=").append(fileDesc);
        sb.append(", contentType=").append(contentType);
        sb.append(", contentBase64=").append(contentBase64);
        sb.append(", fileId=").append(fileId);
        sb.append(", comment=").append(comment);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(TransProof source) {
        this.seqId=source.seqId;
        this.platformId=source.platformId;
        this.txnId=source.txnId;
        this.proofType=source.proofType;
        this.fileDesc=source.fileDesc;
        this.contentType=source.contentType;
        this.contentBase64=source.contentBase64;
        this.fileId=source.fileId;
        this.comment=source.comment;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}