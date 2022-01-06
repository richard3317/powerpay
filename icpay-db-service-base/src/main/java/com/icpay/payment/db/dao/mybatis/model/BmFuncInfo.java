package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_bm_func_info

 *
 * @mbg.generated
 */
public class BmFuncInfo implements Serializable {
    /**
     * 权限ID
     * Database column : tbl_bm_func_info.func_cd
     *
     * @mbg.generated
     */
    private String funcCd;

    /**
     * 权限名称
     * Database column : tbl_bm_func_info.func_nm
     *
     * @mbg.generated
     */
    private String funcNm;

    /**
     * 分类级别:0=模块,1=菜单,2=功能点
     * Database column : tbl_bm_func_info.func_tp
     *
     * @mbg.generated
     */
    private String funcTp;

    /**
     * 权限路径
     * Database column : tbl_bm_func_info.func_href
     *
     * @mbg.generated
     */
    private String funcHref;

    /**
     * 显示顺序，若大于 10000 则不显示
     * Database column : tbl_bm_func_info.func_idx
     *
     * @mbg.generated
     */
    private Integer funcIdx;

    /**
     * 父级权限ID
     * Database column : tbl_bm_func_info.parent_cd
     *
     * @mbg.generated
     */
    private String parentCd;

    /**
     * Database column : tbl_bm_func_info.module_cd
     *
     * @mbg.generated
     */
    private String moduleCd;

    /**
     * Database column : tbl_bm_func_info.func_st
     *
     * @mbg.generated
     */
    private String funcSt;

    /**
     * Database column : tbl_bm_func_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_bm_func_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_bm_func_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_bm_func_info
     *
     * @mbg.generated
     */
    public BmFuncInfo(String funcCd, String funcNm, String funcTp, String funcHref, Integer funcIdx, String parentCd, String moduleCd, String funcSt, Date recCrtTs, Date recUpdTs) {
        this.funcCd = funcCd;
        this.funcNm = funcNm;
        this.funcTp = funcTp;
        this.funcHref = funcHref;
        this.funcIdx = funcIdx;
        this.parentCd = parentCd;
        this.moduleCd = moduleCd;
        this.funcSt = funcSt;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_bm_func_info
     *
     * @mbg.generated
     */
    public BmFuncInfo() {
        super();
    }

    /**
     * 权限ID
     * @return func_cd 权限ID
     *
     * @mbg.generated
     */
    public String getFuncCd() {
        return funcCd;
    }

    /**
     * 权限ID
     * @param funcCd 权限ID
     *
     * @mbg.generated
     */
    public void setFuncCd(String funcCd) {
        this.funcCd = funcCd;
    }

    /**
     * 权限名称
     * @return func_nm 权限名称
     *
     * @mbg.generated
     */
    public String getFuncNm() {
        return funcNm;
    }

    /**
     * 权限名称
     * @param funcNm 权限名称
     *
     * @mbg.generated
     */
    public void setFuncNm(String funcNm) {
        this.funcNm = funcNm;
    }

    /**
     * 分类级别:0=模块,1=菜单,2=功能点
     * @return func_tp 分类级别:0=模块,1=菜单,2=功能点
     *
     * @mbg.generated
     */
    public String getFuncTp() {
        return funcTp;
    }

    /**
     * 分类级别:0=模块,1=菜单,2=功能点
     * @param funcTp 分类级别:0=模块,1=菜单,2=功能点
     *
     * @mbg.generated
     */
    public void setFuncTp(String funcTp) {
        this.funcTp = funcTp;
    }

    /**
     * 权限路径
     * @return func_href 权限路径
     *
     * @mbg.generated
     */
    public String getFuncHref() {
        return funcHref;
    }

    /**
     * 权限路径
     * @param funcHref 权限路径
     *
     * @mbg.generated
     */
    public void setFuncHref(String funcHref) {
        this.funcHref = funcHref;
    }

    /**
     * 显示顺序，若大于 10000 则不显示
     * @return func_idx 显示顺序，若大于 10000 则不显示
     *
     * @mbg.generated
     */
    public Integer getFuncIdx() {
        return funcIdx;
    }

    /**
     * 显示顺序，若大于 10000 则不显示
     * @param funcIdx 显示顺序，若大于 10000 则不显示
     *
     * @mbg.generated
     */
    public void setFuncIdx(Integer funcIdx) {
        this.funcIdx = funcIdx;
    }

    /**
     * 父级权限ID
     * @return parent_cd 父级权限ID
     *
     * @mbg.generated
     */
    public String getParentCd() {
        return parentCd;
    }

    /**
     * 父级权限ID
     * @param parentCd 父级权限ID
     *
     * @mbg.generated
     */
    public void setParentCd(String parentCd) {
        this.parentCd = parentCd;
    }

    /**
     * @return module_cd 
     *
     * @mbg.generated
     */
    public String getModuleCd() {
        return moduleCd;
    }

    /**
     * @param moduleCd 
     *
     * @mbg.generated
     */
    public void setModuleCd(String moduleCd) {
        this.moduleCd = moduleCd;
    }

    /**
     * @return func_st 
     *
     * @mbg.generated
     */
    public String getFuncSt() {
        return funcSt;
    }

    /**
     * @param funcSt 
     *
     * @mbg.generated
     */
    public void setFuncSt(String funcSt) {
        this.funcSt = funcSt;
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
     * Database table : tbl_bm_func_info
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
        BmFuncInfo other = (BmFuncInfo) that;
        return (this.getFuncCd() == null ? other.getFuncCd() == null : this.getFuncCd().equals(other.getFuncCd()))
            && (this.getFuncNm() == null ? other.getFuncNm() == null : this.getFuncNm().equals(other.getFuncNm()))
            && (this.getFuncTp() == null ? other.getFuncTp() == null : this.getFuncTp().equals(other.getFuncTp()))
            && (this.getFuncHref() == null ? other.getFuncHref() == null : this.getFuncHref().equals(other.getFuncHref()))
            && (this.getFuncIdx() == null ? other.getFuncIdx() == null : this.getFuncIdx().equals(other.getFuncIdx()))
            && (this.getParentCd() == null ? other.getParentCd() == null : this.getParentCd().equals(other.getParentCd()))
            && (this.getModuleCd() == null ? other.getModuleCd() == null : this.getModuleCd().equals(other.getModuleCd()))
            && (this.getFuncSt() == null ? other.getFuncSt() == null : this.getFuncSt().equals(other.getFuncSt()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_bm_func_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFuncCd() == null) ? 0 : getFuncCd().hashCode());
        result = prime * result + ((getFuncNm() == null) ? 0 : getFuncNm().hashCode());
        result = prime * result + ((getFuncTp() == null) ? 0 : getFuncTp().hashCode());
        result = prime * result + ((getFuncHref() == null) ? 0 : getFuncHref().hashCode());
        result = prime * result + ((getFuncIdx() == null) ? 0 : getFuncIdx().hashCode());
        result = prime * result + ((getParentCd() == null) ? 0 : getParentCd().hashCode());
        result = prime * result + ((getModuleCd() == null) ? 0 : getModuleCd().hashCode());
        result = prime * result + ((getFuncSt() == null) ? 0 : getFuncSt().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_bm_func_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", funcCd=").append(funcCd);
        sb.append(", funcNm=").append(funcNm);
        sb.append(", funcTp=").append(funcTp);
        sb.append(", funcHref=").append(funcHref);
        sb.append(", funcIdx=").append(funcIdx);
        sb.append(", parentCd=").append(parentCd);
        sb.append(", moduleCd=").append(moduleCd);
        sb.append(", funcSt=").append(funcSt);
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
    public void cloneFrom(BmFuncInfo source) {
        this.funcCd=source.funcCd;
        this.funcNm=source.funcNm;
        this.funcTp=source.funcTp;
        this.funcHref=source.funcHref;
        this.funcIdx=source.funcIdx;
        this.parentCd=source.parentCd;
        this.moduleCd=source.moduleCd;
        this.funcSt=source.funcSt;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}