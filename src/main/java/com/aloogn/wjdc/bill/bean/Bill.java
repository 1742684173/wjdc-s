package com.aloogn.wjdc.bill.bean;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private Integer id;

    private Integer methodid;

    private Date dates;

    private Float sums;

    private Integer sortid;

    private String descs;

    private Integer userid;

    private Date createtime;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMethodid() {
        return methodid;
    }

    public void setMethodid(Integer methodid) {
        this.methodid = methodid;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public Float getSums() {
        return sums;
    }

    public void setSums(Float sums) {
        this.sums = sums;
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs == null ? null : descs.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

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
        Bill other = (Bill) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMethodid() == null ? other.getMethodid() == null : this.getMethodid().equals(other.getMethodid()))
            && (this.getDates() == null ? other.getDates() == null : this.getDates().equals(other.getDates()))
            && (this.getSums() == null ? other.getSums() == null : this.getSums().equals(other.getSums()))
            && (this.getSortid() == null ? other.getSortid() == null : this.getSortid().equals(other.getSortid()))
            && (this.getDescs() == null ? other.getDescs() == null : this.getDescs().equals(other.getDescs()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMethodid() == null) ? 0 : getMethodid().hashCode());
        result = prime * result + ((getDates() == null) ? 0 : getDates().hashCode());
        result = prime * result + ((getSums() == null) ? 0 : getSums().hashCode());
        result = prime * result + ((getSortid() == null) ? 0 : getSortid().hashCode());
        result = prime * result + ((getDescs() == null) ? 0 : getDescs().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}