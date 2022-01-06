package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_i18n_msgs
 * @author USER
 *
 * @mbg.generated
 */
public class I18nMsgsKey implements Serializable {
    /**
     * 分类，若为 #CONF 表示为语系配置项
     * Database column : tbl_i18n_msgs.category
     *
     * @mbg.generated
     */
    private String category;

    /**
     * 语言
     * Database column : tbl_i18n_msgs.lang
     *
     * @mbg.generated
     */
    private String lang;

    /**
     * 讯息代码(或配置键值，alt-lang 表示 替代语系)
     * Database column : tbl_i18n_msgs.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    public I18nMsgsKey(String category, String lang, String code) {
        this.category = category;
        this.lang = lang;
        this.code = code;
    }

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    public I18nMsgsKey() {
        super();
    }

    /**
     * 分类，若为 #CONF 表示为语系配置项
     * @return category 分类，若为 #CONF 表示为语系配置项
     *
     * @mbg.generated
     */
    public String getCategory() {
        return category;
    }

    /**
     * 分类，若为 #CONF 表示为语系配置项
     * @param category 分类，若为 #CONF 表示为语系配置项
     *
     * @mbg.generated
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 语言
     * @return lang 语言
     *
     * @mbg.generated
     */
    public String getLang() {
        return lang;
    }

    /**
     * 语言
     * @param lang 语言
     *
     * @mbg.generated
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * 讯息代码(或配置键值，alt-lang 表示 替代语系)
     * @return code 讯息代码(或配置键值，alt-lang 表示 替代语系)
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * 讯息代码(或配置键值，alt-lang 表示 替代语系)
     * @param code 讯息代码(或配置键值，alt-lang 表示 替代语系)
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Database table : tbl_i18n_msgs
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
        I18nMsgsKey other = (I18nMsgsKey) that;
        return (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getLang() == null ? other.getLang() == null : this.getLang().equals(other.getLang()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()));
    }

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getLang() == null) ? 0 : getLang().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        return result;
    }

    /**
     * Database table : tbl_i18n_msgs
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", category=").append(category);
        sb.append(", lang=").append(lang);
        sb.append(", code=").append(code);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(I18nMsgsKey source) {
        this.category=source.category;
        this.lang=source.lang;
        this.code=source.code;
    }
}