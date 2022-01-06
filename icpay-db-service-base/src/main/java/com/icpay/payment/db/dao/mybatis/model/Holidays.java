package com.icpay.payment.db.dao.mybatis.model;

import java.io.Serializable;

/**
 * [Model class] 
 *
 * This class was generated by MyBatis Generator.
 * Database table : tbl_holidays

 *
 * @mbg.generated
 */
public class Holidays extends HolidaysKey implements Serializable {
    /**
     * 节假日，以逗号区隔，范例："01,05,06,28,29" 
     * Database column : tbl_holidays.days
     *
     * @mbg.generated
     */
    private String days;

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    public Holidays(String year, String month, String days) {
        super(year, month);
        this.days = days;
    }

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    public Holidays() {
        super();
    }

    /**
     * 节假日，以逗号区隔，范例："01,05,06,28,29" 
     * @return days 节假日，以逗号区隔，范例："01,05,06,28,29" 
     *
     * @mbg.generated
     */
    public String getDays() {
        return days;
    }

    /**
     * 节假日，以逗号区隔，范例："01,05,06,28,29" 
     * @param days 节假日，以逗号区隔，范例："01,05,06,28,29" 
     *
     * @mbg.generated
     */
    public void setDays(String days) {
        this.days = days;
    }

    /**
     * Database table : tbl_holidays
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
        Holidays other = (Holidays) that;
        return (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
            && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
            && (this.getDays() == null ? other.getDays() == null : this.getDays().equals(other.getDays()));
    }

    /**
     * Database table : tbl_holidays
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
        result = prime * result + ((getDays() == null) ? 0 : getDays().hashCode());
        return result;
    }

    /**
     * Database table : tbl_holidays
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
        sb.append(", days=").append(days);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Database table : tbl_holidays
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
    public void cloneFrom(Holidays source) {
        super.cloneFrom(source);
        this.days=source.days;
    }
}