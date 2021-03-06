package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chat_binding

 *
 * @mbg.generated
 */
public class ChatBinding extends ChatBindingKey implements Serializable {
    /**
     * 1=有效，0=无效
     * Database column : tbl_chat_binding.state
     *
     * @mbg.generated
     */
    private String state;

    /**
     * 標籤，以;區隔，例如 "BM;MER;"
     * Database column : tbl_chat_binding.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     * Database column : tbl_chat_binding.comments
     *
     * @mbg.generated
     */
    private String comments;

    /**
     * Database column : tbl_chat_binding.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_chat_binding.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_chat_binding
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chat_binding
     *
     * @mbg.generated
     */
    public ChatBinding(String category, String mchntCd, String chatId, String state, String tags, String comments, Date recCrtTs, Date recUpdTs) {
        super(category, mchntCd, chatId);
        this.state = state;
        this.tags = tags;
        this.comments = comments;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_chat_binding
     *
     * @mbg.generated
     */
    public ChatBinding() {
        super();
    }

    /**
     * 1=有效，0=无效
     * @return state 1=有效，0=无效
     *
     * @mbg.generated
     */
    public String getState() {
        return state;
    }

    /**
     * 1=有效，0=无效
     * @param state 1=有效，0=无效
     *
     * @mbg.generated
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 標籤，以;區隔，例如 "BM;MER;"
     * @return tags 標籤，以;區隔，例如 "BM;MER;"
     *
     * @mbg.generated
     */
    public String getTags() {
        return tags;
    }

    /**
     * 標籤，以;區隔，例如 "BM;MER;"
     * @param tags 標籤，以;區隔，例如 "BM;MER;"
     *
     * @mbg.generated
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * @return comments 
     *
     * @mbg.generated
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments 
     *
     * @mbg.generated
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return rec_crt_ts 
     *
     * @mbg.generated
     */
    public Date getRecCrtTs() {
        return recCrtTs;
    }

    /**
     * @param recCrtTs 
     *
     * @mbg.generated
     */
    public void setRecCrtTs(Date recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    /**
     * @return rec_upd_ts 
     *
     * @mbg.generated
     */
    public Date getRecUpdTs() {
        return recUpdTs;
    }

    /**
     * @param recUpdTs 
     *
     * @mbg.generated
     */
    public void setRecUpdTs(Date recUpdTs) {
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_chat_binding
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
        ChatBinding other = (ChatBinding) that;
        return (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getMchntCd() == null ? other.getMchntCd() == null : this.getMchntCd().equals(other.getMchntCd()))
            && (this.getChatId() == null ? other.getChatId() == null : this.getChatId().equals(other.getChatId()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getTags() == null ? other.getTags() == null : this.getTags().equals(other.getTags()))
            && (this.getComments() == null ? other.getComments() == null : this.getComments().equals(other.getComments()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_chat_binding
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getMchntCd() == null) ? 0 : getMchntCd().hashCode());
        result = prime * result + ((getChatId() == null) ? 0 : getChatId().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getTags() == null) ? 0 : getTags().hashCode());
        result = prime * result + ((getComments() == null) ? 0 : getComments().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chat_binding
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(getSuperToString());
        sb.append(", state=").append(state);
        sb.append(", tags=").append(tags);
        sb.append(", comments=").append(comments);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_chat_binding
     *
     * @mbg.generated
     */
    private String getSuperToString() {
        String s = super.toString();
        String superCls = super.getClass().getSimpleName();
        if (!(s.contains("[Hash=") && s.contains(superCls))) return "";
        int end=-1;
        int start = s.indexOf("[Hash=");
        if (start>=0) {
            	start = s.indexOf(", ", start);
            	if (start>=0) {
                		end = s.lastIndexOf(']');
                		if (end>0) 
                			return ", "+s.substring(start+2, end)+"";
                	}
            }
            return "";
        }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(ChatBinding source) {
        super.cloneFrom(source);
        this.state=source.state;
        this.tags=source.tags;
        this.comments=source.comments;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}