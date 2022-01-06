package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_batch_step_info

 *
 * @mbg.generated
 */
public class BatchStepInfo extends BatchStepInfoKey implements Serializable {
    /**
     * Database column : tbl_batch_step_info.step_desc
     *
     * @mbg.generated
     */
    private String stepDesc;

    /**
     * Database column : tbl_batch_step_info.step_bean_name
     *
     * @mbg.generated
     */
    private String stepBeanName;

    /**
     * Database column : tbl_batch_step_info.step_run_period
     *
     * @mbg.generated
     */
    private String stepRunPeriod;

    /**
     * Database column : tbl_batch_step_info.step_ctrl_flgs
     *
     * @mbg.generated
     */
    private String stepCtrlFlgs;

    /**
     * Database column : tbl_batch_step_info.rec_st
     *
     * @mbg.generated
     */
    private String recSt;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public BatchStepInfo(Integer batchNo, Integer jobNo, Integer stepNo, String stepDesc, String stepBeanName, String stepRunPeriod, String stepCtrlFlgs, String recSt) {
        super(batchNo, jobNo, stepNo);
        this.stepDesc = stepDesc;
        this.stepBeanName = stepBeanName;
        this.stepRunPeriod = stepRunPeriod;
        this.stepCtrlFlgs = stepCtrlFlgs;
        this.recSt = recSt;
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    public BatchStepInfo() {
        super();
    }

    /**
     * @return step_desc 
     *
     * @mbg.generated
     */
    public String getStepDesc() {
        return stepDesc;
    }

    /**
     * @param stepDesc 
     *
     * @mbg.generated
     */
    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    /**
     * @return step_bean_name 
     *
     * @mbg.generated
     */
    public String getStepBeanName() {
        return stepBeanName;
    }

    /**
     * @param stepBeanName 
     *
     * @mbg.generated
     */
    public void setStepBeanName(String stepBeanName) {
        this.stepBeanName = stepBeanName;
    }

    /**
     * @return step_run_period 
     *
     * @mbg.generated
     */
    public String getStepRunPeriod() {
        return stepRunPeriod;
    }

    /**
     * @param stepRunPeriod 
     *
     * @mbg.generated
     */
    public void setStepRunPeriod(String stepRunPeriod) {
        this.stepRunPeriod = stepRunPeriod;
    }

    /**
     * @return step_ctrl_flgs 
     *
     * @mbg.generated
     */
    public String getStepCtrlFlgs() {
        return stepCtrlFlgs;
    }

    /**
     * @param stepCtrlFlgs 
     *
     * @mbg.generated
     */
    public void setStepCtrlFlgs(String stepCtrlFlgs) {
        this.stepCtrlFlgs = stepCtrlFlgs;
    }

    /**
     * @return rec_st 
     *
     * @mbg.generated
     */
    public String getRecSt() {
        return recSt;
    }

    /**
     * @param recSt 
     *
     * @mbg.generated
     */
    public void setRecSt(String recSt) {
        this.recSt = recSt;
    }

    /**
     * Database table : tbl_batch_step_info
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
        BatchStepInfo other = (BatchStepInfo) that;
        return (this.getBatchNo() == null ? other.getBatchNo() == null : this.getBatchNo().equals(other.getBatchNo()))
            && (this.getJobNo() == null ? other.getJobNo() == null : this.getJobNo().equals(other.getJobNo()))
            && (this.getStepNo() == null ? other.getStepNo() == null : this.getStepNo().equals(other.getStepNo()))
            && (this.getStepDesc() == null ? other.getStepDesc() == null : this.getStepDesc().equals(other.getStepDesc()))
            && (this.getStepBeanName() == null ? other.getStepBeanName() == null : this.getStepBeanName().equals(other.getStepBeanName()))
            && (this.getStepRunPeriod() == null ? other.getStepRunPeriod() == null : this.getStepRunPeriod().equals(other.getStepRunPeriod()))
            && (this.getStepCtrlFlgs() == null ? other.getStepCtrlFlgs() == null : this.getStepCtrlFlgs().equals(other.getStepCtrlFlgs()))
            && (this.getRecSt() == null ? other.getRecSt() == null : this.getRecSt().equals(other.getRecSt()));
    }

    /**
     * Database table : tbl_batch_step_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBatchNo() == null) ? 0 : getBatchNo().hashCode());
        result = prime * result + ((getJobNo() == null) ? 0 : getJobNo().hashCode());
        result = prime * result + ((getStepNo() == null) ? 0 : getStepNo().hashCode());
        result = prime * result + ((getStepDesc() == null) ? 0 : getStepDesc().hashCode());
        result = prime * result + ((getStepBeanName() == null) ? 0 : getStepBeanName().hashCode());
        result = prime * result + ((getStepRunPeriod() == null) ? 0 : getStepRunPeriod().hashCode());
        result = prime * result + ((getStepCtrlFlgs() == null) ? 0 : getStepCtrlFlgs().hashCode());
        result = prime * result + ((getRecSt() == null) ? 0 : getRecSt().hashCode());
        return result;
    }

    /**
     * Database table : tbl_batch_step_info
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
        sb.append(", stepDesc=").append(stepDesc);
        sb.append(", stepBeanName=").append(stepBeanName);
        sb.append(", stepRunPeriod=").append(stepRunPeriod);
        sb.append(", stepCtrlFlgs=").append(stepCtrlFlgs);
        sb.append(", recSt=").append(recSt);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_batch_step_info
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
    public void cloneFrom(BatchStepInfo source) {
        super.cloneFrom(source);
        this.stepDesc=source.stepDesc;
        this.stepBeanName=source.stepBeanName;
        this.stepRunPeriod=source.stepRunPeriod;
        this.stepCtrlFlgs=source.stepCtrlFlgs;
        this.recSt=source.recSt;
    }
}