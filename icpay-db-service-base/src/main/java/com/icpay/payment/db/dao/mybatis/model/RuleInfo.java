package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_rule_info

 *
 * @mbg.generated
 */
public class RuleInfo extends RuleInfoKey implements Serializable {
    /**
     * 规则类型，FREQ=计次/单次，ACC=数值累加(例如：累计金额)
     * Database column : tbl_rule_info.rule_type
     *
     * @mbg.generated
     */
    private String ruleType;

    /**
     * 計次或計量的分組Key, 以 JsonPath語法表示，逗號區隔各Key，例如: $.user.merId,$.user.userId
     * Database column : tbl_rule_info.group_by_keys
     *
     * @mbg.generated
     */
    private String groupByKeys;

    /**
     * 累计值字段名称(以 JsonPath語法表示)，用以累加数值，例如: $.params.txnAmt
     * Database column : tbl_rule_info.acc_key
     *
     * @mbg.generated
     */
    private String accKey;

    /**
     * 排序
     * Database column : tbl_rule_info.sort_order
     *
     * @mbg.generated
     */
    private Integer sortOrder;

    /**
     * 规则比对方式 "AND" 或 "OR", 莫认为 "AND"
     * Database column : tbl_rule_info.check_type
     *
     * @mbg.generated
     */
    private String checkType;

    /**
     * 阀值(大于此值则触发)
     * Database column : tbl_rule_info.threshold
     *
     * @mbg.generated
     */
    private Long threshold;

    /**
     * 评估规则的时间段，单位:秒
     * Database column : tbl_rule_info.timeout
     *
     * @mbg.generated
     */
    private Integer timeout;

    /**
     * 动作代号，用以指定符合规则后采取的行动(tbl_action)
     * Database column : tbl_rule_info.act_id
     *
     * @mbg.generated
     */
    private String actId;

    /**
     * 默认动作参数，若动作项目未指定参数，则以此字段作为动作参数
     * Database column : tbl_rule_info.act_param
     *
     * @mbg.generated
     */
    private String actParam;

    /**
     * 0=无效，1=有效
     * Database column : tbl_rule_info.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     * 描述
     * Database column : tbl_rule_info.desc
     *
     * @mbg.generated
     */
    private String desc;

    /**
     * 备注
     * Database column : tbl_rule_info.memo
     *
     * @mbg.generated
     */
    private String memo;

    /**
     * Database column : tbl_rule_info.rec_crt_ts
     *
     * @mbg.generated
     */
    private Date recCrtTs;

    /**
     * Database column : tbl_rule_info.rec_upd_ts
     *
     * @mbg.generated
     */
    private Date recUpdTs;

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    public RuleInfo(String category, String ruleId, String ruleType, String groupByKeys, String accKey, Integer sortOrder, String checkType, Long threshold, Integer timeout, String actId, String actParam, String status, String desc, String memo, Date recCrtTs, Date recUpdTs) {
        super(category, ruleId);
        this.ruleType = ruleType;
        this.groupByKeys = groupByKeys;
        this.accKey = accKey;
        this.sortOrder = sortOrder;
        this.checkType = checkType;
        this.threshold = threshold;
        this.timeout = timeout;
        this.actId = actId;
        this.actParam = actParam;
        this.status = status;
        this.desc = desc;
        this.memo = memo;
        this.recCrtTs = recCrtTs;
        this.recUpdTs = recUpdTs;
    }

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    public RuleInfo() {
        super();
    }

    /**
     * 规则类型，FREQ=计次/单次，ACC=数值累加(例如：累计金额)
     * @return rule_type 规则类型，FREQ=计次/单次，ACC=数值累加(例如：累计金额)
     *
     * @mbg.generated
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * 规则类型，FREQ=计次/单次，ACC=数值累加(例如：累计金额)
     * @param ruleType 规则类型，FREQ=计次/单次，ACC=数值累加(例如：累计金额)
     *
     * @mbg.generated
     */
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * 計次或計量的分組Key, 以 JsonPath語法表示，逗號區隔各Key，例如: $.user.merId,$.user.userId
     * @return group_by_keys 計次或計量的分組Key, 以 JsonPath語法表示，逗號區隔各Key，例如: $.user.merId,$.user.userId
     *
     * @mbg.generated
     */
    public String getGroupByKeys() {
        return groupByKeys;
    }

    /**
     * 計次或計量的分組Key, 以 JsonPath語法表示，逗號區隔各Key，例如: $.user.merId,$.user.userId
     * @param groupByKeys 計次或計量的分組Key, 以 JsonPath語法表示，逗號區隔各Key，例如: $.user.merId,$.user.userId
     *
     * @mbg.generated
     */
    public void setGroupByKeys(String groupByKeys) {
        this.groupByKeys = groupByKeys;
    }

    /**
     * 累计值字段名称(以 JsonPath語法表示)，用以累加数值，例如: $.params.txnAmt
     * @return acc_key 累计值字段名称(以 JsonPath語法表示)，用以累加数值，例如: $.params.txnAmt
     *
     * @mbg.generated
     */
    public String getAccKey() {
        return accKey;
    }

    /**
     * 累计值字段名称(以 JsonPath語法表示)，用以累加数值，例如: $.params.txnAmt
     * @param accKey 累计值字段名称(以 JsonPath語法表示)，用以累加数值，例如: $.params.txnAmt
     *
     * @mbg.generated
     */
    public void setAccKey(String accKey) {
        this.accKey = accKey;
    }

    /**
     * 排序
     * @return sort_order 排序
     *
     * @mbg.generated
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * 排序
     * @param sortOrder 排序
     *
     * @mbg.generated
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 规则比对方式 "AND" 或 "OR", 莫认为 "AND"
     * @return check_type 规则比对方式 "AND" 或 "OR", 莫认为 "AND"
     *
     * @mbg.generated
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * 规则比对方式 "AND" 或 "OR", 莫认为 "AND"
     * @param checkType 规则比对方式 "AND" 或 "OR", 莫认为 "AND"
     *
     * @mbg.generated
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    /**
     * 阀值(大于此值则触发)
     * @return threshold 阀值(大于此值则触发)
     *
     * @mbg.generated
     */
    public Long getThreshold() {
        return threshold;
    }

    /**
     * 阀值(大于此值则触发)
     * @param threshold 阀值(大于此值则触发)
     *
     * @mbg.generated
     */
    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    /**
     * 评估规则的时间段，单位:秒
     * @return timeout 评估规则的时间段，单位:秒
     *
     * @mbg.generated
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * 评估规则的时间段，单位:秒
     * @param timeout 评估规则的时间段，单位:秒
     *
     * @mbg.generated
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * 动作代号，用以指定符合规则后采取的行动(tbl_action)
     * @return act_id 动作代号，用以指定符合规则后采取的行动(tbl_action)
     *
     * @mbg.generated
     */
    public String getActId() {
        return actId;
    }

    /**
     * 动作代号，用以指定符合规则后采取的行动(tbl_action)
     * @param actId 动作代号，用以指定符合规则后采取的行动(tbl_action)
     *
     * @mbg.generated
     */
    public void setActId(String actId) {
        this.actId = actId;
    }

    /**
     * 默认动作参数，若动作项目未指定参数，则以此字段作为动作参数
     * @return act_param 默认动作参数，若动作项目未指定参数，则以此字段作为动作参数
     *
     * @mbg.generated
     */
    public String getActParam() {
        return actParam;
    }

    /**
     * 默认动作参数，若动作项目未指定参数，则以此字段作为动作参数
     * @param actParam 默认动作参数，若动作项目未指定参数，则以此字段作为动作参数
     *
     * @mbg.generated
     */
    public void setActParam(String actParam) {
        this.actParam = actParam;
    }

    /**
     * 0=无效，1=有效
     * @return status 0=无效，1=有效
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0=无效，1=有效
     * @param status 0=无效，1=有效
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 描述
     * @return desc 描述
     *
     * @mbg.generated
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 描述
     * @param desc 描述
     *
     * @mbg.generated
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 备注
     * @return memo 备注
     *
     * @mbg.generated
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 备注
     * @param memo 备注
     *
     * @mbg.generated
     */
    public void setMemo(String memo) {
        this.memo = memo;
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
     * Database table : tbl_rule_info
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
        RuleInfo other = (RuleInfo) that;
        return (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getRuleId() == null ? other.getRuleId() == null : this.getRuleId().equals(other.getRuleId()))
            && (this.getRuleType() == null ? other.getRuleType() == null : this.getRuleType().equals(other.getRuleType()))
            && (this.getGroupByKeys() == null ? other.getGroupByKeys() == null : this.getGroupByKeys().equals(other.getGroupByKeys()))
            && (this.getAccKey() == null ? other.getAccKey() == null : this.getAccKey().equals(other.getAccKey()))
            && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()))
            && (this.getCheckType() == null ? other.getCheckType() == null : this.getCheckType().equals(other.getCheckType()))
            && (this.getThreshold() == null ? other.getThreshold() == null : this.getThreshold().equals(other.getThreshold()))
            && (this.getTimeout() == null ? other.getTimeout() == null : this.getTimeout().equals(other.getTimeout()))
            && (this.getActId() == null ? other.getActId() == null : this.getActId().equals(other.getActId()))
            && (this.getActParam() == null ? other.getActParam() == null : this.getActParam().equals(other.getActParam()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getRecCrtTs() == null ? other.getRecCrtTs() == null : this.getRecCrtTs().equals(other.getRecCrtTs()))
            && (this.getRecUpdTs() == null ? other.getRecUpdTs() == null : this.getRecUpdTs().equals(other.getRecUpdTs()));
    }

    /**
     * Database table : tbl_rule_info
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getRuleId() == null) ? 0 : getRuleId().hashCode());
        result = prime * result + ((getRuleType() == null) ? 0 : getRuleType().hashCode());
        result = prime * result + ((getGroupByKeys() == null) ? 0 : getGroupByKeys().hashCode());
        result = prime * result + ((getAccKey() == null) ? 0 : getAccKey().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        result = prime * result + ((getCheckType() == null) ? 0 : getCheckType().hashCode());
        result = prime * result + ((getThreshold() == null) ? 0 : getThreshold().hashCode());
        result = prime * result + ((getTimeout() == null) ? 0 : getTimeout().hashCode());
        result = prime * result + ((getActId() == null) ? 0 : getActId().hashCode());
        result = prime * result + ((getActParam() == null) ? 0 : getActParam().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getRecCrtTs() == null) ? 0 : getRecCrtTs().hashCode());
        result = prime * result + ((getRecUpdTs() == null) ? 0 : getRecUpdTs().hashCode());
        return result;
    }

    /**
     * Database table : tbl_rule_info
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
        sb.append(", ruleType=").append(ruleType);
        sb.append(", groupByKeys=").append(groupByKeys);
        sb.append(", accKey=").append(accKey);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", checkType=").append(checkType);
        sb.append(", threshold=").append(threshold);
        sb.append(", timeout=").append(timeout);
        sb.append(", actId=").append(actId);
        sb.append(", actParam=").append(actParam);
        sb.append(", status=").append(status);
        sb.append(", desc=").append(desc);
        sb.append(", memo=").append(memo);
        sb.append(", recCrtTs=").append(recCrtTs);
        sb.append(", recUpdTs=").append(recUpdTs);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_rule_info
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
    public void cloneFrom(RuleInfo source) {
        super.cloneFrom(source);
        this.ruleType=source.ruleType;
        this.groupByKeys=source.groupByKeys;
        this.accKey=source.accKey;
        this.sortOrder=source.sortOrder;
        this.checkType=source.checkType;
        this.threshold=source.threshold;
        this.timeout=source.timeout;
        this.actId=source.actId;
        this.actParam=source.actParam;
        this.status=source.status;
        this.desc=source.desc;
        this.memo=source.memo;
        this.recCrtTs=source.recCrtTs;
        this.recUpdTs=source.recUpdTs;
    }
}