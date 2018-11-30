package com.ruoyi.system.domain;

import java.util.Date;

public class CheckingOperation {
    private String mainid;

    private String checkingdate;

    private String checkingtype;

    private Date createdTime;

    private Date updateTime;

    private Integer cancelFlag;

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getCheckingdate() {
        return checkingdate;
    }

    public void setCheckingdate(String checkingdate) {
        this.checkingdate = checkingdate == null ? null : checkingdate.trim();
    }

    public String getCheckingtype() {
        return checkingtype;
    }

    public void setCheckingtype(String checkingtype) {
        this.checkingtype = checkingtype == null ? null : checkingtype.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(Integer cancelFlag) {
        this.cancelFlag = cancelFlag;
    }
}