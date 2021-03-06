package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_bm_role_info

 *
 * @mbg.generated
 */
public class BmRoleInfo implements Serializable {
    /**
     * Database column : tbl_bm_role_info.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     * Database column : tbl_bm_role_info.role_nm
     *
     * @mbg.generated
     */
    private String roleNm;

    /**
     * Database column : tbl_bm_role_info.role_st
     *
     * @mbg.generated
     */
    private String roleSt;

    /**
     * Database column : tbl_bm_role_info.grp_no
     *
     * @mbg.generated
     */
    private String grpNo;

    /**
     * Database column : tbl_bm_role_info.department
     *
     * @mbg.generated
     */
    private String department;

    /**
     * Database column : tbl_bm_role_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_bm_role_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    public BmRoleInfo(Integer roleId, String roleNm, String roleSt, String grpNo, String department, Date recCrtTs, Date recUpdTs) {
        this.roleId = roleId;
        this.roleNm = roleNm;
        this.roleSt = roleSt;
        this.grpNo = grpNo;
        this.department = department;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    public BmRoleInfo() {
        super();
    }

    /**
     * @return role_id 
     *
     * @mbg.generated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId 
     *
     * @mbg.generated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return role_nm 
     *
     * @mbg.generated
     */
    public String getRoleNm() {
        return roleNm;
    }

    /**
     * @param roleNm 
     *
     * @mbg.generated
     */
    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    /**
     * @return role_st 
     *
     * @mbg.generated
     */
    public String getRoleSt() {
        return roleSt;
    }

    /**
     * @param roleSt 
     *
     * @mbg.generated
     */
    public void setRoleSt(String roleSt) {
        this.roleSt = roleSt;
    }

    /**
     * @return grp_no 
     *
     * @mbg.generated
     */
    public String getGrpNo() {
        return grpNo;
    }

    /**
     * @param grpNo 
     *
     * @mbg.generated
     */
    public void setGrpNo(String grpNo) {
        this.grpNo = grpNo;
    }

    /**
     * @return department 
     *
     * @mbg.generated
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department 
     *
     * @mbg.generated
     */
    public void setDepartment(String department) {
        this.department = department;
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
     * Database table : tbl_bm_role_info
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
        BmRoleInfo other = (BmRoleInfo) that;
        return (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getRoleNm() == null ? other.getRoleNm() == null : this.getRoleNm().equals(other.getRoleNm()))
            && (this.getRoleSt() == null ? other.getRoleSt() == null : this.getRoleSt().equals(other.getRoleSt()))
            && (this.getGrpNo() == null ? other.getGrpNo() == null : this.getGrpNo().equals(other.getGrpNo()))
            && (this.getDepartment() == null ? other.getDepartment() == null : this.getDepartment().equals(other.getDepartment()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getRoleNm() == null) ? 0 : getRoleNm().hashCode());
        result = prime * result + ((getRoleSt() == null) ? 0 : getRoleSt().hashCode());
        result = prime * result + ((getGrpNo() == null) ? 0 : getGrpNo().hashCode());
        result = prime * result + ((getDepartment() == null) ? 0 : getDepartment().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_bm_role_info
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash=").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", roleNm=").append(roleNm);
        sb.append(", roleSt=").append(roleSt);
        sb.append(", grpNo=").append(grpNo);
        sb.append(", department=").append(department);
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
    public void cloneFrom(BmRoleInfo source) {
        this.roleId=source.roleId;
        this.roleNm=source.roleNm;
        this.roleSt=source.roleSt;
        this.grpNo=source.grpNo;
        this.department=source.department;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}