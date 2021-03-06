package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_bm_role_func

 *
 * @mbg.generated
 */
public class BmRoleFunc implements Serializable {
    /**
     * 角色权限ID
     * Database column : tbl_bm_role_func.role_func_id
     *
     * @mbg.generated
     */
    private Integer roleFuncId;

    /**
     * 角色ID
     * Database column : tbl_bm_role_func.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * 权限ID
     * Database column : tbl_bm_role_func.func_cd
     *
     * @mbg.generated
     */
    private String funcCd;

    /**
     * 更改时间
     * Database column : tbl_bm_role_func.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    public BmRoleFunc(Integer roleFuncId, Integer roleId, String funcCd, Date recCrtTs) {
        this.roleFuncId = roleFuncId;
        this.roleId = roleId;
        this.funcCd = funcCd;
        this.recCrtTs = recCrtTs;
    }

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    public BmRoleFunc() {
        super();
    }

    /**
     * 角色权限ID
     * @return role_func_id 角色权限ID
     *
     * @mbg.generated
     */
    public Integer getRoleFuncId() {
        return roleFuncId;
    }

    /**
     * 角色权限ID
     * @param roleFuncId 角色权限ID
     *
     * @mbg.generated
     */
    public void setRoleFuncId(Integer roleFuncId) {
        this.roleFuncId = roleFuncId;
    }

    /**
     * 角色ID
     * @return role_id 角色ID
     *
     * @mbg.generated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色ID
     * @param roleId 角色ID
     *
     * @mbg.generated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
     * 更改时间
     * @return rec_crt_ts 更改时间
     *
     * @mbg.generated
     */
    public Date getRecCrtTs() {
        return recCrtTs;
    }

    /**
     * 更改时间
     * @param recCrtTs 更改时间
     *
     * @mbg.generated
     */
    public void setRecCrtTs(Date recCrtTs) {
        this.recCrtTs = recCrtTs;
    }

    /**
     * Database table : tbl_bm_role_func
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
        BmRoleFunc other = (BmRoleFunc) that;
        return (this.getRoleFuncId() == null ? other.getRoleFuncId() == null : this.getRoleFuncId().equals(other.getRoleFuncId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getFuncCd() == null ? other.getFuncCd() == null : this.getFuncCd().equals(other.getFuncCd()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()));
    }

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleFuncId() == null) ? 0 : getRoleFuncId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getFuncCd() == null) ? 0 : getFuncCd().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_bm_role_func
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", roleFuncId=").append(roleFuncId);
        sb.append(", roleId=").append(roleId);
        sb.append(", funcCd=").append(funcCd);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Copy properties value from source.
     * @param source The instance that clone from.
     *
     * @mbg.generated
     */
    public void cloneFrom(BmRoleFunc source) {
        this.roleFuncId=source.roleFuncId;
        this.roleId=source.roleId;
        this.funcCd=source.funcCd;
        this.recCrtTs=source.recCrtTs;
    }
}