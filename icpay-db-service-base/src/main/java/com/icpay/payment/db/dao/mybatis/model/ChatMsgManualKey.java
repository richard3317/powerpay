package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_chat_msg_manual

 *
 * @mbg.generated
 */
public class ChatMsgManualKey implements Serializable {
    /**
     * TG, COMMON
     * Database column : tbl_chat_msg_manual.catelog
     *
     * @mbg.generated
     */
    private String catelog;

    /**
     * 命令：例如 bindcad(绑卡), calcmac(计算MAC)
     * Database column : tbl_chat_msg_manual.cmd_key
     *
     * @mbg.generated
     */
    private String cmdKey;

    /**
     * Database table : tbl_chat_msg_manual
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_chat_msg_manual
     *
     * @mbg.generated
     */
    public ChatMsgManualKey(String catelog, String cmdKey) {
        this.catelog = catelog;
        this.cmdKey = cmdKey;
    }

    /**
     * Database table : tbl_chat_msg_manual
     *
     * @mbg.generated
     */
    public ChatMsgManualKey() {
        super();
    }

    /**
     * TG, COMMON
     * @return catelog TG, COMMON
     *
     * @mbg.generated
     */
    public String getCatelog() {
        return catelog;
    }

    /**
     * TG, COMMON
     * @param catelog TG, COMMON
     *
     * @mbg.generated
     */
    public void setCatelog(String catelog) {
        this.catelog = catelog;
    }

    /**
     * 命令：例如 bindcad(绑卡), calcmac(计算MAC)
     * @return cmd_key 命令：例如 bindcad(绑卡), calcmac(计算MAC)
     *
     * @mbg.generated
     */
    public String getCmdKey() {
        return cmdKey;
    }

    /**
     * 命令：例如 bindcad(绑卡), calcmac(计算MAC)
     * @param cmdKey 命令：例如 bindcad(绑卡), calcmac(计算MAC)
     *
     * @mbg.generated
     */
    public void setCmdKey(String cmdKey) {
        this.cmdKey = cmdKey;
    }

    /**
     * Database table : tbl_chat_msg_manual
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
        ChatMsgManualKey other = (ChatMsgManualKey) that;
        return (this.getCatelog() == null ? other.getCatelog() == null : this.getCatelog().equals(other.getCatelog()))
            && (this.getCmdKey() == null ? other.getCmdKey() == null : this.getCmdKey().equals(other.getCmdKey()));
    }

    /**
     * Database table : tbl_chat_msg_manual
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCatelog() == null) ? 0 : getCatelog().hashCode());
        result = prime * result + ((getCmdKey() == null) ? 0 : getCmdKey().hashCode());
        return result;
    }

    /**
     * Database table : tbl_chat_msg_manual
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", catelog=").append(catelog);
        sb.append(", cmdKey=").append(cmdKey);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(ChatMsgManualKey source) {
        this.catelog=source.catelog;
        this.cmdKey=source.cmdKey;
    }
}